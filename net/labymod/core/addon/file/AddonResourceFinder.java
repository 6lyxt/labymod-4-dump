// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon.file;

import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.SimpleFileVisitor;
import java.net.URI;
import java.util.Enumeration;
import java.util.Iterator;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;
import java.net.URISyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.nio.file.Path;
import java.io.ByteArrayInputStream;
import java.util.zip.ZipFile;
import net.labymod.api.util.io.IOUtil;
import java.nio.file.Paths;
import java.net.URL;

public final class AddonResourceFinder
{
    @Nullable
    public static AddonResource find(final URL url, final String localPath) throws IOException, URISyntaxException {
        final Path path = Paths.get(url.toURI());
        if (!IOUtil.exists(path)) {
            return null;
        }
        if (IOUtil.isDirectory(path)) {
            final Path resolvedPath = path.resolve(localPath);
            if (!IOUtil.exists(resolvedPath)) {
                return null;
            }
            return new AddonResource(resolvedPath.getFileName().toString(), resolvedPath.toUri(), () -> IOUtil.newInputStream(resolvedPath));
        }
        else {
            try (final ZipFile file = new ZipFile(path.toFile())) {
                final ZipEntry entry = file.getEntry(localPath);
                if (entry == null) {
                    final AddonResource addonResource = null;
                    file.close();
                    return addonResource;
                }
                try (final InputStream stream = file.getInputStream(entry)) {
                    final ByteArrayInputStream copiedStream = new ByteArrayInputStream(IOUtil.readBytes(stream));
                    final String name = entry.getName();
                    return new AddonResource(name, createJarFileURI(path, entry.getName()), () -> copiedStream);
                }
            }
        }
    }
    
    public static <T> List<T> discoverResources(final URL url, final Predicate<AddonResource> filter, final Function<AddonResource, T> mapper) throws IOException, URISyntaxException {
        final List<T> result = new ArrayList<T>();
        final Path path = Paths.get(url.toURI());
        if (!IOUtil.exists(path)) {
            return result;
        }
        if (IOUtil.isDirectory(path)) {
            final AddonResourceLocationCollector collector = new AddonResourceLocationCollector();
            try {
                Files.walkFileTree(path, collector);
            }
            catch (final IOException exception) {
                return result;
            }
            final List<AddonResource> resourceLocations = collector.getResourceLocations();
            for (final AddonResource resourceLocation : resourceLocations) {
                if (filter.test(resourceLocation)) {
                    result.add(mapper.apply(resourceLocation));
                }
            }
        }
        else {
            try (final ZipFile file = new ZipFile(path.toFile())) {
                final Enumeration<? extends ZipEntry> entries = file.entries();
                while (entries.hasMoreElements()) {
                    final ZipEntry entry = (ZipEntry)entries.nextElement();
                    if (entry.isDirectory()) {
                        continue;
                    }
                    try (final InputStream inputStream = file.getInputStream(entry)) {
                        final ByteArrayInputStream buffer = new ByteArrayInputStream(IOUtil.readBytes(inputStream));
                        final AddonResource location = new AddonResource(entry.getName(), createJarFileURI(path, entry.getName()), () -> buffer);
                        if (filter.test(location)) {
                            result.add(mapper.apply(location));
                        }
                        if (inputStream == null) {
                            continue;
                        }
                    }
                }
            }
        }
        return result;
    }
    
    private static URI createJarFileURI(final Path path, final String entry) {
        final String filePath = path.toUri().toString();
        return URI.create("jar:" + filePath + "!/" + entry);
    }
    
    private static class AddonResourceLocationCollector extends SimpleFileVisitor<Path>
    {
        private final List<AddonResource> resourceLocations;
        
        private AddonResourceLocationCollector() {
            this.resourceLocations = new ArrayList<AddonResource>();
        }
        
        @Override
        public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
            this.resourceLocations.add(new AddonResource(file.getFileName().toString(), file.toUri(), () -> IOUtil.newInputStream(file)));
            return FileVisitResult.CONTINUE;
        }
        
        @Override
        public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }
        
        @Override
        public FileVisitResult visitFileFailed(final Path file, final IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
        
        @Override
        public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
        
        public List<AddonResource> getResourceLocations() {
            return this.resourceLocations;
        }
    }
}
