// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.mixins.client.renderer;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.pipeline.MojangLight;
import net.labymod.api.client.gfx.pipeline.Blaze3DShaderUniformPipeline;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.Laby;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.util.math.vector.FloatVector4;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.util.math.vector.FloatMatrix4;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ bfk.class })
public class MixinEntityRendererLightDirection
{
    private static final FloatMatrix4 LIGHT_DIRECTION_MATRIX;
    private static final FloatVector3 LABYMOD$LIGHT0_DIRECTION;
    private static final FloatVector3 LABYMOD$LIGHT1_DIRECTION;
    private static final FloatVector4 LABYMOD$LIGHT0_DIRECTION_TRANSFORM;
    private static final FloatVector4 LABYMOD$LIGHT1_DIRECTION_TRANSFORM;
    
    @Inject(method = { "renderWorldPass" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;shadeModel(I)V", ordinal = 1, shift = At.Shift.BEFORE) })
    private void labyMod$updateLightDirection(final int p_renderWorldPass_1_, final float p_renderWorldPass_2_, final long p_renderWorldPass_3_, final CallbackInfo ci) {
        final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
        final Blaze3DShaderUniformPipeline pipeline = gfx.blaze3DGlStatePipeline().shaderUniformPipeline();
        final MojangLight light = pipeline.light();
        MixinEntityRendererLightDirection.LABYMOD$LIGHT0_DIRECTION_TRANSFORM.set(MixinEntityRendererLightDirection.LABYMOD$LIGHT0_DIRECTION.getX(), MixinEntityRendererLightDirection.LABYMOD$LIGHT0_DIRECTION.getY(), MixinEntityRendererLightDirection.LABYMOD$LIGHT0_DIRECTION.getZ(), 1.0f);
        MixinEntityRendererLightDirection.LABYMOD$LIGHT0_DIRECTION_TRANSFORM.transform(MixinEntityRendererLightDirection.LIGHT_DIRECTION_MATRIX);
        MixinEntityRendererLightDirection.LABYMOD$LIGHT1_DIRECTION_TRANSFORM.set(MixinEntityRendererLightDirection.LABYMOD$LIGHT1_DIRECTION.getX(), MixinEntityRendererLightDirection.LABYMOD$LIGHT1_DIRECTION.getY(), MixinEntityRendererLightDirection.LABYMOD$LIGHT1_DIRECTION.getZ(), 1.0f);
        MixinEntityRendererLightDirection.LABYMOD$LIGHT1_DIRECTION_TRANSFORM.transform(MixinEntityRendererLightDirection.LIGHT_DIRECTION_MATRIX);
        light.light0Direction().set(MixinEntityRendererLightDirection.LABYMOD$LIGHT0_DIRECTION_TRANSFORM.getX(), MixinEntityRendererLightDirection.LABYMOD$LIGHT0_DIRECTION_TRANSFORM.getY(), MixinEntityRendererLightDirection.LABYMOD$LIGHT0_DIRECTION_TRANSFORM.getZ());
        light.light1Direction().set(MixinEntityRendererLightDirection.LABYMOD$LIGHT1_DIRECTION_TRANSFORM.getX(), MixinEntityRendererLightDirection.LABYMOD$LIGHT1_DIRECTION_TRANSFORM.getY(), MixinEntityRendererLightDirection.LABYMOD$LIGHT1_DIRECTION_TRANSFORM.getZ());
    }
    
    static {
        LIGHT_DIRECTION_MATRIX = new FloatMatrix4().identity();
        LABYMOD$LIGHT0_DIRECTION = new FloatVector3(0.2f, 1.0f, -0.7f).normalize();
        LABYMOD$LIGHT1_DIRECTION = new FloatVector3(-0.2f, 1.0f, 0.7f).normalize();
        LABYMOD$LIGHT0_DIRECTION_TRANSFORM = new FloatVector4();
        LABYMOD$LIGHT1_DIRECTION_TRANSFORM = new FloatVector4();
    }
}
