// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gri.class })
public class MixinLevelRendererRenderWorldEvent
{
    @Inject(method = { "lambda$addMainPass$1" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V", shift = At.Shift.BEFORE) })
    private void labyMod$fireWorldRenderEvent$transparencyChain(final grb $$0, final fqg $$1, final fpy $$2, final brm $$3, final Matrix4f $$4, final Matrix4f $$5, final fkr $$6, final fkr $$7, final boolean $$8, final gvh $$9, final fkr $$10, final fkr $$11, final CallbackInfo ci) {
        final fld poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
        RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), $$1.a(false));
    }
}
