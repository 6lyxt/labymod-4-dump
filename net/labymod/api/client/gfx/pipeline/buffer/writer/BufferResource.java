// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.buffer.writer;

import java.nio.ByteBuffer;

public abstract class BufferResource implements AutoCloseable
{
    private final int offset;
    private final int capacity;
    private final int generation;
    private boolean closed;
    
    public BufferResource(final int offset, final int capacity, final int generation) {
        this.offset = offset;
        this.capacity = capacity;
        this.generation = generation;
    }
    
    public int getOffset() {
        return this.offset;
    }
    
    public int getCapacity() {
        return this.capacity;
    }
    
    public int getGeneration() {
        return this.generation;
    }
    
    public abstract ByteBuffer byteBuffer();
    
    protected abstract void onClose();
    
    @Override
    public void close() throws Exception {
        if (this.closed) {
            return;
        }
        this.closed = true;
        this.onClose();
    }
}
