// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.matrix;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.function.Functional;
import java.util.ArrayDeque;
import java.util.Deque;
import net.labymod.api.util.math.vector.FloatVector3;

public class DynamicStackProvider implements StackProvider
{
    private static final FloatVector3 ROTATION_VECTOR;
    private final Deque<MatrixEntry> stack;
    
    public DynamicStackProvider() {
        this.stack = Functional.of((ArrayDeque)new ArrayDeque(), stack -> stack.add(new MatrixEntry()));
    }
    
    @Override
    public void translate(final float x, final float y, final float z) {
        final MatrixEntry pose = this.stack.getLast();
        pose.position().multiplyWithTranslation(x, y, z);
    }
    
    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        final MatrixEntry currentEntry = this.stack.getLast();
        DynamicStackProvider.ROTATION_VECTOR.set(x, y, z);
        final Quaternion rotation = DynamicStackProvider.ROTATION_VECTOR.rotationDegrees(angle);
        currentEntry.multiply(rotation);
    }
    
    @Override
    public void rotateRadians(final float radians, final float x, final float y, final float z) {
        final MatrixEntry currentEntry = this.stack.getLast();
        DynamicStackProvider.ROTATION_VECTOR.set(x, y, z);
        final Quaternion rotation = DynamicStackProvider.ROTATION_VECTOR.rotation(radians, false);
        currentEntry.multiply(rotation);
    }
    
    @Override
    public void scale(final float x, final float y, final float z) {
        final MatrixEntry currentEntry = this.stack.getLast();
        currentEntry.position().scale(x, y, z);
        if (x == y && y == z) {
            if (x > 0.0f) {
                return;
            }
            currentEntry.normal().multiply(-1.0f);
        }
        final float normalizedX = 1.0f / x;
        final float normalizedY = 1.0f / y;
        final float normalizedZ = 1.0f / z;
        final float inverseCubeRoot = MathHelper.fastInverseCubeRoot(normalizedX * normalizedY * normalizedZ);
        final FloatMatrix3 scaleMatrix = FloatMatrix3.scaleMatrix(inverseCubeRoot * normalizedX, inverseCubeRoot * normalizedY, inverseCubeRoot * normalizedZ);
        currentEntry.normal().multiply(scaleMatrix);
    }
    
    @Override
    public void multiply(final FloatMatrix4 matrix) {
        this.stack.getLast().position().multiply(matrix);
    }
    
    @Override
    public void push() {
        final MatrixEntry last = this.stack.getLast();
        this.stack.addLast(new MatrixEntry(last.position().copy(), last.normal().copy()));
    }
    
    @Override
    public void pop() {
        this.stack.removeLast();
    }
    
    @Override
    public void identity() {
        final MatrixEntry last = this.stack.getLast();
        last.identity();
    }
    
    @Override
    public FloatMatrix4 getPosition() {
        return this.stack.getLast().position();
    }
    
    @Override
    public FloatMatrix3 getNormal() {
        return this.stack.getLast().normal();
    }
    
    @Nullable
    @Override
    public Object getPoseStack() {
        return null;
    }
    
    static {
        ROTATION_VECTOR = new FloatVector3();
    }
}
