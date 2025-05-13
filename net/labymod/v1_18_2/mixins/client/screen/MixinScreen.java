// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.mixins.client.screen;

import net.labymod.api.client.gui.screen.widget.converter.WidgetWatcher;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.converter.ConvertableMinecraftWidget;
import net.labymod.core.client.gui.screen.widget.window.debug.util.VersionedWidget;
import java.util.Iterator;
import java.util.ArrayList;
import net.labymod.api.client.gui.mouse.MutableMouse;
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
import net.labymod.v1_18_2.client.gui.ScreenAccessor;
import net.labymod.core.client.gui.screen.accessor.FileDropHandler;

@Mixin({ edw.class })
public abstract class MixinScreen implements FileDropHandler, ScreenAccessor
{
    @Shadow
    @Nullable
    protected dyr e;
    @Shadow
    @Final
    private List<ebd> o;
    
    @Shadow
    public abstract void a(final List<Path> p0);
    
    @Insert(method = { "init(Lnet/minecraft/client/Minecraft;II)V" }, at = @At("HEAD"))
    public void fireScreenInitEvent(final dyr mc, final int width, final int height, final InsertInfo ci) {
        Laby.fireEvent(new VersionedScreenInitEvent(this));
    }
    
    @Insert(method = { "renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;I)V" }, at = @At("HEAD"), cancellable = true)
    public void renderThemeBackground(final dtm stack, final int param1, final InsertInfo ci) {
        this.renderThemeBackground(((VanillaStackAccessor)stack).stack(), ci);
    }
    
    @Insert(method = { "renderDirtBackground(I)V" }, at = @At("HEAD"), cancellable = true)
    public void renderThemeDirtBackground(final int param1, final InsertInfo ci) {
        this.renderThemeBackground(Laby.labyAPI().renderPipeline().renderContexts().currentStack(), ci);
    }
    
    private void renderThemeBackground(Stack stack, final InsertInfo ci) {
        final Theme theme = Laby.labyAPI().themeService().currentTheme();
        final BackgroundRenderer backgroundRenderer = theme.getBackgroundRenderer();
        if (backgroundRenderer == null) {
            return;
        }
        stack = ((stack != null) ? stack : ((VanillaStackAccessor)new dtm()).stack());
        if (backgroundRenderer.renderBackground(stack, this)) {
            ci.cancel();
        }
    }
    
    @Insert(method = { "renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;I)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;fillGradient(Lcom/mojang/blaze3d/vertex/PoseStack;IIIIII)V", shift = At.Shift.BEFORE), cancellable = true)
    private void labyMod$disableMenuBackground(final dtm poseStack, final int vOffset, final InsertInfo ci) {
        if (Laby.labyAPI().config().appearance().hideMenuBackground().get()) {
            ci.cancel();
        }
    }
    
    @Override
    public void renderComponentHoverEffect(final Stack stack, final Style style, final int x, final int y) {
        this.shadow$a((dtm)stack.getProvider().getPoseStack(), (qu)style, x, y);
    }
    
    @Shadow
    protected abstract void shadow$a(final dtm p0, @Nullable final qu p1, final int p2, final int p3);
    
    @Override
    public void setMinecraft(final dyr minecraft) {
        this.e = minecraft;
    }
    
    @Override
    public boolean handleDroppedFiles(final MutableMouse mouse, final List<Path> paths) {
        this.a(paths);
        return false;
    }
    
    @Override
    public List<Object> getWrappedWidgets() {
        final List<Object> wrappedWidgets = new ArrayList<Object>();
        for (final ebd child : this.o) {
            wrappedWidgets.add(this.asVersionedWidget(child));
        }
        return wrappedWidgets;
    }
    
    private VersionedWidget asVersionedWidget(final ebd child) {
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
        if (child instanceof final ebc container) {
            for (final ebd entry : container.i()) {
                versionedWidget.addChild(this.asVersionedWidget(entry));
            }
        }
        return versionedWidget;
    }
}
