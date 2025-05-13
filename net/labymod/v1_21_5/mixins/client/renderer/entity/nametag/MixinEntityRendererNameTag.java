// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer.entity.nametag;

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
import net.labymod.v1_21_5.client.util.EntityRenderStateAccessor;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.core.client.gfx.pipeline.renderer.nametag.NameTagRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gxu.class })
public class MixinEntityRendererNameTag
{
    @Shadow
    @Final
    private fti a;
    @Unique
    private final NameTagRenderer labyMod$nameTagRenderer;
    
    public MixinEntityRendererNameTag() {
        this.labyMod$nameTagRenderer = new NameTagRenderer();
    }
    
    @Inject(method = { "renderNameTag" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$transformNameTag(final hec state, final xg displayName, final fld stack, final grn bufferSource, final int $$4, final CallbackInfo ci, @Local(argsOnly = true) final LocalRef<xg> displayNameRef) {
        final EntityRenderStateAccessor<bxe> entityState = EntityRenderStateAccessor.self(state);
        if (entityState == null) {
            return;
        }
        if (this.labyMod$nameTagRenderer.transformNameTagContent(((VanillaStackAccessor)stack).stack(), (Entity)entityState.labyMod$getEntity(), displayName, newDisplayName -> displayNameRef.set((Object)newDisplayName))) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderNameTag" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack$Pose;pose()Lorg/joml/Matrix4f;", shift = At.Shift.BEFORE) })
    private void labyMod$renderCustomNameTags(final hec state, final xg displayName, final fld poseStack, final grn $$3, final int packedLightCoords, final CallbackInfo ci) {
        final EntityRenderStateAccessor<bxe> entityState = EntityRenderStateAccessor.self(state);
        if (entityState == null) {
            return;
        }
        final Stack apiStack = ((VanillaStackAccessor)poseStack).stack();
        final Entity apiEntity = (Entity)entityState.labyMod$getEntity();
        this.labyMod$nameTagRenderer.renderNameTags(apiStack, apiEntity, this.a.a((xl)displayName) + 2, packedLightCoords);
    }
    
    @WrapOperation(method = { "renderNameTag" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I", ordinal = 0) })
    private int labyMod$applyCustomBackgroundColor(final fti instance, final xg component, final float x, final float y, final int color, final boolean shadow, final Matrix4f modelMatrix, final grn bufferSource, final fti.a displayMode, final int backgroundColor, final int packedLightCoords, final Operation<Integer> original) {
        return (int)original.call(new Object[] { instance, component, x, y, color, shadow, modelMatrix, bufferSource, displayMode, this.labyMod$nameTagRenderer.getNameTagBackgroundColor(ColorFormat.ARGB32.normalizedAlpha(backgroundColor)), packedLightCoords });
    }
    
    @Inject(method = { "renderNameTag" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.BEFORE) })
    private void labyMod$resetNameTagType(final hec state, final xg $$1, final fld $$2, final grn $$3, final int $$4, final CallbackInfo ci) {
        final EntityRenderStateAccessor<bxe> entityState = EntityRenderStateAccessor.self(state);
        if (entityState == null) {
            return;
        }
        this.labyMod$nameTagRenderer.resetTag((Entity)entityState.labyMod$getEntity());
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;renderNameTag(Lnet/minecraft/client/renderer/entity/state/EntityRenderState;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.BEFORE) })
    private void labyMod$setEntityNameTagType(final hec state, final fld $$1, final grn $$2, final int $$3, final CallbackInfo ci) {
        final EntityRenderStateAccessor<bxe> entityState = EntityRenderStateAccessor.self(state);
        if (entityState == null) {
            return;
        }
        ((Entity)entityState.labyMod$getEntity()).setNameTagType(TagType.MAIN_TAG);
    }
}
