// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.matrix;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix4;

public interface StackProvider
{
    void translate(final float p0, final float p1, final float p2);
    
    void rotate(final float p0, final float p1, final float p2, final float p3);
    
    void rotateRadians(final float p0, final float p1, final float p2, final float p3);
    
    void scale(final float p0, final float p1, final float p2);
    
    void multiply(final FloatMatrix4 p0);
    
    void push();
    
    void pop();
    
    void identity();
    
    FloatMatrix4 getPosition();
    
    FloatMatrix3 getNormal();
    
    @Nullable
    Object getPoseStack();
}
