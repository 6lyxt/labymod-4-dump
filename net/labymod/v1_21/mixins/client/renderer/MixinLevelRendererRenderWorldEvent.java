// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_21.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.joml.Matrix4f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gex.class })
public class MixinLevelRendererRenderWorldEvent
{
    @Shadow
    @Nullable
    private gfc Q;
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderStateShard$OutputStateShard;clearRenderState()V", ordinal = 0, shift = At.Shift.AFTER) })
    private void labyMod$fireWorldRenderEvent$transparencyChain(final fgf $$0, final boolean $$1, final ffy $$2, final ges $$3, final gey $$4, final Matrix4f $$5, final Matrix4f $$6, final CallbackInfo ci) {
        final fbi poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
        RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), $$0.a(false));
    }
    
    @Inject(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;waterMask()Lnet/minecraft/client/renderer/RenderType;", shift = At.Shift.BEFORE) })
    private void labyMod$fireWorldRenderEvent(final fgf $$0, final boolean $$1, final ffy $$2, final ges $$3, final gey $$4, final Matrix4f $$5, final Matrix4f $$6, final CallbackInfo ci) {
        if (this.Q == null) {
            final fbi poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
            RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), $$0.a(false));
        }
    }
}
