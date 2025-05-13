// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_3.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.labymod.api.mixin.dynamic.DynamicMixin;
import org.spongepowered.asm.mixin.Pseudo;

@Pseudo
@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin({ glh.class })
public class MixinOptiFineLevelRendererSmoothFPS
{
    @WrapOperation(method = { "lambda$addMainPass$1(Lnet/minecraft/client/renderer/FogParameters;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/Camera;Lnet/minecraft/util/profiling/ProfilerFiller;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lnet/minecraft/client/renderer/culling/Frustum;ZLcom/mojang/blaze3d/resource/ResourceHandle;Lnet/minecraft/client/renderer/LightTexture;)V" }, at = { @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glFinish()V", remap = false) })
    @Dynamic
    private void labyMod$disableSmoothFPS(final Operation<Void> original) {
    }
}
