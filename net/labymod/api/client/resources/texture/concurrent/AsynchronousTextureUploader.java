// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture.concurrent;

import net.labymod.api.client.gfx.texture.TextureFilter;
import net.labymod.api.client.gfx.texture.GFXTextureFilter;
import java.util.concurrent.CompletableFuture;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface AsynchronousTextureUploader
{
    CompletableFuture<Void> prepareAndUploadTexture(final Runnable p0, final AsynchronousTextureTask p1);
    
    default RefreshableTexture newRefreshableTexture(final GFXTextureFilter minFilter, final GFXTextureFilter magFilter) {
        return this.newRefreshableTexture(TextureFilter.from(minFilter), TextureFilter.from(magFilter));
    }
    
    RefreshableTexture newRefreshableTexture(final TextureFilter p0, final TextureFilter p1);
}
