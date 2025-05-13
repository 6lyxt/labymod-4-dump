// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.buffer.writer;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryAllocator;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryHandler;

public class NativeBufferWriter implements BufferWriter
{
    private static final MemoryHandler HANDLER;
    private static final MemoryAllocator ALLOCATOR;
    private static final long NULL = 0L;
    private static final int MAX_GROWTH_SIZE = 2097152;
    private long pointer;
    private int capacity;
    private int writeOffset;
    private int nextResultOffset;
    private int bufferResourceCount;
    private int generation;
    
    public NativeBufferWriter(final int capacity) {
        this.capacity = capacity;
        this.pointer = NativeBufferWriter.ALLOCATOR.malloc(capacity);
        if (this.pointer == 0L) {
            throw new OutOfMemoryError("Failed to allocate" + capacity + " bytes");
        }
    }
    
    @Override
    public void write(final byte v0) {
        if (this.isClosed()) {
            return;
        }
        NativeBufferWriter.HANDLER.putByte(this.getPosition(), v0);
        this.addWriteOffset(1);
    }
    
    @Override
    public void write(final byte v0, final byte v1) {
        if (this.isClosed()) {
            return;
        }
        final long position = this.getPosition();
        NativeBufferWriter.HANDLER.putByte(position, v0);
        NativeBufferWriter.HANDLER.putByte(position + 1L, v1);
        this.addWriteOffset(2);
    }
    
    @Override
    public void write(final byte v0, final byte v1, final byte v2) {
        if (this.isClosed()) {
            return;
        }
        final long position = this.getPosition();
        NativeBufferWriter.HANDLER.putByte(position, v0);
        NativeBufferWriter.HANDLER.putByte(position + 1L, v1);
        NativeBufferWriter.HANDLER.putByte(position + 2L, v2);
        this.addWriteOffset(3);
    }
    
    @Override
    public void write(final byte v0, final byte v1, final byte v2, final byte v3) {
        if (this.isClosed()) {
            return;
        }
        final long position = this.getPosition();
        NativeBufferWriter.HANDLER.putByte(position, v0);
        NativeBufferWriter.HANDLER.putByte(position + 1L, v1);
        NativeBufferWriter.HANDLER.putByte(position + 2L, v2);
        NativeBufferWriter.HANDLER.putByte(position + 3L, v3);
        this.addWriteOffset(4);
    }
    
    @Override
    public void write(final short v0) {
        if (this.isClosed()) {
            return;
        }
        NativeBufferWriter.HANDLER.putShort(this.getPosition(), v0);
        this.addWriteOffset(2);
    }
    
    @Override
    public void write(final short v0, final short v1) {
        if (this.isClosed()) {
            return;
        }
        final long position = this.getPosition();
        NativeBufferWriter.HANDLER.putShort(position, v0);
        NativeBufferWriter.HANDLER.putShort(position + 2L, v1);
        this.addWriteOffset(4);
    }
    
    @Override
    public void write(final short v0, final short v1, final short v2) {
        if (this.isClosed()) {
            return;
        }
        final long position = this.getPosition();
        NativeBufferWriter.HANDLER.putShort(position, v0);
        NativeBufferWriter.HANDLER.putShort(position + 2L, v1);
        NativeBufferWriter.HANDLER.putShort(position + 4L, v2);
        this.addWriteOffset(6);
    }
    
    @Override
    public void write(final short v0, final short v1, final short v2, final short v3) {
        if (this.isClosed()) {
            return;
        }
        final long position = this.getPosition();
        NativeBufferWriter.HANDLER.putShort(position, v0);
        NativeBufferWriter.HANDLER.putShort(position + 2L, v1);
        NativeBufferWriter.HANDLER.putShort(position + 4L, v2);
        NativeBufferWriter.HANDLER.putShort(position + 6L, v3);
        this.addWriteOffset(8);
    }
    
    @Override
    public void write(final int v0) {
        if (this.isClosed()) {
            return;
        }
        NativeBufferWriter.HANDLER.putInt(this.getPosition(), v0);
        this.addWriteOffset(4);
    }
    
    @Override
    public void write(final int v0, final int v1) {
        if (this.isClosed()) {
            return;
        }
        final long position = this.getPosition();
        NativeBufferWriter.HANDLER.putInt(position, v0);
        NativeBufferWriter.HANDLER.putInt(position + 4L, v1);
        this.addWriteOffset(8);
    }
    
    @Override
    public void write(final int v0, final int v1, final int v2) {
        if (this.isClosed()) {
            return;
        }
        final long position = this.getPosition();
        NativeBufferWriter.HANDLER.putInt(position, v0);
        NativeBufferWriter.HANDLER.putInt(position + 4L, v1);
        NativeBufferWriter.HANDLER.putInt(position + 8L, v2);
        this.addWriteOffset(12);
    }
    
