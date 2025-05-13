// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.listener;

import net.labymod.api.event.Subscribe;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gfx.target.RenderTarget;
import net.labymod.api.Laby;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;

public final class RenderTargetListener
{
    private final GFXRenderPipeline renderPipeline;
    
    public RenderTargetListener(final GFXRenderPipeline renderPipeline) {
        this.renderPipeline = renderPipeline;
    }
    
    @Subscribe
    public void onWindowResize(final WindowResizeEvent event) {
        final RenderTarget activityRenderTarget = this.renderPipeline.getActivityRenderTarget();
        if (activityRenderTarget == null) {
            return;
        }
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        activityRenderTarget.resize(window.getRawWidth(), window.getRawHeight());
    }
}
