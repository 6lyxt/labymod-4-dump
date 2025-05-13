// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.loader.isolated.util;

public final class IsolatedClassLoaders
{
    public static final IsolatedClassLoader LWJGL_CLASS_LOADER;
    public static final IsolatedClassLoader LWJGL2_CLASS_LOADER;
    public static final IsolatedClassLoader JNA_CLASS_LOADER;
    public static final IsolatedClassLoader THIN_LWJGL_LOADER;
    
    private IsolatedClassLoaders() {
    }
    
    static {
        LWJGL_CLASS_LOADER = new IsolatedClassLoader("LWJGL3");
        LWJGL2_CLASS_LOADER = new IsolatedClassLoader("LWJGL2");
        JNA_CLASS_LOADER = new IsolatedClassLoader("JNA");
        THIN_LWJGL_LOADER = new IsolatedClassLoader("THIN-LWJGL");
    }
}
