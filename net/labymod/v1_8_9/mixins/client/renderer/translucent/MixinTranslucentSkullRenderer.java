// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer.translucent;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.TranslucentSkins;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.authlib.GameProfile;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bhk.class })
public class MixinTranslucentSkullRenderer
{
    @Inject(method = { "renderSkull" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.BEFORE) })
    private void labyMod$fix$enableBlend(final float lvt_1_1_, final float lvt_2_1_, final float lvt_3_1_, final cq lvt_4_1_, final float lvt_5_1_, final int lvt_6_1_, final GameProfile lvt_7_1_, final int lvt_8_1_, final CallbackInfo ci) {
        TranslucentSkins.enableBlend();
    }
    
    @Inject(method = { "renderSkull" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", shift = At.Shift.AFTER) })
    private void labyMod$fix$disableBlend(final float lvt_1_1_, final float lvt_2_1_, final float lvt_3_1_, final cq lvt_4_1_, final float lvt_5_1_, final int lvt_6_1_, final GameProfile lvt_7_1_, final int lvt_8_1_, final CallbackInfo ci) {
        TranslucentSkins.disableBlend();
    }
}
