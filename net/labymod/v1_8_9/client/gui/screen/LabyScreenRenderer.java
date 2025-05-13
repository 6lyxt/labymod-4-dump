// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.gui.screen;

import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.event.client.gui.screen.VersionedScreenInitEvent;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gui.mouse.Mouse;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.LabyAPI;
import java.util.Objects;
import net.labymod.v1_8_9.client.render.matrix.VersionedStackProvider;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.VanillaLabyScreen;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;

public class LabyScreenRenderer extends axu implements LabyScreenRendererAccessor, LabyScreenAccessor
{
    private final VanillaLabyScreen screen;
    
    public LabyScreenRenderer(final LabyScreen screen) {
        (this.screen = new VanillaLabyScreen(screen)).setMouseDraggedHandler(new LegacyMouseDraggedHandler());
    }
    
    public void a(final int mouseX, final int mouseY, final float partialTicks) {
        final float tickDelta = Laby.labyAPI().minecraft().getTickDelta();
        final LabyAPI labyAPI = Laby.labyAPI();
        final Stack stack = VersionedStackProvider.DEFAULT_STACK;
        final Minecraft minecraft = labyAPI.minecraft();
        bfl.a(516, 0.003921569f);
        this.c();
        this.screen.pushCustomFontStack();
        final ScreenContext screenContext2;
        final ScreenContext screenContext = screenContext2 = Laby.references().renderEnvironmentContext().screenContext();
        final Stack stack2 = stack;
        final MutableMouse mouse = minecraft.mouse();
        final float tickDelta2 = tickDelta;
        final VanillaLabyScreen screen = this.screen;
        Objects.requireNonNull(screen);
        screenContext2.runInContext(stack2, mouse, tickDelta2, screen::render);
        this.screen.popCustomFontStack();
    }
    
    public void c() {
        final Stack stack = VersionedStackProvider.DEFAULT_STACK;
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        final MutableMouse mouse = minecraft.mouse();
        final float tickDelta = minecraft.getTickDelta();
        final ScreenContext screenContext2;
        final ScreenContext screenContext = screenContext2 = Laby.references().renderEnvironmentContext().screenContext();
        final Stack stack2 = stack;
        final MutableMouse mouse2 = mouse;
        final float tickDelta2 = tickDelta;
        final VanillaLabyScreen screen = this.screen;
        Objects.requireNonNull(screen);
        final boolean renderBackground = screenContext2.runInContextWithState(stack2, mouse2, tickDelta2, screen::renderBackground);
        if (renderBackground) {
            return;
        }
        super.c();
    }
    
    public void l() {
        this.j.Z();
    }
    
    public boolean wrappedCharTyped(final Key key, final char character) {
        return !key.isAction() && this.screen.charTyped(key, character);
    }
    
    public boolean wrappedKeyPressed(final Key key, final InputType type) {
        return this.screen.keyPressed(key, type);
    }
    
    public boolean wrappedKeyReleased(final Key key, final InputType type) {
        return this.screen.keyReleased(key, type);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton) {
        this.wrappedMouseClicked(Laby.labyAPI().minecraft().mouse(), mouseButton);
    }
    
    public boolean wrappedMouseClicked(final MutableMouse mouse, final int mouseButton) {
        final MouseButton button = DefaultKeyMapper.pressMouse(mouseButton);
        return button != null && this.screen.mouseClicked(mouse, button);
    }
    
    public boolean handleDroppedFiles(final MutableMouse mouse, final List<Path> paths) {
        return this.screen.fileDropped(mouse, paths);
    }
    
    protected void b(final int mouseX, final int mouseY, final int mouseButton) {
        this.wrappedMouseReleased(Laby.labyAPI().minecraft().mouse(), mouseButton);
    }
    
    public boolean wrappedMouseReleased(final MutableMouse mouse, final int mouseButton) {
        final MouseButton button = DefaultKeyMapper.releaseMouse(mouseButton);
        return button != null && this.screen.mouseReleased(mouse, button);
    }
    
    public boolean wrappedMouseClickMove(final MutableMouse mouse, final int mouseButton, final double deltaX, final double deltaY) {
        if (this.screen.isFirstInTree()) {
            return false;
        }
        final MouseButton button = DefaultKeyMapper.pressMouse(mouseButton);
        return button != null && this.screen.mouseDragged(mouse, button, deltaX, deltaY);
    }
    
    protected void a(final int mouseX, final int mouseY, final int mouseButton, final long lastMouseEvent) {
        final VanillaLabyScreen.MouseDraggedHandler mouseDraggedHandler = this.screen.mouseDraggedHandler();
        final MutableMouse mouse = Laby.labyAPI().minecraft().mouse();
        final FloatVector2 deltaMousePosition = mouseDraggedHandler.getDeltaMousePosition(mouse);
        this.wrappedMouseClickMove(mouse, mouseButton, deltaMousePosition.getX(), deltaMousePosition.getY());
    }
    
    public void b() {
        this.n.clear();
        this.screen.pushCustomFontStack();
        this.screen.init(this.l, this.m);
        this.screen.popCustomFontStack();
    }
    
    public void e() {
        this.screen.pushCustomFontStack();
        this.screen.tick();
        this.screen.popCustomFontStack();
    }
    
    public void m() {
        this.screen.onCloseScreen();
    }
    
    public boolean d() {
        return this.screen().isPauseScreen();
    }
    
    public LabyScreen screen() {
        return this.screen.screen();
    }
    
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        return this.screen.mouseScrolled(mouse, Math.max(-1.0, Math.min(1.0, scrollDelta)));
    }
    
    public void a(final ave lvt_1_1_, final int lvt_2_1_, final int lvt_3_1_) {
        super.a(lvt_1_1_, lvt_2_1_, lvt_3_1_);
        final Object versionedScreen = this.screen().mostInnerScreen();
        if (versionedScreen instanceof axu) {
            Laby.fireEvent(new VersionedScreenInitEvent(versionedScreen));
        }
    }
    
    public void setParentScreen(final ScreenWrapper parentScreen) {
        this.screen.setParentScreen(parentScreen);
    }
    
    private static class LegacyMouseDraggedHandler implements VanillaLabyScreen.MouseDraggedHandler
    {
        @Override
        public FloatVector2 getDeltaMousePosition(final Mouse mouse) {
            final Window window = Laby.labyAPI().minecraft().minecraftWindow();
            final float factor = window.getScaledWidth() / (float)window.getRawWidth();
            final float deltaX = org.lwjgl.input.Mouse.getEventDX() * factor;
            final float deltaY = -org.lwjgl.input.Mouse.getEventDY() * factor;
            return new FloatVector2(deltaX, deltaY);
        }
    }
}
