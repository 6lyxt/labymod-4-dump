// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.gui.screen;

import net.labymod.api.client.gui.screen.ScreenWrapper;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.matrix.Stack;
import java.util.Objects;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import org.jetbrains.annotations.NotNull;
import net.labymod.core.client.gui.screen.ModernMouseDraggedHandler;
import net.labymod.api.client.component.Component;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.VanillaLabyScreen;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.core.client.gui.screen.accessor.FileDropHandler;

public class LabyScreenRenderer extends fod implements FileDropHandler, LabyScreenAccessor
{
    private final VanillaLabyScreen screen;
    
    public LabyScreenRenderer(final LabyScreen screen) {
        super((wz)Laby.labyAPI().minecraft().componentMapper().toMinecraftComponent(Component.empty()));
        (this.screen = new VanillaLabyScreen(screen)).setMouseDraggedHandler(new ModernMouseDraggedHandler());
    }
    
    protected void aT_() {
        super.aT_();
        this.screen.pushCustomFontStack();
        this.p();
        this.screen.init(this.m, this.n);
        this.screen.popCustomFontStack();
    }
    
    public void e() {
        this.screen.pushCustomFontStack();
        super.e();
        this.screen.tick();
        this.screen.popCustomFontStack();
    }
    
    public void j() {
        super.j();
        this.screen.onCloseScreen();
    }
    
    public void a(@NotNull final fhz graphics, final int mouseX, final int mouseY, final float tickDelta) {
        this.screen.pushCustomFontStack();
        super.a(graphics, mouseX, mouseY, tickDelta);
        final Stack stack = ((VanillaStackAccessor)graphics.c()).stack();
        final ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        screenContext.runInContext(stack, mouseX, mouseY, tickDelta, context -> {
            Objects.requireNonNull(graphics);
            context.setFlushGraphicsAction(graphics::e);
            this.screen.render(context);
            return;
        });
        this.l.aO().c().b();
        this.screen.popCustomFontStack();
    }
    
    public void b(final fhz graphics, final int mouseX, final int mouseY, final float tickDelta) {
        final Stack stack = ((VanillaStackAccessor)graphics.c()).stack();
        final MutableMouse mouse = new MutableMouse(mouseX, mouseY);
        final ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        final boolean renderBackground = screenContext.runInContextWithState(stack, mouse, tickDelta, context -> {
            Objects.requireNonNull(graphics);
            context.setFlushGraphicsAction(graphics::e);
            return Boolean.valueOf(this.screen.renderBackground(context));
        });
        if (renderBackground) {
            return;
        }
        super.b(graphics, mouseX, mouseY, tickDelta);
    }
    
    public void a(@NotNull final fgo mc, final int width, final int height) {
        this.screen.pushCustomFontStack();
        super.a(mc, width, height);
        this.screen.resize(width, height);
        this.screen.popCustomFontStack();
    }
    
    public boolean a(final int keyCode, final int value1, final int value2) {
        final Key key = DefaultKeyMapper.lastPressed();
        final InputType type = KeyMapper.getInputType(key);
        return this.screen.keyPressed(key, type);
    }
    
    public boolean c(final int keyCode, final int value1, final int value2) {
        final Key key = DefaultKeyMapper.lastReleased();
        final InputType type = KeyMapper.getInputType(key);
        return this.screen.keyReleased(key, type);
    }
    
    public boolean a(final char character, final int value1) {
        final Key key = DefaultKeyMapper.lastPressed();
        return KeyMapper.getInputType(key) == InputType.CHARACTER && this.screen.charTyped(key, character);
    }
    
    public boolean a(final double mouseX, final double mouseY, final int mouseButton) {
        return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final MouseButton button = DefaultKeyMapper.pressMouse(mouseButton);
            return button != null && this.screen.mouseClicked(mouse, button);
        });
    }
    
    public boolean b(final double mouseX, final double mouseY, final int mouseButton) {
        return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final MouseButton button = DefaultKeyMapper.releaseMouse(mouseButton);
            return button != null && this.screen.mouseReleased(mouse, button);
        });
    }
    
    public boolean a(final double mouseX, final double mouseY, final double scrollDeltaX, final double scrollDeltaY) {
        return Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> this.screen.mouseScrolled(mouse, scrollDeltaY));
    }
    
    public boolean a(final double mouseX, final double mouseY, final int button, final double deltaX, final double deltaY) {
        return !this.screen.isFirstInTree() && Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            final MouseButton mouseButton = DefaultKeyMapper.pressMouse(button);
            return mouseButton != null && this.screen.mouseDragged(mouse, mouseButton, deltaX, deltaY);
        });
    }
    
    public boolean handleDroppedFiles(final MutableMouse mouse, final List<Path> files) {
        return this.screen.fileDropped(mouse, files);
    }
    
    public void a(@NotNull final List<Path> paths) {
        final MutableMouse mouse = Laby.labyAPI().minecraft().mouse();
        this.handleDroppedFiles(mouse, paths);
    }
    
    public boolean k() {
        return this.screen().isPauseScreen();
    }
    
    public LabyScreen screen() {
        return this.screen.screen();
    }
    
    public void setParentScreen(final ScreenWrapper parentScreen) {
        this.screen.setParentScreen(parentScreen);
    }
}
