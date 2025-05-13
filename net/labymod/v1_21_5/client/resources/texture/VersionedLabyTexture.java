// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.resources.texture;

import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.TextureFormat;
import net.labymod.api.client.resources.texture.LabyTexture;

public class VersionedLabyTexture extends hki
{
    private final LabyTexture delegate;
    
    public VersionedLabyTexture(final LabyTexture delegate) {
        super((alr)delegate.getTextureLocation());
        this.delegate = delegate;
    }
    
    public void a(final boolean blur, final boolean mipmap) {
        this.delegate.setTextureFiltering(blur, mipmap);
    }
    
    public void a(final hkr $$0) {
        this.delegate.load();
        this.a = (GpuTexture)new fjl(this.delegate.getClass().getName(), TextureFormat.RGBA8, 1, 1, 1, this.delegate.getTextureId());
    }
    
    public void close() {
        this.delegate.close();
    }
    
    public LabyTexture getDelegate() {
        return this.delegate;
    }
}
