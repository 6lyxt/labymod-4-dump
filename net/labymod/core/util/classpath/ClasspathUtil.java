// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.classpath;

import java.net.URISyntaxException;
import org.jetbrains.annotations.NotNull;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.Optional;
import java.net.URL;
import java.io.FileNotFoundException;
import java.util.Locale;
import net.labymod.core.addon.AddonClassLoader;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.core.main.LabyMod;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

public final class ClasspathUtil
{
    public static List<String> rootResources(final String addonId, final Class<?> callerCls) throws IOException {
        List<String> names = new ArrayList<String>();
        final String jarPath = getJarPath(callerCls);
        if (jarPath.endsWith(".jar")) {
            names = getJarEntryNames(jarPath, "");
            names = names.stream().filter(name -> !name.contains("/")).collect((Collector<? super Object, ?, List<String>>)Collectors.toList());
        }
        else {
            try (final InputStream inputStream = getResourceAsInputStream(addonId, "", false);
                 final BufferedReader resourceReader = new BufferedReader(new InputStreamReader(inputStream))) {
                String resource;
                while ((resource = resourceReader.readLine()) != null) {
                    if (resource.contains(".")) {
                        names.add(resource);
                    }
                }
            }
        }
        return names;
    }
    
    public static InputStream getResourceAsInputStream(final String addonId, final String path) throws IOException {
        return getResourceAsInputStream(addonId, path, true);
    }
    
    public static InputStream getResourceAsInputStream(final String addonId, final String path, final boolean shouldScanAddons) throws IOException {
        URL resource = null;
        if (shouldScanAddons) {
            final Optional<LoadedAddon> optionalAddon = LabyMod.getInstance().addonService().getOptionalAddon(addonId);
            if (optionalAddon.isPresent()) {
                final LoadedAddon loadedAddon = optionalAddon.get();
                if (!(loadedAddon.getClassLoader() instanceof AddonClassLoader)) {
                    throw new IOException(String.format(Locale.ROOT, "The loaded addon %s has a different class loader %s", addonId, loadedAddon.getClassLoader().getClass().getName()));
                }
                resource = loadedAddon.getClassLoader().getResource(path);
            }
        }
        if (resource == null) {
            resource = ClasspathUtil.class.getClassLoader().getResource(path);
        }
        if (resource == null) {
            throw new FileNotFoundException("No resource was found at this location: (" + path);
        }
        return resource.openStream();
    }
    
    @NotNull
    public static List<String> getJarEntryNames(final String jarPath, final String path) throws IOException {
        final List<String> paths = new ArrayList<String>();
        try (final JarFile file = new JarFile(jarPath)) {
            final Enumeration<JarEntry> entries = file.entries();
            while (entries.hasMoreElements()) {
                final JarEntry entry = entries.nextElement();
                if (path == null || path.isEmpty()) {
                    paths.add(entry.getName());
                }
                else {
                    if (!entry.getName().startsWith(path)) {
                        continue;
                    }
                    paths.add(entry.getName());
                }
            }
        }
        return paths;
    }
    
    private static String getJarPath(final Class<?> cls) {
        try {
            return cls.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        }
        catch (final URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }
}
