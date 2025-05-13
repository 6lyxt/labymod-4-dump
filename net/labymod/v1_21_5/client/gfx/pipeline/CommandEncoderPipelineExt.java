// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.gfx.pipeline;

import com.mojang.blaze3d.systems.CommandEncoder;

public interface CommandEncoderPipelineExt
{
    default void invalidate(final CommandEncoder encoder) {
        if (encoder instanceof final CommandEncoderPipelineExt pipelineExt) {
            pipelineExt.invalidatePipeline();
        }
    }
    
    void invalidatePipeline();
}
