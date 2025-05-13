// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.render;

import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.RenderHandEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.Stack;

public final class RenderHandEventCaller
{
    private static boolean previousScreenContext;
    
    public static RenderHandEvent call(final Stack stack, final Phase phase) {
        final GFXRenderPipeline pipeline = Laby.references().gfxRenderPipeline();
        final GFXBridge gfx = pipeline.gfx();
        gfx.storeBlaze3DStates();
        final RenderHandEvent event = new RenderHandEvent(stack, phase);
        final RenderEnvironmentContext context = pipeline.renderEnvironmentContext();
        if (phase == Phase.PRE) {
            RenderHandEventCaller.previousScreenContext = context.isScreenContext();
            context.setScreenContext(true);
        }
        else {
            context.setScreenContext(RenderHandEventCaller.previousScreenContext);
        }
        Laby.fireEvent(event);
        gfx.restoreBlaze3DStates();
        return event;
    }
}
