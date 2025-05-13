// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx;

@Deprecated(forRemoval = true, since = "4.2.41")
public interface GFXObject
{
    int getHandle();
    
    default long getContextHandle() {
        return 0L;
    }
}
