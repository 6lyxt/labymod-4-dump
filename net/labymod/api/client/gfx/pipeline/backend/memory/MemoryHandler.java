// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.backend.memory;

import java.nio.ByteBuffer;

public interface MemoryHandler
{
    ByteBuffer create(final int p0);
    
    ByteBuffer resize(final ByteBuffer p0, final int p1, final int p2);
    
    ByteBuffer slice(final ByteBuffer p0, final int p1, final int p2);
    
    void free(final long p0);
    
    void free(final ByteBuffer p0);
    
    MemoryBlock mallocBlock(final long p0);
    
    MemoryBlock callocBlock(final long p0, final long p1);
    
    MemoryAllocator getAllocator(final boolean p0);
    
    ByteBuffer memByteBuffer(final long p0, final int p1);
    
    void memCopy(final long p0, final long p1, final long p2);
    
    void putByte(final long p0, final byte p1);
    
    void putShort(final long p0, final short p1);
    
    void putInt(final long p0, final int p1);
    
    void putFloat(final long p0, final float p1);
}
