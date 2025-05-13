// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user;

import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.api.user.shop.CloakPriority;
import net.labymod.api.user.group.Group;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.core.main.user.shop.item.AbstractItem;
import java.util.Iterator;
import net.labymod.core.main.user.shop.item.model.type.TextureType;
import net.labymod.core.main.user.shop.item.texture.ItemTexture;
import net.labymod.core.main.user.group.GroupIdentifier;
import net.labymod.core.main.debug.InvalidCosmeticDataDebugger;
import net.labymod.api.event.Phase;
import net.labymod.api.event.labymod.user.UserUpdateDataEvent;
import net.labymod.api.user.GameUserService;
import net.labymod.api.util.ThreadSafe;
import com.google.gson.JsonElement;
import net.labymod.api.Laby;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.CountryCode;
import net.labymod.core.main.user.util.SprayCooldownTracker;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.labymod.core.main.user.group.GroupHolder;
import net.labymod.api.tag.TaggedObject;
import java.util.UUID;
import net.labymod.api.LabyAPI;
import net.labymod.api.user.GameUser;

public class DefaultGameUser implements GameUser
{
    private final LabyAPI labyAPI;
    private final UUID uniqueId;
    private final GameUserData userData;
    private final GameUserItemStorage userItemStorage;
    private final TaggedObject taggedObject;
    private boolean disposed;
    private WhitelistState whitelistState;
    private final GroupHolder groupHolder;
    private final CompletableFuture<GameUser> dataLoadedFuture;
    private final List<Runnable> disposeListeners;
    private final SprayCooldownTracker sprayCooldownTracker;
    @Nullable
    private CountryCode countryCode;
    
    public DefaultGameUser(@NotNull final UUID uniqueId) {
        this.taggedObject = new TaggedObject();
        this.whitelistState = WhitelistState.UNKNOWN;
        this.groupHolder = new GroupHolder();
        this.dataLoadedFuture = new CompletableFuture<GameUser>();
        this.disposeListeners = new ArrayList<Runnable>();
        this.sprayCooldownTracker = new SprayCooldownTracker(this);
        this.labyAPI = Laby.labyAPI();
        this.uniqueId = uniqueId;
        this.userData = new GameUserData();
        this.userItemStorage = new GameUserItemStorage();
    }
    
    public void updateUserData(final JsonElement element) {
        final GameUserService gameUserService = Laby.references().gameUserService();
        if (gameUserService instanceof final DefaultGameUserService service) {
            service.executeServiceTask(() -> ThreadSafe.executeOnRenderThread(() -> this.updateUserData(service, element)));
        }
    }
    
    private void updateUserData(final DefaultGameUserService service, final JsonElement element) {
        if (Laby.fireEvent(new UserUpdateDataEvent(Phase.PRE, this)).isCancelled()) {
            return;
        }
        this.dispose();
        this.disposed = false;
        this.userData.setData((GameUserData)service.getUserGson().fromJson(element, (Class)GameUserData.class), item -> {
            final ItemTexture texture = this.userItemStorage.getTexture(item);
            if (texture == null) {
                return;
            }
            else {
                texture.invalidate(false);
                return;
            }
        });
        this.invalidateUserBoundTextures();
        InvalidCosmeticDataDebugger.start(this);
        this.userItemStorage.prepare(this, this.userData);
        final List<GroupIdentifier> groups = this.userData.getGroups();
        if (!groups.isEmpty()) {
            final int id = groups.getFirst().getIdentifier();
            this.setVisibleGroup(id);
        }
        InvalidCosmeticDataDebugger.end();
        this.labyAPI.eventBus().fire(new UserUpdateDataEvent(Phase.POST, this));
        if (!this.dataLoadedFuture.isDone()) {
            this.dataLoadedFuture.complete(this);
        }
    }
    
