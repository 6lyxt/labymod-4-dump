// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.tree;

import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.KeyboardUser;
import java.nio.file.Path;
import java.util.List;
import java.util.Iterator;
import net.labymod.api.util.KeyValue;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.MouseUser;
import net.labymod.api.event.client.gui.screen.tree.ScreenPhase;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopHandler;
import net.labymod.api.service.DefaultRegistry;

@Singleton
@Implements(ScreenTreeTopRegistry.class)
public class DefaultScreenTreeTopRegistry extends DefaultRegistry<ScreenTreeTopHandler> implements ScreenTreeTopRegistry
{
    @Inject
    public DefaultScreenTreeTopRegistry() {
    }
    
    @Override
    public void mouseClicked(final ScreenPhase phase, final MouseUser screen, final MutableMouse mouse, final MouseButton mouseButton) {
        for (final KeyValue<ScreenTreeTopHandler> e : this.getElements()) {
            e.getValue().mouseClicked(phase, screen, mouse, mouseButton);
        }
    }
    
    @Override
    public void mouseReleased(final ScreenPhase phase, final MouseUser screen, final MutableMouse mouse, final MouseButton mouseButton) {
        for (final KeyValue<ScreenTreeTopHandler> e : this.getElements()) {
            e.getValue().mouseReleased(phase, screen, mouse, mouseButton);
        }
    }
    
    @Override
    public void mouseScrolled(final ScreenPhase phase, final MouseUser screen, final MutableMouse mouse, final double scrollDelta) {
        for (final KeyValue<ScreenTreeTopHandler> e : this.getElements()) {
            e.getValue().mouseScrolled(phase, screen, mouse, scrollDelta);
        }
    }
    
    @Override
    public void mouseDragged(final ScreenPhase phase, final MouseUser screen, final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        for (final KeyValue<ScreenTreeTopHandler> e : this.getElements()) {
            e.getValue().mouseDragged(phase, screen, mouse, button, deltaX, deltaY);
        }
    }
    
    @Override
    public void fileDropped(final ScreenPhase phase, final MouseUser screen, final MutableMouse mouse, final List<Path> paths) {
        for (final KeyValue<ScreenTreeTopHandler> e : this.getElements()) {
            e.getValue().fileDropped(phase, screen, mouse, paths);
        }
    }
    
    @Override
    public void keyPressed(final ScreenPhase phase, final KeyboardUser screen, final Key key, final InputType type) {
        for (final KeyValue<ScreenTreeTopHandler> e : this.getElements()) {
            e.getValue().keyPressed(phase, screen, key, type);
        }
    }
    
    @Override
    public void keyReleased(final ScreenPhase phase, final KeyboardUser screen, final Key key, final InputType type) {
        for (final KeyValue<ScreenTreeTopHandler> e : this.getElements()) {
            e.getValue().keyReleased(phase, screen, key, type);
        }
    }
    
    @Override
    public void charTyped(final ScreenPhase phase, final KeyboardUser screen, final Key key, final char character) {
        for (final KeyValue<ScreenTreeTopHandler> e : this.getElements()) {
            e.getValue().charTyped(phase, screen, key, character);
        }
    }
}
