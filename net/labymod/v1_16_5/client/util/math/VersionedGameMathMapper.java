// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.util.math;

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
    private static final a MOJANG_SHARED_MATRIX3F;
    private static final b MOJANG_SHARED_MATRIX4F;
    
    @Override
    public FloatMatrix4 fromMatrix4f(final Object matrix) {
        if (!(matrix instanceof b)) {
            return VersionedGameMathMapper.MATRIX4F_IDENTITY;
        }
        final b matrix4f = (b)matrix;
        return this._fromMatrix4f(matrix4f);
    }
    
    @Override
    public <T> T toMatrix4f(final FloatMatrix4 matrix) {
        return (T)this._toMatrix4f(matrix);
    }
    
    @Override
    public FloatMatrix3 fromMatrix3f(final Object matrix) {
        if (!(matrix instanceof a)) {
            return VersionedGameMathMapper.MATRIX3F_IDENTITY;
        }
        final a matrix3f = (a)matrix;
        return this._fromMatrix3f(matrix3f);
    }
    
    @Override
    public <T> T toMatrix3f(final FloatMatrix3 matrix) {
        return (T)this._toMatrix3f(matrix);
    }
    
    @Override
    public void applyFloatMatrix4(final FloatMatrix4 source, final Object destination) {
        if (!(destination instanceof b)) {
            return;
        }
        final b matrix4f = (b)destination;
        this._applyFloatMatrix4(source, matrix4f);
    }
    
    @Override
    public void applyMatrix4f(final Object source, final FloatMatrix4 destination) {
        if (!(source instanceof b)) {
            return;
        }
        final b matrix4f = (b)source;
        this._applyMatrix4f(matrix4f, destination);
    }
    
    @Override
    public void applyFloatMatrix3(final FloatMatrix3 source, final Object destination) {
        if (!(destination instanceof a)) {
            return;
        }
        final a matrix3f = (a)destination;
        this._applyFloatMatrix3(source, matrix3f);
    }
    
    @Override
    public void applyMatrix3f(final Object source, final FloatMatrix3 destination) {
        if (!(source instanceof a)) {
            return;
        }
        final a matrix3f = (a)source;
        this._applyMatrix3f(matrix3f, destination);
    }
    
    @Override
    public Quaternion fromQuaternion(final Object quaternion) {
        if (!(quaternion instanceof d)) {
            return VersionedGameMathMapper.QUATERNION_IDENTITY;
        }
        final d gameQuaternion = (d)quaternion;
        return new Quaternion(gameQuaternion.a(), gameQuaternion.b(), gameQuaternion.c(), gameQuaternion.d());
    }
    
    @Override
    public <T> T toQuaternion(final Quaternion quaternion) {
        return (T)new d(quaternion.getX(), quaternion.getY(), quaternion.getZ(), quaternion.getW());
    }
    
    @Override
    public AxisAlignedBoundingBox fromAABB(final Object aabb) {
        if (aabb instanceof final dci gameAABB) {
            return new AxisAlignedBoundingBox(gameAABB.a, gameAABB.b, gameAABB.c, gameAABB.d, gameAABB.e, gameAABB.f);
        }
        return VersionedGameMathMapper.BOX_IDENTITY;
    }
    
    @Override
    public <T> T toAABB(final AxisAlignedBoundingBox aabb) {
        return (T)new dci(aabb.getMinX(), aabb.getMinY(), aabb.getMinZ(), aabb.getMaxX(), aabb.getMaxY(), aabb.getMaxZ());
    }
    
    private b _toMatrix4f(final FloatMatrix4 matrix) {
        VersionedGameMathMapper.MOJANG_SHARED_MATRIX4F.a();
        this._applyFloatMatrix4(matrix, VersionedGameMathMapper.MOJANG_SHARED_MATRIX4F);
        return VersionedGameMathMapper.MOJANG_SHARED_MATRIX4F;
    }
    
    private a _toMatrix3f(final FloatMatrix3 matrix) {
        VersionedGameMathMapper.MOJANG_SHARED_MATRIX3F.c();
        this._applyFloatMatrix3(matrix, VersionedGameMathMapper.MOJANG_SHARED_MATRIX3F);
        return VersionedGameMathMapper.MOJANG_SHARED_MATRIX3F;
    }
    
    private FloatMatrix4 _fromMatrix4f(final b matrix) {
        VersionedGameMathMapper.SHARED_MATRIX4F.identity();
        this._applyMatrix4f(matrix, VersionedGameMathMapper.SHARED_MATRIX4F);
        return VersionedGameMathMapper.SHARED_MATRIX4F;
    }
    
    private FloatMatrix3 _fromMatrix3f(final a matrix) {
        VersionedGameMathMapper.SHARED_MATRIX3F.identity();
        this._applyMatrix3f(matrix, VersionedGameMathMapper.SHARED_MATRIX3F);
        return VersionedGameMathMapper.SHARED_MATRIX3F;
    }
    
    private void _applyFloatMatrix3(final FloatMatrix3 source, final a destination) {
        VersionedGameMathMapper.MATRIX3_BUFFER.rewind();
        source.store(VersionedGameMathMapper.MATRIX3_BUFFER);
        this.load(VersionedGameMathMapper.MATRIX3_BUFFER, destination);
    }
    
    private void _applyMatrix3f(final a source, final FloatMatrix3 destination) {
        VersionedGameMathMapper.MATRIX3_BUFFER.rewind();
        this.store(VersionedGameMathMapper.MATRIX3_BUFFER, source);
        destination.load(VersionedGameMathMapper.MATRIX3_BUFFER);
    }
    
    private void _applyMatrix4f(final b source, final FloatMatrix4 destination) {
        VersionedGameMathMapper.MATRIX4_BUFFER.rewind();
        source.a(VersionedGameMathMapper.MATRIX4_BUFFER);
        destination.load(VersionedGameMathMapper.MATRIX4_BUFFER);
    }
    
    private void _applyFloatMatrix4(final FloatMatrix4 source, final b destination) {
        VersionedGameMathMapper.MATRIX4_BUFFER.rewind();
        source.store(VersionedGameMathMapper.MATRIX4_BUFFER);
        this.load(VersionedGameMathMapper.MATRIX4_BUFFER, destination);
    }
    
    public void store(final FloatBuffer buffer, final a matrix3f) {
        buffer.put(bufferIndex(0, 0, 3), matrix3f.a);
        buffer.put(bufferIndex(0, 1, 3), matrix3f.b);
        buffer.put(bufferIndex(0, 2, 3), matrix3f.c);
        buffer.put(bufferIndex(1, 0, 3), matrix3f.d);
        buffer.put(bufferIndex(1, 1, 3), matrix3f.e);
        buffer.put(bufferIndex(1, 2, 3), matrix3f.f);
        buffer.put(bufferIndex(2, 0, 3), matrix3f.g);
        buffer.put(bufferIndex(2, 1, 3), matrix3f.h);
        buffer.put(bufferIndex(2, 2, 3), matrix3f.i);
    }
    
    public void load(final FloatBuffer buffer, final a matrix3f) {
        matrix3f.a = buffer.get(bufferIndex(0, 0, 3));
        matrix3f.b = buffer.get(bufferIndex(0, 1, 3));
        matrix3f.c = buffer.get(bufferIndex(0, 2, 3));
        matrix3f.d = buffer.get(bufferIndex(1, 0, 3));
        matrix3f.e = buffer.get(bufferIndex(1, 1, 3));
        matrix3f.f = buffer.get(bufferIndex(1, 2, 3));
        matrix3f.g = buffer.get(bufferIndex(2, 0, 3));
        matrix3f.h = buffer.get(bufferIndex(2, 1, 3));
        matrix3f.i = buffer.get(bufferIndex(2, 2, 3));
    }
    
    public void load(final FloatBuffer buffer, final b matrix4f) {
        matrix4f.a = buffer.get(bufferIndex(0, 0, 4));
        matrix4f.b = buffer.get(bufferIndex(0, 1, 4));
        matrix4f.c = buffer.get(bufferIndex(0, 2, 4));
        matrix4f.d = buffer.get(bufferIndex(0, 3, 4));
        matrix4f.e = buffer.get(bufferIndex(1, 0, 4));
        matrix4f.f = buffer.get(bufferIndex(1, 1, 4));
        matrix4f.g = buffer.get(bufferIndex(1, 2, 4));
        matrix4f.h = buffer.get(bufferIndex(1, 3, 4));
        matrix4f.i = buffer.get(bufferIndex(2, 0, 4));
        matrix4f.j = buffer.get(bufferIndex(2, 1, 4));
        matrix4f.k = buffer.get(bufferIndex(2, 2, 4));
        matrix4f.l = buffer.get(bufferIndex(2, 3, 4));
        matrix4f.m = buffer.get(bufferIndex(3, 0, 4));
        matrix4f.n = buffer.get(bufferIndex(3, 1, 4));
        matrix4f.o = buffer.get(bufferIndex(3, 2, 4));
        matrix4f.p = buffer.get(bufferIndex(3, 3, 4));
    }
    
    private static int bufferIndex(final int i, final int j, final int v) {
        return j * v + i;
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
        MOJANG_SHARED_MATRIX3F = new a();
        MOJANG_SHARED_MATRIX4F = new b();
    }
}
