// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.memory;

import org.lwjgl.system.MemoryUtil;
import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryAllocator;

public class LWJGL3MemoryAllocator implements MemoryAllocator
{
    private final MemoryUtil.MemoryAllocator allocator;
    
    public LWJGL3MemoryAllocator(final MemoryUtil.MemoryAllocator allocator) {
        this.allocator = allocator;
    }
    
    @Override
    public long getMalloc() {
        return this.allocator.getMalloc();
    }
    
    @Override
    public long getCalloc() {
        return this.allocator.getCalloc();
    }
    
    @Override
    public long getRealloc() {
        return this.allocator.getRealloc();
    }
    
    @Override
    public long getFree() {
        return this.allocator.getFree();
    }
    
    @Override
    public long getAlignedAlloc() {
        return this.allocator.getAlignedAlloc();
    }
    
    @Override
    public long getAlignedFree() {
        return this.allocator.getAlignedFree();
    }
    
    @Override
    public long malloc(final long size) {
        return this.allocator.malloc(size);
    }
    
    @Override
    public long calloc(final long pointer, final long size) {
        return this.allocator.calloc(pointer, size);
    }
    
    @Override
    public long realloc(final long pointer, final long size) {
        return this.allocator.realloc(pointer, size);
    }
    
    @Override
    public void free(final long pointer) {
        this.allocator.free(pointer);
    }
    
    @Override
    public long alignedAlloc(final long alignment, final long size) {
        return this.allocator.aligned_alloc(alignment, size);
    }
    
    @Override
    public void alignedFree(final long pointer) {
        this.allocator.aligned_free(pointer);
    }
}
