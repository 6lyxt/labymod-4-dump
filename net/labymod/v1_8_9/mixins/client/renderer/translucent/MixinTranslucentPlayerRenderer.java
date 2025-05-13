// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.translucent;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.TranslucentSkins;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bln.class })
public class MixinTranslucentPlayerRenderer
{
    @Inject(method = { "doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", shift = At.Shift.BEFORE) })
    private void labyMod$fix$enableBlending(final bet lvt_1_1_, final double lvt_2_1_, final double lvt_4_1_, final double lvt_6_1_, final float lvt_8_1_, final float lvt_9_1_, final CallbackInfo ci) {
        TranslucentSkins.enableBlend();
    }
    
    @Inject(method = { "doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/RendererLivingEntity;doRender(Lnet/minecraft/entity/EntityLivingBase;DDDFF)V", shift = At.Shift.AFTER) })
    private void labyMod$fix$disableBlending(final bet lvt_1_1_, final double lvt_2_1_, final double lvt_4_1_, final double lvt_6_1_, final float lvt_8_1_, final float lvt_9_1_, final CallbackInfo ci) {
        TranslucentSkins.disableBlend();
    }
}
