// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.cosmetics;

import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_19_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ffj.class })
public class MixinCustomItemRenderer
{
    private bcc labyMod$livingEntity;
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V" }, at = { @At("HEAD") })
    private void labyMod$beginRenderStatic(final bcc livingEntity, final cax $$1, final fay.b $$2, final boolean $$3, final eaq $$4, final ezs $$5, final cgx $$6, final int $$7, final int $$8, final int $$9, final CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V" }, at = { @At("TAIL") })
    private void labyMod$endRenderStatic(final bcc livingEntity, final cax $$1, final fay.b $$2, final boolean $$3, final eaq $$4, final ezs $$5, final cgx $$6, final int $$7, final int $$8, final int $$9, final CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(DDD)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(final cax itemStack, final fay.b transformType, final boolean leftHand, final eaq poseStack, final ezs bufferSource, final int packedLightCoords, final int packedOverlayCoords, final flq model, final CallbackInfo ci) {
        if (transformType == fay.b.h) {
            return;
        }
        bcc currentLivingEntity = this.labyMod$livingEntity;
        if (currentLivingEntity == null) {
            currentLivingEntity = (bcc)efu.I().t;
        }
        final ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.b() && currentLivingEntity instanceof buc) {
            final buc player = (buc)currentLivingEntity;
            final fek renderer = efu.I().ag().a((bbn)player);
            if (!(renderer instanceof fjk)) {
                return;
            }
            final fjk playerRenderer = (fjk)renderer;
            final PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(((VanillaStackAccessor)poseStack).stack(), apiItemStack, MinecraftUtil.fromMinecraft(transformType), (Player)player, (PlayerModel)playerRenderer.a(), packedLightCoords);
            if (event.isCancelled()) {
                poseStack.b();
                ci.cancel();
            }
        }
    }
}
