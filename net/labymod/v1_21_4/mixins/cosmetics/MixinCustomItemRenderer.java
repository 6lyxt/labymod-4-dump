// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.cosmetics;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gtd.class })
public class MixinCustomItemRenderer
{
    private bvi labyMod$livingEntity;
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V" }, at = { @At("HEAD") })
    private void labyMod$beginRenderStatic(final bvi livingEntity, final cwq $$1, final cwo $$2, final boolean $$3, final ffv $$4, final glz $$5, final dgj $$6, final int $$7, final int $$8, final int $$9, final CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V" }, at = { @At("TAIL") })
    private void labyMod$endRenderStatic(final bvi livingEntity, final cwq $$1, final cwo $$2, final boolean $$3, final ffv $$4, final glz $$5, final dgj $$6, final int $$7, final int $$8, final int $$9, final CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }
}
