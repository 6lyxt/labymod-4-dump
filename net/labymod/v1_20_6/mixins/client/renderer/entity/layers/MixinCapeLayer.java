// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.model.entity.player.PlayerCapeRenderEvent;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.util.RenderStateShardAttachment;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.entity.player.OptiFinePlayer;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.event.Phase;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.main.LabyMod;
import net.labymod.core.client.gfx.pipeline.renderer.cape.particle.CapeParticleController;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gml.class })
public class MixinCapeLayer
{
    private final CapeParticleController labyMod$capeParticleController;
    private gdy labyMod$renderType;
    
    public MixinCapeLayer() {
        this.labyMod$capeParticleController = LabyMod.references().capeParticleController();
    }
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V" }, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V", shift = At.Shift.AFTER), cancellable = true)
    private void labyMod$firePreCapeRenderEvent(final faa poseStack, final gdq param1, final int param2, final gcp clientPlayer, final float param4, final float param5, final float partialTicks, final float param7, final float param8, final float param9, final InsertInfo ci) {
        if (this.labyMod$fireCapeRenderEvent(poseStack, clientPlayer, Phase.PRE)) {
            poseStack.b();
            ci.cancel();
        }
    }
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/PlayerModel;renderCloak(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER))
    private void labyMod$firePostCapeRenderEvent(final faa poseStack, final gdq param1, final int param2, final gcp clientPlayer, final float param4, final float param5, final float partialTicks, final float param7, final float param8, final float param9, final InsertInfo ci) {
        this.labyMod$fireCapeRenderEvent(poseStack, clientPlayer, Phase.POST);
    }
    
    @WrapOperation(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entitySolid(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;") })
    private gdy labyMod$attach(final alf location, final Operation<gdy> original) {
        return this.labyMod$renderType = (gdy)original.call(new Object[] { location });
    }
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/PlayerModel;renderCloak(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER))
    private void labyMod$renderCapeParticles(final faa param0, final gdq param1, final int param2, final gcp player, final float param4, final float param5, final float partialTicks, final float param7, final float param8, final float param9, final InsertInfo ci) {
        if (player instanceof final OptiFinePlayer optiFinePlayer) {
            if (optiFinePlayer.getOptiFineCapeLocation() != null) {
                return;
            }
        }
        final Stack stack = ((VanillaStackAccessor)param0).stack();
        final FloatMatrix4 modelViewMatrix = stack.getProvider().getPosition().copy();
        RenderStateShardAttachment.addAttachment(this.labyMod$renderType, () -> this.labyMod$capeParticleController.spawn(modelViewMatrix, (Player)player, partialTicks));
    }
    
    private boolean labyMod$fireCapeRenderEvent(final faa poseStack, final gcp clientPlayer, final Phase phase) {
        final Player player = (Player)clientPlayer;
        final PlayerModel playerModel = (PlayerModel)((gnl)this).c();
        final Stack stack = ((VanillaStackAccessor)poseStack).stack();
        return Laby.fireEvent(new PlayerCapeRenderEvent(player, playerModel, stack, phase)).isCancelled();
    }
}
