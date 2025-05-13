// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.input;

import net.labymod.api.Laby;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.core.client.gui.screen.key.mapper.DefaultKeyMapper;
import org.lwjgl.input.Mouse;

public final class InputHandler
{
    private InputHandler() {
    }
    
    public static void fireMouseInput(final boolean isScreenContext) {
        if (isScreenContext) {
            return;
        }
        final int keyCode = Mouse.getEventButton();
        if (keyCode == -1) {
            return;
        }
        final boolean state = Mouse.getEventButtonState();
        final boolean alreadyPressed = DefaultKeyMapper.isMousePressed(keyCode);
        if (alreadyPressed && !state) {
            final Key key = DefaultKeyMapper.releaseMouse(keyCode);
            if (key == null) {
                return;
            }
            fireEvent(KeyEvent.State.UNPRESSED, key);
        }
        else {
            if (alreadyPressed || !state) {
                if (alreadyPressed) {
                    final Key key = DefaultKeyMapper.pressMouse(keyCode);
                    if (key == null) {
                        return;
                    }
                    fireEvent(KeyEvent.State.HOLDING, key);
                }
                return;
            }
            final Key key = DefaultKeyMapper.pressMouse(keyCode);
            if (key == null) {
                return;
            }
            fireEvent(KeyEvent.State.PRESS, key);
        }
    }
    
    public static boolean fireEvent(final KeyEvent.State state, final Key key) {
        if (key == null || key == Key.NONE) {
            return false;
        }
        if (key instanceof MouseButton) {
            MouseButtonEvent.Action action = null;
            switch (state) {
                case UNPRESSED: {
                    action = MouseButtonEvent.Action.RELEASE;
                    break;
                }
                case HOLDING:
                case PRESS: {
                    action = MouseButtonEvent.Action.CLICK;
                    break;
                }
            }
            if (action != null) {
                final MouseButtonEvent event = new MouseButtonEvent(action, Laby.labyAPI().minecraft().absoluteMouse().mutable(), (MouseButton)key);
                if (Laby.fireEvent(event).isCancelled()) {
                    return true;
                }
            }
        }
        return Laby.fireEvent(new KeyEvent(state, key)).isCancelled();
    }
}
