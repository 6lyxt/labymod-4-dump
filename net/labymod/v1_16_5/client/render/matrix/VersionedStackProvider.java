// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.render.matrix;

import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.matrix.AbstractStackProvider;

public final class VersionedStackProvider extends AbstractStackProvider<dfm, a, b>
{
    public VersionedStackProvider(final dfm poseStack) {
        super(poseStack, () -> poseStack.c().b(), () -> poseStack.c().a());
    }
    
    @Override
    public void translate(final float x, final float y, final float z) {
        ((dfm)this.stack).a((double)x, (double)y, (double)z);
    }
    
    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        VersionedStackProvider.ROTATION_VECTOR.set(x, y, z);
        ((dfm)this.stack).a((d)VersionedStackProvider.MAPPER.toQuaternion(VersionedStackProvider.ROTATION_VECTOR.rotationDegrees(angle)));
    }
    
    @Override
    public void rotateRadians(final float radians, final float x, final float y, final float z) {
        VersionedStackProvider.ROTATION_VECTOR.set(x, y, z);
        ((dfm)this.stack).a((d)VersionedStackProvider.MAPPER.toQuaternion(VersionedStackProvider.ROTATION_VECTOR.rotation(radians)));
    }
    
    @Override
    public void scale(final float x, final float y, final float z) {
        ((dfm)this.stack).a(x, y, z);
    }
    
    @Override
    public void multiply(final FloatMatrix4 matrix) {
        ((dfm)this.stack).c().a().a((b)VersionedStackProvider.MAPPER.toMatrix4f(matrix));
    }
    
    @Override
    public void push() {
        ((dfm)this.stack).a();
    }
    
    @Override
    public void pop() {
        ((dfm)this.stack).b();
    }
    
    @Override
    public void identity() {
        final dfm.a last = ((dfm)this.stack).c();
        last.a().a();
        last.b().c();
    }
}
