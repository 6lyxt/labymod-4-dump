// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.isolated.util;

import java.util.zip.ZipEntry;
import java.util.jar.Manifest;
import java.util.Enumeration;
import java.security.CodeSource;
import java.io.InputStream;
import java.util.Iterator;
import net.labymod.api.util.io.zip.Zips;
import java.io.IOException;
import java.util.ArrayList;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.loader.platform.PlatformChildClassloader;
import java.net.URLClassLoader;

public class IsolatedClassLoader extends URLClassLoader implements PlatformChildClassloader
{
    private final String name;
    private final List<Path> files;
    
    public IsolatedClassLoader(final String name) {
        super(new URL[0]);
        this.files = new ArrayList<Path>();
        this.name = name;
    }
    
    public IsolatedClassLoader(final ClassLoader parent, final String name) {
        super(new URL[0], parent);
        this.files = new ArrayList<Path>();
        this.name = name;
    }
    
    public void addPath(final Path path) throws IOException {
        this.files.add(path);
        this.addURL(path.toUri().toURL());
    }
    
    public List<Path> getFiles() {
        return this.files;
    }
    
    public byte[] findJarResource(final String name) {
        final DataHolder dataHolder = new DataHolder();
        for (final Path file : this.files) {
            if (dataHolder.getData() != null) {
                continue;
            }
            try {
                Zips.read(file, (entry, data) -> {
                    if (entry.getName().startsWith("org/lwjgl/glfw")) {
                        System.out.println(entry.getName());
                    }
                    if (entry.getName().equals(name)) {
                        dataHolder.setData(data);
                        return Boolean.valueOf(true);
                    }
                    else {
                        return Boolean.valueOf(false);
                    }
                });
            }
            catch (final IOException exception) {
                exception.printStackTrace();
            }
        }
        return dataHolder.getData();
    }
    
    @Override
    public ClassLoader getChildClassloader() {
        return this;
    }
    
    public byte[] getClassData(final String name) {
        final URL resource = this.findResource(name.replace(".", "/").concat(".class"));
        if (resource == null) {
            return null;
        }
        try (final InputStream stream = resource.openStream()) {
            return stream.readAllBytes();
        }
        catch (final IOException exception) {
            return null;
        }
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return this.getName();
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
    
    private static class DataHolder
    {
        private byte[] data;
        
        public byte[] getData() {
            return this.data;
        }
        
        public void setData(final byte[] data) {
            this.data = data;
        }
    }
}
