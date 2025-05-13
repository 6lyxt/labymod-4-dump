// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.render.matrix;

import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.matrix.AbstractStackProvider;

public final class VersionedStackProvider extends AbstractStackProvider<eaq, c, d>
{
    public VersionedStackProvider(final eaq poseStack) {
        super(poseStack, () -> poseStack.c().b(), () -> poseStack.c().a());
    }
    
    @Override
    public void translate(final float x, final float y, final float z) {
        ((eaq)this.stack).a((double)x, (double)y, (double)z);
    }
    
    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        VersionedStackProvider.ROTATION_VECTOR.set(x, y, z);
        ((eaq)this.stack).a((g)VersionedStackProvider.MAPPER.toQuaternion(VersionedStackProvider.ROTATION_VECTOR.rotationDegrees(angle)));
    }
    
    @Override
    public void rotateRadians(final float radians, final float x, final float y, final float z) {
        VersionedStackProvider.ROTATION_VECTOR.set(x, y, z);
        ((eaq)this.stack).a((g)VersionedStackProvider.MAPPER.toQuaternion(VersionedStackProvider.ROTATION_VECTOR.rotation(radians)));
    }
    
    @Override
    public void scale(final float x, final float y, final float z) {
        ((eaq)this.stack).a(x, y, z);
    }
    
    @Override
    public void multiply(final FloatMatrix4 matrix) {
        ((eaq)this.stack).a((d)VersionedStackProvider.MAPPER.toMatrix4f(matrix));
    }
    
    @Override
    public void push() {
        ((eaq)this.stack).a();
    }
    
    @Override
    public void pop() {
        ((eaq)this.stack).b();
    }
    
    @Override
    public void identity() {
        ((eaq)this.stack).e();
    }
}
