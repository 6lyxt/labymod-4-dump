// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.renderer.entity.player;

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
import net.labymod.v1_21_4.client.util.EntityRenderStateAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_4.client.render.LivingEntityRendererAccessor;

@Mixin({ gtg.class })
public abstract class MixinLivingEntityRenderer<T extends bvi, S extends gzl, M extends gcc<? super S>> extends gse<T, S> implements guc<S, M>, LivingEntityRendererAccessor
{
    @Shadow
    protected M h;
    private T labyMod$entity;
    private ffv labyMod$poseStack;
    private int labyMod$packedLight;
    
    @Shadow
    protected abstract boolean a(final gwu<S, M> p0);
    
    protected MixinLivingEntityRenderer(final gsf.a $$0) {
        super($$0);
    }
    
    @Inject(method = { "shouldShowName(Lnet/minecraft/world/entity/LivingEntity;D)Z" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$showOwnName(final bvi entity, final double $$1, final CallbackInfoReturnable<Boolean> cir) {
        if (entity == flk.Q().t) {
            cir.setReturnValue((Object)LabyMod.getInstance().config().ingame().showMyName().get());
        }
    }
    
    public void addCustomLayer(final gwu layer) {
        this.a((gwu<S, M>)layer);
    }
    
    @Inject(method = { "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At("HEAD") })
    public void labyMod$preRender(final gzl state, final ffv poseStack, final glz bufferSource, final int packedLightCoords, final CallbackInfo ci) {
        final EntityRenderStateAccessor<bvi> entityState = EntityRenderStateAccessor.self(state);
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
    public gmj labyMod$preRender(final gtg instance, final gzl livingEntityRenderState, final boolean flag0, final boolean flag1, final boolean glowing, final Operation<gmj> original) {
        final gmj renderType = (gmj)original.call(new Object[] { instance, livingEntityRenderState, flag0, flag1, glowing });
        if (renderType != null && this.firePlayerModelRenderEvent(Phase.PRE)) {
            return null;
        }
        return renderType;
    }
    
    @Inject(method = { "render(Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/model/EntityModel.renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V", shift = At.Shift.AFTER) })
    public void labyMod$postRender(final gzl $$0, final ffv $$1, final glz $$2, final int $$3, final CallbackInfo ci) {
        this.firePlayerModelRenderEvent(Phase.POST);
        final Blaze3DBufferSource blaze3DBufferSource = Laby.gfx().blaze3DBufferSource();
        blaze3DBufferSource.resetTemporaryBuffer();
    }
    
    private boolean firePlayerModelRenderEvent(final Phase phase) {
        if (!(this.labyMod$entity instanceof gku)) {
            return false;
        }
        final Stack stack = ((VanillaStackAccessor)this.labyMod$poseStack).stack();
        Laby.references().renderEnvironmentContext().setPackedLight(this.labyMod$packedLight);
        return Laby.fireEvent(new PlayerModelRenderEvent((Player)this.labyMod$entity, (PlayerModel)this.h, stack, phase, this.labyMod$packedLight)).isCancelled();
    }
}
