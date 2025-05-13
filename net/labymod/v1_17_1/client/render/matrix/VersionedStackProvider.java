// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.render.matrix;

import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.matrix.AbstractStackProvider;

public final class VersionedStackProvider extends AbstractStackProvider<dql, c, d>
{
    public VersionedStackProvider(final dql poseStack) {
        super(poseStack, () -> poseStack.c().b(), () -> poseStack.c().a());
    }
    
    @Override
    public void translate(final float x, final float y, final float z) {
        ((dql)this.stack).a((double)x, (double)y, (double)z);
    }
    
    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        VersionedStackProvider.ROTATION_VECTOR.set(x, y, z);
        ((dql)this.stack).a((g)VersionedStackProvider.MAPPER.toQuaternion(VersionedStackProvider.ROTATION_VECTOR.rotationDegrees(angle)));
    }
    
    @Override
    public void rotateRadians(final float radians, final float x, final float y, final float z) {
        VersionedStackProvider.ROTATION_VECTOR.set(x, y, z);
        ((dql)this.stack).a((g)VersionedStackProvider.MAPPER.toQuaternion(VersionedStackProvider.ROTATION_VECTOR.rotation(radians)));
    }
    
    @Override
    public void scale(final float x, final float y, final float z) {
        ((dql)this.stack).a(x, y, z);
    }
    
    @Override
    public void multiply(final FloatMatrix4 matrix) {
        ((dql)this.stack).a((d)VersionedStackProvider.MAPPER.toMatrix4f(matrix));
    }
    
    @Override
    public void push() {
        ((dql)this.stack).a();
    }
    
    @Override
    public void pop() {
        ((dql)this.stack).b();
    }
    
    @Override
    public void identity() {
        ((dql)this.stack).e();
    }
}
