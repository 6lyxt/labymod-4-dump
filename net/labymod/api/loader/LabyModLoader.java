// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.loader;

import net.labymod.api.Laby;
import net.labymod.api.models.version.Version;
import java.net.URL;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

public interface LabyModLoader
{
    void gameOptions(final List<String> p0, final Path p1, final Path p2, final String p3, final boolean p4, final boolean p5, final String p6);
    
    void loadAddons();
    
    void loadIsolatedLibraries();
    
    InputStream getMixinServiceResourceAsStream(final String p0, final InputStream p1);
    
    byte[] getMixinServiceClassBytes(final String p0, final String p1, final byte[] p2) throws IOException;
    
    void registerMixinResourceSource(final String p0, final URL p1);
    
    Version version();
    
    List<String> getArguments();
    
    Path getGameDirectory();
    
    Path getAssetsDirectory();
    
    String getProfile();
    
    String getEffectiveReleaseChannel();
    
    boolean isLabyModDevelopmentEnvironment();
    
    boolean isAddonDevelopmentEnvironment();
    
    default boolean isDevelopmentEnvironment() {
        return this.isLabyModDevelopmentEnvironment() || this.isAddonDevelopmentEnvironment();
    }
    
    default boolean isDevelopmentEnvironment(final String namespace) {
        final boolean isLabyModNamespace = namespace.equals("labymod");
        final LabyModLoader loader = Laby.labyAPI().labyModLoader();
        return isLabyModNamespace ? loader.isLabyModDevelopmentEnvironment() : loader.isAddonDevelopmentEnvironment();
    }
}
