// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.mixins.client.renderer;

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

@Mixin({ dzz.class })
public abstract class MixinGameRenderer
{
    @Shadow
    private float l;
    @Shadow
    private float m;
    @Shadow
    private int k;
    @Shadow
    @Final
    private djz e;
    
    @Inject(method = { "renderLevel(FJLcom/mojang/blaze3d/vertex/PoseStack;)V" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderLevel(Lcom/mojang/blaze3d/vertex/PoseStack;FJZLnet/minecraft/client/Camera;Lnet/minecraft/client/renderer/GameRenderer;Lnet/minecraft/client/renderer/LightTexture;Lcom/mojang/math/Matrix4f;)V", shift = At.Shift.BEFORE) })
    private void labyMod$fireCameraEyeHeight(final float partialTicks, final long param, final dfm stack, final CallbackInfo ci) {
        final ExtendedMinecraftCamera camera = (ExtendedMinecraftCamera)Laby.labyAPI().minecraft().getCamera();
        if (camera == null) {
            return;
        }
        final float eyeHeight = camera.getEyeHeight(partialTicks);
        final CameraEyeHeightEvent event = Laby.fireEvent(new CameraEyeHeightEvent(partialTicks, eyeHeight));
        stack.a(0.0, (double)(eyeHeight - event.getEyeHeight()), 0.0);
    }
    
    @Inject(method = { "renderItemInHand" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$firePreRenderHandEvent(final dfm stack, final djk camera, final float partialTicks, final CallbackInfo ci) {
        final RenderHandEvent event = RenderHandEventCaller.call(((VanillaStackAccessor)stack).stack(), Phase.PRE);
        if (event.isCancelled()) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderItemInHand" }, at = { @At("TAIL") })
    private void labyMod$firePostRenderHandEvent(final dfm stack, final djk camera, final float partialTicks, final CallbackInfo ci) {
        RenderHandEventCaller.call(((VanillaStackAccessor)stack).stack(), Phase.POST);
    }
    
    @Insert(method = { "tickFov()V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getFieldOfViewModifier()F", shift = At.Shift.AFTER), cancellable = true)
    public void labyMod$fireFieldOfViewTickEvent(final InsertInfo ci) {
        float modifier = 1.0f;
        final aqa aa = this.e.aa();
        if (aa instanceof final dzj player) {
            modifier = player.v();
        }
        final FieldOfViewTickEvent fieldOfViewTickEvent = new FieldOfViewTickEvent(this.l, this.m, modifier, this.k);
        Laby.fireEvent(fieldOfViewTickEvent);
        this.l = fieldOfViewTickEvent.getFov();
        this.m = fieldOfViewTickEvent.getOldFov();
        if (fieldOfViewTickEvent.isOverwriteVanilla()) {
            ci.cancel();
        }
    }
    
    @Redirect(method = { "render" }, at = @At(value = "FIELD", opcode = 180, target = "Lnet/minecraft/client/Options;hideGui:Z"))
    private boolean labyMod$fireHiddenOverlayRenderEvent(final dkd options) {
        final boolean hideGui = options.aI;
        if (hideGui && this.e.y == null) {
            final Stack stack = ((VanillaStackAccessor)new dfm()).stack();
            Laby.fireEvent(new IngameOverlayRenderEvent(stack, Phase.PRE, true));
            Laby.fireEvent(new IngameOverlayRenderEvent(stack, Phase.POST, true));
        }
        return hideGui;
    }
    
    @WrapWithCondition(method = { "renderLevel" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;bobView(Lcom/mojang/blaze3d/vertex/PoseStack;F)V") })
    private boolean labyMod$noViewBobbing(final dzz instance, final dfm stack, final float partialTicks) {
        return !LabyMod.getInstance().config().ingame().noViewBobbing().get();
    }
}
