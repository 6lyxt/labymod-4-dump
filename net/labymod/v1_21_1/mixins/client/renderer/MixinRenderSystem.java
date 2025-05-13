// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.renderer;

import net.labymod.api.util.Buffers;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.Blaze3DShaderUniformPipeline;
import net.labymod.api.client.gfx.pipeline.MojangLight;
import org.joml.Vector3f;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.client.gfx.pipeline.Fog;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.util.math.vector.FloatMatrix4;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import org.joml.Matrix4f;
import java.nio.FloatBuffer;
import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderSystem.class })
public class MixinRenderSystem
{
    private static final FloatBuffer MATRIX_BUFFER;
    @Shadow
    private static Matrix4f projectionMatrix;
    @Shadow
    private static Matrix4f modelViewMatrix;
    
    @Inject(method = { "setProjectionMatrix" }, at = { @At(value = "FIELD", target = "Lcom/mojang/blaze3d/systems/RenderSystem;projectionMatrix:Lorg/joml/Matrix4f;", shift = At.Shift.AFTER) })
    private static void labyMod$onApplyProjectionMatrix(final Matrix4f matrix4f, final fbq vertexSorting, final CallbackInfo ci) {
        final FloatMatrix4 matrix = labyMod$shaderUniformPipeline().matrices().projectionMatrix();
        matrix4f.get(MixinRenderSystem.MATRIX_BUFFER);
        matrix.load(MixinRenderSystem.MATRIX_BUFFER);
    }
    
    @Inject(method = { "setTextureMatrix" }, at = { @At(value = "FIELD", target = "Lcom/mojang/blaze3d/systems/RenderSystem;textureMatrix:Lorg/joml/Matrix4f;", shift = At.Shift.AFTER) })
    private static void labyMod$onApplyTextureMatrix(final Matrix4f matrix4f, final CallbackInfo ci) {
        final FloatMatrix4 matrix = labyMod$shaderUniformPipeline().matrices().textureMatrix();
        matrix4f.get(MixinRenderSystem.MATRIX_BUFFER);
        matrix.load(MixinRenderSystem.MATRIX_BUFFER);
    }
    
    @Inject(method = { "applyModelViewMatrix" }, at = { @At("TAIL") })
    private static void labyMod$storeModelViewMatrix(final CallbackInfo ci) {
        final FloatMatrix4 matrix = labyMod$shaderUniformPipeline().matrices().modelViewMatrix();
        MixinRenderSystem.modelViewMatrix.get(MixinRenderSystem.MATRIX_BUFFER);
        matrix.load(MixinRenderSystem.MATRIX_BUFFER);
    }
    
    @Inject(method = { "_restoreProjectionMatrix" }, at = { @At(value = "FIELD", target = "Lcom/mojang/blaze3d/systems/RenderSystem;projectionMatrix:Lorg/joml/Matrix4f;", shift = At.Shift.AFTER) })
    private static void labyMod$onApplyProjectionMatrix(final CallbackInfo ci) {
        final FloatMatrix4 matrix = labyMod$shaderUniformPipeline().matrices().projectionMatrix();
        MixinRenderSystem.projectionMatrix.get(MixinRenderSystem.MATRIX_BUFFER);
        matrix.load(MixinRenderSystem.MATRIX_BUFFER);
    }
    
    @Inject(method = { "setShaderFogColor(FFFF)V" }, at = { @At("TAIL") })
    private static void labyMod$onFogColor(final float red, final float green, final float blue, final float alpha, final CallbackInfo ci) {
        final Fog fog = labyMod$shaderUniformPipeline().fog();
        fog.updateColor(red, green, blue, alpha);
    }
    
    @Inject(method = { "setShaderColor" }, at = { @At("TAIL") })
    private static void labyMod$onColor(final float red, final float green, final float blue, final float alpha, final CallbackInfo ci) {
        final FloatVector4 colorModulator = labyMod$shaderUniformPipeline().colorModulator();
        colorModulator.set(red, green, blue, alpha);
    }
    
    @Inject(method = { "setShaderLights" }, at = { @At("TAIL") })
    private static void onApplyLightDirections(final Vector3f light0Direction, final Vector3f light1Direction, final CallbackInfo ci) {
        final MojangLight light = labyMod$shaderUniformPipeline().light();
        light.updateLight0Direction(light0Direction.x(), light0Direction.y(), light0Direction.z());
        light.updateLight1Direction(light1Direction.x(), light1Direction.y(), light1Direction.z());
    }
    
    private static Blaze3DShaderUniformPipeline labyMod$shaderUniformPipeline() {
        return Laby.labyAPI().gfxRenderPipeline().gfx().blaze3DGlStatePipeline().shaderUniformPipeline();
    }
    
    static {
        MATRIX_BUFFER = Buffers.createFloatBuffer(16);
    }
}
