// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.loader.platform;

import java.util.jar.Manifest;
import java.io.IOException;
import java.util.Enumeration;
import java.net.URL;
import java.security.CodeSource;

public interface PlatformChildClassloader
{
    ClassLoader getChildClassloader();
    
    default String getName() {
        return this.getClass().getName();
    }
    
    Class<?> defineClassObject(final String p0, final byte[] p1, final int p2, final int p3, final CodeSource p4);
    
    URL findResourceObject(final String p0);
    
    Enumeration<URL> findResourceObjects(final String p0) throws IOException;
    
    Package findPackageObject(final String p0);
    
    Package definePackageObject(final String p0, final Manifest p1, final URL p2);
    
    Package definePackageObject(final String p0, final String p1, final String p2, final String p3, final String p4, final String p5, final String p6, final URL p7);
}
