// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.context;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface FrameContextRegistry
{
    void register(final FrameContext p0);
    
    void beginFrame();
    
    void endFrame();
}
