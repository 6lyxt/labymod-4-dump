// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.renderer.entity.nametag;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ goe.class })
public class MixinPlayerRendererNameTag
{
    @Inject(method = { "renderNameTag(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;renderNameTag(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IF)V", shift = At.Shift.BEFORE, ordinal = 0) })
    private void labyMod$setScoreboardNameTagType(final gcp entity, final xp displayName, final faa poseStack, final gdq bufferSource, final int packedLight, final float partialTicks, final CallbackInfo callbackInfo) {
        ((Entity)entity).setNameTagType(TagType.SCOREBOARD);
    }
    
    @Inject(method = { "renderNameTag(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;renderNameTag(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IF)V", shift = At.Shift.BEFORE, ordinal = 1) })
    private void labyMod$setMainNameTagType(final gcp entity, final xp displayName, final faa poseStack, final gdq bufferSource, final int packedLight, final float partialTicks, final CallbackInfo callbackInfo) {
        ((Entity)entity).setNameTagType(TagType.MAIN_TAG);
    }
}
