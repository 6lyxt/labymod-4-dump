// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.client.gfx.texture;

import net.labymod.v1_20_1.client.renderer.LightTextureAccessor;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.Laby;
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
        final GFXBridge gfx = Laby.gfx();
        gfx.setActiveTexture(slot);
        gfx.bindResourceLocation(this.getLightmapLocation(), true, true);
    }
    
    @Override
    protected ResourceLocation getLightmapLocation() {
        return ((LightTextureAccessor)this.getLightTexture()).getTextureLocation();
    }
    
    @Override
    public int getTextureId() {
        return ((LightTextureAccessor)this.getLightTexture()).getTexture().a();
    }
    
    private fjw getLightTexture() {
        return enn.N().j.n();
    }
}