    @Override
    public void write(final int v0, final int v1, final int v2, final int v3) {
        if (this.isClosed()) {
            return;
        }
        final long position = this.getPosition();
        NativeBufferWriter.HANDLER.putInt(position, v0);
        NativeBufferWriter.HANDLER.putInt(position + 4L, v1);
        NativeBufferWriter.HANDLER.putInt(position + 8L, v2);
        NativeBufferWriter.HANDLER.putInt(position + 12L, v3);
        this.addWriteOffset(16);
    }
    
    @Override
    public void write(final float v0) {
        if (this.isClosed()) {
            return;
        }
        NativeBufferWriter.HANDLER.putFloat(this.getPosition(), v0);
        this.addWriteOffset(4);
    }
    
    @Override
    public void write(final float v0, final float v1) {
        if (this.isClosed()) {
            return;
        }
        final long position = this.getPosition();
        NativeBufferWriter.HANDLER.putFloat(position, v0);
        NativeBufferWriter.HANDLER.putFloat(position + 4L, v1);
        this.addWriteOffset(8);
    }
    
    @Override
    public void write(final float v0, final float v1, final float v2) {
        if (this.isClosed()) {
            return;
        }
        final long position = this.getPosition();
        NativeBufferWriter.HANDLER.putFloat(position, v0);
        NativeBufferWriter.HANDLER.putFloat(position + 4L, v1);
        NativeBufferWriter.HANDLER.putFloat(position + 8L, v2);
        this.addWriteOffset(12);
    }
    
    @Override
    public void write(final float v0, final float v1, final float v2, final float v3) {
        if (this.isClosed()) {
            return;
        }
        final long position = this.getPosition();
        NativeBufferWriter.HANDLER.putFloat(position, v0);
        NativeBufferWriter.HANDLER.putFloat(position + 4L, v1);
        NativeBufferWriter.HANDLER.putFloat(position + 8L, v2);
        NativeBufferWriter.HANDLER.putFloat(position + 12L, v3);
        this.addWriteOffset(16);
    }
    
    @Override
    public void addWriteOffset(final int offset) {
        this.writeOffset += offset;
    }
    
    @Override
    public void ensureCapacity(final int additionalSpace) {
        final int writeIndex = this.writeOffset;
        final int spaceNeeded = writeIndex + additionalSpace;
        if (spaceNeeded > this.capacity) {
            final int allowedIncrease = Math.min(this.capacity, 2097152);
            final int requestedCapacity = Math.max(this.capacity + additionalSpace, allowedIncrease);
            this.resize(requestedCapacity);
        }
    }
    
    private void resize(final int newCapacity) {
        this.pointer = NativeBufferWriter.ALLOCATOR.realloc(this.pointer, newCapacity);
        if (this.pointer == 0L) {
            throw new OutOfMemoryError("Failed to resize writer from " + this.capacity + " bytes to " + newCapacity + " bytes");
        }
        this.capacity = newCapacity;
    }
    
    @Override
    public void close() throws Exception {
        if (this.pointer == 0L) {
            return;
        }
        NativeBufferWriter.ALLOCATOR.free(this.pointer);
        this.pointer = 0L;
        this.generation = -1;
    }
    
    @Override
    public BufferResource build() {
        if (this.isClosed()) {
            return null;
        }
        final int nextOffset = this.nextResultOffset;
        final int offset = this.writeOffset - nextOffset;
        if (offset == 0) {
            return null;
        }
        this.nextResultOffset = this.writeOffset;
        ++this.bufferResourceCount;
        return new NativeBufferResource(nextOffset, offset, this.generation, this);
    }
    
    public long getPointer() {
        return this.pointer;
    }
    
    void freeResource() {
        final int bufferResourceCount = this.bufferResourceCount - 1;
        this.bufferResourceCount = bufferResourceCount;
        if (bufferResourceCount <= 0) {
            this.discardResources();
        }
    }
    
    boolean isValid(final int generation) {
        return generation == this.generation;
    }
    
    private long getPosition() {
        return this.pointer + this.writeOffset;
    }
    
    private void discardResources() {
        final int offset = this.writeOffset - this.nextResultOffset;
        if (offset > 0) {
            NativeBufferWriter.HANDLER.memCopy(this.pointer + this.nextResultOffset, this.pointer, offset);
        }
        this.writeOffset = offset;
        this.nextResultOffset = 0;
        ++this.generation;
    }
    
    private void checkClosed() {
        if (this.isClosed()) {
            throw new IllegalStateException("Writer is closed");
        }
    }
    
    public boolean isClosed() {
        return this.pointer == 0L;
    }
    
    static {
        HANDLER = Laby.gfx().backend().memoryHandler();
        ALLOCATOR = NativeBufferWriter.HANDLER.getAllocator(false);
    }
}
