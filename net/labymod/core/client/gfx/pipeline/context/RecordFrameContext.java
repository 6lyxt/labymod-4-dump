// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.context;

import net.labymod.core.main.lagdetector.LagDetector;
import net.labymod.core.main.profiler.RenderProfiler;
import net.labymod.api.client.gfx.pipeline.context.FrameContext;

public final class RecordFrameContext implements FrameContext
{
    @Override
    public void beginFrame() {
    }
    
    @Override
    public void endFrame() {
        RenderProfiler.resetRenderCalls();
        LagDetector.onUpdate();
    }
}
