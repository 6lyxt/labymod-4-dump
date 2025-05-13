// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.renderer;

import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_3.client.renderer.LightTextureAccessor;

@Mixin({ glj.class })
public class MixinLightTexture implements LightTextureAccessor
{
    @Shadow
    @Final
    private ffd e;
    private ResourceLocation labyMod$textureLocation;
    
    public MixinLightTexture() {
        this.labyMod$textureLocation = ResourceLocation.create("minecraft", "lightmap");
    }
    
    @Override
    public int getTextureId() {
        return this.e.g();
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        return this.labyMod$textureLocation;
    }
}
