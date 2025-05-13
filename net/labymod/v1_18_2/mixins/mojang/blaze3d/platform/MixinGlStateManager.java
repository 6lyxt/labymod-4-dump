// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.mojang.blaze3d.platform;

import net.labymod.api.Laby;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DGlStatePipeline;
import net.labymod.core.main.profiler.RenderProfiler;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.pipeline.texture.TextureTracker;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import com.mojang.blaze3d.platform.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ GlStateManager.class })
public class MixinGlStateManager
{
    @Shadow
    public static int activeTexture;
    
    @Inject(method = { "_activeTexture" }, at = { @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;glActiveTexture(I)V", shift = At.Shift.AFTER) })
    private static void labyMod$trackTexture(final int slot, final CallbackInfo ci) {
        TextureTracker.setActiveTexture(MixinGlStateManager.activeTexture);
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
    
    @Inject(method = { "_drawElements" }, at = { @At("TAIL") })
    private static void labyMod$countDrawElements(final int param0, final int param1, final int param2, final long param3, final CallbackInfo ci) {
        RenderProfiler.increaseRenderCall();
    }
    
    @Inject(method = { "_glBindFramebuffer" }, at = { @At("TAIL") })
    private static void labyMod$storeFramebuffer(final int target, final int framebuffer, final CallbackInfo ci) {
        ((AbstractBlaze3DGlStatePipeline)Laby.labyAPI().gfxRenderPipeline().gfx().blaze3DGlStatePipeline()).setBindingFramebuffer(framebuffer);
    }
}
