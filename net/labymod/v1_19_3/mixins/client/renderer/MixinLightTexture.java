// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.renderer;

import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_3.client.renderer.LightTextureAccessor;

@Mixin({ fdu.class })
public class MixinLightTexture implements LightTextureAccessor
{
    @Shadow
    @Final
    private fny d;
    @Shadow
    @Final
    private acf f;
    private ResourceLocation labyMod$textureLocation;
    
    @Override
    public fnx getTexture() {
        return (fnx)this.d;
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        if (this.labyMod$textureLocation == null) {
            this.labyMod$textureLocation = (ResourceLocation)this.f;
        }
        return this.labyMod$textureLocation;
    }
}
