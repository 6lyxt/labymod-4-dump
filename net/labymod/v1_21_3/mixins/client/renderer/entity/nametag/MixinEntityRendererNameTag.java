// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.renderer.entity.nametag;

import net.labymod.api.client.entity.player.tag.TagType;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.util.color.format.ColorFormat;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.joml.Matrix4f;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_21_3.client.util.EntityRenderStateAccessor;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.core.client.gfx.pipeline.renderer.nametag.NameTagRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ grp.class })
public class MixinEntityRendererNameTag
{
    @Shadow
    @Final
    private fnq a;
    @Unique
    private final NameTagRenderer labyMod$nameTagRenderer;
    
    public MixinEntityRendererNameTag() {
        this.labyMod$nameTagRenderer = new NameTagRenderer();
    }
    
    @Inject(method = { "renderNameTag" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$transformNameTag(final gxv state, final xv displayName, final fgs stack, final gll bufferSource, final int $$4, final CallbackInfo ci, @Local(argsOnly = true) final LocalRef<xv> displayNameRef) {
        final EntityRenderStateAccessor<bvk> entityState = EntityRenderStateAccessor.self(state);
        if (entityState == null) {
            return;
        }
        if (this.labyMod$nameTagRenderer.transformNameTagContent(((VanillaStackAccessor)stack).stack(), (Entity)entityState.labyMod$getEntity(), displayName, newDisplayName -> displayNameRef.set((Object)newDisplayName))) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderNameTag" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack$Pose;pose()Lorg/joml/Matrix4f;", shift = At.Shift.BEFORE) })
    private void labyMod$renderCustomNameTags(final gxv state, final xv displayName, final fgs poseStack, final gll $$3, final int packedLightCoords, final CallbackInfo ci) {
        final EntityRenderStateAccessor<bvk> entityState = EntityRenderStateAccessor.self(state);
        if (entityState == null) {
            return;
        }
        final Stack apiStack = ((VanillaStackAccessor)poseStack).stack();
        final Entity apiEntity = (Entity)entityState.labyMod$getEntity();
        this.labyMod$nameTagRenderer.renderNameTags(apiStack, apiEntity, this.a.a((ya)displayName) + 2, packedLightCoords);
    }
    
    @WrapOperation(method = { "renderNameTag" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I", ordinal = 0) })
    private int labyMod$applyCustomBackgroundColor(final fnq instance, final xv component, final float x, final float y, final int color, final boolean shadow, final Matrix4f modelMatrix, final gll bufferSource, final fnq.a displayMode, final int backgroundColor, final int packedLightCoords, final Operation<Integer> original) {
        return (int)original.call(new Object[] { instance, component, x, y, color, shadow, modelMatrix, bufferSource, displayMode, this.labyMod$nameTagRenderer.getNameTagBackgroundColor(ColorFormat.ARGB32.normalizedAlpha(backgroundColor)), packedLightCoords });
    }
    
    @Inject(method = { "renderNameTag" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.BEFORE) })
    private void labyMod$resetNameTagType(final gxv state, final xv $$1, final fgs $$2, final gll $$3, final int $$4, final CallbackInfo ci) {
        final EntityRenderStateAccessor<bvk> entityState = EntityRenderStateAccessor.self(state);
        if (entityState == null) {
            return;
        }
        this.labyMod$nameTagRenderer.resetTag((Entity)entityState.labyMod$getEntity());
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;renderNameTag(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.BEFORE) })
    private void labyMod$setEntityNameTagType(final gxv state, final fgs $$1, final gll $$2, final int $$3, final CallbackInfo ci) {
        final EntityRenderStateAccessor<bvk> entityState = EntityRenderStateAccessor.self(state);
        if (entityState == null) {
            return;
        }
        ((Entity)entityState.labyMod$getEntity()).setNameTagType(TagType.MAIN_TAG);
    }
}
