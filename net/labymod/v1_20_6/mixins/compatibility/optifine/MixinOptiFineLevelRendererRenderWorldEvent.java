// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_6.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_20_6.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.joml.Matrix4f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_6.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.labymod.api.mixin.dynamic.DynamicMixin;

@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin({ gdo.class })
public class MixinOptiFineLevelRendererRenderWorldEvent
{
    @Shadow
    @Nullable
    private gdt Q;
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderStateShard$OutputStateShard;clearRenderState()V", ordinal = 0, shift = At.Shift.AFTER) })
    private void labyMod$fireWorldRenderEvent$transparencyChain(final float partialTick, final long nanoTime, final boolean renderBlockOutline, final fes camera, final gdj gameRenderer, final gdp lightTexture, final Matrix4f projectionMatrix, final Matrix4f frustrumMatrix, final CallbackInfo ci) {
        final faa poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
        RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), partialTick);
    }
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;waterMask()Lnet/minecraft/client/renderer/RenderType;", shift = At.Shift.BEFORE) })
    @Dynamic
    private void labyMod$fireWorldRenderEvent(final float partialTick, final long nanoTime, final boolean renderBlockOutline, final fes camera, final gdj gameRenderer, final gdp lightTexture, final Matrix4f projectionMatrix, final Matrix4f frustrumMatrix, final CallbackInfo ci) {
        if (this.Q == null) {
            final faa poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
            RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), partialTick);
        }
    }
}
