// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.gfx.texture;

import net.labymod.v1_21_5.client.renderer.LightTextureAccessor;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import com.mojang.blaze3d.systems.RenderSystem;
import javax.inject.Inject;
import net.labymod.api.client.gfx.texture.LightmapTexture;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.gfx.texture.AbstractLightmapTexture;

@Singleton
@Implements(LightmapTexture.class)
public class VersionedLightmapTexture extends AbstractLightmapTexture
{
    @Inject
    public VersionedLightmapTexture() {
    }
    
    @Override
    protected void applyLightmap(final int slot) {
        RenderSystem.setShaderTexture(slot, this.getLightTexture().a());
        ShaderTextures.setShaderTexture(slot, this.getTextureId());
    }
    
    @Override
    protected ResourceLocation getLightmapLocation() {
        return ((LightTextureAccessor)this.getLightTexture()).getTextureLocation();
    }
    
    @Override
    public int getTextureId() {
        return ((LightTextureAccessor)this.getLightTexture()).getTextureId();
    }
    
    private grk getLightTexture() {
        return fqq.Q().j.l();
    }
}
