// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user;

import net.labymod.api.util.io.web.request.Response;
import java.util.Collections;
import net.labymod.api.event.client.network.playerinfo.PlayerInfoRemoveEvent;
import java.util.Iterator;
import java.util.Set;
import java.util.Collection;
import java.util.HashSet;
import net.labymod.api.event.client.network.server.ServerDisconnectEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.model.entity.player.PlayerCapeRenderEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.session.SessionUpdateEvent;
import net.labymod.api.event.labymod.user.UserDiscoverEvent;
import org.jetbrains.annotations.NotNull;
import com.google.gson.JsonObject;
import net.labymod.api.Constants;
import net.labymod.api.util.io.web.request.Request;
import com.google.gson.JsonElement;
import net.labymod.api.util.io.web.request.AbstractRequest;
import javax.inject.Inject;
import net.labymod.api.event.EventBus;
import net.labymod.core.generated.DefaultReferenceStorage;
import net.labymod.core.main.user.listener.LanguageFlagVisibilityListener;
import net.labymod.core.main.user.type.GameUserItemTypeAdapter;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.lang.reflect.Type;
import net.labymod.core.main.user.type.SprayPacksTypeAdapter;
import net.labymod.core.main.user.shop.spray.model.SprayPacks;
import com.google.gson.GsonBuilder;
import net.labymod.api.Laby;
import net.labymod.core.main.LabyMod;
import net.labymod.api.LabyAPI;
import net.labymod.core.labyconnect.object.lootbox.LootBoxService;
import net.labymod.core.main.user.shop.emote.DailyEmoteService;
import net.labymod.core.main.user.whitelist.WhitelistService;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.core.main.user.shop.spray.SprayService;
import net.labymod.core.main.user.shop.item.ItemService;
import net.labymod.core.main.user.group.DefaultGroupService;
import net.labymod.api.user.GameUser;
import java.util.UUID;
import java.util.Map;
import com.google.gson.Gson;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.user.GameUserService;
import net.labymod.api.service.ChainedService;

@Singleton
@Implements(GameUserService.class)
public class DefaultGameUserService extends ChainedService implements GameUserService
{
    private final Gson userGson;
    private final Map<UUID, GameUser> users;
    private final DefaultGroupService groupService;
    private final ItemService itemService;
    private final SprayService sprayService;
    private final EmoteService emoteService;
    private final WhitelistService whitelistService;
    private final DailyEmoteService dailyEmoteService;
    private final LootBoxService lootBoxService;
    private final LabyAPI labyAPI;
    private GameUser clientUser;
    
    @Inject
    public DefaultGameUserService(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        final DefaultReferenceStorage references = LabyMod.references();
        this.groupService = this.registerService(Laby.references().groupService());
        this.whitelistService = this.registerService(new WhitelistService());
        this.itemService = this.registerService(new ItemService());
        this.emoteService = this.registerService(references.emoteService());
        this.sprayService = this.registerService(references.sprayService());
        this.lootBoxService = this.registerService(references.lootBoxService());
        this.users = new GameUserHashMap();
        this.dailyEmoteService = this.registerService(references.dailyEmoteService());
        this.userGson = new GsonBuilder().registerTypeAdapter((Type)SprayPacks.class, (Object)new SprayPacksTypeAdapter()).registerTypeAdapter(TypeToken.getParameterized((Type)List.class, new Type[] { GameUserItem.class }).getType(), (Object)new GameUserItemTypeAdapter(this.itemService)).create();
        final EventBus eventBus = this.labyAPI.eventBus();
        eventBus.registerListener(this);
        eventBus.registerListener(new LanguageFlagVisibilityListener(this));
    }
    
    public void resolveUserData(final UUID uniqueId, final boolean asynchronous) {
        try {
            final GameUser gameUser = this.gameUser(uniqueId);
            this.resolveUserData(gameUser, asynchronous);
        }
        catch (final Exception exception) {
            exception.printStackTrace();
        }
    }
    
