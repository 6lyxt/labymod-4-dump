// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.addon;

import java.util.jar.Manifest;
import java.io.IOException;
import java.util.Enumeration;
import java.security.CodeSource;
import java.util.Iterator;
import net.labymod.api.addon.LoadedAddon;
import java.net.URL;
import net.labymod.api.models.addon.info.InstalledAddonInfo;
import net.labymod.api.loader.platform.PlatformChildClassloader;
import java.net.URLClassLoader;

public class AddonClassLoader extends URLClassLoader implements PlatformChildClassloader
{
    private final InstalledAddonInfo addonInfo;
    
    public AddonClassLoader(final URL[] urls, final ClassLoader parent, final InstalledAddonInfo addonInfo) {
        super(urls, parent);
        this.addonInfo = addonInfo;
    }
    
    public InstalledAddonInfo getAddonInfo() {
        return this.addonInfo;
    }
    
    public void addURL(final URL url) {
        super.addURL(url);
    }
    
    public boolean containsClass(final String name) {
        try {
            super.loadClass(name);
            return true;
        }
        catch (final ClassNotFoundException exception) {
            return false;
        }
    }
    
    public Class<?> loadClassDirect(final String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }
    
    @Override
    public Class<?> loadClass(final String name) throws ClassNotFoundException {
        try {
            return this.loadClassDirect(name);
        }
        catch (final ClassNotFoundException ex) {
            for (final LoadedAddon addon : DefaultAddonService.getInstance().getLoadedAddons()) {
                if (addon.info() != this.addonInfo) {
                    if (!(addon.getClassLoader() instanceof AddonClassLoader)) {
                        continue;
                    }
                    try {
                        return ((AddonClassLoader)addon.getClassLoader()).loadClassDirect(name);
                    }
                    catch (final ClassNotFoundException ex2) {
                        continue;
                    }
                    break;
                }
            }
            throw new ClassNotFoundException("Cannot find class '" + name + "' in any addon");
        }
    }
    
    @Override
    public ClassLoader getChildClassloader() {
        return this;
    }
    
    @Override
    public Class<?> defineClassObject(final String name, final byte[] data, final int offset, final int length, final CodeSource source) {
        return super.defineClass(name, data, offset, length, source);
    }
    
    @Override
    public URL findResourceObject(final String name) {
        return super.findResource(name);
    }
    
    @Override
    public Enumeration<URL> findResourceObjects(final String name) throws IOException {
        return this.findResources(name);
    }
    
    @Override
    public Package findPackageObject(final String name) {
        return super.getPackage(name);
    }
    
    @Override
    public Package definePackageObject(final String name, final Manifest manifest, final URL url) {
        return super.definePackage(name, manifest, url);
    }
    
    @Override
    public Package definePackageObject(final String name, final String specTitle, final String specVersion, final String specVendor, final String implTitle, final String implVersion, final String implVendor, final URL url) {
        return super.definePackage(name, specTitle, specVersion, specVendor, implTitle, implVersion, implVendor, url);
    }
}
