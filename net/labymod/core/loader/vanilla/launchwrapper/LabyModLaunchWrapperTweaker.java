// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper;

import net.minecraft.launchwrapper.LaunchClassLoader;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import net.minecraft.launchwrapper.ITweaker;

public class LabyModLaunchWrapperTweaker implements ITweaker
{
    private static final String CORE_TWEAKER = "net.labymod.core.loader.vanilla.launchwrapper.Java17LabyModLaunchWrapperTweaker";
    private static final String OUTDATED_JAVA_VERSION_MESSAGE = "LabyMod4 was started with an outdated Java version (%s). Please use Java 17 or higher.";
    private static final String OUTDATED_JAVA_VERSION_QUICK_LAUNCHER_MESSAGE = "QuickLauncher has launched LabyMod 4 with an outdated Java version (%s), please launch LabyMod 4 via another launcher to create a new short for the profile.";
    private static final String OUTDATED_JAVA_VERSION_DEV_ENVIRONMENT_MESSAGE = "Run configuration has an outdated Java version (%s). Please use Java 17 or higher.";
    private static final String TWEAKER_CLASS_ERROR_MESSAGE = "Core Tweaker \"%s\" could not be found!";
    private static final int INVALID_JAVA_VERSION = -1;
    private static final String LABY_GRADLE_LAUNCHER_NAME = "LabyGradle";
    private static final String QUICK_LAUNCHER_NAME = "QuickLauncher";
    @Deprecated
    private static final String OLD_QUICK_LAUNCHER_NAME = "LabyMod";
    private static final int INVALID_JAVA_VERSION_EXIT_STATUS = -1337;
    private static final int CORE_TWEAKER_NOT_FOUND_EXIT_STATUS = -42;
    private static final int JAVA_GOES_WILD_EXIT_STATUS = -420;
    private ITweaker tweaker;
    
    public LabyModLaunchWrapperTweaker() {
        System.setProperty("java.awt.headless", "true");
        String javaVersion;
        final String plainJavaVersion = javaVersion = System.getProperty("java.version");
        final String outdatedJavaVersionErrorMessage = this.getOutdatedJavaVersionErrorMessage(this.getLauncherType(), plainJavaVersion);
        if (javaVersion.startsWith("1.")) {
            this.displayWindow(outdatedJavaVersionErrorMessage, -1337);
            return;
        }
        if (javaVersion.contains(".")) {
            javaVersion = javaVersion.substring(0, javaVersion.indexOf("."));
        }
        final int majorVersion = this.getMajorVersion(javaVersion);
        if (majorVersion < 17) {
            this.displayWindow(outdatedJavaVersionErrorMessage, -1337);
            return;
        }
        final Class<?> tweakerClass = this.findClass("net.labymod.core.loader.vanilla.launchwrapper.Java17LabyModLaunchWrapperTweaker");
        if (tweakerClass == null) {
            this.displayWindow(String.format(Locale.ROOT, "Core Tweaker \"%s\" could not be found!", "net.labymod.core.loader.vanilla.launchwrapper.Java17LabyModLaunchWrapperTweaker"), -42);
            return;
        }
        try {
            this.tweaker = (ITweaker)tweakerClass.getDeclaredConstructor((Class<?>[])new Class[0]).newInstance(new Object[0]);
        }
        catch (final ReflectiveOperationException exception) {
            this.displayWindow(exception.getMessage(), -420);
        }
    }
    
    private LauncherType getLauncherType() {
        final String launcherBrand = System.getProperty("minecraft.launcher.brand", "Unknown");
        if (launcherBrand.equals("LabyMod") || launcherBrand.equals("QuickLauncher")) {
            return LauncherType.QUICK_LAUNCHER;
        }
        if (launcherBrand.equals("LabyGradle")) {
            return LauncherType.LABY_GRADLE;
        }
        return LauncherType.OTHER;
    }
    
    private void displayWindow(final String message, final int exitStatus) {
        this.error(message);
        System.exit(exitStatus);
    }
    
    public void acceptOptions(final List<String> arguments, final Path gameDirectory, final Path assetsDirectory, final String profile, final boolean developmentEnvironment, final boolean addonDevelopmentEnvironment) {
        this.tweaker.acceptOptions((List)arguments, gameDirectory, assetsDirectory, profile, developmentEnvironment, addonDevelopmentEnvironment);
    }
    
    public void injectIntoClassLoader(final LaunchClassLoader classLoader) {
        this.tweaker.injectIntoClassLoader(classLoader);
    }
    
    public String getLaunchTarget() {
        return this.tweaker.getLaunchTarget();
    }
    
    public String[] getLaunchArguments() {
        return this.tweaker.getLaunchArguments();
    }
    
    private String getOutdatedJavaVersionErrorMessage(final LauncherType type, final String outdatedVersion) {
        switch (type.ordinal()) {
            case 0: {
                return String.format(Locale.ROOT, "QuickLauncher has launched LabyMod 4 with an outdated Java version (%s), please launch LabyMod 4 via another launcher to create a new short for the profile.", outdatedVersion);
            }
            case 1: {
                return String.format(Locale.ROOT, "Run configuration has an outdated Java version (%s). Please use Java 17 or higher.", outdatedVersion);
            }
            case 2: {
                return String.format(Locale.ROOT, "LabyMod4 was started with an outdated Java version (%s). Please use Java 17 or higher.", outdatedVersion);
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + type);
            }
        }
    }
    
    private int getMajorVersion(final String version) {
        try {
            return Integer.parseInt(version);
        }
        catch (final NumberFormatException exception) {
            return -1;
        }
    }
    
    private Class<?> findClass(final String className) {
        try {
            return Class.forName(className);
        }
        catch (final ClassNotFoundException exception) {
            return null;
        }
    }
    
    private void error(final String message) {
        System.err.println(message);
    }
    
    enum LauncherType
    {
        QUICK_LAUNCHER, 
        LABY_GRADLE, 
        OTHER;
    }
}
