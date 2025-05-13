// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.volt.asm.util;

import java.net.URL;
import java.util.function.Function;
import java.util.function.IntSupplier;

public class ASMContext
{
    private static final IntSupplier CLASS_VERSION;
    private static ClassLoader platformClassLoader;
    private static boolean devEnvironment;
    private static Function<String, URL> resourceFinder;
    
    public static boolean isDevEnvironment() {
        return ASMContext.devEnvironment;
    }
    
    public static void setDevEnvironment(final boolean devEnvironment) {
        ASMContext.devEnvironment = devEnvironment;
    }
    
    public static ClassLoader getPlatformClassLoader() {
        return ASMContext.platformClassLoader;
    }
    
    public static void setPlatformClassLoader(final ClassLoader platformClassLoader) {
        ASMContext.platformClassLoader = platformClassLoader;
    }
    
    public static void setResourceFinder(final Function<String, URL> resourceFinder) {
        ASMContext.resourceFinder = resourceFinder;
    }
    
    public static URL findResource(final String name) {
        return ASMContext.resourceFinder.apply(name);
    }
    
    public static int getClassVersion() {
        return ASMContext.CLASS_VERSION.getAsInt();
    }
    
    static {
        CLASS_VERSION = (() -> 52);
        ASMContext.devEnvironment = false;
        ASMContext.resourceFinder = (Function<String, URL>)(name -> ASMContext.platformClassLoader.getResource(name));
    }
}
