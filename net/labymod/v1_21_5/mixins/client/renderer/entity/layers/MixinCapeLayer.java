// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer.entity.layers;

import net.labymod.api.event.client.render.model.entity.player.PlayerCapeRenderEvent;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.util.RenderStateShardAttachment;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.v1_21_5.client.util.PoseStackVisitor;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.entity.player.OptiFinePlayer;
import net.labymod.v1_21_5.client.util.EntityRenderStateAccessor;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.Phase;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.core.main.LabyMod;
import net.labymod.core.client.gfx.pipeline.renderer.cape.particle.CapeParticleController;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ hbj.class })
public class MixinCapeLayer
{
    @Shadow
    @Final
    private gib<hfq> a;
    private final CapeParticleController labyMod$capeParticleController;
    private gry labyMod$renderType;
    
    public MixinCapeLayer() {
        this.labyMod$capeParticleController = LabyMod.references().capeParticleController();
    }
    
    @Inject(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;", shift = At.Shift.BEFORE) }, cancellable = true)
    private void labyMod$firePreCapeRenderEvent(final fld poseStack, final grn bufferSource, final int $$2, final hfq state, final float $$4, final float $$5, final CallbackInfo ci) {
        if (this.labyMod$fireCapeRenderEvent(poseStack, state, Phase.PRE)) {
            poseStack.b();
            ci.cancel();
        }
    }
    
    @Inject(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER) })
    private void labyMod$firePostCapeRenderEvent(final fld poseStack, final grn bufferSource, final int $$2, final hfq state, final float $$4, final float $$5, final CallbackInfo ci) {
        this.labyMod$fireCapeRenderEvent(poseStack, state, Phase.POST);
    }
    
    @WrapOperation(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entitySolid(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;") })
    private gry labyMod$attach(final alr location, final Operation<gry> original) {
        return this.labyMod$renderType = (gry)original.call(new Object[] { location });
    }
    
    @Inject(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/renderer/entity/state/PlayerRenderState;FF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/model/HumanoidModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER) })
    private void labyMod$renderCapeParticles(final fld poseStack, final grn $$1, final int $$2, final hfq state, final float $$4, final float $$5, final CallbackInfo ci) {
        final EntityRenderStateAccessor<gqj> playerState = EntityRenderStateAccessor.self(state);
        if (playerState == null) {
            return;
        }
        final gqj player = playerState.labyMod$getEntity();
        if (player instanceof final OptiFinePlayer optiFinePlayer) {
            if (optiFinePlayer.getOptiFineCapeLocation() != null) {
                return;
            }
        }
        final Stack stack = ((VanillaStackAccessor)poseStack).stack();
        poseStack.a();
        this.a.e().a(MinecraftUtil.VISITOR_STACK, (gkr.d)new PoseStackVisitor(poseStack));
        final FloatMatrix4 modelViewMatrix = stack.getProvider().getPosition().copy();
        RenderStateShardAttachment.addAttachment(this.labyMod$renderType, () -> this.labyMod$capeParticleController.spawn(modelViewMatrix, (Player)player, Laby.labyAPI().minecraft().getPartialTicks()));
        poseStack.b();
    }
    
    private boolean labyMod$fireCapeRenderEvent(final fld poseStack, final hfq state, final Phase phase) {
        final EntityRenderStateAccessor<gqj> playerState = EntityRenderStateAccessor.self(state);
        if (playerState == null) {
            return false;
        }
        final Player player = (Player)playerState.labyMod$getEntity();
        final PlayerModel playerModel = (PlayerModel)((hcj)this).d();
        final Stack stack = ((VanillaStackAccessor)poseStack).stack();
        return Laby.fireEvent(new PlayerCapeRenderEvent(player, playerModel, stack, phase)).isCancelled();
    }
}
