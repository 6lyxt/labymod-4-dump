// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.texture;

import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import net.labymod.core.client.resources.DefaultThemeResourceLocation;

public class DefaultThemeTextureLocation extends DefaultThemeResourceLocation implements ThemeTextureLocation
{
    private final int width;
    private final int height;
    
    private DefaultThemeTextureLocation(final String id, final String namespace, final String path, final int width, final int height) {
        super(id, namespace, path);
        this.width = width;
        this.height = height;
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    public static class Builder extends DefaultThemeResourceLocation.Builder
    {
        private int width;
        private int height;
        
        @Override
        public Builder id(final String id) {
            return (Builder)super.id(id);
        }
        
        @Override
        public Builder namespace(final String namespace) {
            return (Builder)super.namespace(namespace);
        }
        
        @Override
        public Builder path(final String path) {
            return (Builder)super.path(path);
        }
        
        public Builder width(final int width) {
            this.width = width;
            return this;
        }
        
        public Builder height(final int height) {
            this.height = height;
            return this;
        }
        
        @Override
        public DefaultThemeTextureLocation build() {
            return new DefaultThemeTextureLocation(this.id, this.namespace, this.path, this.width, this.height);
        }
    }
}
