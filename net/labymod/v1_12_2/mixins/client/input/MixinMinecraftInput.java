// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.mixins.client.input;

import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.event.client.input.MouseScrollEvent;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.client.gui.MouseUser;
import net.labymod.api.event.client.input.MouseDragEvent;
import java.util.List;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.Minecraft;
import org.lwjgl.input.Mouse;
import java.util.Iterator;
import net.labymod.api.event.client.input.CharacterTypedEvent;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.KeyboardUser;
import net.labymod.api.event.client.gui.screen.tree.ScreenPhase;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import net.labymod.api.Laby;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import org.lwjgl.input.Keyboard;
import net.labymod.v1_12_2.client.gui.screen.LabyScreenRenderer;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import net.labymod.v1_12_2.client.VersionedMinecraft;

@Mixin({ bib.class })
public abstract class MixinMinecraftInput implements VersionedMinecraft
{
    @Shadow
    @Nullable
    public blk m;
    private ScreenTreeTopRegistry treeTopRegistry;
    private MouseButton labyMod$currentEventButton;
    private long labyMod$lastMouseEvent;
    
    @Override
    public boolean dispatchKeyboardInput(final LabyScreenRenderer screenRenderer) {
        final boolean press = Keyboard.getEventKeyState();
        final int eventKey = Keyboard.getEventKey();
        final boolean alreadyPressed = press && DefaultKeyMapper.isKeyPressed(eventKey);
        boolean multiChar = false;
        Key key;
        if (!press && eventKey == 0 && Keyboard.getEventCharacter() != '\0') {
            key = Key.NONE;
            DefaultKeyMapper.setLastPressed(Key.NONE);
            multiChar = true;
        }
        else if (press) {
            key = DefaultKeyMapper.pressKey(eventKey);
            DefaultKeyMapper.setLastPressed(key);
        }
        else {
            key = DefaultKeyMapper.releaseKey(eventKey);
            DefaultKeyMapper.setLastReleased(key);
        }
        if (key == null) {
            return false;
        }
        final InputType inputType = KeyMapper.getInputType(key);
        if (key != Key.NONE) {
            KeyEvent.State state;
            if (alreadyPressed) {
                state = KeyEvent.State.HOLDING;
            }
            else if (press) {
                state = KeyEvent.State.PRESS;
            }
            else {
                state = KeyEvent.State.UNPRESSED;
            }
            final KeyEvent keyEvent = Laby.fireEvent(new KeyEvent(state, key));
            if (keyEvent.isCancelled()) {
                return true;
            }
        }
        boolean result = false;
        if (key != Key.ESCAPE && key != Key.NONE) {
            for (final IngameOverlayActivity activity : this.labyMod$getActivities()) {
                if (!activity.isAcceptingInput()) {
                    continue;
                }
                if (press) {
                    this.labyMod$treeTopRegistry().keyPressed(ScreenPhase.PRE, activity, key, inputType);
                    if (activity.keyPressed(key, inputType)) {
                        result = true;
                    }
                    this.labyMod$treeTopRegistry().keyPressed(ScreenPhase.POST, activity, key, inputType);
                }
                else {
                    this.labyMod$treeTopRegistry().keyReleased(ScreenPhase.PRE, activity, key, inputType);
                    if (activity.keyReleased(key, inputType)) {
                        result = true;
                    }
                    this.labyMod$treeTopRegistry().keyReleased(ScreenPhase.POST, activity, key, inputType);
                }
            }
            if (result) {
                return true;
            }
        }
        if (screenRenderer != null && key != Key.NONE) {
            if (press) {
                result = screenRenderer.wrappedKeyPressed(key, inputType);
            }
            else {
                result = screenRenderer.wrappedKeyReleased(key, inputType);
            }
        }
        if (result) {
            return true;
        }
        if ((!press && !multiChar) || inputType != InputType.CHARACTER) {
            return false;
        }
        final char character = Keyboard.getEventCharacter();
        if (character == '\0') {
            return false;
        }
        if (Laby.fireEvent(new CharacterTypedEvent(key, character)).isCancelled()) {
            return true;
        }
        for (final IngameOverlayActivity activity2 : this.labyMod$getActivities()) {
            if (!activity2.isAcceptingInput()) {
                continue;
            }
            this.labyMod$treeTopRegistry().charTyped(ScreenPhase.PRE, activity2, key, character);
            if (activity2.charTyped(key, character)) {
                result = true;
            }
            this.labyMod$treeTopRegistry().charTyped(ScreenPhase.POST, activity2, key, character);
        }
        if (result) {
            return true;
        }
        if (screenRenderer != null) {
            result = screenRenderer.wrappedCharTyped(key, character);
        }
        return result;
    }
    
