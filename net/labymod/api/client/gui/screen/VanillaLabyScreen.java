// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.client.gui.screen.FileDroppedEvent;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.KeyboardUser;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.MouseUser;
import net.labymod.api.event.client.gui.screen.tree.ScreenPhase;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.Laby;
import net.labymod.api.util.math.vector.FloatVector2;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;

public class VanillaLabyScreen
{
    private static final ScreenTreeTopRegistry TREE_TOP_REGISTRY;
    private static final ScreenCustomFontStack SCREEN_CUSTOM_FONT_STACK;
    private final LabyScreen screen;
    private ScreenWrapper parentScreen;
    private Point dragStart;
    private MouseButton dragButton;
    private MouseDraggedHandler mouseDraggedHandler;
    
    public VanillaLabyScreen(final LabyScreen screen) {
        this.mouseDraggedHandler = (mouse -> new FloatVector2());
        this.screen = screen;
    }
    
    public void init(final int width, final int height) {
        this.screen.resize(width, height);
        this.screen.load(Laby.labyAPI().minecraft().minecraftWindow());
    }
    
    public void tick() {
        try {
            this.screen.tick();
        }
        catch (final Exception e) {
            e.printStackTrace();
        }
    }
    
    public void onCloseScreen() {
        this.screen.onCloseScreen();
    }
    
    public void render(final ScreenContext context) {
        this.screen.render(context);
        if (this.isFirstInTree()) {
            this.screen.renderOverlay(context);
            this.screen.renderHoverComponent(context);
        }
        final MutableMouse mouse = context.mouse();
        if (this.dragStart != null && !this.dragStart.matches(mouse)) {
            final FloatVector2 deltaMousePosition = this.mouseDraggedHandler.getDeltaMousePosition(mouse);
            this.mouseDragged(mouse, this.dragButton, deltaMousePosition.getX(), deltaMousePosition.getY());
        }
    }
    
    public boolean renderBackground(final ScreenContext context) {
        return this.screen.renderBackground(context);
    }
    
