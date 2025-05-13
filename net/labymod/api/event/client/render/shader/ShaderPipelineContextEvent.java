// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.shader;

import net.labymod.api.util.debug.Preconditions;
import org.jetbrains.annotations.NotNull;
import java.util.function.BooleanSupplier;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext;
import net.labymod.api.event.Event;

public class ShaderPipelineContextEvent implements Event
{
    @Nullable
    private ShadowRenderPassContext shadowRenderPassContext;
    @NotNull
    private BooleanSupplier activeShaderPackSupplier;
    
    public ShaderPipelineContextEvent() {
        this.activeShaderPackSupplier = (() -> false);
    }
    
    @Nullable
    public ShadowRenderPassContext getShadowRenderPassContext() {
        return this.shadowRenderPassContext;
    }
    
    public void setShadowRenderPassContext(@Nullable final ShadowRenderPassContext shadowRenderPassContext) {
        this.shadowRenderPassContext = shadowRenderPassContext;
    }
    
    @NotNull
    public BooleanSupplier getActiveShaderPackSupplier() {
        return this.activeShaderPackSupplier;
    }
    
    public void setActiveShaderPackSupplier(final BooleanSupplier activeShaderPackSupplier) {
        Preconditions.notNull(activeShaderPackSupplier, "activeShaderPackSupplier");
        this.activeShaderPackSupplier = activeShaderPackSupplier;
    }
}