    private void releaseUserBoundTextures() {
        for (final GameUserItem userItem : this.userData.getItems()) {
            final AbstractItem item = userItem.item();
            final ItemDetails details = item.itemDetails();
            if (details.getTextureType() != TextureType.USER_BOUND) {
                continue;
            }
            final TextureRepository textureRepository = Laby.references().textureRepository();
            textureRepository.invalidateRemoteTextures(url -> {
                final boolean invalidate = url.startsWith("https://dl.labymod.net/") && url.contains(this.getUniqueId().toString());
                if (invalidate) {
                    final ItemTexture texture = this.getUserItemStorage().getTexture(item);
                    if (texture != null) {
                        texture.invalidate(true);
                    }
                }
                return invalidate;
            });
        }
    }
    
    private void invalidateUserBoundTextures() {
        for (final GameUserItem userItem : this.userData.getItems()) {
            final AbstractItem item = userItem.item();
            final ItemDetails details = item.itemDetails();
            final ItemTexture texture = this.getUserItemStorage().getTexture(item);
            if (details.getTextureType() == TextureType.USER_BOUND) {
                if (texture == null) {
                    continue;
                }
                texture.invalidate(true);
            }
        }
    }
    
    public void userDataFailed(final Exception exception) {
        if (!this.dataLoadedFuture.isDone()) {
            this.dataLoadedFuture.completeExceptionally(exception);
        }
    }
    
    @Override
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    @Override
    public boolean isUsingLabyMod() {
        return this.hasTag(DefaultGameUser.FAMILIAR);
    }
    
    @Override
    public String toString() {
        return this.uniqueId.toString();
    }
    
    public GameUserData getUserData() {
        return this.userData;
    }
    
    public GameUserItemStorage getUserItemStorage() {
        return this.userItemStorage;
    }
    
    @Override
    public WhitelistState whitelistState() {
        return this.whitelistState;
    }
    
    public void setWhitelistState(final WhitelistState whitelistState) {
        this.whitelistState = whitelistState;
    }
    
    @NotNull
    @Override
    public TextColor displayColor() {
        return this.visibleGroup().getTextColor();
    }
    
    @Override
    public CompletableFuture<GameUser> ensureDataAvailable() {
        return this.dataLoadedFuture;
    }
    
    public void setCountryCode(@Nullable final CountryCode countryCode) {
        this.countryCode = countryCode;
    }
    
    @Nullable
    @Override
    public CountryCode getCountryCode() {
        return this.countryCode;
    }
    
    @NotNull
    @Override
    public Group visibleGroup() {
        return this.groupHolder.visibleGroup();
    }
    
    public void setVisibleGroup(final int identifier) {
        this.groupHolder.setId(identifier);
        if (this.groupHolder.isLegacy()) {
            this.setTag(DefaultGameUser.LEGACY);
        }
    }
    
    @Override
    public boolean isLegacy() {
        return this.hasTag(DefaultGameUser.LEGACY);
    }
    
    public boolean shouldHideCape() {
        final CosmeticsConfig cosmeticConfig = Laby.labyAPI().config().ingame().cosmetics();
        final CloakPriority cloakPriority = cosmeticConfig.cloakPriority().get();
        return cloakPriority == CloakPriority.LABYMOD && this.hasTag(DefaultGameUser.HIDE_CAPE) && cosmeticConfig.renderCosmetics().get();
    }
    
    @Override
    public int hashCode() {
        return this.uniqueId.hashCode();
    }
    
    @Override
    public void addDisposeListener(final Runnable listener) {
        this.disposeListeners.add(listener);
    }
    
    @Override
    public boolean isDisposed() {
        return this.disposed;
    }
    
    @Override
    public void dispose() {
        for (final Runnable listener : this.disposeListeners) {
            listener.run();
        }
        this.disposeListeners.clear();
        this.disposed = true;
        if (ThreadSafe.isRenderThread()) {
            this.releaseUserBoundTextures();
        }
        else {
            ThreadSafe.executeOnRenderThread(this::releaseUserBoundTextures);
        }
    }
    
    @Override
    public TaggedObject taggedObject() {
        return this.taggedObject;
    }
    
    public SprayCooldownTracker sprayCooldownTracker() {
        return this.sprayCooldownTracker;
    }
    
    public GroupHolder groupHolder() {
        return this.groupHolder;
    }
}
