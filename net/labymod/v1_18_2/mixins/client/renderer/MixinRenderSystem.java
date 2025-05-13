// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.renderer;

import net.labymod.api.util.Buffers;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.Blaze3DShaderUniformPipeline;
import net.labymod.api.client.gfx.pipeline.MojangLight;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.client.gfx.pipeline.Fog;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.util.math.vector.FloatMatrix4;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import java.nio.FloatBuffer;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderSystem.class })
public class MixinRenderSystem
{
    private static final FloatBuffer MATRIX_BUFFER;
    @Shadow
    private static d projectionMatrix;
    
    @Inject(method = { "setProjectionMatrix" }, at = { @At(value = "FIELD", target = "Lcom/mojang/blaze3d/systems/RenderSystem;projectionMatrix:Lcom/mojang/math/Matrix4f;", shift = At.Shift.AFTER) })
    private static void labyMod$onApplyProjectionMatrix(final d matrix4f, final CallbackInfo ci) {
        final FloatMatrix4 matrix = labyMod$shaderUniformPipeline().matrices().projectionMatrix();
        matrix4f.c(MixinRenderSystem.MATRIX_BUFFER);
        matrix.load(MixinRenderSystem.MATRIX_BUFFER);
    }
    
    @Inject(method = { "setTextureMatrix" }, at = { @At(value = "FIELD", target = "Lcom/mojang/blaze3d/systems/RenderSystem;textureMatrix:Lcom/mojang/math/Matrix4f;", shift = At.Shift.AFTER) })
    private static void labyMod$onApplyTextureMatrix(final d matrix4f, final CallbackInfo ci) {
        final FloatMatrix4 matrix = labyMod$shaderUniformPipeline().matrices().textureMatrix();
        matrix4f.c(MixinRenderSystem.MATRIX_BUFFER);
        matrix.load(MixinRenderSystem.MATRIX_BUFFER);
    }
    
    @Redirect(method = { "applyModelViewMatrix" }, at = @At(value = "INVOKE", target = "Lcom/mojang/math/Matrix4f;copy()Lcom/mojang/math/Matrix4f;"))
    private static d labyMod$storeModelViewMatrix(final d modelViewMatrix) {
        final FloatMatrix4 matrix = labyMod$shaderUniformPipeline().matrices().modelViewMatrix();
        modelViewMatrix.c(MixinRenderSystem.MATRIX_BUFFER);
        matrix.load(MixinRenderSystem.MATRIX_BUFFER);
        return modelViewMatrix;
    }
    
    @Inject(method = { "_restoreProjectionMatrix" }, at = { @At(value = "FIELD", target = "Lcom/mojang/blaze3d/systems/RenderSystem;projectionMatrix:Lcom/mojang/math/Matrix4f;", shift = At.Shift.AFTER) })
    private static void labyMod$onApplyProjectionMatrix(final CallbackInfo ci) {
        final FloatMatrix4 matrix = labyMod$shaderUniformPipeline().matrices().projectionMatrix();
        MixinRenderSystem.projectionMatrix.c(MixinRenderSystem.MATRIX_BUFFER);
        matrix.load(MixinRenderSystem.MATRIX_BUFFER);
    }
    
    @Inject(method = { "_setShaderFogColor" }, at = { @At("TAIL") })
    private static void labyMod$onFogColor(final float red, final float green, final float blue, final float alpha, final CallbackInfo ci) {
        final Fog fog = labyMod$shaderUniformPipeline().fog();
        fog.updateColor(red, green, blue, alpha);
    }
    
    @Inject(method = { "_setShaderColor" }, at = { @At("TAIL") })
    private static void labyMod$onColor(final float red, final float green, final float blue, final float alpha, final CallbackInfo ci) {
        final FloatVector4 colorModulator = labyMod$shaderUniformPipeline().colorModulator();
        colorModulator.set(red, green, blue, alpha);
    }
    
    @Inject(method = { "_setShaderLights" }, at = { @At("TAIL") })
    private static void onApplyLightDirections(final k light0Direction, final k light1Direction, final CallbackInfo ci) {
        final MojangLight light = labyMod$shaderUniformPipeline().light();
        light.updateLight0Direction(light0Direction.a(), light0Direction.b(), light0Direction.c());
        light.updateLight1Direction(light1Direction.a(), light1Direction.b(), light1Direction.c());
    }
    
    private static Blaze3DShaderUniformPipeline labyMod$shaderUniformPipeline() {
        return Laby.labyAPI().gfxRenderPipeline().gfx().blaze3DGlStatePipeline().shaderUniformPipeline();
    }
    
    static {
        MATRIX_BUFFER = Buffers.createFloatBuffer(16);
    }
}
