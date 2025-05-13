// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.resources.texture;

import net.labymod.api.client.resources.texture.LabyTexture;

public class VersionedLabyTexture extends hel
{
    private final LabyTexture delegate;
    
    public VersionedLabyTexture(final LabyTexture delegate) {
        super((akv)delegate.getTextureLocation());
        this.delegate = delegate;
    }
    
    public void a(final boolean blur, final boolean mipmap) {
        this.delegate.setTextureFiltering(blur, mipmap);
    }
    
    public void a(final heu $$0) {
        this.delegate.load();
    }
    
    public int a() {
        return this.delegate.getTextureId();
    }
    
    public void b() {
        this.delegate.release();
    }
    
    public void c() {
        this.delegate.bind();
    }
    
    public void close() {
        this.delegate.close();
    }
    
    public LabyTexture getDelegate() {
        return this.delegate;
    }
}
