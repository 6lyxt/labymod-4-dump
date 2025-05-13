// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_4.client.util.math;

import org.joml.Quaternionf;
import org.joml.Matrix4f;
import org.joml.Matrix3f;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.util.math.GameMathMapper;

@Singleton
@Implements(GameMathMapper.class)
public class VersionedGameMathMapper implements GameMathMapper
{
    private static final FloatMatrix4 MATRIX4F_IDENTITY;
    private static final FloatMatrix3 MATRIX3F_IDENTITY;
    private static final Quaternion QUATERNION_IDENTITY;
    private static final AxisAlignedBoundingBox BOX_IDENTITY;
    private static final FloatMatrix3 SHARED_MATRIX3F;
    private static final FloatMatrix4 SHARED_MATRIX4F;
    private static final Matrix3f MOJANG_SHARED_MATRIX3F;
    private static final Matrix4f MOJANG_SHARED_MATRIX4F;
    
    @Override
    public FloatMatrix4 fromMatrix4f(final Object matrix) {
        if (matrix instanceof final Matrix4f matrix4f) {
            return this._fromMatrix4f(matrix4f);
        }
        return VersionedGameMathMapper.MATRIX4F_IDENTITY;
    }
    
    @Override
    public <T> T toMatrix4f(final FloatMatrix4 matrix) {
        return (T)this._toMatrix4f(matrix);
    }
    
    @Override
    public FloatMatrix3 fromMatrix3f(final Object matrix) {
        if (matrix instanceof final Matrix3f matrix3f) {
            return this._fromMatrix3f(matrix3f);
        }
        return VersionedGameMathMapper.MATRIX3F_IDENTITY;
    }
    
    @Override
    public <T> T toMatrix3f(final FloatMatrix3 matrix) {
        return (T)this._toMatrix3f(matrix);
    }
    
    @Override
    public void applyFloatMatrix4(final FloatMatrix4 source, final Object destination) {
        if (destination instanceof final Matrix4f matrix4f) {
            this._applyFloatMatrix4(source, matrix4f);
        }
    }
    
    @Override
    public void applyMatrix4f(final Object source, final FloatMatrix4 destination) {
        if (source instanceof final Matrix4f matrix4f) {
            this._applyMatrix4f(matrix4f, destination);
        }
    }
    
    @Override
    public void applyFloatMatrix3(final FloatMatrix3 source, final Object destination) {
        if (destination instanceof final Matrix3f matrix3f) {
            this._applyFloatMatrix3(source, matrix3f);
        }
    }
    
    @Override
    public void applyMatrix3f(final Object source, final FloatMatrix3 destination) {
        if (source instanceof final Matrix3f matrix3f) {
            this._applyMatrix3f(matrix3f, destination);
        }
    }
    
    @Override
    public Quaternion fromQuaternion(final Object quaternion) {
        if (quaternion instanceof final Quaternionf gameQuaternion) {
            return new Quaternion(gameQuaternion.x(), gameQuaternion.y(), gameQuaternion.z(), gameQuaternion.w());
        }
        return VersionedGameMathMapper.QUATERNION_IDENTITY;
    }
    
    @Override
    public <T> T toQuaternion(final Quaternion quaternion) {
        return (T)new Quaternionf(quaternion.getX(), quaternion.getY(), quaternion.getZ(), quaternion.getW());
    }
    
    @Override
    public AxisAlignedBoundingBox fromAABB(final Object aabb) {
        if (aabb instanceof final ecz gameAABB) {
            return new AxisAlignedBoundingBox(gameAABB.a, gameAABB.b, gameAABB.c, gameAABB.d, gameAABB.e, gameAABB.f);
        }
        return VersionedGameMathMapper.BOX_IDENTITY;
    }
    
    @Override
    public <T> T toAABB(final AxisAlignedBoundingBox aabb) {
        return (T)new ecz(aabb.getMinX(), aabb.getMinY(), aabb.getMinZ(), aabb.getMaxX(), aabb.getMaxY(), aabb.getMaxZ());
    }
    
    private Matrix4f _toMatrix4f(final FloatMatrix4 matrix) {
        VersionedGameMathMapper.MOJANG_SHARED_MATRIX4F.identity();
        this._applyFloatMatrix4(matrix, VersionedGameMathMapper.MOJANG_SHARED_MATRIX4F);
        return VersionedGameMathMapper.MOJANG_SHARED_MATRIX4F;
    }
    
    private Matrix3f _toMatrix3f(final FloatMatrix3 matrix) {
        VersionedGameMathMapper.MOJANG_SHARED_MATRIX3F.identity();
        this._applyFloatMatrix3(matrix, VersionedGameMathMapper.MOJANG_SHARED_MATRIX3F);
        return VersionedGameMathMapper.MOJANG_SHARED_MATRIX3F;
    }
    
    private FloatMatrix4 _fromMatrix4f(final Matrix4f matrix) {
        VersionedGameMathMapper.SHARED_MATRIX4F.identity();
        this._applyMatrix4f(matrix, VersionedGameMathMapper.SHARED_MATRIX4F);
        return VersionedGameMathMapper.SHARED_MATRIX4F;
    }
    
    private FloatMatrix3 _fromMatrix3f(final Matrix3f matrix) {
        VersionedGameMathMapper.SHARED_MATRIX3F.identity();
        this._applyMatrix3f(matrix, VersionedGameMathMapper.SHARED_MATRIX3F);
        return VersionedGameMathMapper.SHARED_MATRIX3F;
    }
    
    private void _applyFloatMatrix3(final FloatMatrix3 source, final Matrix3f destination) {
        destination.set(source.getM00(), source.getM10(), source.getM20(), source.getM01(), source.getM11(), source.getM21(), source.getM02(), source.getM12(), source.getM22());
    }
    
    private void _applyMatrix3f(final Matrix3f source, final FloatMatrix3 destination) {
        destination.set(source.m00(), source.m10(), source.m20(), source.m01(), source.m11(), source.m21(), source.m02(), source.m12(), source.m22());
    }
    
    private void _applyMatrix4f(final Matrix4f source, final FloatMatrix4 destination) {
        destination.set(source.m00(), source.m10(), source.m20(), source.m30(), source.m01(), source.m11(), source.m21(), source.m31(), source.m02(), source.m12(), source.m22(), source.m32(), source.m03(), source.m13(), source.m23(), source.m33());
    }
    
    private void _applyFloatMatrix4(final FloatMatrix4 source, final Matrix4f destination) {
        destination.set(source.getM00(), source.getM10(), source.getM20(), source.getM30(), source.getM01(), source.getM11(), source.getM21(), source.getM31(), source.getM02(), source.getM12(), source.getM22(), source.getM32(), source.getM03(), source.getM13(), source.getM23(), source.getM33());
    }
    
    static {
        MATRIX4F_IDENTITY = new FloatMatrix4().identity();
        MATRIX3F_IDENTITY = new FloatMatrix3().identity();
        QUATERNION_IDENTITY = new Quaternion();
        BOX_IDENTITY = new AxisAlignedBoundingBox();
        SHARED_MATRIX3F = new FloatMatrix3();
        SHARED_MATRIX4F = new FloatMatrix4();
        MOJANG_SHARED_MATRIX3F = new Matrix3f();
        MOJANG_SHARED_MATRIX4F = new Matrix4f();
    }
}
