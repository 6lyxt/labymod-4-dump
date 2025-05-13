// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.renderer.entity.layers;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.model.entity.player.PlayerCapeRenderEvent;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
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

@Mixin({ ehu.class })
public class MixinCapeLayer
{
    private static final String RENDER_METHOD = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V";
    private final CapeParticleController labyMod$capeParticleController;
    private float labyMod$partialTicks;
    private dzj labyMod$player;
    private eao labyMod$renderType;
    
    public MixinCapeLayer() {
        this.labyMod$capeParticleController = LabyMod.references().capeParticleController();
    }
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V" }, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(DDD)V", shift = At.Shift.AFTER), cancellable = true)
    private void labyMod$firePreCapeRenderEvent(final dfm poseStack, final eag param1, final int param2, final dzj player, final float param4, final float param5, final float partialTicks, final float param7, final float param8, final float param9, final InsertInfo ci) {
        this.labyMod$partialTicks = partialTicks;
        this.labyMod$player = player;
        if (this.labyMod$fireCapeRenderEvent(poseStack, player, Phase.PRE)) {
            poseStack.b();
            ci.cancel();
        }
    }
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/PlayerModel;renderCloak(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER))
    private void labyMod$firePostCapeRenderEvent(final dfm poseStack, final eag param1, final int param2, final dzj player, final float param4, final float param5, final float partialTicks, final float param7, final float param8, final float param9, final InsertInfo ci) {
        this.labyMod$fireCapeRenderEvent(poseStack, player, Phase.POST);
    }
    
    @WrapOperation(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;entitySolid(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;") })
    private eao labyMod$attach(final vk location, final Operation<eao> original) {
        return this.labyMod$renderType = (eao)original.call(new Object[] { location });
    }
    
    @Insert(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/PlayerModel;renderCloak(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;II)V", shift = At.Shift.AFTER))
    private void labyMod$renderCapeParticles(final dfm param0, final eag param1, final int param2, final dzj player, final float param4, final float param5, final float partialTicks, final float param7, final float param8, final float param9, final InsertInfo ci) {
        if (player instanceof OptiFinePlayer && ((OptiFinePlayer)player).getOptiFineCapeLocation() != null) {
            return;
        }
        final Stack stack = ((VanillaStackAccessor)param0).stack();
        final FloatMatrix4 modelViewMatrix = stack.getProvider().getPosition().copy();
        RenderStateShardAttachment.addAttachment(this.labyMod$renderType, () -> this.labyMod$capeParticleController.spawn(modelViewMatrix, (Player)player, partialTicks));
    }
    
    @ModifyVariable(method = { "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V" }, ordinal = 6, at = @At("STORE"))
    private float interpolate(final float yaw) {
        return (yaw - this.labyMod$player.aB) * this.labyMod$partialTicks + this.labyMod$player.aB;
    }
    
    private boolean labyMod$fireCapeRenderEvent(final dfm poseStack, final dzj clientPlayer, final Phase phase) {
        final Player player = (Player)clientPlayer;
        final PlayerModel playerModel = (PlayerModel)((eit)this).aC_();
        final Stack stack = ((VanillaStackAccessor)poseStack).stack();
        return Laby.fireEvent(new PlayerCapeRenderEvent(player, playerModel, stack, phase)).isCancelled();
    }
}
