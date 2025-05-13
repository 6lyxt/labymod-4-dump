// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.backend.memory;

import java.nio.ByteBuffer;

public interface MemoryBlock
{
    long address();
    
    long size();
    
    boolean isFreed();
    
    void free();
    
    void clear();
    
    ByteBuffer asBuffer();
    
    MemoryWriter getOrCreateWriter();
}
