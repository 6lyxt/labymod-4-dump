// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.client.render.matrix;

import net.labymod.api.util.math.vector.FloatMatrix4;
import org.joml.Quaternionf;
import org.joml.Matrix4f;
import org.joml.Matrix3f;
import net.labymod.api.client.render.matrix.AbstractStackProvider;

public final class VersionedStackProvider extends AbstractStackProvider<ffv, Matrix3f, Matrix4f>
{
    public VersionedStackProvider(final ffv poseStack) {
        super(poseStack, () -> poseStack.c().b(), () -> poseStack.c().a());
    }
    
    @Override
    public void translate(final float x, final float y, final float z) {
        ((ffv)this.stack).a(x, y, z);
    }
    
    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        VersionedStackProvider.ROTATION_VECTOR.set(x, y, z);
        ((ffv)this.stack).a((Quaternionf)VersionedStackProvider.MAPPER.toQuaternion(VersionedStackProvider.ROTATION_VECTOR.rotationDegrees(angle)));
    }
    
    @Override
    public void rotateRadians(final float radians, final float x, final float y, final float z) {
        VersionedStackProvider.ROTATION_VECTOR.set(x, y, z);
        ((ffv)this.stack).a((Quaternionf)VersionedStackProvider.MAPPER.toQuaternion(VersionedStackProvider.ROTATION_VECTOR.rotation(radians)));
    }
    
    @Override
    public void scale(final float x, final float y, final float z) {
        ((ffv)this.stack).b(x, y, z);
    }
    
    @Override
    public void multiply(final FloatMatrix4 matrix) {
        ((ffv)this.stack).a((Matrix4f)VersionedStackProvider.MAPPER.toMatrix4f(matrix));
    }
    
    @Override
    public void push() {
        ((ffv)this.stack).a();
    }
    
    @Override
    public void pop() {
        ((ffv)this.stack).b();
    }
    
    @Override
    public void identity() {
        ((ffv)this.stack).e();
    }
}
