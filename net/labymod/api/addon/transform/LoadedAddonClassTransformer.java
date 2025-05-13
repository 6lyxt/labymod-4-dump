// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.addon.transform;

public interface LoadedAddonClassTransformer
{
    void init();
    
    boolean shouldTransform(final String p0, final String p1);
    
    byte[] transform(final String p0, final String p1, final byte[] p2);
}
