// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util;

import java.util.function.IntFunction;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.LongBuffer;
import java.nio.IntBuffer;
import java.nio.CharBuffer;
import java.nio.ShortBuffer;
import org.jetbrains.annotations.NotNull;
import java.nio.ByteOrder;
import java.nio.ByteBuffer;

public final class Buffers
{
    private Buffers() {
    }
    
    @NotNull
    public static ByteBuffer createByteBuffer(final int size) {
        return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }
    
    @NotNull
    public static ShortBuffer createShortBuffer(final int size) {
        return createByteBuffer(size << 1).asShortBuffer();
    }
    
    @NotNull
    public static CharBuffer createCharBuffer(final int size) {
        return createByteBuffer(size << 1).asCharBuffer();
    }
    
    @NotNull
    public static IntBuffer createIntBuffer(final int size) {
        return createByteBuffer(size << 2).asIntBuffer();
    }
    
    @NotNull
    public static LongBuffer createLongBuffer(final int size) {
        return createByteBuffer(size << 3).asLongBuffer();
    }
    
    @NotNull
    public static FloatBuffer createFloatBuffer(final int size) {
        return createByteBuffer(size << 2).asFloatBuffer();
    }
    
    @NotNull
    public static DoubleBuffer createDoubleBuffer(final int size) {
        return createByteBuffer(size << 3).asDoubleBuffer();
    }
    
    public static ByteBuffer cloneBuffer(final ByteBuffer buffer) {
        return cloneBuffer(buffer, Buffers::createByteBuffer);
    }
    
    public static ByteBuffer cloneBuffer(final ByteBuffer buffer, final IntFunction<ByteBuffer> bufferFactory) {
        final ByteBuffer clone = bufferFactory.apply(buffer.capacity());
        final int position = buffer.position();
        clone.put(buffer);
        buffer.position(position);
        clone.flip();
        return clone;
    }
}
