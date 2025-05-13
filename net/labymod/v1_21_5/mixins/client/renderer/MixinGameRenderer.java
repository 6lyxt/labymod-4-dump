// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.mixins.client.renderer;

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
import net.labymod.v1_21_5.client.util.MinecraftUtil;
import net.labymod.core.event.client.render.camera.CameraEyeHeightEvent;
import net.labymod.api.Laby;
import net.labymod.core.client.world.ExtendedMinecraftCamera;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.joml.Quaternionfc;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ grd.class })
public abstract class MixinGameRenderer
{
    @Shadow
    @Final
    private fqq j;
    @Shadow
    private float q;
    @Shadow
    private float r;
    private int labyMod$tick;
    
    @WrapOperation(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lorg/joml/Matrix4f;rotation(Lorg/joml/Quaternionfc;)Lorg/joml/Matrix4f;", remap = false) })
    private Matrix4f labyMod$setViewMatrix(Matrix4f self, final Quaternionfc quaternion, final Operation<Matrix4f> original, final fqg deltaTracker) {
        final ExtendedMinecraftCamera camera = (ExtendedMinecraftCamera)Laby.labyAPI().minecraft().getCamera();
        self = (Matrix4f)original.call(new Object[] { self, quaternion });
        if (camera != null) {
            final float partialTicks = deltaTracker.a(false);
            final float eyeHeight = camera.getEyeHeight(partialTicks);
            final CameraEyeHeightEvent event = Laby.fireEvent(new CameraEyeHeightEvent(partialTicks, eyeHeight));
            self.translate(0.0f, eyeHeight - event.getEyeHeight(), 0.0f);
        }
        return MinecraftUtil.setViewMatrix(self);
    }
    
    @Inject(method = { "tick" }, at = { @At("HEAD") })
    private void labyMod$tick(final CallbackInfo ci) {
        ++this.labyMod$tick;
    }
    
    @Inject(method = { "renderLevel" }, at = { @At("TAIL") })
    private void labyMod$resetRenderLevel(final CallbackInfo ci) {
        MinecraftUtil.setDefaultViewMatrix();
    }
    
    @Inject(method = { "renderItemInHand" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$firePreRenderHandEvent(final fpy camera, final float partialTicks, final Matrix4f viewMatrix, final CallbackInfo ci) {
        final fld poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
        final RenderHandEvent event = RenderHandEventCaller.call(((VanillaStackAccessor)poseStack).stack(), Phase.PRE);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderItemInHand" }, at = { @At("TAIL") })
    private void labyMod$firePostRenderHandEvent(final fpy camera, final float partialTicks, final Matrix4f viewMatrix, final CallbackInfo ci) {
        final fld poseStack = MinecraftUtil.levelRenderContext().getPoseStack();
        RenderHandEventCaller.call(((VanillaStackAccessor)poseStack).stack(), Phase.POST);
    }
    
    @Insert(method = { "tickFov()V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getFieldOfViewModifier(ZF)F", shift = At.Shift.AFTER), cancellable = true)
    public void labyMod$fireFieldOfViewTickEvent(final InsertInfo ci) {
        float modifier = 1.0f;
        final bxe ao = this.j.ao();
        if (ao instanceof final gqj cameraEntity) {
            modifier = cameraEntity.a(this.j.n.aE().a(), ((Double)this.j.n.an().c()).floatValue());
        }
        final FieldOfViewTickEvent fieldOfViewTickEvent = new FieldOfViewTickEvent(this.q, this.r, modifier, this.labyMod$tick);
        Laby.fireEvent(fieldOfViewTickEvent);
        this.q = fieldOfViewTickEvent.getFov();
        this.r = fieldOfViewTickEvent.getOldFov();
        if (fieldOfViewTickEvent.isOverwriteVanilla()) {
            ci.cancel();
        }
    }
    
    @WrapWithCondition(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V") })
    private boolean labyMod$noViewBobbing(final grd instance, final fld stack, final float partialTicks) {
        return !LabyMod.getInstance().config().ingame().noViewBobbing().get();
    }
}
