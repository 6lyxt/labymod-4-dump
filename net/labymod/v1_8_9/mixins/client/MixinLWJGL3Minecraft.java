// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client;

import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL;
import java.util.concurrent.Callable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ ave.class })
public abstract class MixinLWJGL3Minecraft
{
    @Shadow
    public abstract void a(final b p0);
    
    @Redirect(method = { "addGraphicsAndWorldToCrashReport" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/crash/CrashReportCategory;addCrashSectionCallable(Ljava/lang/String;Ljava/util/concurrent/Callable;)V", ordinal = 2))
    private void labyMod$fixEarlyOpenGLCrash(final c category, final String name, final Callable<String> callable) {
        try {
            GL.getCapabilities();
            category.a(name, () -> GL11.glGetString(7937) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936));
        }
        catch (final IllegalStateException ex) {}
    }
    
    @Redirect(method = { "runGameLoop" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/shader/Framebuffer;framebufferRender(II)V"))
    private void labyMod$renderFramebuffer(final bfw framebuffer, final int width, final int height) {
        framebuffer.c(Display.getFramebufferWidth(), Display.getFramebufferHeight());
    }
    
    @Redirect(method = { "drawSplashScreen" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/shader/Framebuffer;framebufferRender(II)V"))
    private void labyMod$renderSplashFramebuffer(final bfw framebuffer, final int width, final int height) {
        framebuffer.c(Display.getFramebufferWidth(), Display.getFramebufferHeight());
    }
}
