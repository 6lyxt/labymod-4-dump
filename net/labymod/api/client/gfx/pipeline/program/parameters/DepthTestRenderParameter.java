// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters;

import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

public class DepthTestRenderParameter extends RenderParameter
{
    private final int func;
    
    public DepthTestRenderParameter(final int func) {
        this.func = func;
    }
    
    @Override
    public void apply() {
        if (this.func == 519) {
            return;
        }
        this.gfx.enableDepth();
        this.gfx.depthFunc(this.func);
    }
    
    @Override
    public void clear() {
        if (this.func == 519) {
            return;
        }
        this.gfx.disableDepth();
        this.gfx.depthFunc(515);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DepthTestRenderParameter that = (DepthTestRenderParameter)o;
        return this.func == that.func;
    }
    
    @Override
    public int hashCode() {
        return this.func;
    }
}
