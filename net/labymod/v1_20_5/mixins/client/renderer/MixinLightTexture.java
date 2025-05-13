// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.renderer;

import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_5.client.renderer.LightTextureAccessor;

@Mixin({ gdo.class })
public class MixinLightTexture implements LightTextureAccessor
{
    @Shadow
    @Final
    private gon d;
    @Shadow
    @Final
    private alf f;
    private ResourceLocation labyMod$textureLocation;
    
    @Override
    public gol getTexture() {
        return (gol)this.d;
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        if (this.labyMod$textureLocation == null) {
            this.labyMod$textureLocation = (ResourceLocation)this.f;
        }
        return this.labyMod$textureLocation;
    }
}
