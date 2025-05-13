// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.texture;

import java.io.InputStream;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.request.AbstractRequest;
import java.net.URL;
import net.labymod.core.main.user.shop.item.model.type.TextureType;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.core.main.user.shop.item.geometry.DepthMap;
import net.labymod.api.client.resources.texture.GameImageTexture;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.resources.Resources;
import net.labymod.core.main.user.shop.item.ItemDetails;
import java.io.IOException;
import net.labymod.api.Constants;
import java.util.Locale;
import java.util.function.Function;
import net.labymod.api.client.resources.CompletableResourceLocation;
import java.util.UUID;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.user.GameUser;
import net.labymod.core.main.user.shop.item.model.TextureDetails;
import java.util.function.Consumer;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import net.labymod.core.main.user.shop.item.texture.listener.ItemTextureListener;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.core.main.user.shop.item.AbstractItem;

public class ItemTexture
{
    private final AbstractItem item;
    private final TextureRepository textureRepository;
    private ItemTextureListener listener;
    private AnimatedResourceLocation animatedResourceLocation;
    private boolean fakeResolved;
    private boolean resolved;
    private ResourceLocation textureLocation;
    private ResourceLocation previousTextureLocation;
    
    private ItemTexture(final AbstractItem item) {
        this.listener = ItemTextureListener.NOP_LISTENER;
        this.previousTextureLocation = Textures.EMPTY;
        this.item = item;
        this.textureRepository = Laby.references().textureRepository();
    }
    
    private ItemTexture(final ItemTexture other) {
        this.listener = ItemTextureListener.NOP_LISTENER;
        this.previousTextureLocation = Textures.EMPTY;
        this.item = other.item;
        this.textureRepository = other.textureRepository;
        this.animatedResourceLocation = other.animatedResourceLocation;
        this.fakeResolved = other.fakeResolved;
        this.resolved = other.resolved;
        this.textureLocation = other.textureLocation;
        this.previousTextureLocation = other.previousTextureLocation;
    }
    
    public static ItemTexture create(final AbstractItem item) {
        return new ItemTexture(item);
    }
    
    public static ItemTexture copyOf(final ItemTexture other) {
        return new ItemTexture(other);
    }
    
    public void invalidate() {
        this.invalidate(false);
    }
    
    public void invalidate(final boolean useFallback) {
        this.fakeResolved = false;
        this.resolved = false;
        this.previousTextureLocation = (useFallback ? Textures.WHITE : this.textureLocation);
    }
    
    public ResourceLocation getOrResolveTexture(final Player player, final ItemMetadata data, final TextureReleaseFunction releaseFunction) {
        final boolean released = releaseFunction.released(this.resolved ? this.textureLocation : this.previousTextureLocation);
        if (released) {
            this.fakeResolved = false;
        }
        if (!this.fakeResolved) {
            this.fakeResolved = true;
            this.loadTexture(player, data, this::setTextureLocation);
            return this.previousTextureLocation;
        }
        if (this.item.isShopItem()) {
            return this.textureLocation;
        }
        return this.resolved ? this.textureLocation : this.previousTextureLocation;
    }
    
    private void loadTexture(final Player player, final ItemMetadata data, final Consumer<ResourceLocation> callback) {
        this.listener.onBegin();
        final TextureDetails textureDetails = data.getTextureDetails();
        if ((textureDetails != null && textureDetails.getType() == TextureDetails.Type.SKIN) || data.isMojangBound()) {
            callback.accept(this.getMojangBoundTexture(player, textureDetails));
            return;
        }
        this.item.getItemExecutorService().submit(() -> {
            try {
                callback.accept(this.getItemTexture(data, player));
            }
            catch (final Exception ignored) {
                final GameUser clientUser = Laby.references().gameUserService().clientGameUser();
                if (clientUser.visibleGroup().isStaffOrCosmeticCreator()) {
                    callback.accept(Textures.WHITE);
                }
                this.listener.onError();
            }
        });
    }
    
    public ResourceLocation getMojangBoundTexture(final Player player, @Nullable final TextureDetails textureDetails) {
        final UUID textureUuid = (textureDetails == null) ? null : textureDetails.getUuid();
        if (textureUuid == null) {
            return player.skinTexture();
        }
        final CompletableResourceLocation skinTexture = LabyMod.references().mojangTextureService().getTexture(textureUuid, MojangTextureType.SKIN);
        this.setupCompletableResourceLocation(skinTexture);
        return skinTexture.getCompleted();
    }
    
