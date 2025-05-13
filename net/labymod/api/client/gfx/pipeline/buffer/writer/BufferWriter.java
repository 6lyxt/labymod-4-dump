// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.buffer.writer;

public interface BufferWriter extends AutoCloseable
{
    void write(final byte p0);
    
    void write(final byte p0, final byte p1);
    
    void write(final byte p0, final byte p1, final byte p2);
    
    void write(final byte p0, final byte p1, final byte p2, final byte p3);
    
    void write(final short p0);
    
    void write(final short p0, final short p1);
    
    void write(final short p0, final short p1, final short p2);
    
    void write(final short p0, final short p1, final short p2, final short p3);
    
    void write(final int p0);
    
    void write(final int p0, final int p1);
    
    void write(final int p0, final int p1, final int p2);
    
    void write(final int p0, final int p1, final int p2, final int p3);
    
    void write(final float p0);
    
    void write(final float p0, final float p1);
    
    void write(final float p0, final float p1, final float p2);
    
    void write(final float p0, final float p1, final float p2, final float p3);
    
    void addWriteOffset(final int p0);
    
    void ensureCapacity(final int p0);
    
    BufferResource build();
}
