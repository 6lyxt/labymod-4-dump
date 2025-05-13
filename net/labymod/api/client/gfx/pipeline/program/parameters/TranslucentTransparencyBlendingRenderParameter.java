// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters;

import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

public class TranslucentTransparencyBlendingRenderParameter extends RenderParameter
{
    @Override
    public void apply() {
        this.gfx.enableBlend();
        this.gfx.blendSeparate(770, 771, 1, 771);
    }
    
    @Override
    public void clear() {
        this.gfx.disableDepth();
        this.gfx.defaultBlend();
    }
}
