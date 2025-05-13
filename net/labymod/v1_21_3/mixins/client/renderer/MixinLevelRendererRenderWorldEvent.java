// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.world.RenderWorldEventCaller;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.v1_21_3.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ glh.class })
public class MixinLevelRendererRenderWorldEvent
{
    @Inject(method = { "lambda$addMainPass$1" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/util/profiling/ProfilerFiller;pop()V", shift = At.Shift.BEFORE) })
    private void labyMod$fireWorldRenderEvent$transparencyChain(final gkz $$0, final flw $$1, final flp $$2, final bpt $$3, final Matrix4f $$4, final Matrix4f $$5, final fge $$6, final fge $$7, final fge $$8, final fge $$9, final boolean $$10, final gpc $$11, final fge $$12, final CallbackInfo ci) {
        final fgs poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
        RenderWorldEventCaller.call(((VanillaStackAccessor)poseStack).stack(), $$1.a(false));
    }
}
