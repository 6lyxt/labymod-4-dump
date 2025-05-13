// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters;

import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

public class CullRenderParameter extends RenderParameter
{
    private final boolean enabled;
    
    public CullRenderParameter(final boolean enabled) {
        this.enabled = enabled;
    }
    
    @Override
    public void apply() {
        if (this.enabled) {
            this.gfx.enableCull();
        }
        else {
            this.gfx.disableCull();
        }
    }
    
    @Override
    public void clear() {
        if (this.enabled) {
            this.gfx.disableCull();
        }
        else {
            this.gfx.enableCull();
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
        final CullRenderParameter that = (CullRenderParameter)o;
        return this.enabled == that.enabled;
    }
    
    @Override
    public int hashCode() {
        return this.enabled ? 1 : 0;
    }
}
