// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.texture.LightmapTexture;
import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

public class LightmapParameter extends RenderParameter
{
    private final int slot;
    
    public LightmapParameter(final int slot) {
        this.slot = slot;
    }
    
    @Override
    public void apply() {
        this.getLightmapTexture().apply(this.slot);
    }
    
    @Override
    public void clear() {
        this.getLightmapTexture().clear(this.slot);
    }
    
    private LightmapTexture getLightmapTexture() {
        return Laby.labyAPI().gfxRenderPipeline().getLightmapTexture();
    }
    
    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        final LightmapParameter that = (LightmapParameter)object;
        return this.slot == that.slot;
    }
    
    @Override
    public int hashCode() {
        return this.slot;
    }
}
