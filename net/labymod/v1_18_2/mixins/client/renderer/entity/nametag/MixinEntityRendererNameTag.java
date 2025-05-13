// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.renderer.entity.nametag;

import net.labymod.api.client.entity.player.tag.TagType;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.util.color.format.ColorFormat;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.labymod.api.client.render.matrix.Stack;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Unique;
import net.labymod.core.client.gfx.pipeline.renderer.nametag.NameTagRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ evj.class })
public class MixinEntityRendererNameTag
{
    @Shadow
    @Final
    private dzp a;
    @Unique
    private final NameTagRenderer labyMod$nameTagRenderer;
    
    public MixinEntityRendererNameTag() {
        this.labyMod$nameTagRenderer = new NameTagRenderer();
    }
    
    @Inject(method = { "renderNameTag" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V", shift = At.Shift.AFTER) }, cancellable = true)
    private void labyMod$transformNameTag(final axk entity, final qk displayName, final dtm stack, final eqs bufferSource, final int packedLight, final CallbackInfo ci, @Local(argsOnly = true) final LocalRef<qk> displayNameRef) {
        if (this.labyMod$nameTagRenderer.transformNameTagContent(((VanillaStackAccessor)stack).stack(), (Entity)entity, displayName, newDisplayName -> displayNameRef.set((Object)newDisplayName))) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderNameTag" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack$Pose;pose()Lcom/mojang/math/Matrix4f;", shift = At.Shift.BEFORE) })
    private void labyMod$renderCustomNameTags(final axk entity, final qk displayName, final dtm stack, final eqs bufferSource, final int packedLight, final CallbackInfo ci) {
        final Stack apiStack = ((VanillaStackAccessor)stack).stack();
        final Entity apiEntity = (Entity)entity;
        this.labyMod$nameTagRenderer.renderNameTags(apiStack, apiEntity, this.a.a((qn)displayName) + 2, packedLight);
    }
    
    @WrapOperation(method = { "renderNameTag" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInBatch(Lnet/minecraft/network/chat/Component;FFIZLcom/mojang/math/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;ZII)I", ordinal = 0) })
    private int labyMod$applyCustomBackgroundColor(final dzp instance, final qk component, final float x, final float y, final int color, final boolean shadow, final d modelMatrix, final eqs bufferSource, final boolean discrete, final int backgroundColor, final int packedLightCoords, final Operation<Integer> original) {
        return (int)original.call(new Object[] { instance, component, x, y, color, shadow, modelMatrix, bufferSource, discrete, this.labyMod$nameTagRenderer.getNameTagBackgroundColor(ColorFormat.ARGB32.normalizedAlpha(backgroundColor)), packedLightCoords });
    }
    
    @Inject(method = { "renderNameTag" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.BEFORE) })
    private void labyMod$resetNameTagType(final axk entity, final qk displayName, final dtm stack, final eqs bufferSource, final int packedLight, final CallbackInfo ci) {
        this.labyMod$nameTagRenderer.resetTag((Entity)entity);
    }
    
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/EntityRenderer;renderNameTag(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", shift = At.Shift.BEFORE) })
    private void labyMod$setEntityNameTagType(final axk entity, final float $$1, final float $$2, final dtm $$3, final eqs $$4, final int $$5, final CallbackInfo ci) {
        ((Entity)entity).setNameTagType(TagType.MAIN_TAG);
    }
}
