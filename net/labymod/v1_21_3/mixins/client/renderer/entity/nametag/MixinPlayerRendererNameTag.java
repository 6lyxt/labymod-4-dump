// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.renderer.entity.nametag;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.Entity;
import net.labymod.v1_21_3.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gwx.class })
public class MixinPlayerRendererNameTag
{
    @Inject(method = { "renderNameTag(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;renderNameTag(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.BEFORE, ordinal = 0) })
    private void labyMod$setScoreboardNameTagType(final gzg state, final xv displayName, final fgs poseStack, final gll bufferSource, final int par5, final CallbackInfo ci) {
        final EntityRenderStateAccessor<bvk> entityState = EntityRenderStateAccessor.self(state);
        if (entityState == null) {
            return;
        }
        ((Entity)entityState.labyMod$getEntity()).setNameTagType(TagType.SCOREBOARD);
    }
    
    @Inject(method = { "renderNameTag(Lnet/minecraft/client/renderer/entity/state/PlayerRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;renderNameTag(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.BEFORE, ordinal = 1) })
    private void labyMod$setMainNameTagType(final gzg state, final xv displayName, final fgs poseStack, final gll bufferSource, final int par5, final CallbackInfo ci) {
        final EntityRenderStateAccessor<bvk> entityState = EntityRenderStateAccessor.self(state);
        if (entityState == null) {
            return;
        }
        ((Entity)entityState.labyMod$getEntity()).setNameTagType(TagType.MAIN_TAG);
    }
}