    @Override
    public boolean dispatchMouseInput() {
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        final MutableMouse mouse = minecraft.absoluteMouse().mutable();
        final Window window = minecraft.minecraftWindow();
        final int width = window.getScaledWidth();
        final int height = window.getScaledHeight();
        final double widthFactor = width / (double)window.getRawWidth();
        final double heightFactor = height / (double)window.getRawHeight();
        final int rawMouseX = Mouse.getEventX();
        final int rawMouseY = Mouse.getEventY();
        final double mouseX = rawMouseX * widthFactor;
        final double mouseY = height - rawMouseY * heightFactor;
        return mouse.set(mouseX, mouseY, () -> {
            List<IngameOverlayActivity> activities = null;
            final int scrollDelta = Mouse.getEventDWheel();
            if (scrollDelta != 0) {
                activities = this.labyMod$getActivities();
                if (this.labyMod$handleMouseScroll(scrollDelta, mouse, activities)) {
                    return true;
                }
            }
            final int button = Mouse.getEventButton();
            KeyEvent.State state = Mouse.getEventButtonState() ? KeyEvent.State.PRESS : KeyEvent.State.UNPRESSED;
            MouseButton mouseButton = null;
            if (button == -1) {
                if (this.labyMod$currentEventButton == null) {
                    return false;
                }
                else {
                    state = KeyEvent.State.HOLDING;
                    mouseButton = this.labyMod$currentEventButton;
                }
            }
            if (mouseButton == null) {
                if (state != KeyEvent.State.UNPRESSED || this.m instanceof LabyScreenRenderer) {
                    mouseButton = DefaultKeyMapper.pressMouse(button);
                }
                else {
                    mouseButton = DefaultKeyMapper.releaseMouse(button);
                }
                this.labyMod$currentEventButton = mouseButton;
            }
            if (mouseButton == null) {
                return false;
            }
            else {
                if (activities == null) {
                    activities = this.labyMod$getActivities();
                }
                if (state == KeyEvent.State.PRESS) {
                    return this.labyMod$handleMousePressed(mouse, mouseButton, activities);
                }
                else if (state == KeyEvent.State.UNPRESSED) {
                    return this.labyMod$handleMouseReleased(mouse, mouseButton, activities);
                }
                else {
                    if (this.labyMod$lastMouseEvent <= 0L) {
                        this.labyMod$currentEventButton = null;
                    }
                    return false;
                }
            }
        });
    }
    
    @Override
    public boolean handleMouseDragged(final MutableMouse mouse, final MouseButton mouseButton, final double deltaX, final double deltaY) {
        return this.labyMod$handleMouseDragged(mouse, mouseButton, this.labyMod$getActivities(), deltaX, deltaY);
    }
    
