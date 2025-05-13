// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.debug;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import net.labymod.api.Constants;
import net.labymod.api.models.OperatingSystem;

public final class Renderdoc
{
    private static boolean initialized;
    private static boolean loaded;
    
    private Renderdoc() {
    }
    
    public static boolean isLoaded() {
        return Renderdoc.loaded;
    }
    
    public static void load() {
        if (Renderdoc.initialized) {
            throw new IllegalStateException("Renderdoc already initialized");
        }
        Renderdoc.initialized = true;
        final OperatingSystem currentPlatform = OperatingSystem.getPlatform();
        if (currentPlatform != OperatingSystem.WINDOWS || !Constants.SystemProperties.getBoolean(Constants.SystemProperties.RENDERDOC)) {
            return;
        }
        try {
            final Path renderdocLibraryFile = getRenderdocLibraryFile(currentPlatform);
            System.load(renderdocLibraryFile.toAbsolutePath().toString());
            Renderdoc.loaded = true;
            System.out.println("Renderdoc loaded");
        }
        catch (final Throwable throwable) {
            System.err.println("Renderdoc could not be loaded");
        }
    }
    
    private static Path getRenderdocLibraryFile(final OperatingSystem system) {
        final String path = System.getenv("RENDERDOC");
        if (path == null) {
            throw new IllegalStateException("Environment variable \"RENDERDOC\" is not set.");
        }
        final Path file = Path.of(path, new String[0]).resolve("renderdoc." + system.getLibraryExtensionName());
        if (!Files.exists(file, new LinkOption[0])) {
            throw new IllegalStateException(String.valueOf(file) + " does not exist.");
        }
        if (!Files.isRegularFile(file, new LinkOption[0])) {
            throw new IllegalStateException(String.valueOf(file) + " is not a regular file.");
        }
        return file;
    }
}
