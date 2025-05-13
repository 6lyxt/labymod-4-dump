// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.render.matrix;

import org.lwjgl.BufferUtils;
import net.labymod.api.client.gfx.pipeline.util.MatrixTracker;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.vector.FloatMatrix4;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.client.render.matrix.Stack;
import java.nio.FloatBuffer;
import net.labymod.api.client.render.matrix.StackProvider;

public class VersionedStackProvider implements StackProvider
{
    private static final FloatBuffer MATRIX_BUFFER;
    public static final Stack DEFAULT_STACK;
    private static final FloatMatrix3 EMPTY_NORMAL_MATRIX;
    
    @NotNull
    private static Stack createStack() {
        return Stack.create(new VersionedStackProvider());
    }
    
    @Override
    public void translate(final float x, final float y, final float z) {
        bus.c(x, y, z);
    }
    
    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        bus.b(angle, x, y, z);
    }
    
    @Override
    public void rotateRadians(final float radians, final float x, final float y, final float z) {
        bus.b((float)Math.toDegrees(radians), x, y, z);
    }
    
    @Override
    public void scale(final float x, final float y, final float z) {
        bus.b(x, y, z);
    }
    
    @Override
    public void multiply(final FloatMatrix4 matrix) {
        matrix.store(VersionedStackProvider.MATRIX_BUFFER);
        bus.a(VersionedStackProvider.MATRIX_BUFFER);
    }
    
    @Override
    public void push() {
        bus.G();
    }
    
    @Override
    public void pop() {
        bus.H();
    }
    
    @Override
    public void identity() {
        bus.F();
    }
    
    @Override
    public FloatMatrix3 getNormal() {
        return VersionedStackProvider.EMPTY_NORMAL_MATRIX;
    }
    
    @Nullable
    @Override
    public Object getPoseStack() {
        return null;
    }
    
    @Override
    public FloatMatrix4 getPosition() {
        return MatrixTracker.MODEL_VIEW_MATRIX.getProvider().getPosition();
    }
    
    static {
        MATRIX_BUFFER = BufferUtils.createFloatBuffer(16);
        DEFAULT_STACK = createStack();
        EMPTY_NORMAL_MATRIX = new FloatMatrix3();
    }
}
