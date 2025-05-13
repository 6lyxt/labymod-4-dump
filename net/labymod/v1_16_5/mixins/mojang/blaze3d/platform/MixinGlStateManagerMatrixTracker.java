// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.mojang.blaze3d.platform;

import java.nio.FloatBuffer;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.client.gfx.pipeline.util.MatrixTracker;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ dem.class })
public class MixinGlStateManagerMatrixTracker
{
    @Inject(method = { "_pushMatrix" }, at = { @At("TAIL") })
    private static void labyMod$pushMatrix(final CallbackInfo ci) {
        MatrixTracker.push();
    }
    
    @Inject(method = { "_popMatrix" }, at = { @At("TAIL") })
    private static void labyMod$popMatrix(final CallbackInfo ci) {
        MatrixTracker.pop();
    }
    
    @Inject(method = { "_matrixMode" }, at = { @At("TAIL") })
    private static void labyMod$matrixMode(final int mode, final CallbackInfo ci) {
        MatrixTracker.beginTracking(mode);
    }
    
    @Inject(method = { "_translated" }, at = { @At("TAIL") })
    private static void labyMod$translatef(final double x, final double y, final double z, final CallbackInfo ci) {
        MatrixTracker.translate((float)x, (float)y, (float)z);
    }
    
    @Inject(method = { "_translatef" }, at = { @At("TAIL") })
    private static void labyMod$translatef(final float x, final float y, final float z, final CallbackInfo ci) {
        MatrixTracker.translate(x, y, z);
    }
    
    @Inject(method = { "_scaled" }, at = { @At("TAIL") })
    private static void labyMod$scalef(final double x, final double y, final double z, final CallbackInfo ci) {
        MatrixTracker.scale((float)x, (float)y, (float)z);
    }
    
    @Inject(method = { "_scalef" }, at = { @At("TAIL") })
    private static void labyMod$scalef(final float x, final float y, final float z, final CallbackInfo ci) {
        MatrixTracker.scale(x, y, z);
    }
    
    @Inject(method = { "_rotatef" }, at = { @At("TAIL") })
    private static void labyMod$rotatef(final float angle, final float x, final float y, final float z, final CallbackInfo ci) {
        MatrixTracker.rotate(angle, x, y, z);
    }
    
    @Inject(method = { "_loadIdentity" }, at = { @At("TAIL") })
    private static void labyMod$loadIdentify(final CallbackInfo ci) {
        MatrixTracker.loadIdentity();
    }
    
    @Inject(method = { "_ortho" }, at = { @At("TAIL") })
    private static void labyMod$ortho(final double lvt_0_1_, final double lvt_2_1_, final double lvt_4_1_, final double lvt_6_1_, final double lvt_8_1_, final double lvt_10_1_, final CallbackInfo ci) {
        MatrixTracker.ortho((float)lvt_0_1_, (float)lvt_2_1_, (float)lvt_4_1_, (float)lvt_6_1_, (float)lvt_8_1_, (float)lvt_10_1_);
    }
    
    @Inject(method = { "_multMatrix(Ljava/nio/FloatBuffer;)V" }, at = { @At("TAIL") })
    private static void labyMod$mulMatrix(final FloatBuffer buffer, final CallbackInfo ci) {
        MatrixTracker.multiply(buffer);
    }
}
