// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderPipeline;
import net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface RenderEnvironmentContext
{
    public static final int NO_OVERLAY = 655360;
    public static final int FULL_BRIGHT = 15728880;
    public static final float GAME_UNIT = 0.0625f;
    
    RenderAttributesStack renderAttributesStack();
    
    boolean isScreenContext();
    
    void setScreenContext(final boolean p0);
    
    default ShadowRenderPassContext shadowRenderPassContext() {
        return this.shaderPipeline().shadowRenderPassContext();
    }
    
    ShaderPipeline shaderPipeline();
    
    int getPackedLight();
    
    default int getPackedLightWithCondition() {
        return this.isScreenContext() ? 15728880 : this.getPackedLight();
    }
    
    void setPackedLight(final int p0);
    
    ScreenContext screenContext();
}
