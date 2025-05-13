// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.render.matrix;

import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4fc;
import net.labymod.api.util.math.MathHelper;
import org.joml.Matrix4f;
import net.labymod.api.util.math.vector.FloatMatrix4;
import org.joml.Matrix4fStack;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.client.render.matrix.StackProvider;

public class JomlStackProvider implements StackProvider
{
    private static final FloatMatrix3 NORMAL_IDENTITY;
    private final Matrix4fStack stack;
    
    public JomlStackProvider(final Matrix4fStack stack) {
        this.stack = stack;
    }
    
    @Override
    public void translate(final float x, final float y, final float z) {
        this.stack.translate(x, y, z);
    }
    
    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        this.stack.rotate(angle, x, y, z);
    }
    
    @Override
    public void rotateRadians(final float radians, final float x, final float y, final float z) {
        this.stack.rotate(radians, x, y, z);
    }
    
    @Override
    public void scale(final float x, final float y, final float z) {
        this.stack.scale(x, y, z);
    }
    
    @Override
    public void multiply(final FloatMatrix4 matrix) {
        this.stack.mul((Matrix4fc)MathHelper.mapper().toMatrix4f(matrix));
    }
    
    @Override
    public void push() {
        this.stack.pushMatrix();
    }
    
    @Override
    public void pop() {
        this.stack.popMatrix();
    }
    
    @Override
    public void identity() {
        this.stack.identity();
    }
    
    @Override
    public FloatMatrix4 getPosition() {
        return MathHelper.mapper().fromMatrix4f(this.stack);
    }
    
    @Override
    public FloatMatrix3 getNormal() {
        return JomlStackProvider.NORMAL_IDENTITY;
    }
    
    @Nullable
    @Override
    public Object getPoseStack() {
        return this.stack;
    }
    
    static {
        NORMAL_IDENTITY = new FloatMatrix3().identity();
    }
}
