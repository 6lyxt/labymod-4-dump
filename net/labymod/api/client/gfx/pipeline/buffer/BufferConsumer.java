// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.buffer;

import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.util.math.vector.FloatMatrix3;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.util.math.vector.FloatVector3;

public interface BufferConsumer
{
    public static final FloatVector3 NORMAL = new FloatVector3(0.0f, 0.0f, 0.0f);
    public static final FloatVector4 POSITION = new FloatVector4(0.0f, 0.0f, 0.0f, 1.0f);
    
    default BufferConsumer pos(final float x, final float y, final float z) {
        return this.putFloat(x, y, z);
    }
    
    default BufferConsumer color(final int argb) {
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        return this.color(colorFormat.red(argb), colorFormat.green(argb), colorFormat.blue(argb), colorFormat.alpha(argb));
    }
    
    default BufferConsumer color(final float red, final float green, final float blue, final float alpha) {
        return this.packedColor(ColorFormat.ABGR32.pack(red, green, blue, alpha));
    }
    
    default BufferConsumer color(final int red, final int green, final int blue, final int alpha) {
        return this.packedColor(ColorFormat.ABGR32.pack(red, green, blue, alpha));
    }
    
    default BufferConsumer packedColor(final int color) {
        return this.putInt(color);
    }
    
    default BufferConsumer uv(final float u, final float v) {
        return this.putFloat(u, v);
    }
    
    default BufferConsumer normal(final float x, final float y, final float z) {
        return this.putByte(MathHelper.normalIntValue(x), MathHelper.normalIntValue(y), MathHelper.normalIntValue(z));
    }
    
    default BufferConsumer packedOverlay(final int packedOverlay) {
        return this.putInt(packedOverlay);
    }
    
    default BufferConsumer light(final int packedLight) {
        return this.putInt(packedLight);
    }
    
    default void vertex(final float x, final float y, final float z, final float red, final float green, final float blue, final float alpha, final float u, final float v, final int packedOverlay, final int packedLight, final float normalX, final float normalY, final float normalZ) {
        this.pos(x, y, z);
        this.color(red, green, blue, alpha);
        this.uv(u, v);
        this.packedOverlay(packedOverlay);
        this.light(packedLight);
        this.normal(normalX, normalY, normalZ);
        this.endVertex();
    }
    
    default BufferConsumer putFloat(final float v0) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putFloat(final float v0, final float v1) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putFloat(final float v0, final float v1, final float v2) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putFloat(final float v0, final float v1, final float v2, final float v3) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putShort(final short v0) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putShort(final short v0, final short v1) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putShort(final short v0, final short v1, final short v2) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putShort(final short v0, final short v1, final short v2, final short v3) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putInt(final int v0) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putInt(final int v0, final int v1) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putInt(final int v0, final int v1, final int v2) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putInt(final int v0, final int v1, final int v2, final int v3) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putByte(final byte v0) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putByte(final byte v0, final byte v1) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putByte(final byte v0, final byte v1, final byte v2) {
        throw new UnsupportedOperationException();
    }
    
    default BufferConsumer putByte(final byte v0, final byte v1, final byte v2, final byte v3) {
        throw new UnsupportedOperationException();
    }
    
    BufferConsumer endVertex();
    
    default BufferConsumer normal(final Stack stack, final float x, final float y, final float z) {
        return this.normal(stack.getProvider().getNormal(), x, y, z);
    }
    
    default BufferConsumer normal(final FloatMatrix3 matrix, final float x, final float y, final float z) {
        BufferConsumer.NORMAL.set(x, y, z);
        BufferConsumer.NORMAL.transform(matrix);
        return this.normal(BufferConsumer.NORMAL.getX(), BufferConsumer.NORMAL.getY(), BufferConsumer.NORMAL.getZ());
    }
    
    default BufferConsumer pos(final Stack stack, final double x, final double y, final double z) {
        return this.pos(stack, (float)x, (float)y, (float)z);
    }
    
    default BufferConsumer pos(final Stack stack, final float x, final float y, final float z) {
        return this.pos(stack.getProvider().getPosition(), x, y, z);
    }
    
    default BufferConsumer pos(final FloatMatrix4 matrix, final double x, final double y, final double z) {
        return this.pos(matrix, (float)x, (float)y, (float)z);
    }
    
    default BufferConsumer pos(final FloatMatrix4 matrix, final float x, final float y, final float z) {
        BufferConsumer.POSITION.set(x, y, z, 1.0f);
        BufferConsumer.POSITION.transform(matrix);
        return this.pos(BufferConsumer.POSITION.getX(), BufferConsumer.POSITION.getY(), BufferConsumer.POSITION.getZ());
    }
}
