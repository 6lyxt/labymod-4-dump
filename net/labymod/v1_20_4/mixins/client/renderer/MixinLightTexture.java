// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.renderer;

import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_4.client.renderer.LightTextureAccessor;

@Mixin({ ftg.class })
public class MixinLightTexture implements LightTextureAccessor
{
    @Shadow
    @Final
    private gea d;
    @Shadow
    @Final
    private ahg f;
    private ResourceLocation labyMod$textureLocation;
    
    @Override
    public gdy getTexture() {
        return (gdy)this.d;
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        if (this.labyMod$textureLocation == null) {
            this.labyMod$textureLocation = (ResourceLocation)this.f;
        }
        return this.labyMod$textureLocation;
    }
}
