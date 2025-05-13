// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.window;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.event.client.input.MouseScrollEvent;
import net.labymod.api.event.client.input.MouseDragEvent;
import net.labymod.api.event.client.input.MouseButtonEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.client.Minecraft;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.GameRenderEvent;
import net.labymod.api.client.gui.KeyboardUser;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import java.nio.file.Path;
import net.labymod.api.client.gui.MouseUser;
import net.labymod.api.event.client.gui.screen.tree.ScreenPhase;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Comparator;
import javax.inject.Inject;
import net.labymod.core.client.gui.screen.widget.window.debug.bounds.DefaultBoundsDebugScreenWindowOverlay;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.window.AbstractScreenWindowOverlay;
import java.util.List;
import net.labymod.api.event.client.gui.screen.tree.ScreenTreeTopRegistry;
import net.labymod.api.LabyAPI;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.widget.window.ScreenWindowManagement;

@Singleton
@Implements(ScreenWindowManagement.class)
@Deprecated
public class DefaultScreenWindowManagement implements ScreenWindowManagement
{
    private final LabyAPI labyAPI;
    private final ScreenTreeTopRegistry treeTopRegistry;
    private final List<AbstractScreenWindowOverlay> overlays;
    
    @Inject
    public DefaultScreenWindowManagement(final LabyAPI labyAPI, final ScreenTreeTopRegistry treeTopRegistry) {
        this.labyAPI = labyAPI;
        this.treeTopRegistry = treeTopRegistry;
        this.overlays = new ArrayList<AbstractScreenWindowOverlay>();
        this.registerOverlay(new DefaultBoundsDebugScreenWindowOverlay());
        this.labyAPI.eventBus().registerListener(this);
    }
    
    @Override
    public void registerOverlay(final AbstractScreenWindowOverlay overlay) {
        this.overlays.add(overlay);
        this.labyAPI.eventBus().registerListener(overlay);
        this.overlays.sort(Comparator.comparingInt(AbstractScreenWindowOverlay::getIdentifier));
    }
    
    @Override
    public void widgetPreInitialize(final Widget widget, final Parent parent) {
        for (final AbstractScreenWindowOverlay overlay : this.overlays) {
            overlay.widgetPreInitialize(widget, parent);
        }
    }
    
    @Override
    public void renderWidgetOverlay(final Stack stack, final MutableMouse mouse, final Widget widget) {
        for (final AbstractScreenWindowOverlay overlay : this.overlays) {
            overlay.renderWidget(stack, mouse, widget);
        }
    }
    
    @Override
    public void preRenderActivity(final Stack stack, final MutableMouse mouse, final Activity activity) {
        for (final AbstractScreenWindowOverlay overlay : this.overlays) {
            overlay.preRenderActivity(stack, mouse, activity);
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        for (final AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.mouseClicked(ScreenPhase.PRE, overlay, mouse, mouseButton);
            final boolean result = overlay.mouseClicked(mouse, mouseButton);
            this.treeTopRegistry.mouseClicked(ScreenPhase.POST, overlay, mouse, mouseButton);
            if (result) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        for (final AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.mouseReleased(ScreenPhase.PRE, overlay, mouse, mouseButton);
            final boolean result = overlay.mouseReleased(mouse, mouseButton);
            this.treeTopRegistry.mouseReleased(ScreenPhase.POST, overlay, mouse, mouseButton);
            if (result) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double delta) {
        for (final AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.mouseScrolled(ScreenPhase.PRE, overlay, mouse, delta);
            final boolean result = overlay.mouseScrolled(mouse, delta);
            this.treeTopRegistry.mouseScrolled(ScreenPhase.POST, overlay, mouse, delta);
            if (result) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        for (final AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.mouseDragged(ScreenPhase.PRE, overlay, mouse, button, deltaX, deltaY);
            final boolean result = overlay.mouseDragged(mouse, button, deltaX, deltaY);
            this.treeTopRegistry.mouseDragged(ScreenPhase.POST, overlay, mouse, button, deltaX, deltaY);
            if (result) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean fileDropped(final MutableMouse mouse, final List<Path> paths) {
        for (final AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.fileDropped(ScreenPhase.PRE, overlay, mouse, paths);
            final boolean result = overlay.fileDropped(mouse, paths);
            this.treeTopRegistry.fileDropped(ScreenPhase.POST, overlay, mouse, paths);
            if (result) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType inputType) {
        for (final AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.keyPressed(ScreenPhase.PRE, overlay, key, inputType);
            final boolean result = overlay.keyPressed(key, inputType);
            this.treeTopRegistry.keyPressed(ScreenPhase.POST, overlay, key, inputType);
            if (result) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean keyReleased(final Key key, final InputType type) {
        for (final AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.keyReleased(ScreenPhase.PRE, overlay, key, type);
            final boolean result = overlay.keyReleased(key, type);
            this.treeTopRegistry.keyReleased(ScreenPhase.POST, overlay, key, type);
            if (result) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean charTyped(final Key key, final char character) {
        for (final AbstractScreenWindowOverlay overlay : this.overlays) {
            this.treeTopRegistry.charTyped(ScreenPhase.PRE, overlay, key, character);
            final boolean result = overlay.charTyped(key, character);
            this.treeTopRegistry.charTyped(ScreenPhase.POST, overlay, key, character);
            if (result) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public <T extends AbstractScreenWindowOverlay> T overlay(final Class<T> hudWidgetScreenWindowOverlayClass) {
        for (final AbstractScreenWindowOverlay overlay : this.overlays) {
            if (overlay.getClass().equals(hudWidgetScreenWindowOverlayClass)) {
                return (T)overlay;
            }
        }
        return null;
    }
    
    @Subscribe(125)
    public void onScreenRender(final GameRenderEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        final Stack stack = event.stack();
        final Minecraft minecraft = this.labyAPI.minecraft();
        final MutableMouse mouse = minecraft.absoluteMouse().mutable();
        for (final AbstractScreenWindowOverlay overlay : this.overlays) {
            stack.push();
            stack.translate(0.0f, 0.0f, 10.0f);
            overlay.renderWindow(stack, mouse);
            stack.pop();
        }
    }
    
    @Subscribe
    public void onMouseButton(final MouseButtonEvent event) {
        switch (event.action()) {
            case CLICK: {
                final boolean cancelled = this.mouseClicked(event.mouse(), event.button());
                if (cancelled) {
                    event.setCancelled(true);
                    break;
                }
                break;
            }
            case RELEASE: {
                this.mouseReleased(event.mouse(), event.button());
                break;
            }
        }
    }
    
    @Subscribe
    public void onMouseDrag(final MouseDragEvent event) {
        this.mouseDragged(event.mouse(), event.button(), event.deltaX(), event.deltaY());
    }
    
    @Subscribe
    public void onMouseScroll(final MouseScrollEvent event) {
        event.setCancelled(this.mouseScrolled(event.mouse(), event.delta()));
    }
    
    @Subscribe
    public void onKey(final KeyEvent event) {
        final Key key = event.key();
        final InputType inputType = KeyMapper.getInputType(key);
        switch (event.state()) {
            case UNPRESSED: {
                this.keyReleased(key, inputType);
                break;
            }
            case PRESS: {
                this.keyPressed(key, inputType);
                break;
            }
        }
        Laby.references().documentHandler().onKey(event);
    }
}
