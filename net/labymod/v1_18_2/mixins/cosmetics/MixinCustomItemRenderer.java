// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.cosmetics;

import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_18_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ewh.class })
public class MixinCustomItemRenderer
{
    private axy labyMod$livingEntity;
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V" }, at = { @At("HEAD") })
    private void labyMod$beginRenderStatic(final axy livingEntity, final buw $$1, final ery.b $$2, final boolean $$3, final dtm $$4, final eqs $$5, final cav $$6, final int $$7, final int $$8, final int $$9, final CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V" }, at = { @At("TAIL") })
    private void labyMod$endRenderStatic(final axy livingEntity, final buw $$1, final ery.b $$2, final boolean $$3, final dtm $$4, final eqs $$5, final cav $$6, final int $$7, final int $$8, final int $$9, final CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(DDD)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(final buw itemStack, final ery.b transformType, final boolean leftHand, final dtm poseStack, final eqs bufferSource, final int packedLightCoords, final int packedOverlayCoords, final fck model, final CallbackInfo ci) {
        if (transformType == ery.b.h) {
            return;
        }
        axy currentLivingEntity = this.labyMod$livingEntity;
        if (currentLivingEntity == null) {
            currentLivingEntity = (axy)dyr.D().s;
        }
        final ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.b() && currentLivingEntity instanceof boj) {
            final boj player = (boj)currentLivingEntity;
            final evj renderer = dyr.D().ab().a((axk)player);
            if (!(renderer instanceof faf)) {
                return;
            }
            final faf playerRenderer = (faf)renderer;
            final PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(((VanillaStackAccessor)poseStack).stack(), apiItemStack, MinecraftUtil.fromMinecraft(transformType), (Player)player, (PlayerModel)playerRenderer.a(), packedLightCoords);
            if (event.isCancelled()) {
                poseStack.b();
                ci.cancel();
            }
        }
    }
}
