// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer;

import net.labymod.v1_21_5.client.util.MinecraftUtil;
import net.labymod.api.client.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import com.mojang.blaze3d.textures.GpuTexture;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_5.client.renderer.LightTextureAccessor;

@Mixin({ grk.class })
public class MixinLightTexture implements LightTextureAccessor
{
    @Shadow
    @Final
    private GpuTexture e;
    private ResourceLocation labyMod$textureLocation;
    
    public MixinLightTexture() {
        this.labyMod$textureLocation = ResourceLocation.create("minecraft", "lightmap");
    }
    
    @Override
    public int getTextureId() {
        return MinecraftUtil.getGpuTextureId(this.e);
    }
    
    @Override
    public ResourceLocation getTextureLocation() {
        return this.labyMod$textureLocation;
    }
}
