// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math.vector;

public interface Matrix4
{
    void translate(final float p0, final float p1, final float p2);
    
    void rotate(final float p0, final float p1, final float p2, final float p3);
    
    void rotateRadians(final float p0, final float p1, final float p2, final float p3);
    
    void scale(final float p0, final float p1, final float p2);
    
    void multiply(final FloatMatrix4 p0);
}
