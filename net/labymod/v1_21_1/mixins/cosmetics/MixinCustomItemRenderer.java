// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.cosmetics;

import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_21_1.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ glh.class })
public class MixinCustomItemRenderer
{
    private btn labyMod$livingEntity;
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V" }, at = { @At("HEAD") })
    private void labyMod$beginRenderStatic(final btn livingEntity, final cuq $$1, final cun $$2, final boolean $$3, final fbi $$4, final gez $$5, final dcw $$6, final int $$7, final int $$8, final int $$9, final CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V" }, at = { @At("TAIL") })
    private void labyMod$endRenderStatic(final btn livingEntity, final cuq $$1, final cun $$2, final boolean $$3, final fbi $$4, final gez $$5, final dcw $$6, final int $$7, final int $$8, final int $$9, final CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(final cuq itemStack, final cun displayContext, final boolean leftHand, final fbi poseStack, final gez bufferSource, final int packedLightCoords, final int packedOverlayCoords, final gsm model, final CallbackInfo ci) {
        if (displayContext == cun.h) {
            return;
        }
        btn currentLivingEntity = this.labyMod$livingEntity;
        if (currentLivingEntity == null) {
            currentLivingEntity = (btn)fgo.Q().s;
        }
        final ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.e() && currentLivingEntity instanceof cmx) {
            final cmx player = (cmx)currentLivingEntity;
            final gki renderer = fgo.Q().ap().a((bsr)player);
            if (!(renderer instanceof gpo)) {
                return;
            }
            final gpo playerRenderer = (gpo)renderer;
            final PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(((VanillaStackAccessor)poseStack).stack(), apiItemStack, MinecraftUtil.fromMinecraft(displayContext), (Player)player, (PlayerModel)playerRenderer.a(), packedLightCoords);
            if (event.isCancelled()) {
                poseStack.b();
                ci.cancel();
            }
        }
    }
}
