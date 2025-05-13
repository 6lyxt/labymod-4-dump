// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.renderer;

import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_18_2.client.renderer.LightTextureAccessor;

@Mixin({ eqr.class })
public class MixinLightTexture implements LightTextureAccessor
{
    @Shadow
    @Final
    private fao d;
    @Shadow
    @Final
    private yt f;
    private ResourceLocation labyMod$textureLocation;
    
    @Override
    public fam getTexture() {
        return (fam)this.d;
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        if (this.labyMod$textureLocation == null) {
            this.labyMod$textureLocation = (ResourceLocation)this.f;
        }
        return this.labyMod$textureLocation;
    }
}
