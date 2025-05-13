// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_21_3.client.util.MinecraftUtil;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_3.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.labymod.api.mixin.dynamic.DynamicMixin;

@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin({ glh.class })
public class MixinOptiFineLevelRendererRenderWorldEvent
{
    @WrapOperation(method = { "addMainPass(Lcom/mojang/blaze3d/framegraph/FrameGraphBuilder;Lnet/minecraft/client/renderer/culling/Frustum;Lnet/minecraft/client/Camera;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lnet/minecraft/client/renderer/FogParameters;ZZLnet/minecraft/client/DeltaTracker;Lnet/minecraft/util/profiling/ProfilerFiller;Lnet/minecraft/client/renderer/LightTexture;)V" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/framegraph/FramePass;executes(Ljava/lang/Runnable;)V") })
    private void labyMod$fireWorldRenderEvent$transparencyChain(final fex framePass, final Runnable task, final Operation<Void> original) {
        final Runnable wrappedTask = () -> {
            task.run();
            final fgs poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
            RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), Laby.labyAPI().minecraft().getPartialTicks());
            return;
        };
        original.call(new Object[] { framePass, wrappedTask });
    }
}
