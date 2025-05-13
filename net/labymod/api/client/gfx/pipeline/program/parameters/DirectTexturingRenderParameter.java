// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.program.parameters;

import java.util.Objects;
import net.labymod.api.client.gfx.pipeline.util.TextureId;
import java.util.function.IntSupplier;
import net.labymod.api.client.gfx.pipeline.program.RenderParameter;

public class DirectTexturingRenderParameter extends RenderParameter
{
    private final int textureSlot;
    private final IntSupplier textureId;
    
    public DirectTexturingRenderParameter(final int textureSlot, final IntSupplier textureId) {
        this.textureSlot = textureSlot;
        this.textureId = textureId;
    }
    
    @Override
    public void apply() {
        this.gfx.enableTexture();
        this.gfx.setActiveTexture(this.textureSlot);
        this.gfx.bindTexture2D(TextureId.of(this.textureId.getAsInt()));
    }
    
    @Override
    public void clear() {
    }
    
    public int getTextureSlot() {
        return this.textureSlot;
    }
    
    public int getTextureId() {
        return this.textureId.getAsInt();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final DirectTexturingRenderParameter that = (DirectTexturingRenderParameter)o;
        return this.textureSlot == that.textureSlot && Objects.equals(this.textureId, that.textureId);
    }
    
    @Override
    public int hashCode() {
        int result = this.textureSlot;
        result = 31 * result + ((this.textureId != null) ? this.textureId.hashCode() : 0);
        return result;
    }
}