    public void resize(final int width, final int height) {
        this.screen.resize(width, height);
    }
    
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        final boolean firstInTree = this.isFirstInTree();
        if (firstInTree) {
            this.pushCustomFontStack();
            VanillaLabyScreen.TREE_TOP_REGISTRY.mouseClicked(ScreenPhase.PRE, this.screen, mouse, mouseButton);
            this.dragStart = mouse.copy();
            this.dragButton = mouseButton;
        }
        final boolean result = this.screen.mouseClicked(mouse, mouseButton);
        if (firstInTree) {
            VanillaLabyScreen.TREE_TOP_REGISTRY.mouseClicked(ScreenPhase.POST, this.screen, mouse, mouseButton);
            this.popCustomFontStack();
        }
        return result;
    }
    
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        final boolean firstInTree = this.isFirstInTree();
        if (firstInTree) {
            this.pushCustomFontStack();
            VanillaLabyScreen.TREE_TOP_REGISTRY.mouseReleased(ScreenPhase.PRE, this.screen, mouse, mouseButton);
            this.dragStart = null;
            this.dragButton = null;
        }
        final boolean result = this.screen.mouseReleased(mouse, mouseButton);
        if (firstInTree) {
            VanillaLabyScreen.TREE_TOP_REGISTRY.mouseReleased(ScreenPhase.POST, this.screen, mouse, mouseButton);
            this.popCustomFontStack();
        }
        return result;
    }
    
    public boolean mouseScrolled(final MutableMouse mouse, final double scroll) {
        final boolean firstInTree = this.isFirstInTree();
        if (firstInTree) {
            this.pushCustomFontStack();
            VanillaLabyScreen.TREE_TOP_REGISTRY.mouseScrolled(ScreenPhase.PRE, this.screen, mouse, scroll);
        }
        final boolean result = this.screen.mouseScrolled(mouse, scroll);
        if (firstInTree) {
            VanillaLabyScreen.TREE_TOP_REGISTRY.mouseScrolled(ScreenPhase.POST, this.screen, mouse, scroll);
            this.popCustomFontStack();
        }
        return result;
    }
    
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton mouseButton, final double deltaX, final double deltaY) {
        final boolean firstInTree = this.isFirstInTree();
        if (firstInTree) {
            this.pushCustomFontStack();
            VanillaLabyScreen.TREE_TOP_REGISTRY.mouseDragged(ScreenPhase.PRE, this.screen, mouse, mouseButton, deltaX, deltaY);
        }
        final boolean result = this.screen.mouseDragged(mouse, mouseButton, deltaX, deltaY);
        if (firstInTree) {
            VanillaLabyScreen.TREE_TOP_REGISTRY.mouseDragged(ScreenPhase.POST, this.screen, mouse, mouseButton, deltaX, deltaY);
            this.popCustomFontStack();
        }
        return result;
    }
    
    public boolean charTyped(final Key key, final char character) {
        final boolean firstInTree = this.isFirstInTree();
        if (firstInTree) {
            this.pushCustomFontStack();
            VanillaLabyScreen.TREE_TOP_REGISTRY.charTyped(ScreenPhase.PRE, this.screen, key, character);
        }
        final boolean result = this.screen.charTyped(key, character);
        if (firstInTree) {
            VanillaLabyScreen.TREE_TOP_REGISTRY.charTyped(ScreenPhase.POST, this.screen, key, character);
            this.popCustomFontStack();
        }
        return result;
    }
    
    public boolean keyPressed(final Key key, final InputType type) {
        final boolean firstInTree = this.isFirstInTree();
        if (firstInTree) {
            this.pushCustomFontStack();
            VanillaLabyScreen.TREE_TOP_REGISTRY.keyPressed(ScreenPhase.PRE, this.screen, key, type);
        }
        final boolean result = this.screen.keyPressed(key, type);
        if (firstInTree) {
            VanillaLabyScreen.TREE_TOP_REGISTRY.keyPressed(ScreenPhase.POST, this.screen, key, type);
            this.popCustomFontStack();
        }
        return result;
    }
    
    public boolean keyReleased(final Key key, final InputType type) {
        final boolean firstInTree = this.isFirstInTree();
        if (firstInTree) {
            this.pushCustomFontStack();
            VanillaLabyScreen.TREE_TOP_REGISTRY.keyReleased(ScreenPhase.PRE, this.screen, key, type);
        }
        final boolean result = this.screen.keyReleased(key, type);
        if (firstInTree) {
            VanillaLabyScreen.TREE_TOP_REGISTRY.keyReleased(ScreenPhase.POST, this.screen, key, type);
            this.popCustomFontStack();
        }
        return result;
    }
    
    public boolean fileDropped(final MutableMouse mouse, final List<Path> files) {
        final FileDroppedEvent event = Laby.fireEvent(new FileDroppedEvent(mouse, files));
        if (event.isCancelled()) {
            return true;
        }
        if (this.isFirstInTree()) {
            VanillaLabyScreen.TREE_TOP_REGISTRY.fileDropped(ScreenPhase.PRE, this.screen, event.mouse(), event.paths());
        }
        final boolean result = this.screen.fileDropped(event.mouse(), event.paths());
        if (this.isFirstInTree()) {
            VanillaLabyScreen.TREE_TOP_REGISTRY.fileDropped(ScreenPhase.POST, this.screen, event.mouse(), event.paths());
        }
        return result;
    }
    
    public boolean isFirstInTree() {
        return this.parentScreen == null;
    }
    
    public void setParentScreen(final ScreenWrapper parentScreen) {
        this.parentScreen = parentScreen;
    }
    
    public void pushCustomFontStack() {
        VanillaLabyScreen.SCREEN_CUSTOM_FONT_STACK.push(this.screen);
    }
    
    public void popCustomFontStack() {
        VanillaLabyScreen.SCREEN_CUSTOM_FONT_STACK.pop(this.screen);
    }
    
    public LabyScreen screen() {
        return this.screen;
    }
    
    public void setMouseDraggedHandler(@NotNull final MouseDraggedHandler mouseDraggedHandler) {
        Objects.requireNonNull(mouseDraggedHandler, "mouseDraggedHandler must not be null");
        this.mouseDraggedHandler = mouseDraggedHandler;
    }
    
    @NotNull
    public MouseDraggedHandler mouseDraggedHandler() {
        return this.mouseDraggedHandler;
    }
    
    static {
        TREE_TOP_REGISTRY = Laby.references().screenTreeTopRegistry();
        SCREEN_CUSTOM_FONT_STACK = Laby.references().screenCustomFontStack();
    }
    
    public interface MouseDraggedHandler
    {
        FloatVector2 getDeltaMousePosition(final Mouse p0);
    }
}
