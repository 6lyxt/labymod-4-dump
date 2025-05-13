// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.cosmetics;

import net.labymod.api.event.client.render.item.PlayerItemRenderContextEvent;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.event.client.render.item.PlayerItemRenderContextEventCaller;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_20_1.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fpw.class })
public class MixinCustomItemRenderer
{
    private bfz labyMod$livingEntity;
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V" }, at = { @At("HEAD") })
    private void labyMod$beginRenderStatic(final bfz livingEntity, final cfz $$1, final cfw $$2, final boolean $$3, final eij $$4, final fjx $$5, final cmm $$6, final int $$7, final int $$8, final int $$9, final CallbackInfo ci) {
        this.labyMod$livingEntity = livingEntity;
    }
    
    @Inject(method = { "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V" }, at = { @At("TAIL") })
    private void labyMod$endRenderStatic(final bfz livingEntity, final cfz $$1, final cfw $$2, final boolean $$3, final eij $$4, final fjx $$5, final cmm $$6, final int $$7, final int $$8, final int $$9, final CallbackInfo ci) {
        this.labyMod$livingEntity = null;
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$callPlayerItemRenderContext(final cfz itemStack, final cfw displayContext, final boolean leftHand, final eij poseStack, final fjx bufferSource, final int packedLightCoords, final int packedOverlayCoords, final fwr model, final CallbackInfo ci) {
        if (displayContext == cfw.h) {
            return;
        }
        bfz currentLivingEntity = this.labyMod$livingEntity;
        if (currentLivingEntity == null) {
            currentLivingEntity = (bfz)enn.N().t;
        }
        final ItemStack apiItemStack = MinecraftUtil.fromMinecraft(itemStack);
        if (!itemStack.b() && currentLivingEntity instanceof byo) {
            final byo player = (byo)currentLivingEntity;
            final fox renderer = enn.N().an().a((bfj)player);
            if (!(renderer instanceof fty)) {
                return;
            }
            final fty playerRenderer = (fty)renderer;
            final PlayerItemRenderContextEvent event = PlayerItemRenderContextEventCaller.call(((VanillaStackAccessor)poseStack).stack(), apiItemStack, MinecraftUtil.fromMinecraft(displayContext), (Player)player, (PlayerModel)playerRenderer.a(), packedLightCoords);
            if (event.isCancelled()) {
                poseStack.b();
                ci.cancel();
            }
        }
    }
}
