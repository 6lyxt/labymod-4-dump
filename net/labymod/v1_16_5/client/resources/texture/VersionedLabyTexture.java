// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.resources.texture;

import java.io.IOException;
import net.labymod.api.client.resources.texture.LabyTexture;

public class VersionedLabyTexture extends ejy
{
    private final LabyTexture delegate;
    
    public VersionedLabyTexture(final LabyTexture delegate) {
        super((vk)delegate.getTextureLocation());
        this.delegate = delegate;
    }
    
    public void a(final boolean blur, final boolean mipmap) {
        this.delegate.setTextureFiltering(blur, mipmap);
    }
    
    public void a(final ach resourceManager) throws IOException {
        this.delegate.load();
    }
    
    public int b() {
        return this.delegate.getTextureId();
    }
    
    public void c() {
        this.delegate.release();
    }
    
    public void d() {
        this.delegate.bind();
    }
    
    public void close() {
        this.delegate.close();
    }
    
    public LabyTexture getDelegate() {
        return this.delegate;
    }
}
