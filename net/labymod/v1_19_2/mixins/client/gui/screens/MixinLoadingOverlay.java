// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.mixins.client.gui.screens;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.util.ScreenUtil;
import net.labymod.core.client.render.batch.DefaultRenderContexts;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ekz.class })
public class MixinLoadingOverlay
{
    @Inject(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V", shift = At.Shift.AFTER) })
    private void labyMod$clearDepthBuffer(final eaq poseStack, final int mouseX, final int mouseY, final float tickDelta, final CallbackInfo ci) {
        Laby.gfx().clearDepth();
    }
    
    @WrapOperation(method = { "render" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;render(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V") })
    private void labyMod$renderScreen(final elm screen, final eaq poseStack, final int mouseX, final int mouseY, final float tickDelta, final Operation<Void> original) {
        final Stack stack = ((VanillaStackAccessor)poseStack).stack();
        final DefaultRenderContexts renderContexts = (DefaultRenderContexts)Laby.labyAPI().renderPipeline().renderContexts();
        renderContexts.setCurrentStack(stack);
        ScreenUtil.wrapRender(stack, tickDelta, () -> original.call(new Object[] { screen, poseStack, mouseX, mouseY, tickDelta }));
        renderContexts.setCurrentStack(null);
    }
}
