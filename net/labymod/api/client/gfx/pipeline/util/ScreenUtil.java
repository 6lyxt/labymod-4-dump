// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.util;

import net.labymod.api.event.client.render.ScreenRenderEvent;
import net.labymod.api.client.render.statistics.FrameTimer;
import net.labymod.api.event.Phase;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;

public final class ScreenUtil
{
    public static void wrapRender(final Stack stack, final float tickDelta, final Runnable renderer) {
        final FrameTimer frameTimer = Laby.references().frameTimer();
        if (frameTimer.isPaused() && !frameTimer.isPauseInterrupted()) {
            return;
        }
        fireScreenRenderEvent(stack, Phase.PRE, tickDelta);
        renderer.run();
        fireScreenRenderEvent(stack, Phase.POST, tickDelta);
        if (frameTimer.isPauseInterrupted()) {
            frameTimer.onPauseFrameRendered();
        }
    }
    
    public static void setScreenContext(final boolean screenContext) {
        Laby.labyAPI().gfxRenderPipeline().renderEnvironmentContext().setScreenContext(screenContext);
    }
    
    private static void fireScreenRenderEvent(final Stack stack, final Phase phase, final float tickDelta) {
        Laby.fireEvent(new ScreenRenderEvent(stack, phase, tickDelta));
    }
}
