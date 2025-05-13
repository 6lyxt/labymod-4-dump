// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.resources.texture;

import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.util.lang.Runnables;
import org.jetbrains.annotations.NotNull;
import net.labymod.core.event.client.resources.ReleaseTextureEventCaller;
import java.util.Objects;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import net.labymod.core.client.resources.PathResourceLocation;
import net.labymod.api.util.ThreadSafe;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.resources.texture.Texture;
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
    private ekd textureManager;
    
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
    public void register(final ResourceLocation location, final Texture texture, @Nullable final Runnable finishHandler) {
        final ejq minecraftTexture = this.convertToVersionedTexture(texture);
        if (ThreadSafe.isRenderThread()) {
            this.registerTexture(location, minecraftTexture, finishHandler);
        }
        else {
            ThreadSafe.executeOnRenderThread(() -> this.registerTexture(location, minecraftTexture, finishHandler));
        }
    }
    
    @Override
    public void onTick() {
        super.onTick();
        for (ResourceLocation resourceLocation : this.labyAPI.renderPipeline().resources().resourceLocationFactory().getCachedResourceLocations().values()) {
            if (resourceLocation instanceof final PathResourceLocation path) {
                final ekd textureManager = this.getTextureManager();
                final vk minecraftLocation = resourceLocation.getMinecraftLocation();
                final boolean hasResource = ((GameTextureManager)textureManager).hasResource(resourceLocation.getMinecraftLocation());
                if (!hasResource || !path.isModified()) {
                    continue;
                }
                textureManager.c(minecraftLocation);
                textureManager.a(minecraftLocation);
                VersionedTextureRepository.LOGGER.info("Texture " + String.valueOf(minecraftLocation) + " has been reloaded", new Object[0]);
            }
        }
    }
    
    @Override
    public void registerTexture(final ResourceLocation location, final Object texture, final Runnable finishHandler) {
        if (!(texture instanceof ejq)) {
            throw new IllegalStateException(texture.getClass().getName() + " is not a subtype of " + ejq.class.getName());
        }
        final ejq minecraftTexture = (ejq)texture;
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            this.registerTexture(location, minecraftTexture);
            if (finishHandler != null) {
                finishHandler.run();
            }
        });
    }
    
    @Override
    public void preloadTexture(final ResourceLocation location) {
        if (RenderSystem.isOnRenderThread()) {
            this.registerTexture(location, (ejq)new PreloadedTexture(location.getMinecraftLocation()));
        }
        else {
            ThreadSafe.executeOnRenderThread(() -> this.registerTexture(location, (ejq)new PreloadedTexture(location.getMinecraftLocation())));
        }
    }
    
    @Override
    public void registerAndRelease(final ResourceLocation location, final Texture texture, final Runnable finishHandler) {
        if (djz.C().bh()) {
            this._registerAndRelease(location, texture, finishHandler);
        }
        else {
            djz.C().execute(() -> this._registerAndRelease(location, texture, finishHandler));
        }
    }
    
    private void _registerAndRelease(final ResourceLocation location, final Object texture, final Runnable finishHandler) {
        ((GameTextureManager)this.getTextureManager()).registerAndRelease(location, texture);
        if (finishHandler != null) {
            finishHandler.run();
        }
    }
    
    @Override
    public void releaseTexture(final ResourceLocation location, final Runnable releaseHandler) {
        if (RenderSystem.isOnRenderThreadOrInit()) {
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
        final ejq texture = this.getTextureManager().b((vk)location.getMinecraftLocation());
        if (texture instanceof final VersionedLabyTexture labyTexture) {
            return labyTexture.getDelegate();
        }
        if (!(texture instanceof Texture)) {
            final ejq obj = texture;
            Objects.requireNonNull(obj);
            return obj::b;
        }
        return (Texture)texture;
    }
    
    private void release(final ResourceLocation location, final Runnable releaseHandler) {
        this.getTextureManager().c((vk)location.getMinecraftLocation());
        this.releaseRemoteTexture(location);
        ReleaseTextureEventCaller.call(location);
        if (releaseHandler != null) {
            releaseHandler.run();
        }
    }
    
    private void registerTexture(@NotNull final ResourceLocation location, final ejq texture) {
        this.getTextureManager().a((vk)location.getMinecraftLocation(), texture);
    }
    
    private void registerTexture(@NotNull final ResourceLocation location, final ejq texture, final Runnable finishHandler) {
        this.registerTexture(location, texture);
        Runnables.runIfNotNull(finishHandler);
    }
    
    private ekd getTextureManager() {
        if (this.textureManager == null) {
            this.textureManager = djz.C().M();
        }
        return this.textureManager;
    }
    
    private ejq convertToVersionedTexture(final Texture texture) {
        if (texture instanceof final LabyTexture labyTexture) {
            return (ejq)new VersionedLabyTexture(labyTexture);
        }
        if (texture instanceof final ejq abstractTexture) {
            return abstractTexture;
        }
        throw this.createSubtypeException(texture, ejq.class);
    }
}
