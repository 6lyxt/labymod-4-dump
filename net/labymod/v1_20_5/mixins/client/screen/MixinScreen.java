// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_5.mixins.client.screen;

import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.core.client.gui.screen.widget.window.debug.util.VersionedWidget;
import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.v1_20_5.client.gui.GuiGraphicsAccessor;
import net.labymod.api.client.component.format.Style;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.spongepowered.asm.mixin.injection.At;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.Laby;
import net.labymod.api.event.client.gui.screen.VersionedScreenInitEvent;
import net.labymod.api.volt.callback.InsertInfo;
import java.nio.file.Path;
import org.spongepowered.asm.mixin.Final;
import java.util.List;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_20_5.client.gui.ScreenAccessor;
import net.labymod.core.client.gui.screen.accessor.FileDropHandler;

@Mixin({ fne.class })
public abstract class MixinScreen implements FileDropHandler, ScreenAccessor
{
    @Shadow
    @Nullable
    protected ffg m;
    @Shadow
    protected fgq p;
    @Shadow
    @Final
    private List<fjb> r;
    private fgs labyMod$graphics;
    
    @Shadow
    public abstract void a(final List<Path> p0);
    
    @Insert(method = { "init(Lnet/minecraft/client/Minecraft;II)V" }, at = @At("HEAD"))
    public void fireScreenInitEvent(final ffg mc, final int width, final int height, final InsertInfo ci) {
        Laby.fireEvent(new VersionedScreenInitEvent(this));
    }
    
    @Insert(method = { "renderBackground" }, at = @At("HEAD"), cancellable = true)
    public void renderThemeBackground(final fgs graphics, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        this.renderThemeBackground(((VanillaStackAccessor)graphics.c()).stack(), ci);
    }
    
    private void renderThemeBackground(Stack stack, final InsertInfo ci) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        final BackgroundRenderer backgroundRenderer = theme.getBackgroundRenderer();
        if (backgroundRenderer == null) {
            return;
        }
        stack = ((stack != null) ? stack : ((VanillaStackAccessor)new ezz()).stack());
        if (backgroundRenderer.renderBackground(stack, this)) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderTransparentBackground" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$hideMenuBackground(final fgs $$0, final CallbackInfo ci) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get()) {
            ci.cancel();
        }
    }
    
    @Insert(method = { "renderBackground" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderMenuBackground(Lnet/minecraft/client/gui/GuiGraphics;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$disableMenuBackground(final fgs graphics, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get()) {
            ci.cancel();
        }
    }
    
    @Override
    public void renderComponentHoverEffect(final Stack stack, final Style style, final int x, final int y) {
        if (this.labyMod$graphics == null) {
            this.labyMod$graphics = new fgs(this.m, this.m.aQ().c());
        }
        ((GuiGraphicsAccessor)this.labyMod$graphics).setPose((ezz)stack.getProvider().getPoseStack());
        this.labyMod$graphics.a(this.p, (ym)style, x, y);
    }
    
    @Override
    public void setMinecraft(final ffg minecraft) {
        this.m = minecraft;
    }
    
    @Override
    public boolean handleDroppedFiles(final MutableMouse mouse, final List<Path> paths) {
        this.a(paths);
        return false;
    }
    
    @Override
    public List<Object> getWrappedWidgets() {
        final List<Object> wrappedWidgets = new ArrayList<Object>();
        for (final fjb child : this.r) {
            wrappedWidgets.add(this.asVersionedWidget(child));
        }
        return wrappedWidgets;
    }
    
    private VersionedWidget asVersionedWidget(final fjb child) {
        VersionedWidget versionedWidget = null;
        if (child instanceof ConvertableMinecraftWidget) {
            final ConvertableMinecraftWidget<?> convertable = (ConvertableMinecraftWidget<?>)child;
            final WidgetWatcher<?> watcher = convertable.getWatcher();
            if (watcher != null && watcher.getWidget() != null) {
                versionedWidget = new VersionedWidget(child.getClass(), (Widget)watcher.getWidget());
            }
        }
        if (versionedWidget != null) {
            return versionedWidget;
        }
        versionedWidget = new VersionedWidget(child.getClass());
        if (child instanceof final fja container) {
            for (final fjb entry : container.aD_()) {
                versionedWidget.addChild(this.asVersionedWidget(entry));
            }
        }
        return versionedWidget;
    }
}
