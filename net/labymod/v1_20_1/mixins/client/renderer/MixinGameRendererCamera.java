// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_1.mixins.client.renderer;

import net.labymod.api.Laby;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.api.event.Phase;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fjq.class })
public class MixinGameRendererCamera
{
    @Inject(method = { "renderLevel(FJLcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = { @At("HEAD") })
    public void labyMod$preSetupCamera(final float param0, final long param1, final eij stack, final CallbackInfo callbackInfo) {
        this.labyMod$fireCameraSetupEvent(stack, Phase.PRE);
    }
    
    @Inject(method = { "renderLevel(FJLcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;prepareCullFrustum(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/phys/Vec3;Lorg/joml/Matrix4f;)V", shift = At.Shift.BEFORE) })
    public void labyMod$postSetupCamera(final float param0, final long param1, final eij stack, final CallbackInfo callbackInfo) {
        this.labyMod$fireCameraSetupEvent(stack, Phase.POST);
    }
    
    private void labyMod$fireCameraSetupEvent(final eij poseStack, final Phase phase) {
        Laby.fireEvent(new CameraSetupEvent(((VanillaStackAccessor)poseStack).stack(), phase));
    }
}