    private void resolveUserData(final GameUser gameUser, final boolean asynchronous) {
        Request.ofGson(JsonElement.class).url(Constants.LegacyUrls.USER_DATA, new Object[] { gameUser.getUniqueId().toString() }).async(asynchronous).execute(element -> {
            if (gameUser instanceof final DefaultGameUser defaultGameUser) {
                if (element.hasException()) {
                    final int responseCode = element.exception().getResponseCode();
                    DefaultGameUserService.LOGGER.error("User data for {} could not be resolved. (Response Code: {})", gameUser.getUniqueId(), responseCode);
                }
                else {
                    defaultGameUser.updateUserData((JsonElement)element.getOrDefault(new JsonObject()));
                }
            }
        });
    }
    
    @NotNull
    @Override
    public GameUser gameUser(@NotNull final UUID uniqueId) {
        final GameUser gameUser = this.users.get(uniqueId);
        if (gameUser != null) {
            return gameUser;
        }
        final GameUser newGameUser = new DefaultGameUser(uniqueId);
        this.users.put(uniqueId, newGameUser);
        this.whitelistService.fetch(newGameUser, whitelisted -> this.labyAPI.eventBus().fire(new UserDiscoverEvent(newGameUser, whitelisted)));
        return newGameUser;
    }
    
    @NotNull
    @Override
    public GameUser clientGameUser() {
        if (this.clientUser == null) {
            this.clientUser = this.gameUser(this.labyAPI.getUniqueId());
        }
        return this.clientUser;
    }
    
    @Override
    public void onServiceUnload() {
        this.clear();
        super.onServiceUnload();
    }
    
    private void clear() {
        this.users.clear();
        this.whitelistService.reset();
        this.clientUser = null;
    }
    
    @Subscribe
    public void onSessionUpdate(final SessionUpdateEvent event) {
        if (this.clientUser != null) {
            this.clientUser.dispose();
            this.clientUser = null;
        }
        this.resolveUserData(event.newSession().getUniqueId(), true);
    }
    
    @Subscribe
    public void onUserLoad(final UserDiscoverEvent event) {
        if (event.isWhitelisted()) {
            this.resolveUserData(event.gameUser(), true);
        }
    }
    
    @Subscribe
    public void onCapeRender(final PlayerCapeRenderEvent event) {
        if (event.phase() == Phase.PRE) {
            final GameUser gameUser = event.player().gameUser();
            if (gameUser instanceof final DefaultGameUser defaultGameUser) {
                if (defaultGameUser.shouldHideCape()) {
                    event.setCancelled(true);
                }
            }
        }
    }
    
    @Subscribe
    public void onServerDisconnect(final ServerDisconnectEvent event) {
        final Set<UUID> uuids = new HashSet<UUID>(this.users.keySet());
        for (final UUID uuid : uuids) {
            final GameUser clientGameUser = this.clientGameUser();
            if (clientGameUser.getUniqueId().equals(uuid)) {
                continue;
            }
            final GameUser user = this.gameUser(uuid);
            user.dispose();
            this.users.remove(uuid);
        }
    }
    
    @Subscribe
    public void onPlayerInfoRemove(final PlayerInfoRemoveEvent event) {
        final UUID uniqueId = event.playerInfo().profile().getUniqueId();
        if (uniqueId == null) {
            return;
        }
        final GameUser clientGameUser = this.clientGameUser();
        if (clientGameUser.getUniqueId().equals(uniqueId)) {
            return;
        }
        final GameUser user = this.gameUser(uniqueId);
        user.dispose();
        this.users.remove(uniqueId);
    }
    
    public WhitelistService whitelistService() {
        return this.whitelistService;
    }
    
    public EmoteService emoteService() {
        return this.emoteService;
    }
    
    public ItemService itemService() {
        return this.itemService;
    }
    
    public DailyEmoteService dailyEmoteService() {
        return this.dailyEmoteService;
    }
    
    public Gson getUserGson() {
        return this.userGson;
    }
    
    @NotNull
    @Override
    public Map<UUID, GameUser> getGameUsers() {
        return Collections.unmodifiableMap((Map<? extends UUID, ? extends GameUser>)this.users);
    }
}
