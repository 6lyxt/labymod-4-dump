// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen.tree;

import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.KeyboardUser;
import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.MouseUser;

public interface ScreenTreeTopHandler
{
    default void mouseClicked(final ScreenPhase phase, final MouseUser screen, final MutableMouse mouse, final MouseButton mouseButton) {
    }
    
    default void mouseReleased(final ScreenPhase phase, final MouseUser screen, final MutableMouse mouse, final MouseButton mouseButton) {
    }
    
    default void mouseScrolled(final ScreenPhase phase, final MouseUser screen, final MutableMouse mouse, final double scrollDelta) {
    }
    
    default void mouseDragged(final ScreenPhase phase, final MouseUser screen, final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
    }
    
    default void fileDropped(final ScreenPhase phase, final MouseUser screen, final MutableMouse mouse, final List<Path> paths) {
    }
    
    default void keyPressed(final ScreenPhase phase, final KeyboardUser screen, final Key key, final InputType type) {
    }
    
    default void keyReleased(final ScreenPhase phase, final KeyboardUser screen, final Key key, final InputType type) {
    }
    
    default void charTyped(final ScreenPhase phase, final KeyboardUser screen, final Key key, final char character) {
    }
}
