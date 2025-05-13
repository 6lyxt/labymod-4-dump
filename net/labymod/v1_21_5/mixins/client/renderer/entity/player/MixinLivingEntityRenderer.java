// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer.entity.player;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderEvent;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.event.Phase;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.labymod.api.client.gfx.pipeline.blaze3d.buffer.Blaze3DBufferSource;
import net.labymod.api.Laby;
import net.labymod.v1_21_5.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_5.client.render.LivingEntityRendererAccessor;

@Mixin({ gyw.class })
public abstract class MixinLivingEntityRenderer<T extends byf, S extends hfe, M extends ghn<? super S>> extends gxu<T, S> implements gzs<S, M>, LivingEntityRendererAccessor
{
    @Shadow
    protected M g;
    private T labyMod$entity;
    private fld labyMod$poseStack;
    private int labyMod$packedLight;
    
    @Shadow
    protected abstract boolean a(final hcj<S, M> p0);
    
    protected MixinLivingEntityRenderer(final gxv.a $$0) {
        super($$0);
    }
    
    @Inject(method = { "shouldShowName(Lnet/minecraft/world/entity/LivingEntity;D)Z" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$showOwnName(final byf entity, final double $$1, final CallbackInfoReturnable<Boolean> cir) {
        if (entity == fqq.Q().t) {
            cir.setReturnValue((Object)LabyMod.getInstance().config().ingame().showMyName().get());
        }
    }
    
    public void addCustomLayer(final hcj layer) {
        this.a((hcj<S, M>)layer);
    }
    
    @Inject(method = { "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At("HEAD") })
    public void labyMod$preRender(final hfe state, final fld poseStack, final grn bufferSource, final int packedLightCoords, final CallbackInfo ci) {
        final EntityRenderStateAccessor<byf> entityState = EntityRenderStateAccessor.self(state);
        if (entityState == null) {
            return;
        }
        final Blaze3DBufferSource blaze3DBufferSource = Laby.gfx().blaze3DBufferSource();
        blaze3DBufferSource.setTemporaryBuffer(bufferSource);
        this.labyMod$entity = (T)entityState.labyMod$getEntity();
        this.labyMod$poseStack = poseStack;
        this.labyMod$packedLight = packedLightCoords;
    }
    
    @WrapOperation(method = { "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;ZZZ)Lnet/minecraft/client/renderer/RenderType;") })
    public gry labyMod$preRender(final gyw instance, final hfe livingEntityRenderState, final boolean flag0, final boolean flag1, final boolean glowing, final Operation<gry> original) {
        final gry renderType = (gry)original.call(new Object[] { instance, livingEntityRenderState, flag0, flag1, glowing });
        if (renderType != null && this.firePlayerModelRenderEvent(Phase.PRE)) {
            return null;
        }
        return renderType;
    }
    
    @Inject(method = { "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/model/EntityModel.renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V", shift = At.Shift.AFTER) })
    public void labyMod$postRender(final hfe $$0, final fld $$1, final grn $$2, final int $$3, final CallbackInfo ci) {
        this.firePlayerModelRenderEvent(Phase.POST);
        final Blaze3DBufferSource blaze3DBufferSource = Laby.gfx().blaze3DBufferSource();
        blaze3DBufferSource.resetTemporaryBuffer();
    }
    
    private boolean firePlayerModelRenderEvent(final Phase phase) {
        if (!(this.labyMod$entity instanceof gqj)) {
            return false;
        }
        final Stack stack = ((VanillaStackAccessor)this.labyMod$poseStack).stack();
        Laby.references().renderEnvironmentContext().setPackedLight(this.labyMod$packedLight);
        return Laby.fireEvent(new PlayerModelRenderEvent((Player)this.labyMod$entity, (PlayerModel)this.g, stack, phase, this.labyMod$packedLight)).isCancelled();
    }
}
