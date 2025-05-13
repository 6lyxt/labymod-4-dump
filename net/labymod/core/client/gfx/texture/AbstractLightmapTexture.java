// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.texture;

import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.texture.LightmapTexture;

public abstract class AbstractLightmapTexture implements LightmapTexture
{
    private final ResourceLocation brightLocation;
    
    public AbstractLightmapTexture() {
        this.brightLocation = Textures.WHITE;
    }
    
    @Override
    public void apply(final int slot) {
        final GFXRenderPipeline renderPipeline = Laby.labyAPI().gfxRenderPipeline();
        final GFXBridge gfx = renderPipeline.gfx();
        if (renderPipeline.renderEnvironmentContext().isScreenContext()) {
            gfx.setActiveTexture(slot);
            gfx.bindResourceLocation(this.brightLocation, true, true);
        }
        else {
            this.applyLightmap(slot);
        }
        gfx.setActiveTexture(0);
    }
    
    @Override
    public void clear(final int slot) {
        ShaderTextures.setShaderTexture(slot, 0);
    }
    
    protected abstract void applyLightmap(final int p0);
    
    protected abstract ResourceLocation getLightmapLocation();
}
