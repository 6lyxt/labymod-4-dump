// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.backend.memory;

public interface MemoryWriter
{
    MemoryWriter put(final byte p0);
    
    MemoryWriter putShort(final short p0);
    
    MemoryWriter putInt(final int p0);
    
    MemoryWriter putLong(final long p0);
    
    MemoryWriter putFloat(final float p0);
    
    MemoryWriter set(final int p0, final int p1);
    
    void start();
    
    void finish();
}
