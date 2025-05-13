// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.mojang.blaze3d.platform;

import net.labymod.api.client.gfx.pipeline.MojangLight;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Laby;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DGlStatePipeline;
import net.labymod.core.main.profiler.RenderProfiler;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.pipeline.texture.TextureTracker;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dem.class })
public class MixinGlStateManager
{
    @Shadow
    public static int p;
    
    @Inject(method = { "_activeTexture" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;glActiveTexture(I)V", shift = At.Shift.AFTER) })
    private static void labyMod$trackTexture(final int slot, final CallbackInfo ci) {
        TextureTracker.setActiveTexture(MixinGlStateManager.p);
    }
    
    @Inject(method = { "_bindTexture" }, at = { @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glBindTexture(II)V", shift = At.Shift.AFTER) })
    private static void labyMod$bindTexture(final int textureId, final CallbackInfo ci) {
        TextureTracker.bindTexture(textureId);
    }
    
    @Inject(method = { "_enableTexture" }, at = { @At("TAIL") })
    private static void labyMod$enableTexture(final CallbackInfo ci) {
        TextureTracker.enableTexture();
    }
    
    @Inject(method = { "_disableTexture" }, at = { @At("TAIL") })
    private static void labyMod$disableTexture(final CallbackInfo ci) {
        TextureTracker.disableTexture();
    }
    
    @Inject(method = { "_drawArrays" }, at = { @At("TAIL") })
    private static void labyMod$countDrawElements(final int lvt_0_1_, final int lvt_1_1_, final int lvt_2_1_, final CallbackInfo ci) {
        RenderProfiler.increaseRenderCall();
    }
    
    @Inject(method = { "_glBindFramebuffer" }, at = { @At("TAIL") })
    private static void labyMod$storeFramebuffer(final int target, final int framebuffer, final CallbackInfo ci) {
        ((AbstractBlaze3DGlStatePipeline)Laby.labyAPI().gfxRenderPipeline().gfx().blaze3DGlStatePipeline()).setBindingFramebuffer(framebuffer);
    }
    
    @Redirect(method = { "glShaderSource" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL20;glShaderSource(ILjava/lang/CharSequence;)V"))
    private static void labyMod$fixAmdShaderSource(final int shader, final CharSequence source) {
        Laby.gfx().shaderSource(shader, source);
    }
    
    @Inject(method = { "setupLevelDiffuseLighting" }, at = { @At("TAIL") })
    private static void labyMod$storeLighting(final g direction0, final g direction1, final b matrix, final CallbackInfo ci) {
        final MojangLight light = Laby.gfx().blaze3DGlStatePipeline().shaderUniformPipeline().light();
        light.updateLight0Direction(direction0.a(), direction0.b(), direction0.c());
        light.updateLight1Direction(direction1.a(), direction1.b(), direction1.c());
    }
}
