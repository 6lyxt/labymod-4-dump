// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.buffer;

import net.labymod.api.reference.annotation.Referenceable;
import java.nio.ByteBuffer;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryBlock;
import java.nio.Buffer;
import net.labymod.api.util.Disposable;

public interface BufferObject extends Disposable
{
    int getId();
    
    void bind();
    
    void bindBufferBase(final int p0);
    
    void bindBufferRange(final int p0, final long p1, final long p2);
    
    void unbind();
    
    void upload(final Buffer p0);
    
    void upload(final long p0);
    
    void upload(final MemoryBlock p0);
    
    ByteBuffer map(final MapBufferAccess p0);
    
    boolean unmap();
    
    @Referenceable
    public interface Factory
    {
        BufferObject create(final BufferTarget p0, final BufferUsage p1);
    }
}
