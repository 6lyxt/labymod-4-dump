// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_17_1.client.util.math;

import net.labymod.api.util.Buffers;
import net.labymod.api.util.math.AxisAlignedBoundingBox;
import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.util.math.vector.FloatMatrix4;
import java.nio.FloatBuffer;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.util.math.GameMathMapper;

@Singleton
@Implements(GameMathMapper.class)
public class VersionedGameMathMapper implements GameMathMapper
{
    private static final FloatBuffer MATRIX4_BUFFER;
    private static final FloatBuffer MATRIX3_BUFFER;
    private static final FloatMatrix4 MATRIX4F_IDENTITY;
    private static final FloatMatrix3 MATRIX3F_IDENTITY;
    private static final Quaternion QUATERNION_IDENTITY;
    private static final AxisAlignedBoundingBox BOX_IDENTITY;
    private static final FloatMatrix3 SHARED_MATRIX3F;
    private static final FloatMatrix4 SHARED_MATRIX4F;
    private static final c MOJANG_SHARED_MATRIX3F;
    private static final d MOJANG_SHARED_MATRIX4F;
    
    @Override
    public FloatMatrix4 fromMatrix4f(final Object matrix) {
        if (matrix instanceof final d matrix4f) {
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
        if (matrix instanceof final c matrix3f) {
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
        if (destination instanceof final d matrix4f) {
            this._applyFloatMatrix4(source, matrix4f);
        }
    }
    
    @Override
    public void applyMatrix4f(final Object source, final FloatMatrix4 destination) {
        if (source instanceof final d matrix4f) {
            this._applyMatrix4f(matrix4f, destination);
        }
    }
    
    @Override
    public void applyFloatMatrix3(final FloatMatrix3 source, final Object destination) {
        if (destination instanceof final c matrix3f) {
            this._applyFloatMatrix3(source, matrix3f);
        }
    }
    
    @Override
    public void applyMatrix3f(final Object source, final FloatMatrix3 destination) {
        if (source instanceof final c matrix3f) {
            this._applyMatrix3f(matrix3f, destination);
        }
    }
    
    @Override
    public Quaternion fromQuaternion(final Object quaternion) {
        if (quaternion instanceof final g gameQuaternion) {
            return new Quaternion(gameQuaternion.e(), gameQuaternion.f(), gameQuaternion.g(), gameQuaternion.h());
        }
        return VersionedGameMathMapper.QUATERNION_IDENTITY;
    }
    
    @Override
    public <T> T toQuaternion(final Quaternion quaternion) {
        return (T)new g(quaternion.getX(), quaternion.getY(), quaternion.getZ(), quaternion.getW());
    }
    
    @Override
    public AxisAlignedBoundingBox fromAABB(final Object aabb) {
        if (aabb instanceof final dmv gameAABB) {
            return new AxisAlignedBoundingBox(gameAABB.a, gameAABB.b, gameAABB.c, gameAABB.d, gameAABB.e, gameAABB.f);
        }
        return VersionedGameMathMapper.BOX_IDENTITY;
    }
    
    @Override
    public <T> T toAABB(final AxisAlignedBoundingBox aabb) {
        return (T)new dmv(aabb.getMinX(), aabb.getMinY(), aabb.getMinZ(), aabb.getMaxX(), aabb.getMaxY(), aabb.getMaxZ());
    }
    
    private d _toMatrix4f(final FloatMatrix4 matrix) {
        VersionedGameMathMapper.MOJANG_SHARED_MATRIX4F.b();
        this._applyFloatMatrix4(matrix, VersionedGameMathMapper.MOJANG_SHARED_MATRIX4F);
        return VersionedGameMathMapper.MOJANG_SHARED_MATRIX4F;
    }
    
    private c _toMatrix3f(final FloatMatrix3 matrix) {
        VersionedGameMathMapper.MOJANG_SHARED_MATRIX3F.c();
        this._applyFloatMatrix3(matrix, VersionedGameMathMapper.MOJANG_SHARED_MATRIX3F);
        return VersionedGameMathMapper.MOJANG_SHARED_MATRIX3F;
    }
    
    private FloatMatrix4 _fromMatrix4f(final d matrix) {
        VersionedGameMathMapper.SHARED_MATRIX4F.identity();
        this._applyMatrix4f(matrix, VersionedGameMathMapper.SHARED_MATRIX4F);
        return VersionedGameMathMapper.SHARED_MATRIX4F;
    }
    
    private FloatMatrix3 _fromMatrix3f(final c matrix) {
        VersionedGameMathMapper.SHARED_MATRIX3F.identity();
        this._applyMatrix3f(matrix, VersionedGameMathMapper.SHARED_MATRIX3F);
        return VersionedGameMathMapper.SHARED_MATRIX3F;
    }
    
    private void _applyFloatMatrix3(final FloatMatrix3 source, final c destination) {
        VersionedGameMathMapper.MATRIX3_BUFFER.rewind();
        source.store(VersionedGameMathMapper.MATRIX3_BUFFER);
        destination.a(VersionedGameMathMapper.MATRIX3_BUFFER);
    }
    
    private void _applyMatrix3f(final c source, final FloatMatrix3 destination) {
        VersionedGameMathMapper.MATRIX3_BUFFER.rewind();
        source.c(VersionedGameMathMapper.MATRIX3_BUFFER);
        destination.load(VersionedGameMathMapper.MATRIX3_BUFFER);
    }
    
    private void _applyMatrix4f(final d source, final FloatMatrix4 destination) {
        VersionedGameMathMapper.MATRIX4_BUFFER.rewind();
        source.c(VersionedGameMathMapper.MATRIX4_BUFFER);
        destination.load(VersionedGameMathMapper.MATRIX4_BUFFER);
    }
    
    private void _applyFloatMatrix4(final FloatMatrix4 source, final d destination) {
        VersionedGameMathMapper.MATRIX4_BUFFER.rewind();
        source.store(VersionedGameMathMapper.MATRIX4_BUFFER);
        destination.a(VersionedGameMathMapper.MATRIX4_BUFFER);
    }
    
    static {
        MATRIX4_BUFFER = Buffers.createFloatBuffer(16);
        MATRIX3_BUFFER = Buffers.createFloatBuffer(12);
        MATRIX4F_IDENTITY = new FloatMatrix4().identity();
        MATRIX3F_IDENTITY = new FloatMatrix3().identity();
        QUATERNION_IDENTITY = new Quaternion();
        BOX_IDENTITY = new AxisAlignedBoundingBox();
        SHARED_MATRIX3F = new FloatMatrix3();
        SHARED_MATRIX4F = new FloatMatrix4();
        MOJANG_SHARED_MATRIX3F = new c();
        MOJANG_SHARED_MATRIX4F = new d();
    }
}
