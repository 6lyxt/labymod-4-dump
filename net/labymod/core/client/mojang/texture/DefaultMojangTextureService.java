// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.mojang.texture;

import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.client.resources.texture.Texture;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.network.ClientPacketListener;
import net.labymod.api.client.network.PlayerSkin;
import net.labymod.api.client.resources.texture.GameImageTexture;
import java.util.function.Consumer;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.mojang.texture.SkinPolicy;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.util.UUIDHelper;
import net.labymod.api.client.resources.CompletableResourceLocation;
import java.util.UUID;
import java.util.HashMap;
import net.labymod.api.LabyAPI;
import net.labymod.api.mojang.texture.MojangTextureType;
import java.util.Map;
import net.labymod.api.mojang.texture.MojangTextureService;

public abstract class DefaultMojangTextureService implements MojangTextureService
{
    private final Map<MojangTextureType, TextureCache> textureCache;
    protected final LabyAPI labyAPI;
    
    public DefaultMojangTextureService(final LabyAPI labyAPI) {
        this.labyAPI = labyAPI;
        this.textureCache = new HashMap<MojangTextureType, TextureCache>();
        for (final MojangTextureType type : MojangTextureType.VALUES) {
            this.textureCache.put(type, new TextureCache(type, this::getDefaultTexture));
        }
    }
    
    @Override
    public CompletableResourceLocation getTexture(final UUID profileId, final MojangTextureType type) {
        final CompletableResourceLocation location = this.obtainLocationFromPlayerInfo(profileId, type);
        return (location == null) ? this.getOrLoad(profileId, type) : location;
    }
    
    @Override
    public CompletableResourceLocation getTexture(final String name, final MojangTextureType type) {
        final UUID uuid = UUIDHelper.createUniqueId(name);
        final ResourceLocation defaultTexture = this.getDefaultTexture(uuid, type);
        final CompletableResourceLocation completable = new CompletableResourceLocation(defaultTexture);
        final LabyNetController controller = Laby.references().labyNetController();
        controller.loadUniqueIdByName(name, result -> {
            if (result.isPresent()) {
                final UUID uuid2 = (UUID)result.get();
                Objects.requireNonNull(completable);
                this.getTexture(uuid2, type, completable::executeCompletableListeners);
            }
            else {
                completable.stopLoading();
            }
            return;
        });
        return completable;
    }
    
    @Override
    public MinecraftServices.SkinVariant getVariant(final ResourceLocation location) {
        return MinecraftServices.SkinVariant.of(location.metadata().get("variant", MinecraftServices.SkinVariant.CLASSIC.getId()));
    }
    
    @Override
    public final void applySkinTexture(final UUID profileId, final MinecraftServices.SkinVariant variant, final String url) {
        this.applyTexture(profileId, MojangTextureType.SKIN, url, texture -> {
            final NetworkPlayerInfo playerInfo = this.getPlayerInfo(profileId);
            if (playerInfo != null) {
                playerInfo.getSkin().setSkinVariant(variant);
            }
        });
    }
    
    @Override
    public final void applyTexture(final UUID profileId, final MojangTextureType type, final String url) {
        this.applyTexture(profileId, type, url, texture -> {
            if (type == MojangTextureType.SKIN) {
                final NetworkPlayerInfo playerInfo = this.getPlayerInfo(profileId);
                if (playerInfo != null) {
                    final GameImage image = texture.getImage();
                    final MinecraftServices.SkinVariant variant = SkinPolicy.guessVariant(image);
                    playerInfo.getSkin().setSkinVariant(variant);
                }
            }
        });
    }
    
    private void applyTexture(final UUID profileId, final MojangTextureType type, final String url, final Consumer<GameImageTexture> callback) {
        final NetworkPlayerInfo playerInfo = this.getPlayerInfo(profileId);
        if (playerInfo == null) {
            return;
        }
        final PlayerSkin skin = playerInfo.getSkin();
        skin.setTexture(type, null);
        final TextureCache cache = this.textureCache.get(type);
        cache.refreshTexture(profileId, url, texture -> {
            if (texture != null) {
                final GameImageTexture gameImageTexture = (GameImageTexture)texture;
                final ResourceLocation location = gameImageTexture.getTextureResourceLocation();
                skin.setTexture(type, location);
                callback.accept(gameImageTexture);
            }
        });
    }
    
    @Nullable
    private NetworkPlayerInfo getPlayerInfo(final UUID profileId) {
        final ClientPacketListener packetListener = this.labyAPI.minecraft().getClientPacketListener();
        return (packetListener == null) ? null : packetListener.getNetworkPlayerInfo(profileId);
    }
    
    @Nullable
    private CompletableResourceLocation obtainLocationFromPlayerInfo(final UUID profileId, final MojangTextureType type) {
        final NetworkPlayerInfo playerInfo = this.getPlayerInfo(profileId);
        if (playerInfo == null) {
            return null;
        }
        final PlayerSkin skin = playerInfo.getSkin();
        final CompletableResourceLocation completable = skin.getCompletableTexture(type);
        completable.addCompletableListener(() -> completable.getCompleted().metadata().set("variant", skin.getSkinVariant().getId()));
        return completable;
    }
    
    private CompletableResourceLocation getOrLoad(final UUID profileId, final MojangTextureType type) {
        final TextureCache cache = this.textureCache.get(type);
        return cache.getOrLoad(profileId, texture -> {});
    }
}
