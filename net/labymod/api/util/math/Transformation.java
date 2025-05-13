// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math;

import net.labymod.api.client.render.matrix.Stack;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.debug.DebugNameable;

public class Transformation implements DebugNameable
{
    private static final Transformation[] EMPTY_TRANSFORMATION;
    private static final FloatMatrix4 SCALE_MATRIX;
    private final FloatVector3 translation;
    private final FloatVector3 rotation;
    private final FloatVector3 scale;
    private final FloatMatrix4 transformationMatrix;
    @Nullable
    private String debugName;
    
    public Transformation() {
        this.transformationMatrix = new FloatMatrix4().identity();
        this.translation = new FloatVector3();
        this.rotation = new FloatVector3();
        this.scale = new FloatVector3(1.0f, 1.0f, 1.0f);
    }
    
    public Transformation(final FloatVector3 translation, final FloatVector3 rotation, final FloatVector3 scale) {
        this.transformationMatrix = new FloatMatrix4().identity();
        this.translation = translation;
        this.rotation = rotation;
        this.scale = scale;
    }
    
    public void setTranslation(final float x, final float y, final float z) {
        this.translation.set(x, y, z);
    }
    
    public void setTranslation(final FloatVector3 vector) {
        this.translation.set(vector);
    }
    
    public void setScale(final float scale) {
        this.scale.set(scale, scale, scale);
    }
    
    public void setScale(final float x, final float y, final float z) {
        this.scale.set(x, y, z);
    }
    
    public void setScale(final FloatVector3 vector) {
        this.scale.set(vector);
    }
    
    public void setRotation(final FloatVector3 rotation) {
        this.rotation.set(rotation);
    }
    
    public void setRotationX(final float x) {
        this.rotation.setX(x);
    }
    
    public void setRotationY(final float y) {
        this.rotation.setY(y);
    }
    
    public void setRotationZ(final float z) {
        this.rotation.setZ(z);
    }
    
    public void setRotation(final float x, final float y, final float z) {
        this.rotation.set(x, y, z);
    }
    
    public void set(final Transformation transformation) {
        this.translation.set(transformation.translation);
        this.rotation.set(transformation.rotation);
        this.scale.set(transformation.scale);
    }
    
    public void transform(final Stack stack) {
        this.transform(stack, Transformation.EMPTY_TRANSFORMATION);
    }
    
    public void transform(final Stack stack, final Transformation transformation) {
        this.translate(stack, this);
        this.translate(stack, transformation);
        this.rotateZ(stack, this);
        this.rotateZ(stack, transformation);
        this.rotateY(stack, this);
        this.rotateY(stack, transformation);
        this.rotateX(stack, this);
        this.rotateX(stack, transformation);
        this.scale(stack, this);
        this.scale(stack, transformation);
    }
    
    public void transform(final Stack stack, final Transformation... transformations) {
        this.translate(stack, this);
        for (final Transformation transformation : transformations) {
            this.translate(stack, transformation);
        }
        this.rotateZ(stack, this);
        for (final Transformation transformation : transformations) {
            this.rotateZ(stack, transformation);
        }
        this.rotateY(stack, this);
        for (final Transformation transformation : transformations) {
            this.rotateY(stack, transformation);
        }
        this.rotateX(stack, this);
        for (final Transformation transformation : transformations) {
            this.rotateX(stack, transformation);
        }
        this.scale(stack, this);
        for (final Transformation transformation : transformations) {
            this.scale(stack, transformation);
        }
    }
    
    private void translate(final Stack stack, final Transformation transformation) {
        final FloatVector3 translation = transformation.getTranslation();
        stack.translate(translation.getX() / 16.0f, translation.getY() / 16.0f, translation.getZ() / 16.0f);
    }
    
    private void rotateZ(final Stack stack, final Transformation transformation) {
        final FloatVector3 rotation = transformation.getRotation();
        final float z = rotation.getZ();
        if (z == 0.0f) {
            return;
        }
        stack.rotateRadians(z, 0.0f, 0.0f, 1.0f);
    }
    
    private void rotateY(final Stack stack, final Transformation transformation) {
        final FloatVector3 rotation = transformation.getRotation();
        final float y = rotation.getY();
        if (y == 0.0f) {
            return;
        }
        stack.rotateRadians(y, 0.0f, 1.0f, 0.0f);
    }
    
    private void rotateX(final Stack stack, final Transformation transformation) {
        final FloatVector3 rotation = transformation.getRotation();
        final float x = rotation.getX();
        if (x == 0.0f) {
            return;
        }
        stack.rotateRadians(x, 1.0f, 0.0f, 0.0f);
    }
    
    private void scale(final Stack stack, final Transformation transformation) {
        final FloatVector3 scale = transformation.getScale();
        stack.scale(scale.getX(), scale.getY(), scale.getZ());
    }
    
    public FloatVector3 getTranslation() {
        return this.translation;
    }
    
    public FloatVector3 getScale() {
        return this.scale;
    }
    
    public FloatVector3 getRotation() {
        return this.rotation;
    }
    
    public void reset() {
        this.setTranslation(0.0f, 0.0f, 0.0f);
        this.setRotation(0.0f, 0.0f, 0.0f);
        this.setScale(1.0f);
    }
    
    @Override
    public void setDebugName(final String name) {
        this.debugName = name;
    }
    
    @Override
    public String getDebugName() {
        return this.debugName;
    }
    
    static {
        EMPTY_TRANSFORMATION = new Transformation[0];
        SCALE_MATRIX = new FloatMatrix4().identity();
    }
}
