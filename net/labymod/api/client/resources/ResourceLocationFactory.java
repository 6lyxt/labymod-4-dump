// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources;

import java.util.Map;
import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import org.jetbrains.annotations.Nullable;
import java.io.InputStream;
import java.nio.file.Path;
import net.labymod.api.client.gui.screen.theme.Theme;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ResourceLocationFactory
{
    default ResourceLocation createMinecraft(@NotNull final String path) {
        return this.create("minecraft", path);
    }
    
    default ResourceLocation createLabyMod(@NotNull final String path) {
        return this.create("labymod", path);
    }
    
    default ResourceLocation parse(@NotNull final String value) {
        final String[] seg = value.split(":", 2);
        if (seg.length == 1) {
            return this.createMinecraft(seg[0]);
        }
        return this.create(seg[0], seg[1]);
    }
    
    ResourceLocation create(@NotNull final String p0, @NotNull final String p1);
    
    default ResourceLocation create(@NotNull final Theme theme, @NotNull final String namespace, @NotNull final String path) {
        return this.create(namespace, theme.getDirectoryPath() + path);
    }
    
    ResourceLocation createPath(@NotNull final Path p0, @NotNull final String p1, @NotNull final String p2);
    
    @NotNull
    AnimatedResourceLocation.Builder builder();
    
    default AnimatedResourceLocation createAnimated(final String namespace, final String path, final InputStream spriteImageStream, final int ratioWidth, final int ratioHeight, final long delay) {
        return this.createAnimated(namespace, path, spriteImageStream, ratioWidth, ratioHeight, delay, null);
    }
    
    AnimatedResourceLocation createAnimated(final String p0, final String p1, final InputStream p2, final int p3, final int p4, final long p5, @Nullable final Runnable p6);
    
    ThemeResourceLocation createThemeResource(final String p0);
    
    ThemeTextureLocation createThemeTexture(final String p0, final int p1, final int p2);
    
    ThemeResourceLocation createThemeResource(final String p0, final String p1);
    
    ThemeTextureLocation createThemeTexture(final String p0, final String p1, final int p2, final int p3);
    
    Map<String, ResourceLocation> getCachedResourceLocations();
    
    @Deprecated
    default ThemeTextureLocation createThemeResource(final String path, final int width, final int height) {
        return this.createThemeTexture(path, width, height);
    }
    
    @Deprecated
    default ThemeTextureLocation createThemeResource(final String namespace, final String path, final int width, final int height) {
        return this.createThemeTexture(namespace, path, width, height);
    }
}
