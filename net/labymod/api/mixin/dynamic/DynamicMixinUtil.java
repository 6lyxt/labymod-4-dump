// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mixin.dynamic;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public final class DynamicMixinUtil
{
    private static LoadResources loadResources;
    
    public static void setLoadResources(final LoadResources loadResources) {
        DynamicMixinUtil.loadResources = loadResources;
    }
    
    public static Enumeration<URL> getResources(final ClassLoader loader, final String name) throws IOException {
        return DynamicMixinUtil.loadResources.findResources(loader, name);
    }
    
    static {
        DynamicMixinUtil.loadResources = ClassLoader::getResources;
    }
    
    public interface LoadResources
    {
        Enumeration<URL> findResources(final ClassLoader p0, final String p1) throws IOException;
    }
}
