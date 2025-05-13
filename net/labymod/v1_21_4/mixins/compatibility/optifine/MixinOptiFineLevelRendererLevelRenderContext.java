// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_4.client.util.MinecraftUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_4.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.labymod.api.mixin.dynamic.DynamicMixin;

@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin(value = { glv.class }, priority = 900)
public class MixinOptiFineLevelRendererLevelRenderContext
{
    @WrapOperation(method = { "lambda$addMainPass$1(Lnet/minecraft/client/renderer/FogParameters;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/Camera;Lnet/minecraft/util/profiling/ProfilerFiller;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lnet/minecraft/client/renderer/culling/Frustum;ZLcom/mojang/blaze3d/resource/ResourceHandle;)V" }, at = { @At(value = "NEW", target = "()Lcom/mojang/blaze3d/vertex/PoseStack;") })
    private ffv labyMod$storePoseStack(final Operation<ffv> original) {
        final ffv poseStack = (ffv)original.call(new Object[0]);
        MinecraftUtil.levelRenderContext().setPoseStack(poseStack);
        return poseStack;
    }
}
