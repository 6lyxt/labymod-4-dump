// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.io.IOException;
import java.nio.file.Path;
import java.net.URL;
import java.net.URLClassLoader;

public class IsolatedClassLoader extends URLClassLoader
{
    public IsolatedClassLoader() {
        super(new URL[0]);
    }
    
    public IsolatedClassLoader(final ClassLoader parent) {
        super(new URL[0], parent);
    }
    
    public void addPath(final Path path) throws IOException {
        this.addURL(path.toUri().toURL());
    }
    
    public void addURL(final URL url) {
        super.addURL(url);
    }
}
