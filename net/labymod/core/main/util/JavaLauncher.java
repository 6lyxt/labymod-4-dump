// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.util;

import java.io.File;
import java.util.Collection;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.labymod.api.models.OperatingSystem;
import java.util.List;

public final class JavaLauncher
{
    private static final String DEFAULT_JAVA_HOME;
    
    public static void launch(final List<String> arguments) {
        String property = System.getProperty("sun.boot.library.path");
        if (property == null || property.isEmpty()) {
            launch(JavaLauncher.DEFAULT_JAVA_HOME, arguments);
            return;
        }
        final OperatingSystem platform = OperatingSystem.getPlatform();
        final Path directory = Paths.get(property, new String[0]);
        if (platform == OperatingSystem.MACOS) {
            property = directory.getParent().resolve("bin").resolve("java").toAbsolutePath().toString();
        }
        else {
            property = directory.resolve("java").toAbsolutePath().toString();
        }
        launch(property, arguments);
    }
    
    private static void launch(final String java, final List<String> arguments) {
        final List<String> finalArguments = new ArrayList<String>();
        finalArguments.add(java);
        finalArguments.addAll(arguments);
        final ProcessBuilder processBuilder = new ProcessBuilder(finalArguments);
        try {
            processBuilder.start();
        }
        catch (final Exception exception) {
            if (!java.equals(JavaLauncher.DEFAULT_JAVA_HOME)) {
                launch(JavaLauncher.DEFAULT_JAVA_HOME, arguments);
                return;
            }
            exception.printStackTrace();
        }
    }
    
    static {
        DEFAULT_JAVA_HOME = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
    }
}
