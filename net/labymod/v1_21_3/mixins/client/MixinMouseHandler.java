// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.labymod.api.event.client.entity.player.ClientHotbarSlotChangeEvent;
import net.labymod.api.util.math.MathHelper;
import org.joml.Vector2i;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import net.labymod.core.client.gui.screen.key.MacOSMouse;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.event.client.entity.player.ClientPlayerTurnEvent;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.core.main.LabyMod;
import net.labymod.core.client.input.MouseBridge;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gui.mouse.MouseHandlerAccessor;

@Mixin({ fmh.class })
public abstract class MixinMouseHandler implements MouseHandlerAccessor
{
    @Shadow
    @Final
    private fmg b;
    @Shadow
    private int i;
    @Shadow
    private double l;
    @Shadow
    private double f;
    @Shadow
    private double g;
    @Shadow
    private double o;
    @Shadow
    private double p;
    @Shadow
    private boolean s;
    private final MouseBridge labyMod$mouseBridge;
    
    public MixinMouseHandler() {
        this.labyMod$mouseBridge = LabyMod.references().mouseBridge();
    }
    
    @Insert(method = { "onPress(JIII)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;", ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$handleMouseEvent(final long windowHandle, final int button, final int action, final int mods, final InsertInfo callback) {
        if (this.labyMod$mouseBridge.handleMouseEvent(button, action)) {
            callback.cancel();
        }
    }
    
    @Insert(method = { "onMove" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isWindowActive()Z", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$handleMouseMove(final long windowHandle, final double mouseX, final double mouseY, final InsertInfo callback) {
        this.labyMod$mouseBridge.handleMouseMove(this.i, this.l, mouseX, mouseY, this.f, this.g, cancel -> {
            if (!(!cancel)) {
                callback.cancel();
                if (this.b.aC()) {
                    this.o += mouseX - this.f;
                    this.p += mouseY - this.g;
                }
                this.f = mouseX;
                this.g = mouseY;
            }
        });
    }
    
    @Insert(method = { "onScroll(JDD)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;"), cancellable = true)
    private void labyMod$handleMouseScroll(final long windowHandle, final double xOffset, final double yOffset, final InsertInfo callback) {
        final fmk options = fmg.Q().n;
        final double scrollOffset = options.V().c() ? Math.signum(yOffset) : yOffset;
        final double delta = scrollOffset * (double)options.H().c();
        if (this.labyMod$mouseBridge.handleMouseScroll(delta)) {
            callback.cancel();
        }
    }
    
    @Redirect(method = { "turnPlayer" }, at = @At(value = "INVOKE", target = "net.minecraft.client.player.LocalPlayer.turn(DD)V"))
    private void redirectPlayerRotation(final gkh player, final double x, final double y) {
        final ClientPlayerTurnEvent event = Laby.fireEvent(new ClientPlayerTurnEvent((ClientPlayer)player, x / 8.0, y / 8.0));
        if (event.isCancelled()) {
            return;
        }
        player.b(event.getX() * 8.0, event.getY() * 8.0);
    }
    
    @Inject(method = { "releaseMouse" }, at = { @At("TAIL") })
    private void labyMod$releaseMouse(final CallbackInfo ci) {
        this.labyMod$updateMouseGrabbed();
    }
    
    @Inject(method = { "grabMouse" }, at = { @At("TAIL") })
    private void labyMod$grabMouse(final CallbackInfo ci) {
        this.labyMod$updateMouseGrabbed();
    }
    
    @ModifyVariable(method = { "onScroll" }, at = @At("HEAD"), index = 5, argsOnly = true)
    private double labyMod$fixMacOSScroll(final double value, final long windowHandle, final double horizontal, final double vertical) {
        return MacOSMouse.fixMouseScroll(value, windowHandle, horizontal, vertical);
    }
    
    private void labyMod$updateMouseGrabbed() {
        final ffv window = fmg.Q().aO();
        final double x = this.f * window.o() / window.m();
        final double y = this.g * window.p() / window.n();
        final MouseBridge bridge = this.labyMod$mouseBridge;
        bridge.updateMouse(x, y);
        bridge.mouse().setGrabbed(this.s);
        bridge.absoluteMouse().setGrabbed(this.s);
    }
    
    @Override
    public void grabMouseNative() {
        ffn.a(this.b.aO().h(), 212995, this.b.aO().m() / 2.0, this.b.aO().n() / 2.0);
    }
    
    @Override
    public void ungrabMouseNative() {
        ffn.a(this.b.aO().h(), 212993, this.b.aO().m() / 2.0, this.b.aO().n() / 2.0);
    }
    
    @WrapOperation(method = { "onScroll" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/ScrollWheelHandler;onMouseScroll(DD)Lorg/joml/Vector2i;") })
    private Vector2i labyMod$fireHotbarSlotChangeEvent(final fmp instance, final double $$0, final double $$1, final Operation<Vector2i> original) {
        final Vector2i mouseScroll = (Vector2i)original.call(new Object[] { instance, $$0, $$1 });
        if (mouseScroll.y == 0) {
            return mouseScroll;
        }
        final gkh player = this.b.t;
        final int slotDelta = MathHelper.clamp(mouseScroll.y, -1, 1);
        final ClientHotbarSlotChangeEvent event = new ClientHotbarSlotChangeEvent(player.gi().j, slotDelta);
        final int originalToSlot = event.toSlot();
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            mouseScroll.y = 0;
            return mouseScroll;
        }
        if (event.toSlot() != originalToSlot && !player.aa_()) {
            player.gi().j = MathHelper.clamp(event.toSlot(), 0, 8);
            mouseScroll.y = 0;
            return mouseScroll;
        }
        return mouseScroll;
    }
}
