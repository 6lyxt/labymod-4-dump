// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.mixins.client.renderer.entity.player;

import net.labymod.core.event.client.render.entity.PlayerRenderEvent;
import net.labymod.v1_20_4.client.util.MinecraftUtil;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderEvent;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.event.Phase;
import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_4.client.render.LivingEntityRendererAccessor;

@Mixin({ fzo.class })
public abstract class MixinLivingEntityRenderer<T extends bml, M extends fjx<T>> implements LivingEntityRendererAccessor
{
    @Shadow
    protected M f;
    private T labyMod$entity;
    private eqb labyMod$poseStack;
    private int labyMod$packedLight;
    
    @Shadow
    protected abstract boolean a(final gcy<T, M> p0);
    
    @Shadow
    @Nullable
    protected abstract ftp a(final T p0, final boolean p1, final boolean p2, final boolean p3);
    
    @Inject(method = { "shouldShowName" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$showOwnName(final T entity, final CallbackInfoReturnable<Boolean> cir) {
        if (entity == evi.O().s) {
            cir.setReturnValue((Object)LabyMod.getInstance().config().ingame().showMyName().get());
        }
    }
    
    @Override
    public void addCustomLayer(final gcy layer) {
        this.a((gcy<T, M>)layer);
    }
    
    @Inject(method = { "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At("HEAD") })
    public void labyMod$preRender(final T entity, final float param1, final float param2, final eqb stack, final fth multiBufferSource, final int packedLight, final CallbackInfo callbackInfo) {
        final Blaze3DBufferSource blaze3DBufferSource = Laby.gfx().blaze3DBufferSource();
        blaze3DBufferSource.setTemporaryBuffer(multiBufferSource);
        this.labyMod$entity = entity;
        this.labyMod$poseStack = stack;
        this.labyMod$packedLight = packedLight;
    }
    
    @Redirect(method = { "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/world/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;"))
    public ftp labyMod$preRender(final fzo instance, final T $$0, final boolean $$1, final boolean $$2, final boolean $$3) {
        final ftp renderType = this.a($$0, $$1, $$2, $$3);
        if (renderType != null && this.firePlayerModelRenderEvent(Phase.PRE)) {
            return null;
        }
        return renderType;
    }
    
    @Inject(method = { "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/model/EntityModel.renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;IIFFFF)V", shift = At.Shift.AFTER) })
    public void labyMod$postRender(final T entity, final float param1, final float param2, final eqb stack, final fth param4, final int packedLight, final CallbackInfo callbackInfo) {
        this.firePlayerModelRenderEvent(Phase.POST);
        final Blaze3DBufferSource blaze3DBufferSource = Laby.gfx().blaze3DBufferSource();
        blaze3DBufferSource.resetTemporaryBuffer();
    }
    
    private boolean firePlayerModelRenderEvent(final Phase phase) {
        if (!(this.labyMod$entity instanceof fsg)) {
            return false;
        }
        final Stack stack = ((VanillaStackAccessor)this.labyMod$poseStack).stack();
        Laby.references().renderEnvironmentContext().setPackedLight(this.labyMod$packedLight);
        return Laby.fireEvent(new PlayerModelRenderEvent((Player)this.labyMod$entity, (PlayerModel)this.f, stack, phase, this.labyMod$packedLight)).isCancelled();
    }
    
    @Inject(method = { "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At("HEAD") })
    private void labyMod$beforeLivingEntityRenderer(final T livingEntity, final float $$1, final float partialTicks, final eqb poseStack, final fth bufferSource, final int packedLightCoords, final CallbackInfo ci) {
        if (livingEntity instanceof final Player player) {
            final PlayerModel playerModel = MinecraftUtil.obtainPlayerModel(player);
            Laby.fireEvent(new PlayerRenderEvent(PlayerRenderEvent.Phase.BEFORE, ((VanillaStackAccessor)poseStack).stack(), player, playerModel, partialTicks, packedLightCoords));
        }
    }
    
    @Inject(method = { "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At("TAIL") })
    private void labyMod$afterLivingEntityRenderer(final T livingEntity, final float $$1, final float partialTicks, final eqb poseStack, final fth bufferSource, final int packedLightCoords, final CallbackInfo ci) {
        if (livingEntity instanceof final Player player) {
            final PlayerModel playerModel = MinecraftUtil.obtainPlayerModel(player);
            Laby.fireEvent(new PlayerRenderEvent(PlayerRenderEvent.Phase.AFTER, ((VanillaStackAccessor)poseStack).stack(), player, playerModel, partialTicks, packedLightCoords));
        }
    }
}
