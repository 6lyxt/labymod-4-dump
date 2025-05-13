// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.renderer;

import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_2.client.renderer.LightTextureAccessor;

@Mixin({ ezr.class })
public class MixinLightTexture implements LightTextureAccessor
{
    @Shadow
    @Final
    private fju d;
    @Shadow
    @Final
    private abb f;
    private ResourceLocation labyMod$textureLocation;
    
    @Override
    public fjs getTexture() {
        return (fjs)this.d;
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        if (this.labyMod$textureLocation == null) {
            this.labyMod$textureLocation = (ResourceLocation)this.f;
        }
        return this.labyMod$textureLocation;
    }
}
