// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { gri.class }, priority = 900)
public class MixinLevelRendererLevelRenderContext
{
    @WrapOperation(method = { "lambda$addMainPass$1(Lnet/minecraft/client/renderer/FogParameters;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/Camera;Lnet/minecraft/util/profiling/ProfilerFiller;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;ZLnet/minecraft/client/renderer/culling/Frustum;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;)V" }, at = { @At(value = "NEW", target = "()Lcom/mojang/blaze3d/vertex/PoseStack;") })
    private fld labyMod$storePoseStack(final Operation<fld> original) {
        final fld poseStack = (fld)original.call(new Object[0]);
        MinecraftUtil.levelRenderContext().setPoseStack(poseStack);
        return poseStack;
    }
}
