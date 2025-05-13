// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture.concurrent;

import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.resources.texture.TextureAllocator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.texture.TextureWrapMode;
import net.labymod.api.client.gfx.texture.TextureParameterName;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import net.labymod.api.client.gfx.texture.TextureTarget;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.texture.TextureFilter;
import java.util.concurrent.CompletableFuture;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.resources.texture.concurrent.RefreshableTexture;

public class DefaultRefreshableTexture implements RefreshableTexture
{
    private Texture parentTexture;
    private int textureId;
    private GameImage image;
    private CompletableFuture<Void> currentTask;
    
    public void setParentTexture(final Texture parentTexture) {
        this.parentTexture = parentTexture;
    }
    
    public void init(final TextureFilter minFilter, final TextureFilter magFilter) {
        final GFXBridge gfx = Laby.references().gfxRenderPipeline().gfx();
        this.textureId = gfx.genTextures();
        gfx.bindTexture(TextureTarget.TEXTURE_2D, TextureId.of(this.textureId));
        gfx.texParameter(TextureTarget.TEXTURE_2D, TextureParameterName.TEXTURE_WRAP_S, TextureWrapMode.CLAMP_TO_EDGE);
        gfx.texParameter(TextureTarget.TEXTURE_2D, TextureParameterName.TEXTURE_WRAP_T, TextureWrapMode.CLAMP_TO_EDGE);
        gfx.texParameter(TextureTarget.TEXTURE_2D, TextureParameterName.TEXTURE_MIN_FILTER, minFilter);
        gfx.texParameter(TextureTarget.TEXTURE_2D, TextureParameterName.TEXTURE_MAG_FILTER, magFilter);
    }
    
    public int getTextureId() {
        return this.textureId;
    }
    
    @Nullable
    @Override
    public GameImage getCurrentImage() {
        return this.image;
    }
    
    @NotNull
    @Override
    public CompletableFuture<Void> queueUpdate(@NotNull final GameImage image) {
        this.checkReleased();
        final GameImage previous = this.image;
        this.image = image;
        if (previous != null && previous != image) {
            previous.close();
        }
        if (this.currentTask != null) {
            return this.currentTask;
        }
        return this.currentTask = TextureAllocator.upload(TextureTarget.TEXTURE_2D, this.textureId, () -> this.image, () -> this.currentTask = null);
    }
    
    @NotNull
    @Override
    public Object texture() {
        return this.parentTexture;
    }
    
    @NotNull
    @Override
    public Texture parentTexture() {
        return this.parentTexture;
    }
    
    @Override
    public void release() {
        ThreadSafe.ensureRenderThread();
        this.checkReleased();
        Laby.references().gfxRenderPipeline().gfx().deleteTextures(this.textureId);
        this.textureId = -1;
    }
    
    private void checkReleased() {
        if (this.wasReleased()) {
            throw new IllegalStateException("This RefreshableTexture has already been released");
        }
    }
    
    @Override
    public boolean wasReleased() {
        return this.textureId == -1;
    }
}
