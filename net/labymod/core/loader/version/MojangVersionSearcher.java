// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.version;

import net.labymod.core.util.logging.DefaultLoggingFactory;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import java.util.Enumeration;
import net.labymod.core.loader.version.parser.VersionManifestException;
import net.labymod.core.loader.version.parser.VersionParser;
import net.labymod.api.util.function.ThrowableRunnable;
import java.net.URISyntaxException;
import java.io.IOException;
import net.labymod.api.util.IsolatedClassLoader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.net.URL;
import java.util.Iterator;
import net.labymod.api.loader.platform.PlatformClassloader;
import java.util.ArrayList;
import java.util.HashSet;
import java.nio.file.Path;
import java.util.List;
import java.util.Collection;
import net.labymod.core.loader.version.reflect.VersionStringReflector;
import net.labymod.api.util.logging.Logging;

public class MojangVersionSearcher implements VersionSearcher
{
    public static final Logging LOGGER;
    private static final VersionStringReflector V1_8_9;
    private static final VersionStringReflector V1_12_2;
    private static final VersionStringReflector[] REFLECTORS;
    private final Collection<String> mainClasses;
    private final List<Throwable> throwables;
    private String version;
    private Path clientJarPath;
    
    public MojangVersionSearcher(final String version) {
        (this.mainClasses = new HashSet<String>()).add("net.minecraft.client.main.Main");
        this.throwables = new ArrayList<Throwable>();
        this.version = version;
    }
    
    @Override
    public void searchClasspath(final PlatformClassloader classLoader) {
        for (final String mainClass : this.mainClasses) {
            final URL classLocation = this.getClassLocation(classLoader, mainClass);
            if (classLocation == null) {
                continue;
            }
            this.safeExecute(() -> this.readClientJar(classLocation));
        }
        if (this.version != null) {
            this.throwables.clear();
            return;
        }
        final StringBuilder errorBuilder = new StringBuilder();
        int errorIndex = 1;
        for (final Throwable throwable : this.throwables) {
            errorBuilder.append("Error #").append(errorIndex);
            for (final StackTraceElement stackTraceElement : throwable.getStackTrace()) {
                errorBuilder.append("\t").append(stackTraceElement).append("\n");
            }
            ++errorIndex;
        }
        throw new RuntimeException(errorBuilder.toString());
    }
    
    private void readClientJar(final URL location) throws IOException, URISyntaxException {
        this.clientJarPath = Paths.get(location.toURI());
        if (Files.notExists(this.clientJarPath, new LinkOption[0])) {
            throw new IllegalStateException("Minecraft jar could not be found (" + String.valueOf(location));
        }
        try (final IsolatedClassLoader loader = new IsolatedClassLoader()) {
            loader.addPath(this.clientJarPath);
            this.safeExecute(() -> this.readVersionJson(loader));
            for (final VersionStringReflector reflector : MojangVersionSearcher.REFLECTORS) {
                if (this.version != null) {
                    break;
                }
                this.safeExecute(() -> reflector.invoke(loader, this::setVersion));
            }
        }
    }
    
    private void safeExecute(final ThrowableRunnable<Throwable> runnable) {
        try {
            runnable.run();
        }
        catch (final Throwable throwable) {
            this.throwables.add(throwable);
        }
    }
    
    private void readVersionJson(final IsolatedClassLoader loader) {
        Enumeration<URL> resources;
        try {
            resources = loader.getResources("version.json");
        }
        catch (final IOException exception) {
            MojangVersionSearcher.LOGGER.error("Version manifest could not be found", exception);
            return;
        }
        while (resources.hasMoreElements() && this.version == null) {
            try {
                VersionParser.parse(resources.nextElement(), this::setVersion);
            }
            catch (final VersionManifestException exception2) {
                if (exception2.getCause() == null) {
                    throw exception2;
                }
                MojangVersionSearcher.LOGGER.error("Parsing of the version manifest failed", exception2);
            }
        }
    }
    
    public void setVersion(final String version) {
        this.version = version;
    }
    
    @Override
    public String getMinecraftVersion() {
        return this.version;
    }
    
    @Override
    public Path getClientJarPath() {
        return this.clientJarPath;
    }
    
    @Override
    public Collection<String> getMainClasses() {
        return this.mainClasses;
    }
    
    @Nullable
    private URL getClassLocation(@NotNull final PlatformClassloader loader, @NotNull final String className) {
        final String suffix = className.replace('.', '/') + ".class";
        final URL classResource = loader.getPlatformClassloader().getResource(suffix);
        if (classResource == null) {
            return null;
        }
        final String url = classResource.toString();
        if (!url.endsWith(suffix)) {
            return null;
        }
        String path = url.substring(0, url.length() - suffix.length());
        if (path.startsWith("jar:")) {
            path = path.substring(4, path.length() - 2);
        }
        try {
            return new URL(path);
        }
        catch (final Exception exception) {
            return null;
        }
    }
    
    static {
        LOGGER = DefaultLoggingFactory.createLogger(MojangVersionSearcher.class);
        V1_8_9 = new VersionStringReflector("net.minecraft.realms.RealmsSharedConstants", "VERSION_STRING");
        V1_12_2 = new VersionStringReflector("net.minecraft.realms.RealmsSharedConstants", "VERSION_STRING");
        REFLECTORS = new VersionStringReflector[] { MojangVersionSearcher.V1_8_9, MojangVersionSearcher.V1_12_2 };
    }
}
