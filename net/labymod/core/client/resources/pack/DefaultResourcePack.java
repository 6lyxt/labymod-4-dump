// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.resources.pack;

import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Paths;
import java.nio.file.DirectoryStream;
import java.nio.file.LinkOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.net.URI;
import java.io.File;
import java.util.Enumeration;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.util.io.IOUtil;
import net.labymod.api.util.function.ThrowableFunction;
import java.io.FileNotFoundException;
import java.net.URL;
import net.labymod.core.util.classpath.ClasspathUtil;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.core.client.resources.PathResourceLocation;
import java.util.Locale;
import java.util.Iterator;
import java.util.Objects;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Collections;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.addon.LoadedAddon;
import java.util.Set;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.resources.pack.ResourcePack;

public class DefaultResourcePack implements ResourcePack
{
    private static final Logging LOGGER;
    private static final String PATH_FORMAT = "%s/%s/%s";
    private final String name;
    private final Set<String> clientNamespaces;
    private final Set<String> dataNamespaces;
    @Nullable
    private final LoadedAddon addon;
    private final List<ResourceLocation> cachedResourceLocations;
    
    public DefaultResourcePack(final String name) {
        this(name, null);
    }
    
    public DefaultResourcePack(final String name, @Nullable final LoadedAddon addon) {
        this(name, new HashSet<String>(Collections.singleton(name)), new HashSet<String>(Collections.singleton(name)), addon);
    }
    
    public DefaultResourcePack(final String name, final Set<String> clientNamespaces, final Set<String> dataNamespaces) {
        this(name, clientNamespaces, dataNamespaces, null);
    }
    
