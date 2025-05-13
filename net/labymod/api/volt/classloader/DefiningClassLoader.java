// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.classloader;

public class DefiningClassLoader extends ClassLoader
{
    public DefiningClassLoader(final ClassLoader parent) {
        super(parent);
    }
    
    public <T> Class<T> defineClass(final String name, final byte[] data) {
        return (Class<T>)this.defineClass(name, data, 0, data.length);
    }
}
