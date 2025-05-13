// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.input;

import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.Minecraft;
import net.labymod.api.event.client.input.MouseDragEvent;
import java.util.function.Consumer;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.event.client.input.MouseScrollEvent;
import java.util.Iterator;
import net.labymod.api.client.gui.MouseUser;
import net.labymod.api.event.client.gui.screen.tree.ScreenPhase;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.Laby;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import javax.inject.Inject;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;
import net.labymod.api.LabyAPI;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public final class MouseBridge
{
    private final LabyAPI labyAPI;
    private final ScreenTreeTopRegistry treeTopRegistry;
    private final MutableMouse mouse;
    private final MutableMouse absoluteMouse;
    private final MutableMouse previousMouse;
    
    @Inject
    public MouseBridge(final LabyAPI labyAPI, final ScreenTreeTopRegistry treeTopRegistry) {
        this.mouse = new MutableMouse();
        this.absoluteMouse = new MutableMouse();
        this.previousMouse = new MutableMouse();
        this.labyAPI = labyAPI;
        this.treeTopRegistry = treeTopRegistry;
    }
    
    public boolean onPress(final MouseButton mouseButton, final KeyEvent.State state) {
        MouseButtonEvent.Action mouseAction = null;
        switch (state) {
            case UNPRESSED: {
                mouseAction = MouseButtonEvent.Action.RELEASE;
                break;
            }
            case PRESS: {
                mouseAction = MouseButtonEvent.Action.CLICK;
                break;
            }
        }
        if (mouseAction != null && Laby.fireEvent(new MouseButtonEvent(mouseAction, this.copyAbsoluteMouse(), mouseButton)).isCancelled()) {
            return true;
        }
        Laby.fireEvent(new KeyEvent(state, mouseButton));
        return false;
    }
    
    public boolean onDrag(final MutableMouse mouse, final MouseButton mouseButton, final double deltaX, final double deltaY) {
        boolean shouldCancel = false;
        for (final IngameOverlayActivity activity : this.labyAPI.ingameOverlay().getActivities()) {
            if (!activity.isAcceptingInput()) {
                continue;
            }
            this.treeTopRegistry.mouseDragged(ScreenPhase.PRE, activity, mouse, mouseButton, deltaX, deltaY);
            if (activity.mouseDragged(mouse, mouseButton, deltaX, deltaY)) {
                shouldCancel = true;
            }
            this.treeTopRegistry.mouseDragged(ScreenPhase.POST, activity, mouse, mouseButton, deltaX, deltaY);
        }
        return shouldCancel;
    }
    
    public boolean onPressInGameOverlay(final MouseButton mouseButton, final KeyEvent.State state) {
        if (state == KeyEvent.State.HOLDING) {
            return false;
        }
        boolean shouldCancel = false;
        final MutableMouse mouse = this.copyAbsoluteMouse();
        for (final IngameOverlayActivity activity : this.labyAPI.ingameOverlay().getActivities()) {
            if (!activity.isAcceptingInput()) {
                continue;
            }
            if (state == KeyEvent.State.PRESS) {
                this.treeTopRegistry.mouseClicked(ScreenPhase.PRE, activity, mouse, mouseButton);
                if (activity.mouseClicked(mouse, mouseButton)) {
                    shouldCancel = true;
                }
                this.treeTopRegistry.mouseClicked(ScreenPhase.POST, activity, mouse, mouseButton);
            }
            else {
                if (state != KeyEvent.State.UNPRESSED) {
                    continue;
                }
                this.treeTopRegistry.mouseReleased(ScreenPhase.PRE, activity, mouse, mouseButton);
                if (activity.mouseReleased(mouse, mouseButton)) {
                    shouldCancel = true;
                }
                this.treeTopRegistry.mouseReleased(ScreenPhase.POST, activity, mouse, mouseButton);
            }
        }
        return shouldCancel;
    }
    
    public boolean onScroll(final double scrollDelta, final boolean cancelIngameInteraction) {
        final MutableMouse mouse = this.copyAbsoluteMouse();
        if (Laby.fireEvent(new MouseScrollEvent(mouse, (float)scrollDelta)).isCancelled()) {
            return true;
        }
        if (cancelIngameInteraction) {
            return false;
        }
        boolean shouldCancel = false;
        for (final IngameOverlayActivity activity : this.labyAPI.ingameOverlay().getActivities()) {
            if (activity.isAcceptingInput()) {
                this.treeTopRegistry.mouseScrolled(ScreenPhase.PRE, activity, mouse, scrollDelta);
                if (activity.mouseScrolled(mouse, scrollDelta)) {
                    shouldCancel = true;
                }
                this.treeTopRegistry.mouseScrolled(ScreenPhase.POST, activity, mouse, scrollDelta);
            }
        }
        return shouldCancel;
    }
    
    private MutableMouse copyAbsoluteMouse() {
        return this.absoluteMouse.mutable();
    }
    
    public MutableMouse mouse() {
        return this.mouse;
    }
    
    public MutableMouse absoluteMouse() {
        return this.absoluteMouse;
    }
    
    public void updateMouse(final double x, final double y) {
        this.previousMouse.set(this.mouse.getX(), this.mouse.getY());
        this.mouse.set(x, y);
        this.absoluteMouse.set(x, y);
    }
    
    public boolean handleMouseEvent(final int keyCode, final int action) {
        KeyEvent.State state = null;
        switch (action) {
            case 1: {
                state = KeyEvent.State.PRESS;
                break;
            }
            case 2: {
                state = KeyEvent.State.HOLDING;
                break;
            }
            default: {
                state = KeyEvent.State.UNPRESSED;
                break;
            }
        }
        MouseButton mouseButton;
        if (state != KeyEvent.State.UNPRESSED || this.labyAPI.minecraft().minecraftWindow().getCurrentVersionedScreen() instanceof LabyScreenAccessor) {
            mouseButton = DefaultKeyMapper.pressMouse(keyCode);
        }
        else {
            mouseButton = DefaultKeyMapper.releaseMouse(keyCode);
        }
        return mouseButton != null && (this.onPress(mouseButton, state) || (!this.cancelInGameInteraction() && this.onPressInGameOverlay(mouseButton, state)));
    }
    
    public void handleMouseMove(final int keyCode, final double mousePressedTime, final double mouseXUnsafe, final double mouseYUnsafe, final double prevMouseXUnsafe, final double prevMouseYUnsafe, final Consumer<Boolean> runnable) {
        if (keyCode == -1 || mousePressedTime <= 0.0) {
            return;
        }
        final Minecraft minecraft = this.labyAPI.minecraft();
        final Window window = minecraft.minecraftWindow();
        final double mouseX = this.absoluteMouse.getX() * window.getScale();
        final double mouseY = this.absoluteMouse.getY() * window.getScale();
        final double widthFactor = window.getAbsoluteScaledWidth() / (double)window.getRawWidth();
        final double heightFactor = window.getAbsoluteScaledHeight() / (double)window.getRawHeight();
        final double scaledX = mouseX * widthFactor;
        final double scaledY = mouseY * heightFactor;
        final double deltaX = (mouseX - this.previousMouse.getXDouble()) * widthFactor;
        final double deltaY = (mouseY - this.previousMouse.getYDouble()) * heightFactor;
        final MouseButton mouseButton = DefaultKeyMapper.pressMouse(keyCode);
        if (mouseButton == null) {
            return;
        }
        if (Laby.fireEvent(new MouseDragEvent(this.mouse, mouseButton, deltaX, deltaY)).isCancelled()) {
            return;
        }
        minecraft.updateMouse(scaledX, scaledY, mouse -> runnable.accept(this.onDrag(mouse, mouseButton, deltaX, deltaY)));
    }
    
    public boolean handleMouseScroll(final double delta) {
        return this.onScroll(delta, this.cancelInGameInteraction());
    }
    
    private boolean cancelInGameInteraction() {
        return !Laby.labyAPI().minecraft().isIngame() || this.labyAPI.minecraft().minecraftWindow().getCurrentVersionedScreen() == null;
    }
    
    public MutableMouse previousMouse() {
        return this.previousMouse;
    }
}