    public DefaultResourcePack(final String name, final Set<String> clientNamespaces, final Set<String> dataNamespaces, @Nullable final LoadedAddon addon) {
        this.cachedResourceLocations = new ArrayList<ResourceLocation>();
        this.name = name;
        this.clientNamespaces = clientNamespaces;
        this.dataNamespaces = dataNamespaces;
        this.addon = addon;
        this.gatherResourceLocations();
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public InputStream getClientResource(final ResourceLocation location) throws IOException {
        return this.getResource("assets", location);
    }
    
    @Override
    public InputStream getDataResource(final ResourceLocation location) throws IOException {
        return this.getResource("data", location);
    }
    
    @Override
    public boolean hasClientResource(final ResourceLocation location) {
        try {
            return this.hasResource(location, this::getClientResource);
        }
        catch (final IOException exception) {
            return false;
        }
    }
    
    @Override
    public boolean hasDataResource(final ResourceLocation location) {
        try {
            return this.hasResource(location, this::getDataResource);
        }
        catch (final IOException exception) {
            return false;
        }
    }
    
    @Override
    public void listResources(final boolean isServerData, final String namespace, final String path, final ResourceOutput output) {
        for (final ResourceLocation cachedResourceLocation : this.cachedResourceLocations) {
            if (!Objects.equals(namespace, cachedResourceLocation.getNamespace())) {
                continue;
            }
            final String locationPath = cachedResourceLocation.getPath();
            if (!locationPath.startsWith(path)) {
                continue;
            }
            final ResourceLocation resourceLocation = cachedResourceLocation;
            final ResourceLocation obj = cachedResourceLocation;
            Objects.requireNonNull(obj);
            output.accept(resourceLocation, obj::openStream);
        }
    }
    
    @Override
    public Set<String> getClientNamespaces() {
        return this.clientNamespaces;
    }
    
    @Override
    public Set<String> getDataNamespaces() {
        return this.dataNamespaces;
    }
    
    private InputStream getResource(final String path, final ResourceLocation location) throws IOException {
        final String resourceLocation = String.format(Locale.ROOT, "%s/%s/%s", path, location.getNamespace(), location.getPath());
        if (location instanceof PathResourceLocation) {
            return location.openStream();
        }
        if (this.addon != null) {
            final AddonClassLoader classLoader = (AddonClassLoader)this.addon.getClassLoader();
            final URL resource = classLoader.findResourceObject(resourceLocation);
            if (resource != null) {
                return resource.openStream();
            }
            throw this.createFileNotFound(this.name, resourceLocation);
        }
        else {
            if (this.cachedResourceLocations.contains(location)) {
                return ClasspathUtil.getResourceAsInputStream(this.name, resourceLocation, false);
            }
            throw this.createFileNotFound("minecraft", path);
        }
    }
    
    private FileNotFoundException createFileNotFound(final String name, final String path) {
        return new FileNotFoundException("[" + name + "] No resource was found at this location: (" + path);
    }
    
    private boolean hasResource(final ResourceLocation location, final ThrowableFunction<ResourceLocation, InputStream, IOException> function) throws IOException {
        final InputStream stream = function.apply(location);
        final boolean hasResource = stream != null;
        IOUtil.closeSilent(stream);
        return hasResource;
    }
    
    private ClassLoader getClassLoader() {
        return (this.addon == null) ? PlatformEnvironment.getPlatformClassloader().getPlatformClassloader() : this.addon.getClassLoader();
    }
    
    private void gatherResourceLocations() {
        final ClassLoader loader = this.getClassLoader();
        final String resourceName = "assets/" + this.name + "/.assetsroot";
        try {
            final Enumeration<URL> resources = PlatformEnvironment.getResources(loader, resourceName);
            if (!resources.hasMoreElements()) {
                this.error("File {} does not exist on the classpath", resourceName);
            }
            else {
                while (resources.hasMoreElements()) {
                    final URL resource = resources.nextElement();
                    this.gatherResourceLocations(resource);
                }
            }
        }
        catch (final IOException exception) {
            this.error("Could not resolve path to " + this.name + " assets", (Throwable)exception);
        }
    }
    
    private void gatherResourceLocations(final URL resource) {
        try {
            final URI uri = resource.toURI();
            final String scheme = uri.getScheme();
            if (!"jar".equals(scheme) && !"file".equals(scheme)) {
                this.warn("Assets URL '{}' uses unexpected scheme", uri);
            }
            final Path uriPath = this.safeGetPath(uri);
            final Path parent = uriPath.getParent();
            final Collection<String> paths = new ArrayList<String>();
            this.gatherResources(paths, parent);
            for (String path : paths) {
                path = path.replace(parent.toString(), "");
                path = path.replace(File.separator, "/").substring(1);
                try {
                    this.cachedResourceLocations.add(ResourceLocation.create(this.name, path));
                }
                catch (final Exception exception) {
                    DefaultResourcePack.LOGGER.error(exception.getMessage(), new Object[0]);
                }
            }
            paths.clear();
        }
        catch (final Exception exception2) {
            this.error("Could not resolve path to " + this.name + " assets", (Throwable)exception2);
        }
    }
    
    private void gatherResources(final Collection<String> pathCollection, final Path directory) {
        try (final DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            stream.forEach(path -> {
                if (Files.isDirectory(path, new LinkOption[0])) {
                    this.gatherResources(pathCollection, path);
                    return;
                }
                else {
                    pathCollection.add(path.toString());
                    return;
                }
            });
        }
        catch (final IOException exception) {
            exception.printStackTrace();
        }
    }
    
    private Path safeGetPath(final URI uri) throws IOException {
        try {
            return Paths.get(uri);
        }
        catch (final FileSystemNotFoundException ex) {}
        catch (final Throwable throwable) {
            DefaultResourcePack.LOGGER.warn("Unable to get path for: {}", uri, throwable);
        }
        try {
            FileSystems.newFileSystem(uri, Collections.emptyMap());
        }
        catch (final FileSystemAlreadyExistsException ex2) {}
        return Paths.get(uri);
    }
    
    private void warn(final String message, final Object... args) {
        DefaultResourcePack.LOGGER.warn("[" + this.name + "]" + message, args);
    }
    
    private void error(final String message, final Object... args) {
        DefaultResourcePack.LOGGER.error("[" + this.name + "] " + message, args);
    }
    
    private void error(final String message, final Throwable throwable) {
        DefaultResourcePack.LOGGER.error("[" + this.name + "] " + message, throwable);
    }
    
    static {
        LOGGER = Logging.create(ResourcePack.class);
    }
}
