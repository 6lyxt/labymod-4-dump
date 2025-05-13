// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_3.mixins.client.window;

import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.util.GLFWUtil;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.BorderlessWindow;
import net.labymod.api.util.bounds.MutableRectangle;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.FullscreenWindowController;

@Mixin({ edh.class })
public abstract class MixinWindowBorderless implements FullscreenWindowController
{
    @Shadow
    private boolean k;
    @Shadow
    private int f;
    @Shadow
    private int g;
    @Shadow
    private int h;
    @Shadow
    private int i;
    private final MutableRectangle labyMod$previousWindowedRectangle;
    private final MutableRectangle labyMod$windowedRectangle;
    private BorderlessWindow labyMod$borderlessWindow;
    
    public MixinWindowBorderless() {
        this.labyMod$previousWindowedRectangle = Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f);
        this.labyMod$windowedRectangle = Rectangle.absolute(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    @Shadow
    public abstract long i();
    
    @Shadow
    @Nullable
    public abstract edc t();
    
    @Inject(method = { "<init>" }, at = { @At("TAIL") })
    private void labyMod$init(final edi eventHandler, final edf screenManager, final ecv data, final String videoModeName, final String title, final CallbackInfo ci) {
        this.labyMod$windowedRectangle.setPosition((float)this.f, (float)this.g);
        this.labyMod$windowedRectangle.setSize((float)this.h, (float)this.i);
        this.labyMod$borderlessWindow = new BorderlessWindow(this.i(), () -> this.labyMod$windowedRectangle, () -> this.labyMod$previousWindowedRectangle, handle -> {
            final edc bestMonitor = this.t();
            if (bestMonitor == null) {
                return null;
            }
            else {
                final edg currentMode = bestMonitor.b();
                new(net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.BorderlessWindow.Monitor.class)();
                bestMonitor.c();
                bestMonitor.d();
                BorderlessWindow.VideoMode mode = null;
                if (currentMode == null) {
                    mode = null;
                }
                else {
                    new(net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.glfw.window.BorderlessWindow.VideoMode.class)();
                    new BorderlessWindow.VideoMode(currentMode.a(), currentMode.b());
                }
                final int x;
                final int y;
                new BorderlessWindow.Monitor(x, y, mode);
                return;
            }
        }, (FullscreenWindowController)this);
    }
    
    @Inject(method = { "onMove" }, at = { @At("TAIL") })
    private void labyMod$setPosition(final long windowHandle, final int x, final int y, final CallbackInfo ci) {
        if (!this.k && !this.labyMod$borderlessWindow.isFullscreen()) {
            this.labyMod$windowedRectangle.setPosition((float)x, (float)y);
        }
    }
    
    @Inject(method = { "onResize" }, at = { @At("TAIL") })
    private void labyMod$setSize(final long windowHandle, final int width, final int height, final CallbackInfo ci) {
        if (!this.k && !this.labyMod$borderlessWindow.isFullscreen()) {
            this.labyMod$windowedRectangle.setSize((float)width, (float)height);
        }
    }
    
    @Inject(method = { "toggleFullScreen" }, at = { @At("TAIL") })
    private void labyMod$setBorderlessFullscreen(final CallbackInfo ci) {
        this.setWindowFullscreen(this.k);
    }
    
    @Inject(method = { "isFullscreen" }, at = { @At("TAIL") }, cancellable = true)
    private void labyMod$isFullscreen(final CallbackInfoReturnable<Boolean> cir) {
        if (GLFWUtil.isBorderlessWindowed()) {
            cir.setReturnValue((Object)this.labyMod$borderlessWindow.isFullscreen());
        }
    }
    
    @Inject(method = { "updateDisplay" }, at = { @At("TAIL") })
    private void labyMod$pollTasks(final CallbackInfo ci) {
        this.labyMod$borderlessWindow.poll();
    }
    
    @Override
    public boolean isWindowFullscreen() {
        return this.k;
    }
    
    @Override
    public void setWindowFullscreen(final boolean value) {
        this.k = value;
        if (GLFWUtil.isBorderlessWindowed()) {
            this.k = false;
            this.labyMod$borderlessWindow.toggleFullscreen();
        }
    }
}
