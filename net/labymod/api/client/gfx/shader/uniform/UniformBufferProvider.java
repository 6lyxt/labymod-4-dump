// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.shader.uniform;

import net.labymod.api.client.gfx.pipeline.backend.memory.MemoryWriter;

public interface UniformBufferProvider
{
    void write(final MemoryWriter p0);
    
    int getByteSize();
}
