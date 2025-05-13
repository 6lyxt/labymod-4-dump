// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.loader.platform;

import java.io.IOException;
import java.util.Enumeration;
import java.io.File;
import org.objectweb.asm.commons.Remapper;
import java.util.List;
import java.net.URL;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface PlatformClassloader
{
    default void addPath(final Path path) {
        try {
            this.addUrl(path.toUri().toURL());
        }
        catch (final MalformedURLException exception) {
            exception.printStackTrace();
        }
    }
    
    void addUrl(final URL p0) throws MalformedURLException;
    
    Class<?> findClass(final String p0) throws ClassNotFoundException;
    
    void registerChildClassloader(final String p0, final PlatformChildClassloader p1);
    
    void registerTransformer(final TransformerPhase p0, final String p1);
    
    void registerTransformer(final TransformerPhase p0, final PlatformClassTransformer p1);
    
    PlatformAccessWidener getAccessWidener();
    
    ClassLoader getPlatformClassloader();
    
    @DevelopmentEnvironmentOnly
    List<URL> getPotentialClasspathAddons();
    
    List<URL> getClasspath();
    
    Platform getPlatform();
    
    Remapper getRuntimeRemapper();
    
    default boolean searchOnClasspath(final String name) {
        final String property = System.getProperty("java.class.path");
        final String[] split;
        final String[] libraries = split = property.split(File.pathSeparator);
        for (final String library : split) {
            if (library.contains(name)) {
                return true;
            }
        }
        return false;
    }
    
    Enumeration<URL> getResources(final ClassLoader p0, final String p1) throws IOException;
    
    public enum TransformerPhase
    {
        PRE, 
        NORMAL, 
        POST;
    }
}
