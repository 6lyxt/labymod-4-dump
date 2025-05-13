// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters.ui;

import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

public class UIBlendingRenderParameter extends RenderParameter
{
    @Override
    public void apply() {
        this.gfx.enableBlend();
        this.gfx.blendSeparate(770, 771, 1, 771);
    }
    
    @Override
    public void clear() {
    }
}
