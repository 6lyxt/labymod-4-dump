// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters;

import java.util.Objects;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

public class TexturingRenderParameter extends RenderParameter
{
    private final int textureSlot;
    private final boolean blur;
    private final boolean mipmap;
    private ResourceLocation location;
    
    public TexturingRenderParameter(final ResourceLocation location, final int textureSlot, final boolean blur, final boolean mipmap) {
        this.location = location;
        this.textureSlot = textureSlot;
        this.blur = blur;
        this.mipmap = mipmap;
    }
    
    @Override
    public void apply() {
        this.gfx.enableTexture();
        this.gfx.setActiveTexture(this.textureSlot);
        this.gfx.bindResourceLocation(this.location, this.blur, this.mipmap);
    }
    
    @Override
    public void clear() {
    }
    
    public ResourceLocation getLocation() {
        return this.location;
    }
    
    public void setLocation(final ResourceLocation location) {
        this.location = location;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final TexturingRenderParameter that = (TexturingRenderParameter)o;
        return this.textureSlot == that.textureSlot && this.blur == that.blur && this.mipmap == that.mipmap && Objects.equals(this.location, that.location);
    }
    
    @Override
    public int hashCode() {
        int result = (this.location != null) ? this.location.hashCode() : 0;
        result = 31 * result + this.textureSlot;
        result = 31 * result + (this.blur ? 1 : 0);
        result = 31 * result + (this.mipmap ? 1 : 0);
        return result;
    }
}
