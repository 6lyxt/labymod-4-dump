// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.mixins.mojang.math;

import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.math.MathHelper;
import org.spongepowered.asm.mixin.Shadow;
import net.labymod.api.util.math.vector.FloatVector3;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.util.math.vector.Matrix4;

@Mixin({ d.class })
public abstract class MixinMatrix4f implements Matrix4
{
    private static final FloatVector3 ROTATION_VECTOR;
    @Shadow
    protected float d;
    @Shadow
    protected float h;
    @Shadow
    protected float l;
    
    @Shadow
    public abstract void a(final g p0);
    
    @Shadow
    public abstract void b(final d p0);
    
    @Override
    public void translate(final float x, final float y, final float z) {
        this.d += x;
        this.h += y;
        this.l += z;
    }
    
    @Override
    public void rotate(final float angle, final float x, final float y, final float z) {
        final g quaternion = MathHelper.mapper().toQuaternion(MixinMatrix4f.ROTATION_VECTOR.rotationDegrees(angle));
        this.a(quaternion);
    }
    
    @Override
    public void rotateRadians(final float radians, final float x, final float y, final float z) {
        final g quaternion = MathHelper.mapper().toQuaternion(MixinMatrix4f.ROTATION_VECTOR.rotation(radians));
        this.a(quaternion);
    }
    
    @Override
    public void scale(final float x, final float y, final float z) {
        this.multiply(FloatMatrix4.scaleMatrix(x, y, z));
    }
    
    @Override
    public void multiply(final FloatMatrix4 matrix) {
        this.b(MathHelper.mapper().toMatrix4f(matrix));
    }
    
    static {
        ROTATION_VECTOR = new FloatVector3(0.0f, 0.0f, 0.0f);
    }
}
