// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.util;

import net.labymod.core.client.input.MouseBridge;
import net.labymod.api.client.gui.window.Window;
import net.labymod.core.main.LabyMod;
import net.labymod.api.Laby;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.api.client.gui.mouse.MouseHandlerAccessor;

@Mixin({ bic.class })
public class MixinMouseHelper implements MouseHandlerAccessor
{
    @Inject(method = { "grabMouseCursor" }, at = { @At("HEAD") })
    private void labyMod$setCursorPosition(final CallbackInfo ci) {
        Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
    }
    
    @Insert(method = { "grabMouseCursor" }, at = @At("TAIL"))
    private void labymod$grabMouse(final InsertInfo ci) {
        this.labyMod$updateMouseGrabbed(true);
    }
    
    @Insert(method = { "ungrabMouseCursor" }, at = @At("TAIL"))
    private void labymod$releaseMouse(final InsertInfo ci) {
        this.labyMod$updateMouseGrabbed(false);
    }
    
    private void labyMod$updateMouseGrabbed(final boolean mouseGrabbed) {
        final Window window = Laby.labyAPI().minecraft().minecraftWindow();
        final int scaledHeight = window.getScaledHeight();
        final double x = Mouse.getX() * (double)window.getScaledWidth() / window.getRawWidth();
        final double y = scaledHeight - Mouse.getY() * (double)scaledHeight / window.getRawHeight();
        final MouseBridge bridge = LabyMod.references().mouseBridge();
        bridge.updateMouse(x, y);
        bridge.mouse().setGrabbed(mouseGrabbed);
        bridge.absoluteMouse().setGrabbed(mouseGrabbed);
    }
    
    @Override
    public void grabMouseNative() {
        Mouse.setGrabbed(true);
    }
    
    @Override
    public void ungrabMouseNative() {
        Mouse.setGrabbed(false);
    }
}
