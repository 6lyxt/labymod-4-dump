// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.renderer;

import net.labymod.api.volt.annotation.Insert;
import net.labymod.core.main.profiler.RenderProfiler;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.pipeline.texture.TextureTracker;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bus.class })
public class MixinGlStateManagerRenderer
{
    @Shadow
    public static int q;
    
    @Inject(method = { "setActiveTexture" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/OpenGlHelper;setActiveTexture(I)V", shift = At.Shift.AFTER) })
    private static void labyMod$setActiveTexture(final int slot, final CallbackInfo ci) {
        TextureTracker.setActiveTexture(MixinGlStateManagerRenderer.q);
    }
    
    @Inject(method = { "bindTexture" }, at = { @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glBindTexture(II)V", shift = At.Shift.AFTER) })
    private static void labyMod$bindTexture(final int textureId, final CallbackInfo ci) {
        TextureTracker.bindTexture(textureId);
    }
    
    @Inject(method = { "enableTexture2D" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager$BooleanState;setEnabled()V", shift = At.Shift.AFTER) })
    private static void labyMod$enableTexture2D(final CallbackInfo ci) {
        TextureTracker.enableTexture();
    }
    
    @Inject(method = { "disableTexture2D" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager$BooleanState;setDisabled()V", shift = At.Shift.AFTER) })
    private static void labyMod$disableTexture2D(final CallbackInfo ci) {
        TextureTracker.disableTexture();
    }
    
    @Insert(method = { "glDrawArrays" }, at = @At("TAIL"))
    private static void labyMod$injectProfiler(final int mode, final int first, final int count, final InsertInfo ii) {
        RenderProfiler.increaseRenderCall();
    }
}
