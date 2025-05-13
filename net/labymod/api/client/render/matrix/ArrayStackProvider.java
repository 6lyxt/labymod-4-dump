// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.matrix;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.api.Laby;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.FloatVector3;

public class ArrayStackProvider implements StackProvider
{
    private static final FloatVector3 ROTATION_VECTOR;
    private final MatrixEntry[] matrices;
    private MatrixEntry currentEntry;
    private int currentIndex;
    
    public ArrayStackProvider() {
        this(32);
    }
    
    public ArrayStackProvider(final int size) {
        if (size < 1) {
            throw new IllegalStateException("size must be >= 1");
        }
        this.matrices = new MatrixEntry[size + 1];
        for (int index = 0; index < this.matrices.length; ++index) {
            this.matrices[index] = new MatrixEntry();
        }
        this.currentEntry = this.getLast();
        this.push();
    }
    
    @Override
    public void translate(final float x, final float y, final float z) {
        final MatrixEntry currentEntry = this.currentEntry;
        currentEntry.position().multiplyWithTranslation(x, y, z);
    }
    
    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        final MatrixEntry currentEntry = this.currentEntry;
        ArrayStackProvider.ROTATION_VECTOR.set(x, y, z);
        final Quaternion rotation = ArrayStackProvider.ROTATION_VECTOR.rotationDegrees(angle);
        currentEntry.multiply(rotation);
    }
    
    @Override
    public void rotateRadians(final float radians, final float x, final float y, final float z) {
        final MatrixEntry currentEntry = this.currentEntry;
        ArrayStackProvider.ROTATION_VECTOR.set(x, y, z);
        final Quaternion rotation = ArrayStackProvider.ROTATION_VECTOR.rotation(radians, false);
        currentEntry.multiply(rotation);
    }
    
    @Override
    public void scale(final float x, final float y, final float z) {
        final MatrixEntry currentEntry = this.currentEntry;
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
        this.currentEntry.position().multiply(matrix);
    }
    
    @Override
    public void push() {
        if (this.currentIndex == this.matrices.length - 2) {
            IdeUtil.doPauseOrThrown(() -> new IllegalStateException("The maximum stack size of " + this.matrices.length + " has been reached (" + Laby.gfx().capabilities().dumpOpenGlStates()));
        }
        final MatrixEntry newEntry = this.matrices[++this.currentIndex];
        newEntry.store(this.currentEntry);
        this.currentEntry = newEntry;
    }
    
    @Override
    public void pop() {
        if (this.currentIndex == 0) {
            IdeUtil.doPauseOrThrown(() -> new IllegalStateException("Already at the bottom of the stack"));
        }
        this.matrices[this.currentIndex--].identity();
        this.currentEntry = this.getLast();
    }
    
    @Override
    public void identity() {
        final MatrixEntry last = this.getLast();
        last.identity();
    }
    
    @Override
    public FloatMatrix4 getPosition() {
        return this.currentEntry.position();
    }
    
    @Override
    public FloatMatrix3 getNormal() {
        return this.currentEntry.normal();
    }
    
    @Nullable
    @Override
    public Object getPoseStack() {
        return null;
    }
    
    private MatrixEntry getLast() {
        return this.matrices[this.currentIndex];
    }
    
    static {
        ROTATION_VECTOR = new FloatVector3();
    }
}
