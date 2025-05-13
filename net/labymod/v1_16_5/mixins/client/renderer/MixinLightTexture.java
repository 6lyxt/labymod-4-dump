// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.renderer;

import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_16_5.client.renderer.LightTextureAccessor;

@Mixin({ eaf.class })
public class MixinLightTexture implements LightTextureAccessor
{
    @Shadow
    @Final
    private ejs a;
    @Shadow
    @Final
    private vk c;
    private ResourceLocation labyMod$textureLocation;
    
    @Override
    public ejq getTexture() {
        return (ejq)this.a;
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        if (this.labyMod$textureLocation == null) {
            this.labyMod$textureLocation = (ResourceLocation)this.c;
        }
        return this.labyMod$textureLocation;
    }
}
