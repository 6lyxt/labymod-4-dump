// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.mixins.client.screen;

import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.core.client.gui.screen.widget.window.debug.util.VersionedWidget;
import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.v1_20_2.client.gui.GuiGraphicsAccessor;
import net.labymod.api.client.component.format.Style;
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
import net.labymod.v1_20_2.client.gui.ScreenAccessor;
import net.labymod.core.client.gui.screen.accessor.FileDropHandler;

@Mixin({ eyk.class })
public abstract class MixinScreen implements FileDropHandler, ScreenAccessor
{
    @Shadow
    @Nullable
    protected eqv f;
    @Shadow
    protected esd i;
    @Shadow
    @Final
    private List<euk> k;
    private esf labyMod$graphics;
    
    @Shadow
    public abstract void a(final List<Path> p0);
    
    @Insert(method = { "init(Lnet/minecraft/client/Minecraft;II)V" }, at = @At("HEAD"))
    public void fireScreenInitEvent(final eqv mc, final int width, final int height, final InsertInfo ci) {
        Laby.fireEvent(new VersionedScreenInitEvent(this));
    }
    
    @Insert(method = { "renderTransparentBackground" }, at = @At("HEAD"), cancellable = true)
    public void renderThemeBackground(final esf graphics, final InsertInfo ci) {
        this.renderThemeBackground(((VanillaStackAccessor)graphics.c()).stack(), ci);
    }
    
    @Insert(method = { "renderDirtBackground" }, at = @At("HEAD"), cancellable = true)
    public void renderThemeDirtBackground(final esf graphics, final InsertInfo ci) {
        this.renderThemeBackground(((VanillaStackAccessor)graphics.c()).stack(), ci);
    }
    
    private void renderThemeBackground(Stack stack, final InsertInfo ci) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        final BackgroundRenderer backgroundRenderer = theme.getBackgroundRenderer();
        if (backgroundRenderer == null) {
            return;
        }
        stack = ((stack != null) ? stack : ((VanillaStackAccessor)new elp()).stack());
        if (backgroundRenderer.renderBackground(stack, this)) {
            ci.cancel();
        }
    }
    
    @Insert(method = { "renderBackground" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;renderTransparentBackground(Lnet/minecraft/client/gui/GuiGraphics;)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$disableMenuBackground(final esf graphics, final int mouseX, final int mouseY, final float partialTicks, final InsertInfo ci) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get()) {
            ci.cancel();
        }
    }
    
    @Override
    public void renderComponentHoverEffect(final Stack stack, final Style style, final int x, final int y) {
        if (this.labyMod$graphics == null) {
            this.labyMod$graphics = new esf(this.f, this.f.aO().b());
        }
        ((GuiGraphicsAccessor)this.labyMod$graphics).setPose((elp)stack.getProvider().getPoseStack());
        this.labyMod$graphics.a(this.i, (uh)style, x, y);
    }
    
    @Override
    public void setMinecraft(final eqv minecraft) {
        this.f = minecraft;
    }
    
    @Override
    public boolean handleDroppedFiles(final MutableMouse mouse, final List<Path> paths) {
        this.a(paths);
        return false;
    }
    
    @Override
    public List<Object> getWrappedWidgets() {
        final List<Object> wrappedWidgets = new ArrayList<Object>();
        for (final euk child : this.k) {
            wrappedWidgets.add(this.asVersionedWidget(child));
        }
        return wrappedWidgets;
    }
    
    private VersionedWidget asVersionedWidget(final euk child) {
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
        if (child instanceof final euj container) {
            for (final euk entry : container.i()) {
                versionedWidget.addChild(this.asVersionedWidget(entry));
            }
        }
        return versionedWidget;
    }
}
