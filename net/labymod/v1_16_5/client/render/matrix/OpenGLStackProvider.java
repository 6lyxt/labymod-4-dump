// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.render.matrix;

import org.lwjgl.BufferUtils;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.client.render.matrix.Stack;
import java.nio.FloatBuffer;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.matrix.StackProvider;

public class OpenGLStackProvider implements StackProvider
{
    private static final FloatMatrix4 IDENTITY;
    private static final FloatBuffer MATRIX_BUFFER;
    public static final Stack DEFAULT_STACK;
    private static final FloatMatrix3 EMPTY_NORMAL_MATRIX;
    
    @NotNull
    private static Stack createStack() {
        return Stack.create(new OpenGLStackProvider());
    }
    
    @Override
    public void translate(final float x, final float y, final float z) {
        dem.c(x, y, z);
    }
    
    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        dem.c(angle, x, y, z);
    }
    
    @Override
    public void rotateRadians(final float radians, final float x, final float y, final float z) {
        dem.c((float)Math.toDegrees(radians), x, y, z);
    }
    
    @Override
    public void scale(final float x, final float y, final float z) {
        dem.b(x, y, z);
    }
    
    @Override
    public void multiply(final FloatMatrix4 matrix) {
        matrix.store(OpenGLStackProvider.MATRIX_BUFFER);
        dem.a(OpenGLStackProvider.MATRIX_BUFFER);
    }
    
    @Override
    public void push() {
        dem.Q();
    }
    
    @Override
    public void pop() {
        dem.R();
    }
    
    @Override
    public void identity() {
        dem.P();
    }
    
    @Override
    public FloatMatrix3 getNormal() {
        return OpenGLStackProvider.EMPTY_NORMAL_MATRIX;
    }
    
    @Nullable
    @Override
    public Object getPoseStack() {
        return null;
    }
    
    @Override
    public FloatMatrix4 getPosition() {
        return OpenGLStackProvider.IDENTITY;
    }
    
    static {
        IDENTITY = FloatMatrix4.newIdentity();
        MATRIX_BUFFER = BufferUtils.createFloatBuffer(16);
        DEFAULT_STACK = createStack();
        EMPTY_NORMAL_MATRIX = new FloatMatrix3();
    }
}
