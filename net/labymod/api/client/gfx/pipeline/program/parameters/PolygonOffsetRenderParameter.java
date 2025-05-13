// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters;

import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

public class PolygonOffsetRenderParameter extends RenderParameter
{
    private final float factor;
    private final float units;
    
    public PolygonOffsetRenderParameter(final float factor, final float units) {
        this.factor = factor;
        this.units = units;
    }
    
    @Override
    public void apply() {
        this.gfx.enablePolygonOffset();
        this.gfx.polygonOffset(this.factor, this.units);
    }
    
    @Override
    public void clear() {
        this.gfx.polygonOffset(0.0f, 0.0f);
        this.gfx.disablePolygonOffset();
    }
}
