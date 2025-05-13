// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.v1_21_3.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = { glh.class }, priority = 900)
public class MixinLevelRendererLevelRenderContext
{
    @Redirect(method = { "lambda$addMainPass$1(Lnet/minecraft/client/renderer/FogParameters;Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/Camera;Lnet/minecraft/util/profiling/ProfilerFiller;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;Lcom/mojang/blaze3d/resource/ResourceHandle;ZLnet/minecraft/client/renderer/culling/Frustum;Lcom/mojang/blaze3d/resource/ResourceHandle;)V" }, at = @At(value = "NEW", target = "()Lcom/mojang/blaze3d/vertex/PoseStack;"))
    private fgs labyMod$storePoseStack() {
        final fgs poseStack = new fgs();
        MinecraftUtil.levelRenderContext().setPoseStack(poseStack);
        return poseStack;
    }
}
