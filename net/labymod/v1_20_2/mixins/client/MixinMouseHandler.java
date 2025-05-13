// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client;

import net.labymod.api.event.client.entity.player.ClientHotbarSlotChangeEvent;
import net.labymod.api.util.math.MathHelper;
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

@Mixin({ eqw.class })
public abstract class MixinMouseHandler implements MouseHandlerAccessor
{
    @Shadow
    @Final
    private eqv a;
    @Shadow
    private int h;
    @Shadow
    private double k;
    @Shadow
    private double e;
    @Shadow
    private double f;
    @Shadow
    private double n;
    @Shadow
    private double o;
    @Shadow
    private boolean s;
    @Shadow
    private double q;
    private final MouseBridge labyMod$mouseBridge;
    
    public MixinMouseHandler() {
        this.labyMod$mouseBridge = LabyMod.references().mouseBridge();
    }
    
    @Shadow
    public abstract boolean h();
    
    @Shadow
    public abstract void a();
    
    @Insert(method = { "onPress(JIII)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;", ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$handleMouseEvent(final long windowHandle, final int button, final int action, final int mods, final InsertInfo callback) {
        if (this.labyMod$mouseBridge.handleMouseEvent(button, action)) {
            callback.cancel();
        }
    }
    
    @Insert(method = { "onMove" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;", ordinal = 0, shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$handleMouseMove(final long windowHandle, final double mouseX, final double mouseY, final InsertInfo callback) {
        this.labyMod$mouseBridge.handleMouseMove(this.h, this.k, mouseX, mouseY, this.e, this.f, cancel -> {
            if (!(!cancel)) {
                callback.cancel();
                this.a.aG().a("mouse");
                if (this.h() && this.a.aA()) {
                    this.n += mouseX - this.e;
                    this.o += mouseY - this.f;
                }
                this.a();
                this.e = mouseX;
                this.f = mouseY;
                this.a.aG().c();
            }
        });
    }
    
    @Insert(method = { "onScroll(JDD)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getOverlay()Lnet/minecraft/client/gui/screens/Overlay;"), cancellable = true)
    private void labyMod$handleMouseScroll(final long windowHandle, final double xOffset, final double yOffset, final InsertInfo callback) {
        final eqz options = eqv.O().m;
        final double scrollOffset = options.O().c() ? Math.signum(yOffset) : yOffset;
        final double delta = scrollOffset * (double)options.C().c();
        if (this.labyMod$mouseBridge.handleMouseScroll(delta)) {
            callback.cancel();
        }
    }
    
    @Redirect(method = { "turnPlayer" }, at = @At(value = "INVOKE", target = "net.minecraft.client.player.LocalPlayer.turn(DD)V"))
    private void redirectPlayerRotation(final fng player, final double x, final double y) {
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
        final ekt window = eqv.O().aM();
        final double x = this.e * window.o() / window.m();
        final double y = this.f * window.p() / window.n();
        final MouseBridge bridge = this.labyMod$mouseBridge;
        bridge.updateMouse(x, y);
        bridge.mouse().setGrabbed(this.s);
        bridge.absoluteMouse().setGrabbed(this.s);
    }
    
    @Override
    public void grabMouseNative() {
        ekk.a(this.a.aM().i(), 212995, this.a.aM().m() / 2.0, this.a.aM().n() / 2.0);
    }
    
    @Override
    public void ungrabMouseNative() {
        ekk.a(this.a.aM().i(), 212993, this.a.aM().m() / 2.0, this.a.aM().n() / 2.0);
    }
    
    @Redirect(method = { "onScroll" }, at = @At(value = "FIELD", ordinal = 5, target = "Lnet/minecraft/client/MouseHandler;accumulatedScrollY:D"))
    private double labyMod$fireHotbarSlotChangeEvent(final eqw instance) {
        final double delta = this.q;
        if (delta == 0.0) {
            return 0.0;
        }
        final fng player = this.a.s;
        final int slotDelta = MathHelper.clamp((int)delta, -1, 1);
        final ClientHotbarSlotChangeEvent event = new ClientHotbarSlotChangeEvent(player.fR().l, slotDelta);
        final int originalToSlot = event.toSlot();
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            return 0.0;
        }
        if (event.toSlot() != originalToSlot && !player.M_()) {
            player.fR().l = MathHelper.clamp(event.toSlot(), 0, 8);
            return 0.0;
        }
        return delta;
    }
}
