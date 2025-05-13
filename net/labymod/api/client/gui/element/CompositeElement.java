// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.element;

import java.util.Map;
import java.nio.file.Path;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import java.util.Iterator;
import java.util.List;
import java.util.Comparator;

public interface CompositeElement<E extends Element> extends Element
{
    public static final Comparator<? super Element> ELEMENT_COMPARATOR = (a, b) -> 0;
    
    List<? extends E> getChildren();
    
    default void sortChildren() {
        for (final E element : this.getChildren()) {
            if (element instanceof final CompositeElement compositeElement) {
                compositeElement.sortChildren();
            }
        }
        this.getChildren().sort(CompositeElement.ELEMENT_COMPARATOR);
    }
    
    default boolean keyPressed(final Key key, final InputType type) {
        final List<? extends E> elements = this.getChildren();
        for (int i = elements.size() - 1; i >= 0 && elements.size() > i; --i) {
            final E element = (E)elements.get(i);
            if (element.isVisible()) {
                if (element.isInteractable()) {
                    if (element.keyPressed(key, type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    default boolean keyReleased(final Key key, final InputType type) {
        final List<? extends E> elements = this.getChildren();
        for (int i = elements.size() - 1; i >= 0 && elements.size() > i; --i) {
            final E element = (E)elements.get(i);
            if (element.isVisible()) {
                if (element.isInteractable()) {
                    if (element.keyReleased(key, type)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    default boolean charTyped(final Key key, final char character) {
        final List<? extends E> elements = this.getChildren();
        for (int i = elements.size() - 1; i >= 0 && elements.size() > i; --i) {
            final E element = (E)elements.get(i);
            if (element.isVisible()) {
                if (element.isInteractable()) {
                    if (element.charTyped(key, character)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    default void tick() {
        for (final E child : this.getChildren()) {
            if (!child.isTicking()) {
                continue;
            }
            child.tick();
        }
    }
    
    default boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        final List<? extends E> elements = this.getChildren();
        for (int i = elements.size() - 1; i >= 0 && elements.size() > i; --i) {
            final E element = (E)elements.get(i);
            if (element.isInteractable()) {
                element.setDragging(false);
                element.transformMouse(mouse, () -> element.mouseReleased(mouse, mouseButton));
            }
        }
        return false;
    }
    
    default boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        final List<? extends E> elements = this.getChildren();
        for (int i = elements.size() - 1; i >= 0 && elements.size() > i; --i) {
            final E element = (E)elements.get(i);
            if (element.isHovered() || element.isInteractableOutside()) {
                if (element.isInteractable()) {
                    if (mouseButton == MouseButton.LEFT) {
                        element.setDragging(true);
                    }
                    if (element.transformMouse(mouse, () -> element.mouseClicked(mouse, mouseButton))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    default boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        final List<? extends E> elements = this.getChildren();
        for (int i = elements.size() - 1; i >= 0; --i) {
            final E element = (E)elements.get(i);
            if (element.isHovered() || element.isInteractableOutside()) {
                if (element.isInteractable()) {
                    if (element.transformMouse(mouse, () -> element.mouseScrolled(mouse, scrollDelta))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    default boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        final List<? extends E> elements = this.getChildren();
        for (int i = elements.size() - 1; i >= 0; --i) {
            final E element = (E)elements.get(i);
            if (element.isInteractable()) {
                if (element.transformMouse(mouse, () -> element.mouseDragged(mouse, button, deltaX, deltaY))) {
                    return true;
                }
            }
        }
        return false;
    }
    
    default boolean fileDropped(final MutableMouse mouse, final List<Path> paths) {
        final List<? extends E> elements = this.getChildren();
        for (int i = elements.size() - 1; i >= 0; --i) {
            final E element = (E)elements.get(i);
            if (element.isHovered() || element.isInteractableOutside()) {
                if (element.isInteractable()) {
                    if (element.transformMouse(mouse, () -> element.fileDropped(mouse, paths))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    default void doScreenAction(final String action, final Map<String, Object> parameters) {
        for (final E child : this.getChildren()) {
            child.doScreenAction(action, parameters);
        }
    }
}