    public ResourceLocation getItemTexture(final ItemMetadata itemMetadata, final Player player) {
        final ItemDetails details = itemMetadata.getDetails();
        final Ratio ratio = details.getRatio();
        final String textureDirectory = details.getTextureDirectory();
        final UUID textureUniqueId = itemMetadata.isUserBound() ? player.getUniqueId() : itemMetadata.texture().map((Function<? super TextureDetails, ? extends UUID>)TextureDetails::getUuid).orElse(null);
        final String textureDirectoryPath = (textureDirectory == null) ? String.valueOf(details.getIdentifier()) : textureDirectory;
        String url = (textureDirectory == null) ? String.format(Locale.ROOT, Constants.LegacyUrls.REMOTE_COSMETICS_TEXTURE, details.getIdentifier(), textureUniqueId.toString()) : String.format(Locale.ROOT, Constants.LegacyUrls.CUSTOM_TEXTURES, textureDirectoryPath, (textureUniqueId == null) ? "cosmetic" : textureUniqueId.toString());
        if (this.item.isShopItem() && itemMetadata.isUserBound()) {
            url = String.format(Locale.ROOT, Constants.LegacyUrls.DEFAULT_REMOTE_COSMETICS_TEXTURE, this.item.getIdentifier());
        }
        final Resources resources = LabyMod.getInstance().renderPipeline().resources();
        final String remotePath = String.format(Locale.ROOT, (this.item.isShopItem() ? "shop_" : "") + "remote/%s/%s", textureDirectoryPath, textureUniqueId);
        ResourceLocation resourceLocation = ResourceLocation.create("labymod", remotePath + ".png");
        resourceLocation.metadata().set("asynchronous", true);
        if (ratio == null) {
            final GameUser clientUser = Laby.references().gameUserService().clientGameUser();
            final CompletableResourceLocation completableLocation = this.getTexture(url, resourceLocation, clientUser, itemMetadata);
            this.setupCompletableResourceLocation(completableLocation);
            resourceLocation = completableLocation.getCompleted();
        }
        else {
            try {
                resourceLocation = this.getAnimatedTexture(resources, remotePath, ratio, url);
            }
            catch (final IOException exception) {
                exception.printStackTrace();
            }
        }
        return resourceLocation;
    }
    
    private CompletableResourceLocation getTexture(final String url, final ResourceLocation resourceLocation, final GameUser clientUser, final ItemMetadata metadata) {
        return this.textureRepository.getOrRegisterTexture(resourceLocation, clientUser.visibleGroup().isStaffOrCosmeticCreator() ? Textures.WHITE : Textures.EMPTY, url, texture -> this.createDepthMap(texture, metadata), this::setupCompletableResourceLocation);
    }
    
    private void createDepthMap(final Texture texture, final ItemMetadata metadata) {
        if (texture instanceof final GameImageTexture gameImageTexture) {
            final GameImage gameImage = gameImageTexture.getImage();
            if (gameImage != null) {
                if (gameImage.getWidth() <= 1 || gameImage.getHeight() <= 1) {
                    return;
                }
                metadata.setDepthMap(new DepthMap(gameImage));
            }
        }
    }
    
    private void invalidatePreviousTexture() {
        final ItemDetails data = this.item.itemDetails();
        if (data.getTextureType() == TextureType.MOJANG_BOUND) {
            this.previousTextureLocation = null;
            this.resolved = true;
            return;
        }
        if (this.previousTextureLocation != null) {
            Laby.references().textureRepository().queueTextureRelease(this.previousTextureLocation);
            this.previousTextureLocation = null;
        }
        this.resolved = true;
    }
    
    public void update() {
        if (this.animatedResourceLocation == null) {
            return;
        }
        this.animatedResourceLocation.update();
        this.setTextureLocation(this.animatedResourceLocation.getCurrentResourceLocation());
    }
    
    public void setItemTextureListener(@Nullable ItemTextureListener listener) {
        if (listener == null) {
            listener = ItemTextureListener.NOP_LISTENER;
        }
        this.listener = listener;
    }
    
    private void setupCompletableResourceLocation(final CompletableResourceLocation location) {
        location.addCompletableListener(() -> {
            if (location.hasError()) {
                this.listener.onError();
            }
            this.setTextureLocation(location.getCompleted());
            this.invalidatePreviousTexture();
            return;
        });
        location.addCompletableListener(() -> {
            if (!location.hasError()) {
                this.listener.onSuccess();
            }
        });
    }
    
    private ResourceLocation getAnimatedTexture(final Resources resources, final String remotePath, final Ratio ratio, final String url) throws IOException {
        final InputStream spriteImageStream = new URL(url).openStream();
        final TextureMeta textureMeta = (TextureMeta)Request.ofGson(TextureMeta.class).url(url + ".json", new Object[0]).async(false).executeSync().get();
        this.animatedResourceLocation = resources.resourceLocationFactory().createAnimated("labymod", remotePath, spriteImageStream, ratio.getWidth(), ratio.getHeight(), textureMeta.getDelay(), () -> this.listener.onSuccess());
        this.resolved = true;
        return this.animatedResourceLocation.getCurrentResourceLocation();
    }
    
    private void setTextureLocation(final ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }
    
    public interface TextureReleaseFunction
    {
        boolean released(final ResourceLocation p0);
    }
}
