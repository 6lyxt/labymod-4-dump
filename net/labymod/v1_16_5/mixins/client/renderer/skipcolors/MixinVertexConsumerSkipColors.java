// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.renderer.skipcolors;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dfq.class })
public interface MixinVertexConsumerSkipColors
{
    @ModifyConstant(method = { "putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;[FFFF[IIZ)V" }, constant = { @Constant(floatValue = 1.0f) }, slice = { @Slice(from = @At(value = "INVOKE", target = "Lcom/mojang/math/Vector4f;transform(Lcom/mojang/math/Matrix4f;)V")) })
    default float labyMod$al(final float oldValue) {
        return GlColorAlphaModifier.isModifiedAlpha() ? GlColorAlphaModifier.getAlpha() : oldValue;
    }
}
