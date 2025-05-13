// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources;

import net.labymod.api.Constants;
import java.util.Iterator;
import net.labymod.api.util.io.IOUtil;
import java.nio.file.InvalidPathException;
import net.labymod.api.client.resources.IllegalResourceLocationException;
import net.labymod.core.client.resources.texture.DefaultThemeTextureLocation;
import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import net.labymod.api.client.resources.ThemeResourceLocation;
import net.labymod.api.util.ide.IdeUtil;
import java.util.function.BiFunction;
import org.jetbrains.annotations.Nullable;
import java.io.InputStream;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.AnimatedResourceLocation;
import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Map;
import java.nio.file.Path;
import java.util.Collection;
import net.labymod.api.client.resources.ResourceLocationFactory;

public abstract class AbstractResourceLocationFactory implements ResourceLocationFactory
{
    private static final String MINECRAFT_NAMESPACE = "minecraft";
    private static final Collection<Path> DIRECTORIES;
    private final Map<String, ResourceLocation> cachedLocations;
    private final Set<String> lockComputes;
    
    public AbstractResourceLocationFactory() {
        this.cachedLocations = new ConcurrentHashMap<String, ResourceLocation>();
        this.lockComputes = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    }
    
    @Override
    public ResourceLocation createPath(@NotNull final Path path, @NotNull final String locationNamespace, @NotNull final String locationPath) {
        return new PathResourceLocation(path, locationNamespace, locationPath);
    }
    
    @NotNull
    @Override
    public AnimatedResourceLocation.Builder builder() {
        return Laby.references().animatedResourceLocationBuilder();
    }
    
    @Override
    public AnimatedResourceLocation createAnimated(final String namespace, final String path, final InputStream spriteImageStream, final int ratioWidth, final int ratioHeight, final long delay, @Nullable final Runnable completableListener) {
        return new DefaultAnimatedResourceLocation(namespace, path, spriteImageStream, ratioWidth, ratioHeight, delay, completableListener);
    }
    
    public ResourceLocation apply(@NotNull final String namespace, @NotNull final String path, @NotNull final BiFunction<String, String, ResourceLocation> locationFactory) {
        final String locationAsString = namespace + ":" + path;
        if ("minecraft".equals(namespace) || path.startsWith("sounds")) {
            return locationFactory.apply(namespace, path);
        }
        if (this.lockComputes.contains(locationAsString)) {
            return locationFactory.apply(namespace, path);
        }
        try {
            this.lockComputes.add(locationAsString);
            return this.cachedLocations.computeIfAbsent(locationAsString, l -> {
                final ResourceLocation location = locationFactory.apply(namespace, path);
                if (!IdeUtil.RUNNING_IN_IDE) {
                    return location;
                }
                else {
                    return this.resolveResourceLocation(location, namespace, path);
                }
            });
        }
        finally {
            this.lockComputes.remove(locationAsString);
        }
    }
    
    @Override
    public ThemeResourceLocation createThemeResource(final String path) {
        return new DefaultThemeResourceLocation.Builder().path(path).build();
    }
    
    @Override
    public ThemeTextureLocation createThemeTexture(final String path, final int width, final int height) {
        return new DefaultThemeTextureLocation.Builder().path(path).width(width).height(height).build();
    }
    
    @Override
    public ThemeResourceLocation createThemeResource(final String namespace, final String path) {
        return new DefaultThemeResourceLocation.Builder().namespace(namespace).path(path).build();
    }
    
    @Override
    public ThemeTextureLocation createThemeTexture(final String namespace, final String path, final int width, final int height) {
        return new DefaultThemeTextureLocation.Builder().namespace(namespace).path(path).width(width).height(height).build();
    }
    
    @Override
    public Map<String, ResourceLocation> getCachedResourceLocations() {
        return this.cachedLocations;
    }
    
    private ResourceLocation resolveResourceLocation(final ResourceLocation delegate, final String namespace, final String path) {
        for (final Path directory : AbstractResourceLocationFactory.DIRECTORIES) {
            Path resourcePath;
            try {
                resourcePath = directory.resolve("assets").resolve(namespace).resolve(path);
            }
            catch (final InvalidPathException exception) {
                throw new IllegalResourceLocationException(exception);
            }
            if (IOUtil.exists(resourcePath)) {
                final PathResourceLocation newLocation = new PathResourceLocation(resourcePath, delegate.getNamespace(), delegate.getPath());
                newLocation.metadata().set(delegate.metadata());
                return newLocation;
            }
        }
        return delegate;
    }
    
    static {
        DIRECTORIES = Constants.SystemProperties.getFiles(Constants.SystemProperties.HOT_FILE_RELOADING_DIRECTORIES);
    }
}
