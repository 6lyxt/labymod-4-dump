// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.resources.texture;

import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.util.lang.Runnables;
import org.jetbrains.annotations.NotNull;
import net.labymod.core.event.client.resources.ReleaseTextureEventCaller;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.util.ThreadSafe;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.texture.Texture;
import java.util.Iterator;
import net.labymod.core.client.resources.PathResourceLocation;
import net.labymod.core.client.resources.texture.GameTextureManager;
import net.labymod.api.client.resources.ResourceLocation;
import javax.inject.Inject;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.GameImageTexture;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.core.client.resources.texture.DefaultAbstractTextureRepository;

@Singleton
@Implements(TextureRepository.class)
public class VersionedTextureRepository extends DefaultAbstractTextureRepository implements TextureRepository
{
    private hks textureManager;
    
    @Inject
    public VersionedTextureRepository(final LabyAPI labyAPI, final GameImageTexture.Factory gameImageTextureFactory, final GameImageProvider gameImageProvider) {
        super(labyAPI, gameImageTextureFactory, gameImageProvider);
    }
    
    @Override
    public boolean hasResource(ResourceLocation location) {
        location = this.unwrap(location);
        return ((GameTextureManager)this.getTextureManager()).hasResource(location) || super.hasResource(location);
    }
    
    @Override
    public void onTick() {
        super.onTick();
        for (ResourceLocation resourceLocation : this.labyAPI.renderPipeline().resources().resourceLocationFactory().getCachedResourceLocations().values()) {
            if (resourceLocation instanceof final PathResourceLocation path) {
                final hks textureManager = this.getTextureManager();
                final alr minecraftLocation = resourceLocation.getMinecraftLocation();
                final boolean hasResource = ((GameTextureManager)textureManager).hasResource(resourceLocation.getMinecraftLocation());
                if (!hasResource || !path.isModified()) {
                    continue;
                }
                textureManager.c(minecraftLocation);
                final hkb texture = textureManager.b(minecraftLocation);
                VersionedTextureRepository.LOGGER.info("Texture " + String.valueOf(minecraftLocation) + " has been reloaded", new Object[0]);
            }
        }
    }
    
    @Override
    public void register(final ResourceLocation location, final Texture texture, @Nullable final Runnable finishHandler) {
        final hkb minecraftTexture = this.convertToVersionedTexture(texture);
        if (ThreadSafe.isRenderThread()) {
            this.registerTexture(location, minecraftTexture, finishHandler);
        }
        else {
            ThreadSafe.executeOnRenderThread(() -> this.registerTexture(location, minecraftTexture, finishHandler));
        }
    }
    
    @Override
    public void registerTexture(final ResourceLocation location, final Object texture, final Runnable finishHandler) {
        if (texture instanceof final hkb minecraftTexture) {
            this.labyAPI.minecraft().executeOnRenderThread(() -> {
                this.registerTexture(location, minecraftTexture);
                if (finishHandler != null) {
                    finishHandler.run();
                }
            });
            return;
        }
        throw new IllegalStateException(texture.getClass().getName() + " is not a subtype of " + hkb.class.getName());
    }
    
    @Override
    public void preloadTexture(final ResourceLocation location) {
        this.registerTexture(location, (hkb)new hki((alr)location.getMinecraftLocation()));
    }
    
    @Override
    public void releaseTexture(final ResourceLocation location, final Runnable releaseHandler) {
        if (RenderSystem.isOnRenderThread()) {
            this.release(location, releaseHandler);
        }
        else {
            this.labyAPI.minecraft().executeOnRenderThread(() -> this.release(location, releaseHandler));
        }
    }
    
    @Override
    public Texture getTexture(final ResourceLocation location) {
        if (!this.hasResource(location)) {
            return null;
        }
        final hkb texture = this.getTextureManager().b((alr)location.getMinecraftLocation());
        if (texture instanceof final VersionedLabyTexture labyTexture) {
            return labyTexture.getDelegate();
        }
        if (texture instanceof final Texture labyTexture2) {
            return labyTexture2;
        }
        return () -> MinecraftUtil.getGpuTextureId(texture.a());
    }
    
    @Override
    public void registerAndRelease(final ResourceLocation location, final Texture texture, final Runnable finishHandler) {
        if (ThreadSafe.isRenderThread()) {
            this._registerAndRelease(location, texture, finishHandler);
        }
        else {
            ThreadSafe.executeOnRenderThread(() -> this._registerAndRelease(location, texture, finishHandler));
        }
    }
    
    private void _registerAndRelease(final ResourceLocation location, final Object texture, final Runnable finishHandler) {
        ((GameTextureManager)this.getTextureManager()).registerAndRelease(location, texture);
        if (finishHandler != null) {
            finishHandler.run();
        }
    }
    
    private void release(final ResourceLocation location, final Runnable releaseHandler) {
        this.getTextureManager().c((alr)location.getMinecraftLocation());
        this.releaseRemoteTexture(location);
        ReleaseTextureEventCaller.call(location);
        if (releaseHandler != null) {
            releaseHandler.run();
        }
    }
    
    private void registerTexture(@NotNull final ResourceLocation location, final hkb texture) {
        final hks textureManager = this.getTextureManager();
        if (texture instanceof final hkh reloadableTexture) {
            textureManager.a((alr)location.getMinecraftLocation(), reloadableTexture);
        }
        else {
            textureManager.a((alr)location.getMinecraftLocation(), texture);
        }
    }
    
    private void registerTexture(@NotNull final ResourceLocation location, final hkb texture, final Runnable finishHandler) {
        this.registerTexture(location, texture);
        Runnables.runIfNotNull(finishHandler);
    }
    
    private hks getTextureManager() {
        if (this.textureManager == null) {
            this.textureManager = fqq.Q().aa();
        }
        return this.textureManager;
    }
    
    private hkb convertToVersionedTexture(final Texture texture) {
        if (texture instanceof final LabyTexture labyTexture) {
            return (hkb)new VersionedLabyTexture(labyTexture);
        }
        if (texture instanceof final hkb abstractTexture) {
            return abstractTexture;
        }
        throw this.createSubtypeException(texture, hkb.class);
    }
}
