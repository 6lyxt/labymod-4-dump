// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters;

import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

public class DepthMaskRenderParameter extends RenderParameter
{
    private static final boolean DEFAULT_WRITE_MASK = true;
    private final boolean writeDepth;
    
    public DepthMaskRenderParameter(final boolean writeDepth) {
        this.writeDepth = writeDepth;
    }
    
    @Override
    public void apply() {
        this.gfx.depthMask(this.writeDepth);
    }
    
    @Override
    public void clear() {
        if (!this.writeDepth) {
            this.gfx.depthMask(true);
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DepthMaskRenderParameter that = (DepthMaskRenderParameter)o;
        return this.writeDepth == that.writeDepth;
    }
    
    @Override
    public int hashCode() {
        return this.writeDepth ? 1 : 0;
    }
}
