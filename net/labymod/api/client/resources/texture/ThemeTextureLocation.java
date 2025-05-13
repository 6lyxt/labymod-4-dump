// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import java.util.Locale;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.client.resources.ThemeResourceLocation;

public interface ThemeTextureLocation extends ThemeResourceLocation
{
    @Deprecated
    public static final ResourceLocationFactory FACTORY = ThemeResourceLocation.FACTORY;
    
    int getWidth();
    
    int getHeight();
    
    default ThemeTextureLocation of(final String namespace, final String path, final int width, final int height) {
        return ThemeResourceLocation.FACTORY.createThemeTexture(namespace, String.format(Locale.ROOT, "textures/%s.png", path), width, height);
    }
    
    default ThemeTextureLocation of(final String path, final int width, final int height) {
        if (path.contains(":")) {
            final String[] split = path.split(":");
            return of(split[0], split[1], width, height);
        }
        return of(null, path, width, height);
    }
    
    default ThemeTextureLocation of(final String path, final int size) {
        return of(path, size, size);
    }
    
    default ThemeTextureLocation of(final String path) {
        return of(path, 128);
    }
}
