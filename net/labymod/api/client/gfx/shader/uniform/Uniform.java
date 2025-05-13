// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

public interface Uniform
{
    void setProgram(final String p0, final int p1);
    
    default void upload() {
        this.upload(false);
    }
    
    void upload(final boolean p0);
    
    int getLocation();
    
    String getName();
}
