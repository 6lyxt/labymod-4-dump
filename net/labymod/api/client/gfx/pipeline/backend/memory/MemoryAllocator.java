// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.backend.memory;

public interface MemoryAllocator
{
    long getMalloc();
    
    long getCalloc();
    
    long getRealloc();
    
    long getFree();
    
    long getAlignedAlloc();
    
    long getAlignedFree();
    
    long malloc(final long p0);
    
    long calloc(final long p0, final long p1);
    
    long realloc(final long p0, final long p1);
    
    void free(final long p0);
    
    long alignedAlloc(final long p0, final long p1);
    
    void alignedFree(final long p0);
}
