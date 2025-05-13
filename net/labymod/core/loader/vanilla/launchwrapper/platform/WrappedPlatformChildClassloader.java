// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.vanilla.launchwrapper.platform;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.Manifest;
import java.net.URL;
import java.security.CodeSource;
import net.labymod.api.loader.platform.PlatformChildClassloader;
import net.minecraft.launchwrapper.loader.ChildClassLoader;

public class WrappedPlatformChildClassloader implements ChildClassLoader
{
    private final PlatformChildClassloader wrapped;
    
    public WrappedPlatformChildClassloader(final PlatformChildClassloader wrapped) {
        this.wrapped = wrapped;
    }
    
    public String getName() {
        return this.wrapped.getName();
    }
    
    public Class<?> defineClassObject(final String name, final byte[] data, final int offset, final int length, final CodeSource source) {
        return this.wrapped.defineClassObject(name, data, offset, length, source);
    }
    
    public URL findResourceObject(final String name) {
        return this.wrapped.findResourceObject(name);
    }
    
    public Package findPackageObject(final String name) {
        return this.wrapped.findPackageObject(name);
    }
    
    public Package definePackageObject(final String name, final Manifest manifest, final URL url) {
        return this.wrapped.definePackageObject(name, manifest, url);
    }
    
    public Package definePackageObject(final String name, final String specTitle, final String specVersion, final String specVendor, final String implTitle, final String implVersion, final String implVendor, final URL url) {
        return this.wrapped.definePackageObject(name, specTitle, specVersion, specVendor, implTitle, implVersion, implVendor, url);
    }
    
    public Enumeration<URL> findResourceObjects(final String name) throws IOException {
        return this.wrapped.findResourceObjects(name);
    }
}
