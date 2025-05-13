// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.cosmetics;

import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_20_6.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gjx.class })
public class MixinCustomItemRenderer
{
    private btr labyMod$livingEntity;
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V" }, at = { @At("HEAD") })
    private void labyMod$beginRenderStatic(final btr livingEntity, final cur $$1, final cuo $$2, final boolean $$3, final faa $$4, final gdq $$5, final dca $$6, final int $$7, final int $$8, final int $$9, final CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V" }, at = { @At("TAIL") })
    private void labyMod$endRenderStatic(final btr livingEntity, final cur $$1, final cuo $$2, final boolean $$3, final faa $$4, final gdq $$5, final dca $$6, final int $$7, final int $$8, final int $$9, final CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(final cur itemStack, final cuo displayContext, final boolean leftHand, final faa poseStack, final gdq bufferSource, final int packedLightCoords, final int packedOverlayCoords, final grc model, final CallbackInfo ci) {
        if (displayContext == cuo.h) {
            return;
        }
        btr currentLivingEntity = this.labyMod$livingEntity;
        if (currentLivingEntity == null) {
            currentLivingEntity = (btr)ffh.Q().s;
        }
        final ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.e() && currentLivingEntity instanceof cmz) {
            final cmz player = (cmz)currentLivingEntity;
            final giy renderer = ffh.Q().ap().a((bsw)player);
            if (!(renderer instanceof goe)) {
                return;
            }
            final goe playerRenderer = (goe)renderer;
            final PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(((VanillaStackAccessor)poseStack).stack(), apiItemStack, MinecraftUtil.fromMinecraft(displayContext), (Player)player, (PlayerModel)playerRenderer.a(), packedLightCoords);
            if (event.isCancelled()) {
                poseStack.b();
                ci.cancel();
            }
        }
    }
}
