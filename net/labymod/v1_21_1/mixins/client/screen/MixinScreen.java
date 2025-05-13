// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.mixins.client.screen;

import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.core.client.gui.screen.widget.window.debug.util.VersionedWidget;
import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.v1_21_1.client.gui.GuiGraphicsAccessor;
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
import javax.annotation.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_21_1.client.gui.ScreenAccessor;
import net.labymod.core.client.gui.screen.accessor.FileDropHandler;

@Mixin({ fod.class })
public abstract class MixinScreen implements FileDropHandler, ScreenAccessor
{
    @Shadow
    @Nullable
    protected fgo l;
    @Shadow
    protected fhx o;
    @Shadow
    @Final
    private List<fki> r;
    private fhz labyMod$graphics;
    
    @Shadow
    public abstract void a(final List<Path> p0);
    
    @Insert(method = { "init(Lnet/minecraft/client/Minecraft;II)V" }, at = @At("HEAD"))
    public void fireScreenInitEvent(final fgo mc, final int width, final int height, final InsertInfo ci) {
        Laby.fireEvent(new VersionedScreenInitEvent(this));
    }
    
    @Insert(method = { "renderBackground" }, at = @At("HEAD"), cancellable = true)
    public void renderThemeBackground(final fhz graphics, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        this.renderThemeBackground(((VanillaStackAccessor)graphics.c()).stack(), ci);
    }
    
    private void renderThemeBackground(Stack stack, final InsertInfo ci) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        final BackgroundRenderer backgroundRenderer = theme.getBackgroundRenderer();
        if (backgroundRenderer == null) {
            return;
        }
        stack = ((stack != null) ? stack : ((VanillaStackAccessor)new fbi()).stack());
        if (backgroundRenderer.renderBackground(stack, this)) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "renderTransparentBackground" }, at = { @At("HEAD") }, cancellable = true)
    private void labyMod$hideMenuBackground(final fhz $$0, final CallbackInfo ci) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get()) {
            ci.cancel();
        }
    }
    
    @Insert(method = { "renderBackground" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderMenuBackground(Lnet/minecraft/client/gui/GuiGraphics;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$disableMenuBackground(final fhz graphics, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get()) {
            ci.cancel();
        }
    }
    
    @Override
    public void renderComponentHoverEffect(final Stack stack, final Style style, final int x, final int y) {
        if (this.labyMod$graphics == null) {
            this.labyMod$graphics = new fhz(this.l, this.l.aO().c());
        }
        ((GuiGraphicsAccessor)this.labyMod$graphics).setPose((fbi)stack.getProvider().getPoseStack());
        this.labyMod$graphics.a(this.o, (xw)style, x, y);
    }
    
    @Override
    public void setMinecraft(final fgo minecraft) {
        this.l = minecraft;
    }
    
    @Override
    public boolean handleDroppedFiles(final MutableMouse mouse, final List<Path> paths) {
        this.a(paths);
        return false;
    }
    
    @Override
    public List<Object> getWrappedWidgets() {
        final List<Object> wrappedWidgets = new ArrayList<Object>();
        for (final fki child : this.r) {
            wrappedWidgets.add(this.asVersionedWidget(child));
        }
        return wrappedWidgets;
    }
    
    private VersionedWidget asVersionedWidget(final fki child) {
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
        if (child instanceof final fkh container) {
            for (final fki entry : container.aK_()) {
                versionedWidget.addChild(this.asVersionedWidget(entry));
            }
        }
        return versionedWidget;
    }
}
