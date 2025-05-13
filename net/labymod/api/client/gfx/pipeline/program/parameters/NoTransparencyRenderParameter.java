// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters;

import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

public class NoTransparencyRenderParameter extends RenderParameter
{
    @Override
    public void apply() {
        this.gfx.disableBlend();
    }
    
    @Override
    public void clear() {
    }
}
