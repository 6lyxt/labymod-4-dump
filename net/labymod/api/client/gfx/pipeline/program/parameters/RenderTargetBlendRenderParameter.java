// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters;

import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

public class RenderTargetBlendRenderParameter extends RenderParameter
{
    @Override
    public void apply() {
        this.gfx.enableBlend();
        this.gfx.blendEquation(32774);
        this.gfx.blendSeparate(1, 771, 1, 771);
    }
    
    @Override
    public void clear() {
        this.gfx.defaultBlend();
    }
}
