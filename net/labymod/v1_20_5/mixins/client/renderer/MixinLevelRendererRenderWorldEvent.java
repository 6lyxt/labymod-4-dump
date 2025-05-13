// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_20_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.joml.Matrix4f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gdn.class })
public class MixinLevelRendererRenderWorldEvent
{
    @Shadow
    @Nullable
    private gds Q;
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderStateShard$OutputStateShard;clearRenderState()V", ordinal = 0, shift = At.Shift.AFTER) })
    private void labyMod$fireWorldRenderEvent$transparencyChain(final float partialTicks, final long $$1, final boolean $$2, final fer $$3, final gdi $$4, final gdo $$5, final Matrix4f $$6, final Matrix4f $$7, final CallbackInfo ci) {
        final ezz poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
        RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), partialTicks);
    }
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;waterMask()Lnet/minecraft/client/renderer/RenderType;", shift = At.Shift.BEFORE) })
    private void labyMod$fireWorldRenderEvent(final float partialTicks, final long $$1, final boolean $$2, final fer $$3, final gdi $$4, final gdo $$5, final Matrix4f $$6, final Matrix4f $$7, final CallbackInfo ci) {
        if (this.Q == null) {
            final ezz poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
            RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), partialTicks);
        }
    }
}
