// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters;

import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

public class ColorMaskRenderParameter extends RenderParameter
{
    private static final boolean DEFAULT_WRITE = true;
    private final boolean red;
    private final boolean green;
    private final boolean blue;
    private final boolean alpha;
    
    public ColorMaskRenderParameter(final boolean red, final boolean green, final boolean blue, final boolean alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    
    @Override
    public void apply() {
        this.gfx.colorMask(this.red, this.green, this.blue, this.alpha);
    }
    
    @Override
    public void clear() {
        this.gfx.colorMask(true, true, true, true);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ColorMaskRenderParameter that = (ColorMaskRenderParameter)o;
        return this.red == that.red && this.green == that.green && this.blue == that.blue && this.alpha == that.alpha;
    }
    
    @Override
    public int hashCode() {
        int result = this.red ? 1 : 0;
        result = 31 * result + (this.green ? 1 : 0);
        result = 31 * result + (this.blue ? 1 : 0);
        result = 31 * result + (this.alpha ? 1 : 0);
        return result;
    }
}
