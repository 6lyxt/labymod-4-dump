// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.blaze3d.buffer;

import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.gfx.pipeline.blaze3d.program.Blaze3DRenderType;

public interface Blaze3DBufferSource
{
    BufferConsumer getBuffer(final Blaze3DRenderType p0);
    
    void endBuffer();
    
    @Deprecated
    default void endLegacyBuffer() {
        throw new UnsupportedOperationException("This function is deprecated an unavailable in the Core profile");
    }
    
    @ApiStatus.Experimental
    default void setTemporaryBuffer(final Object bufferSource) {
    }
    
    @ApiStatus.Experimental
    default void resetTemporaryBuffer() {
    }
}
