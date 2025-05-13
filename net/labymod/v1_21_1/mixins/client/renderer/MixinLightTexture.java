// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.renderer;

import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_1.client.renderer.LightTextureAccessor;

@Mixin({ gey.class })
public class MixinLightTexture implements LightTextureAccessor
{
    @Shadow
    @Final
    private gpy d;
    @Shadow
    @Final
    private akr f;
    private ResourceLocation labyMod$textureLocation;
    
    @Override
    public gpw getTexture() {
        return (gpw)this.d;
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        if (this.labyMod$textureLocation == null) {
            this.labyMod$textureLocation = (ResourceLocation)this.f;
        }
        return this.labyMod$textureLocation;
    }
}
