// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.util;

import net.labymod.api.util.Buffers;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Matrix4f;
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
    
    @Override
    public FloatMatrix4 fromMatrix4f(final Object matrix) {
        if (!(matrix instanceof Matrix4f)) {
            return VersionedGameMathMapper.MATRIX4F_IDENTITY;
        }
        return this._fromMatrix4f((Matrix4f)matrix);
    }
    
    @Override
    public <T> T toMatrix4f(final FloatMatrix4 matrix) {
        return (T)this._toMatrix4f(matrix);
    }
    
    @Override
    public FloatMatrix3 fromMatrix3f(final Object matrix) {
        if (!(matrix instanceof Matrix3f)) {
            return VersionedGameMathMapper.MATRIX3F_IDENTITY;
        }
        return this._fromMatrix3f((Matrix3f)matrix);
    }
    
    @Override
    public <T> T toMatrix3f(final FloatMatrix3 matrix) {
        return (T)this._toMatrix3f(matrix);
    }
    
    @Override
    public void applyFloatMatrix4(final FloatMatrix4 source, final Object destination) {
        if (!(destination instanceof Matrix4f)) {
            return;
        }
        this._applyFloatMatrix4(source, (Matrix4f)destination);
    }
    
    @Override
    public void applyMatrix4f(final Object source, final FloatMatrix4 destination) {
        if (!(source instanceof Matrix4f)) {
            return;
        }
        this._applyMatrix4f((Matrix4f)source, destination);
    }
    
    @Override
    public void applyFloatMatrix3(final FloatMatrix3 source, final Object destination) {
        if (!(destination instanceof Matrix3f)) {
            return;
        }
        this._applyFloatMatrix3(source, (Matrix3f)destination);
    }
    
    @Override
    public void applyMatrix3f(final Object source, final FloatMatrix3 destination) {
        if (!(source instanceof Matrix3f)) {
            return;
        }
        this._applyMatrix3f((Matrix3f)source, destination);
    }
    
    @Override
    public Quaternion fromQuaternion(final Object quaternion) {
        if (!(quaternion instanceof org.lwjgl.util.vector.Quaternion)) {
            return VersionedGameMathMapper.QUATERNION_IDENTITY;
        }
        final org.lwjgl.util.vector.Quaternion gameQuaternion = (org.lwjgl.util.vector.Quaternion)quaternion;
        return new Quaternion(gameQuaternion.getX(), gameQuaternion.getY(), gameQuaternion.getZ(), gameQuaternion.getW());
    }
    
    @Override
    public <T> T toQuaternion(final Quaternion quaternion) {
        return (T)new org.lwjgl.util.vector.Quaternion(quaternion.getX(), quaternion.getY(), quaternion.getZ(), quaternion.getW());
    }
    
    @Override
    public AxisAlignedBoundingBox fromAABB(final Object aabb) {
        if (aabb instanceof final bhb gameAABB) {
            return new AxisAlignedBoundingBox(gameAABB.a, gameAABB.b, gameAABB.c, gameAABB.d, gameAABB.e, gameAABB.f);
        }
        return VersionedGameMathMapper.BOX_IDENTITY;
    }
    
    @Override
    public <T> T toAABB(final AxisAlignedBoundingBox aabb) {
        return (T)new bhb(aabb.getMinX(), aabb.getMinY(), aabb.getMinZ(), aabb.getMaxX(), aabb.getMaxY(), aabb.getMaxZ());
    }
    
    private Matrix4f _toMatrix4f(final FloatMatrix4 matrix) {
        final Matrix4f matrix4f = new Matrix4f();
        this._applyFloatMatrix4(matrix, matrix4f);
        return matrix4f;
    }
    
    private Matrix3f _toMatrix3f(final FloatMatrix3 matrix) {
        final Matrix3f matrix3f = new Matrix3f();
        this._applyFloatMatrix3(matrix, matrix3f);
        return matrix3f;
    }
    
    private FloatMatrix4 _fromMatrix4f(final Matrix4f matrix) {
        final FloatMatrix4 matrix2 = new FloatMatrix4();
        this._applyMatrix4f(matrix, matrix2);
        return matrix2;
    }
    
    private FloatMatrix3 _fromMatrix3f(final Matrix3f matrix) {
        final FloatMatrix3 matrix2 = new FloatMatrix3();
        this._applyMatrix3f(matrix, matrix2);
        return matrix2;
    }
    
    private void _applyFloatMatrix3(final FloatMatrix3 source, final Matrix3f destination) {
        VersionedGameMathMapper.MATRIX3_BUFFER.rewind();
        source.store(VersionedGameMathMapper.MATRIX3_BUFFER);
        destination.load(VersionedGameMathMapper.MATRIX3_BUFFER);
    }
    
    private void _applyMatrix3f(final Matrix3f source, final FloatMatrix3 destination) {
        VersionedGameMathMapper.MATRIX3_BUFFER.rewind();
        source.store(VersionedGameMathMapper.MATRIX3_BUFFER);
        destination.load(VersionedGameMathMapper.MATRIX3_BUFFER);
    }
    
    private void _applyMatrix4f(final Matrix4f source, final FloatMatrix4 destination) {
        VersionedGameMathMapper.MATRIX4_BUFFER.rewind();
        source.store(VersionedGameMathMapper.MATRIX4_BUFFER);
        destination.load(VersionedGameMathMapper.MATRIX4_BUFFER);
    }
    
    private void _applyFloatMatrix4(final FloatMatrix4 source, final Matrix4f destination) {
        VersionedGameMathMapper.MATRIX4_BUFFER.rewind();
        source.store(VersionedGameMathMapper.MATRIX4_BUFFER);
        destination.load(VersionedGameMathMapper.MATRIX4_BUFFER);
    }
    
    static {
        MATRIX4_BUFFER = Buffers.createFloatBuffer(16);
        MATRIX3_BUFFER = Buffers.createFloatBuffer(12);
        MATRIX4F_IDENTITY = new FloatMatrix4().identity();
        MATRIX3F_IDENTITY = new FloatMatrix3().identity();
        QUATERNION_IDENTITY = new Quaternion();
        BOX_IDENTITY = new AxisAlignedBoundingBox();
    }
}
