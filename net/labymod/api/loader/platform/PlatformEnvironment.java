// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.loader.platform;

import net.labymod.api.util.version.CalendarVersion;
import net.labymod.api.util.version.SemanticVersion;
import net.labymod.api.loader.MinecraftVersions;
import java.io.IOException;
import java.util.Collections;
import java.net.URL;
import java.util.Enumeration;
import net.labymod.api.util.version.serial.VersionDeserializer;
import java.nio.file.Path;
import net.labymod.api.models.version.Version;

public final class PlatformEnvironment
{
    public static final boolean ITEM_COMPONENTS;
    private static final Version ANCIENT_PVP_VERSION;
    private static final Version ANCIENT_OPENGL;
    private static final Version NO_MOJANG_MAPPINGS;
    private static final Version NO_SHADER;
    private static final Version NEW_LOGO;
    private static final Version NEW_LOGO_1_20;
    private static final boolean FRESH_UI;
    private static PlatformClassloader platformClassloader;
    private static Version version;
    private static String runningVersion;
    private static Path clientJarPath;
    private static Path obfuscatedJarPath;
    private static boolean ancientPvPVersion;
    private static boolean ancientOpenGL;
    private static boolean noMojangMappings;
    private static boolean noShader;
    private static boolean newLogo;
    
    public static PlatformClassloader getPlatformClassloader() {
        return PlatformEnvironment.platformClassloader;
    }
    
    public static void setPlatformClassloader(final PlatformClassloader platformClassloader) {
        PlatformEnvironment.platformClassloader = platformClassloader;
    }
    
    public static Platform getCurrentPlatform() {
        return PlatformEnvironment.platformClassloader.getPlatform();
    }
    
    public static String getRunningVersion() {
        return PlatformEnvironment.runningVersion;
    }
    
    public static void setRunningVersion(final String runningVersion) {
        PlatformEnvironment.runningVersion = runningVersion;
        PlatformEnvironment.version = VersionDeserializer.from(runningVersion);
        PlatformEnvironment.ancientPvPVersion = isLowerThan(PlatformEnvironment.ANCIENT_PVP_VERSION);
        PlatformEnvironment.ancientOpenGL = isLowerThan(PlatformEnvironment.ANCIENT_OPENGL);
        PlatformEnvironment.noMojangMappings = isLowerThan(PlatformEnvironment.NO_MOJANG_MAPPINGS);
        PlatformEnvironment.noShader = isLowerThan(PlatformEnvironment.NO_SHADER);
        PlatformEnvironment.newLogo = (isGreaterThan(PlatformEnvironment.NEW_LOGO) || isGreaterThan(PlatformEnvironment.NEW_LOGO_1_20));
    }
    
    public static Path getClientJarPath() {
        return PlatformEnvironment.clientJarPath;
    }
    
    public static void setClientJarPath(final Path clientJarPath) {
        PlatformEnvironment.clientJarPath = clientJarPath;
    }
    
    public static Path getObfuscatedJarPath() {
        return PlatformEnvironment.obfuscatedJarPath;
    }
    
    public static void setObfuscatedJarPath(final Path obfuscatedJarPath) {
        PlatformEnvironment.obfuscatedJarPath = obfuscatedJarPath;
    }
    
    public static boolean isAncientPvPVersion() {
        return PlatformEnvironment.ancientPvPVersion;
    }
    
    public static boolean isAncientOpenGL() {
        return PlatformEnvironment.ancientOpenGL;
    }
    
    public static boolean isNoMojangMappings() {
        return PlatformEnvironment.noMojangMappings;
    }
    
    public static boolean isNoShader() {
        return PlatformEnvironment.noShader;
    }
    
    public static boolean isFreshUI() {
        return PlatformEnvironment.FRESH_UI;
    }
    
    public static Enumeration<URL> getResources(final ClassLoader loader, final String name) throws IOException {
        final PlatformClassloader classloader = getPlatformClassloader();
        if (classloader == null) {
            return Collections.emptyEnumeration();
        }
        return classloader.getResources(loader, name);
    }
    
    @Deprecated
    public static boolean isNewLogo() {
        return PlatformEnvironment.newLogo;
    }
    
    private static boolean isLowerThan(final Version version) {
        final Version platformVersion = PlatformEnvironment.version;
        return platformVersion.isLowerThan(version) || platformVersion.equals(version);
    }
    
    private static boolean isGreaterThan(final Version version) {
        final Version platformVersion = PlatformEnvironment.version;
        return platformVersion.isGreaterThan(version) || platformVersion.equals(version);
    }
    
    static {
        ITEM_COMPONENTS = MinecraftVersions.V24w09a.orNewer();
        ANCIENT_PVP_VERSION = new SemanticVersion("1.8.9");
        ANCIENT_OPENGL = new SemanticVersion("1.12.2");
        NO_MOJANG_MAPPINGS = new SemanticVersion("1.13.2");
        NO_SHADER = new SemanticVersion("1.16.5");
        NEW_LOGO = new CalendarVersion("23w14a");
        NEW_LOGO_1_20 = new SemanticVersion("1.20-pre3");
        FRESH_UI = MinecraftVersions.V24w09a.orNewer();
    }
}
