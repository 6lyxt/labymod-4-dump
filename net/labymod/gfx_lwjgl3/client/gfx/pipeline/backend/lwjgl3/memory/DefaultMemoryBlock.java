// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.memory;

import org.lwjgl.system.MemoryUtil;
import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryWriter;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryBlock;

public class DefaultMemoryBlock implements MemoryBlock
{
    private final long address;
    private final long size;
    private MemoryWriter memoryWriter;
    private boolean freed;
    
    public DefaultMemoryBlock(final long address, final long size) {
        this.address = address;
        this.size = size;
    }
    
    @Override
    public long address() {
        return this.address;
    }
    
    @Override
    public long size() {
        return this.size;
    }
    
    @Override
    public boolean isFreed() {
        return this.freed;
    }
    
    @Override
    public void free() {
        if (this.freed) {
            throw new IllegalStateException("Memory block is already freed");
        }
        MemoryOperations.free(this.address);
        this.freed = true;
    }
    
    @Override
    public void clear() {
        MemoryOperations.clear(this.address, this.size);
    }
    
    @Override
    public ByteBuffer asBuffer() {
        if (this.size > 2147483647L) {
            throw new IllegalStateException("Memory block is too large");
        }
        return MemoryUtil.memByteBuffer(this.address, (int)this.size);
    }
    
    @Override
    public MemoryWriter getOrCreateWriter() {
        if (this.memoryWriter == null) {
            this.memoryWriter = new DefaultMemoryWriter(this.address);
        }
        return this.memoryWriter;
    }
}
