// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.resources.texture;

import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.util.lang.Runnables;
import org.jetbrains.annotations.NotNull;
import net.labymod.core.event.client.resources.ReleaseTextureEventCaller;
import java.util.Objects;
import net.labymod.api.client.Minecraft;
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
    private cdr textureManager;
    
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
        final cdf minecraftTexture = this.convertToVersionedTexture(texture);
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
                final cdr textureManager = this.getTextureManager();
                final nf minecraftLocation = resourceLocation.getMinecraftLocation();
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
        if (!(texture instanceof cds)) {
            throw new IllegalStateException(texture.getClass().getName() + " is not a subtype of " + cds.class.getName());
        }
        final cds textureObject = (cds)texture;
        this.labyAPI.minecraft().executeOnRenderThread(() -> {
            this.registerTexture(location, textureObject);
            if (finishHandler != null) {
                finishHandler.run();
            }
        });
    }
    
    @Override
    public void preloadTexture(final ResourceLocation location) {
        final Minecraft minecraft = this.labyAPI.minecraft();
        if (minecraft.isOnRenderThread()) {
            this.registerTexture(location, (cds)new cdm((nf)location.getMinecraftLocation()));
        }
        else {
            minecraft.executeOnRenderThread(() -> this.registerTexture(location, (cds)new cdm((nf)location.getMinecraftLocation())));
        }
    }
    
    @Override
    public void releaseTexture(final ResourceLocation location, final Runnable releaseHandler) {
        if (bib.z().aF()) {
            this.release(location, releaseHandler);
        }
        else {
            bib.z().a(() -> this.release(location, releaseHandler));
        }
    }
    
    @Override
    public Texture getTexture(final ResourceLocation location) {
        if (!this.hasResource(location)) {
            return null;
        }
        final cds texture = this.getTextureManager().b((nf)location.getMinecraftLocation());
        if (texture instanceof final VersionedLabyTexture labyTexture) {
            return labyTexture.getDelegate();
        }
        if (!(texture instanceof Texture)) {
            final cds obj = texture;
            Objects.requireNonNull(obj);
            return obj::b;
        }
        return (Texture)texture;
    }
    
    @Override
    public void registerAndRelease(final ResourceLocation location, final Texture texture, final Runnable finishHandler) {
        if (bib.z().aF()) {
            this._registerAndRelease(location, texture, finishHandler);
        }
        else {
            bib.z().a(() -> this._registerAndRelease(location, texture, finishHandler));
        }
    }
    
    private void _registerAndRelease(final ResourceLocation location, final Object texture, final Runnable finishHandler) {
        ((GameTextureManager)this.getTextureManager()).registerAndRelease(location, texture);
        if (finishHandler != null) {
            finishHandler.run();
        }
    }
    
    private void release(final ResourceLocation location, final Runnable releaseHandler) {
        this.getTextureManager().c((nf)location.getMinecraftLocation());
        this.releaseRemoteTexture(location);
        ReleaseTextureEventCaller.call(location);
        if (releaseHandler != null) {
            releaseHandler.run();
        }
    }
    
    private void registerTexture(@NotNull final ResourceLocation location, final cds texture) {
        this.getTextureManager().a((nf)location.getMinecraftLocation(), texture);
    }
    
    private void registerTexture(@NotNull final ResourceLocation location, final cdf texture, final Runnable finishHandler) {
        this.registerTexture(location, (cds)texture);
        Runnables.runIfNotNull(finishHandler);
    }
    
    private cdr getTextureManager() {
        if (this.textureManager == null) {
            this.textureManager = bib.z().N();
        }
        return this.textureManager;
    }
    
    private cdf convertToVersionedTexture(final Texture texture) {
        if (texture instanceof final LabyTexture labyTexture) {
            return (cdf)new VersionedLabyTexture(labyTexture);
        }
        if (texture instanceof final cdf abstractTexture) {
            return abstractTexture;
        }
        throw this.createSubtypeException(texture, cdf.class);
    }
}
