// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.shadow;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext;
import net.labymod.api.event.client.render.shader.ShaderPipelineContextEvent;
import net.labymod.api.event.Event;

@Deprecated(forRemoval = true, since = "4.1.3")
public class ShadowRenderPassContextEvent implements Event
{
    private final ShaderPipelineContextEvent event;
    
    public ShadowRenderPassContextEvent(final ShaderPipelineContextEvent event) {
        this.event = event;
    }
    
    @Nullable
    public ShadowRenderPassContext getContext() {
        return this.event.getShadowRenderPassContext();
    }
    
    public void setContext(@Nullable final ShadowRenderPassContext context) {
        this.event.setShadowRenderPassContext(context);
    }
}
