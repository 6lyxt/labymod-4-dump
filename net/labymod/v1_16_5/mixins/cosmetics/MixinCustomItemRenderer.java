// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.cosmetics;

import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_16_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ efo.class })
public class MixinCustomItemRenderer
{
    private aqm labyMod$livingEntity;
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;II)V" }, at = { @At("HEAD") })
    private void labyMod$beginRenderStatic(final aqm livingEntity, final bmb $$1, final ebm.b $$2, final boolean $$3, final dfm $$4, final eag $$5, final brx $$6, final int $$7, final int $$8, final CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;II)V" }, at = { @At("TAIL") })
    private void labyMod$endRenderStatic(final aqm livingEntity, final bmb $$1, final ebm.b $$2, final boolean $$3, final dfm $$4, final eag $$5, final brx $$6, final int $$7, final int $$8, final CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(DDD)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(final bmb itemStack, final ebm.b transformType, final boolean leftHand, final dfm poseStack, final eag bufferSource, final int packedLightCoords, final int packedOverlayCoords, final elo model, final CallbackInfo ci) {
        if (transformType == ebm.b.h) {
            return;
        }
        aqm currentLivingEntity = this.labyMod$livingEntity;
        if (currentLivingEntity == null) {
            currentLivingEntity = (aqm)djz.C().s;
        }
        final ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.a() && currentLivingEntity instanceof bfw) {
            final bfw player = (bfw)currentLivingEntity;
            final eeu renderer = djz.C().ac().a((aqa)player);
            if (!(renderer instanceof ejk)) {
                return;
            }
            final ejk playerRenderer = (ejk)renderer;
            final PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(((VanillaStackAccessor)poseStack).stack(), apiItemStack, MinecraftUtil.fromMinecraft(transformType), (Player)player, (PlayerModel)playerRenderer.c(), packedLightCoords);
            if (event.isCancelled()) {
                poseStack.b();
                ci.cancel();
            }
        }
    }
}
