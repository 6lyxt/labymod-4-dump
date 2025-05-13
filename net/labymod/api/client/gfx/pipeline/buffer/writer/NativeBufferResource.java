// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.buffer.writer;

import net.labymod.api.Laby;
import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryHandler;

public class NativeBufferResource extends BufferResource
{
    private static final MemoryHandler HANDLER;
    private final NativeBufferWriter writer;
    
    public NativeBufferResource(final int offset, final int capacity, final int generation, final NativeBufferWriter writer) {
        super(offset, capacity, generation);
        this.writer = writer;
    }
    
    @Override
    public ByteBuffer byteBuffer() {
        if (this.writer.isValid(this.getGeneration())) {
            return NativeBufferResource.HANDLER.memByteBuffer(this.writer.getPointer() + this.getOffset(), this.getCapacity());
        }
        throw new IllegalStateException("Buffer is no longer valid");
    }
    
    @Override
    protected void onClose() {
        if (this.writer.isValid(this.getGeneration())) {
            this.writer.freeResource();
        }
    }
    
    static {
        HANDLER = Laby.gfx().backend().memoryHandler();
    }
}
