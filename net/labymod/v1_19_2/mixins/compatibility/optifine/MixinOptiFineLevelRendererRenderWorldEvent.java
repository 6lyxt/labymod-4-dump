// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.compatibility.optifine;

import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_19_2.mixinplugin.optifine.OptiFineDynamicMixinApplier;
import net.labymod.api.mixin.dynamic.DynamicMixin;

@DynamicMixin(value = "optifine", applier = OptiFineDynamicMixinApplier.class)
@Mixin({ ezq.class })
public class MixinOptiFineLevelRendererRenderWorldEvent
{
    @Shadow
    @Nullable
    private ezv V;
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderStateShard$OutputStateShard;clearRenderState()V", ordinal = 0, shift = At.Shift.AFTER) })
    private void labyMod$fireWorldRenderEvent$transparencyChain(final eaq poseStack, final float partialTicks, final long lvt_3_1_, final boolean lvt_5_1_, final eff lvt_6_1_, final ezl lvt_7_1_, final ezr lvt_8_1_, final d lvt_9_1_, final CallbackInfo ci) {
        RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), partialTicks);
    }
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;waterMask()Lnet/minecraft/client/renderer/RenderType;", shift = At.Shift.BEFORE) })
    @Dynamic
    private void labyMod$fireWorldRenderEvent(final eaq poseStack, final float partialTicks, final long lvt_3_1_, final boolean lvt_5_1_, final eff lvt_6_1_, final ezl lvt_7_1_, final ezr lvt_8_1_, final d lvt_9_1_, final CallbackInfo ci) {
        if (this.V == null) {
            RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), partialTicks);
        }
    }
}
