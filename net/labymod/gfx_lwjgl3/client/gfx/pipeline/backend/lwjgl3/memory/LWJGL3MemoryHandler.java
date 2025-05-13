// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.memory;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryBlock;
import java.nio.Buffer;
import org.lwjgl.system.MemoryUtil;
import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryAllocator;
import java.util.function.Function;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryHandler;

public class LWJGL3MemoryHandler implements MemoryHandler
{
    private static final Function<Boolean, MemoryAllocator> ALLOCATOR_CACHE;
    
    @Override
    public ByteBuffer create(final int capacity) {
        return MemoryUtil.memByteBuffer(MemoryOperations.malloc(capacity), capacity);
    }
    
    @Override
    public ByteBuffer resize(final ByteBuffer source, final int capacity, final int limit) {
        return MemoryUtil.memByteBuffer(MemoryOperations.realloc(MemoryUtil.memAddress0((Buffer)source), capacity), capacity);
    }
    
    @Override
    public ByteBuffer slice(final ByteBuffer source, final int offset, final int capacity) {
        return MemoryUtil.memSlice(source, offset, capacity - offset);
    }
    
    @Override
    public void free(final long pointer) {
        MemoryUtil.nmemFree(pointer);
    }
    
    @Override
    public void free(final ByteBuffer source) {
        MemoryOperations.free(source);
    }
    
    @Override
    public MemoryBlock mallocBlock(final long size) {
        final long address = MemoryOperations.malloc(size);
        return new DefaultMemoryBlock(address, size);
    }
    
    @Override
    public MemoryBlock callocBlock(final long num, final long size) {
        final long address = MemoryOperations.calloc(num, size);
        return new DefaultMemoryBlock(address, num * size);
    }
    
    @Override
    public MemoryAllocator getAllocator(final boolean tracked) {
        return LWJGL3MemoryHandler.ALLOCATOR_CACHE.apply(tracked);
    }
    
    @Override
    public ByteBuffer memByteBuffer(final long pointer, final int capacity) {
        return MemoryUtil.memByteBuffer(pointer, capacity);
    }
    
    @Override
    public void memCopy(final long source, final long destination, final long size) {
        MemoryUtil.memCopy(source, destination, size);
    }
    
    @Override
    public void putByte(final long pointer, final byte value) {
        MemoryUtil.memPutByte(pointer, value);
    }
    
    @Override
    public void putShort(final long pointer, final short value) {
        MemoryUtil.memPutShort(pointer, value);
    }
    
    @Override
    public void putInt(final long pointer, final int value) {
        MemoryUtil.memPutInt(pointer, value);
    }
    
    @Override
    public void putFloat(final long pointer, final float value) {
        MemoryUtil.memPutFloat(pointer, value);
    }
    
    static {
        ALLOCATOR_CACHE = Laby.references().functionMemoizeStorage().memoize(tracked -> new LWJGL3MemoryAllocator(MemoryUtil.getAllocator((boolean)tracked)));
    }
}
