// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_4.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_21_4.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ glv.class })
public class MixinLevelRendererRenderWorldEvent
{
    @Inject(method = { "lambda$addMainPass$1" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V", shift = At.Shift.BEFORE) })
    private void labyMod$fireWorldRenderEvent$transparencyChain(final glo $$0, final fla $$1, final fks $$2, final bou $$3, final Matrix4f $$4, final Matrix4f $$5, final ffh $$6, final ffh $$7, final ffh $$8, final ffh $$9, final boolean $$10, final gpr $$11, final ffh $$12, final CallbackInfo ci) {
        final ffv poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
        RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), $$1.a(false));
    }
}
