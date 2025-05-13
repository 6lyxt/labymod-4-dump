// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.joml.Matrix4f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_1.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.labymod.api.mixin.dynamic.DynamicMixin;

@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin({ fjv.class })
public class MixinOptiFineLevelRendererRenderWorldEvent
{
    @Shadow
    @Nullable
    private fka V;
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderStateShard$OutputStateShard;clearRenderState()V", ordinal = 0, shift = At.Shift.AFTER) })
    private void labyMod$fireWorldRenderEvent$transparencyChain(final eij poseStack, final float partialTicks, final long lvt_3_1_, final boolean lvt_5_1_, final emz lvt_6_1_, final fjq lvt_7_1_, final fjw lvt_8_1_, final Matrix4f lvt_9_1_, final CallbackInfo ci) {
        RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), partialTicks);
    }
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;waterMask()Lnet/minecraft/client/renderer/RenderType;", shift = At.Shift.BEFORE) })
    @Dynamic
    private void labyMod$fireWorldRenderEvent(final eij poseStack, final float partialTicks, final long lvt_3_1_, final boolean lvt_5_1_, final emz lvt_6_1_, final fjq lvt_7_1_, final fjw lvt_8_1_, final Matrix4f lvt_9_1_, final CallbackInfo ci) {
        if (this.V == null) {
            RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), partialTicks);
        }
    }
}
