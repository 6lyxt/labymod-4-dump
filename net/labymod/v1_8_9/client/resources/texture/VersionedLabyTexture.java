// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.resources.texture;

import java.io.IOException;
import net.labymod.api.client.resources.texture.LabyTexture;

public class VersionedLabyTexture extends bme
{
    private final LabyTexture delegate;
    
    public VersionedLabyTexture(final LabyTexture delegate) {
        super((jy)delegate.getTextureLocation());
        this.delegate = delegate;
    }
    
    public void b(final boolean linear, final boolean mipmap) {
        super.b(linear, mipmap);
    }
    
    public void a(final boolean linear, final boolean mipmap) {
        this.delegate.setTextureFiltering(linear, mipmap);
    }
    
    public void a(final bni lvt_1_1_) throws IOException {
        this.delegate.load();
    }
    
    public int b() {
        return this.delegate.getTextureId();
    }
    
    public void c() {
        this.delegate.release();
        this.delegate.close();
    }
    
    public LabyTexture getDelegate() {
        return this.delegate;
    }
}
