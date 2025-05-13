// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.renderer;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.labymod.core.main.LabyMod;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.render.overlay.IngameOverlayRenderEvent;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.event.client.entity.player.FieldOfViewTickEvent;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.api.event.client.render.RenderHandEvent;
import net.labymod.core.event.client.render.RenderHandEventCaller;
import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.labymod.core.event.client.render.camera.CameraEyeHeightEvent;
import net.labymod.api.Laby;
import net.labymod.core.client.world.ExtendedMinecraftCamera;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ fdo.class })
public abstract class MixinGameRenderer
{
    @Shadow
    @Final
    ejf i;
    @Shadow
    private float p;
    @Shadow
    private float q;
    @Shadow
    private int o;
    
    @Inject(method = { "renderLevel(FJLcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lorg/joml/Matrix4f;)V", shift = At.Shift.BEFORE) })
    private void labyMod$fireCameraEyeHeight(final float partialTicks, final long param, final eed stack, final CallbackInfo ci) {
        final ExtendedMinecraftCamera camera = (ExtendedMinecraftCamera)Laby.labyAPI().minecraft().getCamera();
        if (camera == null) {
            return;
        }
        final float eyeHeight = camera.getEyeHeight(partialTicks);
        final CameraEyeHeightEvent event = Laby.fireEvent(new CameraEyeHeightEvent(partialTicks, eyeHeight));
        stack.a(0.0, (double)(eyeHeight - event.getEyeHeight()), 0.0);
    }
    
    @Inject(method = { "renderItemInHand" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$firePreRenderHandEvent(final eed stack, final eir camera, final float partialTicks, final CallbackInfo ci) {
        final RenderHandEvent event = RenderHandEventCaller.call(((VanillaStackAccessor)stack).stack(), Phase.PRE);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderItemInHand" }, at = { @At("TAIL") })
    private void labyMod$firePostRenderHandEvent(final eed stack, final eir camera, final float partialTicks, final CallbackInfo ci) {
        RenderHandEventCaller.call(((VanillaStackAccessor)stack).stack(), Phase.POST);
    }
    
    @Insert(method = { "tickFov()V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getFieldOfViewModifier()F", shift = At.Shift.AFTER), cancellable = true)
    public void labyMod$fireFieldOfViewTickEvent(final InsertInfo ci) {
        float modifier = 1.0f;
        final bdr al = this.i.al();
        if (al instanceof final fcw cameraEntity) {
            modifier = cameraEntity.p();
        }
        final FieldOfViewTickEvent fieldOfViewTickEvent = new FieldOfViewTickEvent(this.p, this.q, modifier, this.o);
        Laby.fireEvent(fieldOfViewTickEvent);
        this.p = fieldOfViewTickEvent.getFov();
        this.q = fieldOfViewTickEvent.getOldFov();
        if (fieldOfViewTickEvent.isOverwriteVanilla()) {
            ci.cancel();
        }
    }
    
    @Redirect(method = { "render" }, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/Options;hideGui:Z"))
    private boolean labyMod$fireHiddenOverlayRenderEvent(final ejj options) {
        final boolean hideGui = options.aa;
        if (hideGui && this.i.z == null) {
            final Stack stack = ((VanillaStackAccessor)new eed()).stack();
            Laby.fireEvent(new IngameOverlayRenderEvent(stack, Phase.PRE, true));
            Laby.fireEvent(new IngameOverlayRenderEvent(stack, Phase.POST, true));
        }
        return hideGui;
    }
    
    @WrapWithCondition(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V") })
    private boolean labyMod$noViewBobbing(final fdo instance, final eed stack, final float partialTicks) {
        return !LabyMod.getInstance().config().ingame().noViewBobbing().get();
    }
}