    private boolean labyMod$handleMouseDragged(final MutableMouse mouse, final MouseButton mouseButton, final List<IngameOverlayActivity> activities, final double deltaX, final double deltaY) {
        if (Laby.fireEvent(new MouseDragEvent(mouse, mouseButton, deltaX, deltaY)).isCancelled()) {
            return true;
        }
        boolean result = false;
        for (final IngameOverlayActivity activity : activities) {
            if (!activity.isAcceptingInput()) {
                continue;
            }
            this.labyMod$treeTopRegistry().mouseDragged(ScreenPhase.PRE, activity, mouse, mouseButton, deltaX, deltaY);
            if (activity.mouseDragged(mouse, mouseButton, deltaX, deltaY)) {
                result = true;
            }
            this.labyMod$treeTopRegistry().mouseDragged(ScreenPhase.POST, activity, mouse, mouseButton, deltaX, deltaY);
        }
        return result;
    }
    
    private boolean labyMod$handleMouseReleased(final MutableMouse mouse, final MouseButton mouseButton, final List<IngameOverlayActivity> activities) {
        this.labyMod$currentEventButton = null;
        if (this.labyMod$fireMouseButtonEvent(MouseButtonEvent.Action.RELEASE, mouse, mouseButton)) {
            return true;
        }
        boolean result = false;
        for (final IngameOverlayActivity activity : activities) {
            if (!activity.isAcceptingInput()) {
                continue;
            }
            this.labyMod$treeTopRegistry().mouseReleased(ScreenPhase.PRE, activity, mouse, mouseButton);
            if (activity.mouseReleased(mouse, mouseButton)) {
                result = true;
            }
            this.labyMod$treeTopRegistry().mouseReleased(ScreenPhase.POST, activity, mouse, mouseButton);
        }
        return result;
    }
    
    private boolean labyMod$handleMousePressed(final MutableMouse mouse, final MouseButton mouseButton, final List<IngameOverlayActivity> activities) {
        this.labyMod$lastMouseEvent = bib.I();
        if (this.labyMod$fireMouseButtonEvent(MouseButtonEvent.Action.CLICK, mouse, mouseButton)) {
            return true;
        }
        boolean result = false;
        for (final IngameOverlayActivity activity : activities) {
            if (!activity.isAcceptingInput()) {
                continue;
            }
            this.labyMod$treeTopRegistry().mouseClicked(ScreenPhase.PRE, activity, mouse, mouseButton);
            if (activity.mouseClicked(mouse, mouseButton)) {
                result = true;
            }
            this.labyMod$treeTopRegistry().mouseClicked(ScreenPhase.POST, activity, mouse, mouseButton);
        }
        return result;
    }
    
    private boolean labyMod$handleMouseScroll(int scrollDelta, final MutableMouse mouse, final List<IngameOverlayActivity> activities) {
        scrollDelta = ((scrollDelta < 0) ? -1 : 1);
        if (Laby.fireEvent(new MouseScrollEvent(mouse, (float)scrollDelta)).isCancelled()) {
            return true;
        }
        boolean result = false;
        for (final Activity activity : activities) {
            if (!result) {
                this.labyMod$treeTopRegistry().mouseScrolled(ScreenPhase.PRE, activity, mouse, scrollDelta);
                if (activity.mouseScrolled(mouse, scrollDelta)) {
                    result = true;
                }
                this.labyMod$treeTopRegistry().mouseScrolled(ScreenPhase.POST, activity, mouse, scrollDelta);
            }
        }
        return result;
    }
    
    private boolean labyMod$fireMouseButtonEvent(final MouseButtonEvent.Action action, final MutableMouse mouse, final MouseButton mouseButton) {
        return Laby.fireEvent(new MouseButtonEvent(action, mouse, mouseButton)).isCancelled();
    }
    
    private List<IngameOverlayActivity> labyMod$getActivities() {
        return Laby.labyAPI().ingameOverlay().getActivities();
    }
    
    private ScreenTreeTopRegistry labyMod$treeTopRegistry() {
        return Laby.references().screenTreeTopRegistry();
    }
    
    @Override
    public MouseButton getCurrentEventButton() {
        return this.labyMod$currentEventButton;
    }
}
