// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.labymod.core.main.LabyMod;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.event.client.entity.player.FieldOfViewTickEvent;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.event.client.render.RenderHandEvent;
import net.labymod.core.event.client.render.RenderHandEventCaller;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.v1_20_5.client.util.MinecraftUtil;
import net.labymod.core.event.client.render.camera.CameraEyeHeightEvent;
import net.labymod.api.Laby;
import net.labymod.core.client.world.ExtendedMinecraftCamera;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ gdi.class })
public abstract class MixinGameRenderer
{
    @Shadow
    @Final
    ffg k;
    @Shadow
    private float r;
    @Shadow
    private float s;
    @Shadow
    private int q;
    
    @WrapOperation(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lorg/joml/Matrix4f;rotationXYZ(FFF)Lorg/joml/Matrix4f;") })
    private Matrix4f labyMod$setViewMatrix(Matrix4f self, final float angleX, final float angleY, final float angleZ, final Operation<Matrix4f> original, final float partialTicks) {
        final ExtendedMinecraftCamera camera = (ExtendedMinecraftCamera)Laby.labyAPI().minecraft().getCamera();
        self = (Matrix4f)original.call(new Object[] { self, angleX, angleY, angleZ });
        if (camera != null) {
            final float eyeHeight = camera.getEyeHeight(partialTicks);
            final CameraEyeHeightEvent event = Laby.fireEvent(new CameraEyeHeightEvent(partialTicks, eyeHeight));
            self.translate(0.0f, eyeHeight - event.getEyeHeight(), 0.0f);
        }
        return MinecraftUtil.setViewMatrix(self);
    }
    
    @Inject(method = { "renderLevel" }, at = { @At("TAIL") })
    private void labyMod$resetRenderLevel(final CallbackInfo ci) {
        MinecraftUtil.setDefaultViewMatrix();
    }
    
    @Inject(method = { "renderItemInHand" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$firePreRenderHandEvent(final fer camera, final float partialTicks, final Matrix4f viewMatrix, final CallbackInfo ci) {
        final ezz poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
        final RenderHandEvent event = RenderHandEventCaller.call(((VanillaStackAccessor)poseStack).stack(), Phase.PRE);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderItemInHand" }, at = { @At("TAIL") })
    private void labyMod$firePostRenderHandEvent(final fer camera, final float partialTicks, final Matrix4f viewMatrix, final CallbackInfo ci) {
        final ezz poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
        RenderHandEventCaller.call(((VanillaStackAccessor)poseStack).stack(), Phase.POST);
    }
    
    @Insert(method = { "tickFov()V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getFieldOfViewModifier()F", shift = At.Shift.AFTER), cancellable = true)
    public void labyMod$fireFieldOfViewTickEvent(final InsertInfo ci) {
        float modifier = 1.0f;
        final bsv an = this.k.an();
        if (an instanceof final gco cameraEntity) {
            modifier = cameraEntity.c();
        }
        final FieldOfViewTickEvent fieldOfViewTickEvent = new FieldOfViewTickEvent(this.r, this.s, modifier, this.q);
        Laby.fireEvent(fieldOfViewTickEvent);
        this.r = fieldOfViewTickEvent.getFov();
        this.s = fieldOfViewTickEvent.getOldFov();
        if (fieldOfViewTickEvent.isOverwriteVanilla()) {
            ci.cancel();
        }
    }
    
    @WrapWithCondition(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V") })
    private boolean labyMod$noViewBobbing(final gdi instance, final ezz stack, final float partialTicks) {
        return !LabyMod.getInstance().config().ingame().noViewBobbing().get();
    }
}
