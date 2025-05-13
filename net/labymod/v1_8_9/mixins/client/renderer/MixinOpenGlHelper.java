// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.spongepowered.asm.mixin.Overwrite;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.ShaderFixer;
import java.nio.ByteBuffer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.Laby;
import net.labymod.core.client.gfx.pipeline.AbstractBlaze3DGlStatePipeline;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bqs.class })
public class MixinOpenGlHelper
{
    @Shadow
    private static boolean V;
    
    @Inject(method = { "glBindFramebuffer" }, at = { @At("HEAD") })
    private static void labyMod$storeFramebuffer(final int target, final int framebuffer, final CallbackInfo ci) {
        ((AbstractBlaze3DGlStatePipeline)Laby.labyAPI().gfxRenderPipeline().gfx().blaze3DGlStatePipeline()).setBindingFramebuffer(framebuffer);
    }
    
    @Overwrite
    public static void a(final int shader, final ByteBuffer buffer) {
        ShaderFixer.glShaderSource(shader, buffer);
    }
    
    @Overwrite
    public static void a(final int location, final IntBuffer buffer) {
        ShaderFixer.glUniform1iv(MixinOpenGlHelper.V, location, buffer);
    }
    
    @Overwrite
    public static void a(final int location, final FloatBuffer buffer) {
        ShaderFixer.glUniform1fv(MixinOpenGlHelper.V, location, buffer);
    }
    
    @Overwrite
    public static void b(final int location, final IntBuffer buffer) {
        ShaderFixer.glUniform2iv(MixinOpenGlHelper.V, location, buffer);
    }
    
    @Overwrite
    public static void b(final int location, final FloatBuffer buffer) {
        ShaderFixer.glUniform2fv(MixinOpenGlHelper.V, location, buffer);
    }
    
    @Overwrite
    public static void c(final int location, final IntBuffer buffer) {
        ShaderFixer.glUniform3iv(MixinOpenGlHelper.V, location, buffer);
    }
    
    @Overwrite
    public static void c(final int location, final FloatBuffer buffer) {
        ShaderFixer.glUniform3fv(MixinOpenGlHelper.V, location, buffer);
    }
    
    @Overwrite
    public static void d(final int location, final IntBuffer buffer) {
        ShaderFixer.glUniform4iv(MixinOpenGlHelper.V, location, buffer);
    }
    
    @Overwrite
    public static void d(final int location, final FloatBuffer buffer) {
        ShaderFixer.glUniform4fv(MixinOpenGlHelper.V, location, buffer);
    }
    
    @Overwrite
    public static void a(final int location, final boolean transpose, final FloatBuffer buffer) {
        ShaderFixer.glUniformMatrix2fv(MixinOpenGlHelper.V, location, transpose, buffer);
    }
    
    @Overwrite
    public static void b(final int location, final boolean transpose, final FloatBuffer buffer) {
        ShaderFixer.glUniformMatrix3fv(MixinOpenGlHelper.V, location, transpose, buffer);
    }
    
    @Overwrite
    public static void c(final int location, final boolean transpose, final FloatBuffer buffer) {
        ShaderFixer.glUniformMatrix4fv(MixinOpenGlHelper.V, location, transpose, buffer);
    }
}
