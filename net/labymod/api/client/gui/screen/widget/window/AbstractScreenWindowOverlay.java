// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.window;

import java.util.Map;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import java.nio.file.Path;
import net.labymod.api.util.bounds.Point;
import java.util.Iterator;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import java.util.ArrayList;
import net.labymod.api.Laby;
import java.util.List;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.gui.KeyboardUser;
import net.labymod.api.client.gui.MouseUser;
import net.labymod.api.client.gui.ScreenUser;

@Deprecated
public abstract class AbstractScreenWindowOverlay implements ScreenUser, MouseUser, KeyboardUser
{
    protected final LabyAPI labyAPI;
    protected final int identifier;
    protected final List<AbstractScreenWindow> windows;
    protected boolean enabled;
    
    public AbstractScreenWindowOverlay(final int identifier) {
        this.labyAPI = Laby.labyAPI();
        this.identifier = identifier;
        this.windows = new ArrayList<AbstractScreenWindow>();
    }
    
    public int getIdentifier() {
        return this.identifier;
    }
    
    public void registerWindow(final AbstractScreenWindow window) {
        this.windows.add(window);
        this.labyAPI.eventBus().registerListener(window);
    }
    
    public abstract void renderWindow(final Stack p0, final MutableMouse p1);
    
    public void widgetPreInitialize(final Widget widget, final Parent parent) {
    }
    
    public abstract void renderWidget(final Stack p0, final MutableMouse p1, final Widget p2);
    
    public abstract void preRenderActivity(final Stack p0, final MutableMouse p1, final Activity p2);
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (!this.isEnabled()) {
            return false;
        }
        final Mouse absolute = this.labyAPI.minecraft().absoluteMouse();
        return mouse.set(absolute.getXDouble(), absolute.getYDouble(), () -> {
            this.windows.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final AbstractScreenWindow window = iterator.next();
                if (window.mouseClicked(mouse, mouseButton)) {
                    return true;
                }
            }
            return false;
        });
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        if (!this.isEnabled()) {
            return false;
        }
        final Mouse absolute = this.labyAPI.minecraft().absoluteMouse();
        return mouse.set(absolute.getXDouble(), absolute.getYDouble(), () -> {
            this.windows.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final AbstractScreenWindow window = iterator.next();
                if (window.mouseReleased(mouse, mouseButton)) {
                    return true;
                }
            }
            return false;
        });
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        if (!this.isEnabled()) {
            return false;
        }
        final Mouse absolute = this.labyAPI.minecraft().absoluteMouse();
        return mouse.set(absolute.getXDouble(), absolute.getYDouble(), () -> {
            this.windows.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final AbstractScreenWindow window = iterator.next();
                if (window.mouseDragged(mouse, button, deltaX, deltaY)) {
                    return true;
                }
            }
            return false;
        });
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double delta) {
        if (!this.isEnabled()) {
            return false;
        }
        final Mouse absolute = this.labyAPI.minecraft().absoluteMouse();
        return mouse.set(absolute.getXDouble(), absolute.getYDouble(), () -> {
            this.windows.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final AbstractScreenWindow window = iterator.next();
                if (window.isInRectangle(mouse) && window.mouseScrolled(mouse, delta)) {
                    return true;
                }
            }
            return false;
        });
    }
    
    @Override
    public boolean fileDropped(final MutableMouse mouse, final List<Path> paths) {
        if (!this.isEnabled()) {
            return false;
        }
        final Mouse absolute = this.labyAPI.minecraft().absoluteMouse();
        return mouse.set(absolute.getXDouble(), absolute.getYDouble(), () -> {
            this.windows.iterator();
            final Iterator iterator;
            while (iterator.hasNext()) {
                final AbstractScreenWindow window = iterator.next();
                if (window.isInRectangle(mouse) && window.fileDropped(mouse, paths)) {
                    return true;
                }
            }
            return false;
        });
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType inputType) {
        if (!this.isEnabled()) {
            return false;
        }
        for (final AbstractScreenWindow window : this.windows) {
            if (window.keyPressed(key, inputType)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean keyReleased(final Key key, final InputType type) {
        if (!this.isEnabled()) {
            return false;
        }
        for (final AbstractScreenWindow window : this.windows) {
            if (window.keyReleased(key, type)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean charTyped(final Key key, final char character) {
        if (!this.isEnabled()) {
            return false;
        }
        for (final AbstractScreenWindow window : this.windows) {
            if (window.charTyped(key, character)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public void doScreenAction(final String action, final Map<String, Object> parameters) {
        if (!this.isEnabled()) {
            return;
        }
        for (final AbstractScreenWindow window : this.windows) {
            window.doScreenAction(action, parameters);
        }
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
