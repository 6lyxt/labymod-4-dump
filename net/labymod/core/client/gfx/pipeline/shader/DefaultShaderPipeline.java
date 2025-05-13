// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.shader;

import net.labymod.core.client.gfx.pipeline.renderer.shadow.DefaultShadowRenderPassContext;
import net.labymod.api.Laby;
import net.labymod.api.event.client.render.shadow.ShadowRenderPassContextEvent;
import net.labymod.api.event.client.render.shader.ShaderPipelineContextEvent;
import javax.inject.Inject;
import java.util.function.BooleanSupplier;
import net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderPipeline;

@Singleton
@Implements(ShaderPipeline.class)
public class DefaultShaderPipeline implements ShaderPipeline
{
    private ShadowRenderPassContext shadowRenderPassContext;
    private BooleanSupplier activeShaderPackSupplier;
    
    @Inject
    public DefaultShaderPipeline() {
        this.fireShaderPipelineContextEvent();
    }
    
    @Override
    public boolean hasActiveShaderPack() {
        return this.activeShaderPackSupplier.getAsBoolean();
    }
    
    @Override
    public ShadowRenderPassContext shadowRenderPassContext() {
        return this.shadowRenderPassContext;
    }
    
    public void fireShaderPipelineContextEvent() {
        final ShaderPipelineContextEvent shaderPipelineEvent = new ShaderPipelineContextEvent();
        Laby.fireEvent(new ShadowRenderPassContextEvent(shaderPipelineEvent));
        Laby.fireEvent(shaderPipelineEvent);
        final ShadowRenderPassContext context = shaderPipelineEvent.getShadowRenderPassContext();
        this.shadowRenderPassContext = ((context == null) ? new DefaultShadowRenderPassContext() : context);
        this.activeShaderPackSupplier = shaderPipelineEvent.getActiveShaderPackSupplier();
    }
}
