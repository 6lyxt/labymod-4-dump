// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.renderer;

import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_2.client.renderer.LightTextureAccessor;

@Mixin({ fod.class })
public class MixinLightTexture implements LightTextureAccessor
{
    @Shadow
    @Final
    private fyr d;
    @Shadow
    @Final
    private aew f;
    private ResourceLocation labyMod$textureLocation;
    
    @Override
    public fyp getTexture() {
        return (fyp)this.d;
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        if (this.labyMod$textureLocation == null) {
            this.labyMod$textureLocation = (ResourceLocation)this.f;
        }
        return this.labyMod$textureLocation;
    }
}
