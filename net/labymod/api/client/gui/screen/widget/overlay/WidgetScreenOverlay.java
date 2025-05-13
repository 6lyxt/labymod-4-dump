// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.overlay;

import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;
import java.util.Iterator;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.Collection;
import java.util.Collections;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.Parent;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.gui.screen.activity.AutoActivity;

@AutoActivity
public class WidgetScreenOverlay extends ScreenOverlay
{
    private final List<WidgetReference> references;
    
    public WidgetScreenOverlay() {
        super(20);
        this.references = new ArrayList<WidgetReference>();
        this.setActive(true);
    }
    
    @Override
    public void load(final Parent parent) {
        final List<Widget> genericChildren = ((Document)this.document).getGenericChildren();
        final Widget[] children = genericChildren.toArray(new Widget[0]);
        genericChildren.clear();
        super.load(parent);
        Collections.addAll(genericChildren, children);
        for (final WidgetReference reference : this.references.toArray(new WidgetReference[0])) {
            if (reference.clickRemoveStrategy() != WidgetReference.ClickRemoveStrategy.NEVER) {
                reference.remove();
            }
        }
    }
    
    @Override
    public void render(final ScreenContext context) {
        ((Document)this.document).renderChildren().set(false);
        for (final WidgetReference reference : this.references) {
            reference.onPreRender();
        }
        super.render(context);
        for (final WidgetReference reference : this.references) {
            final ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
            final LabyScreen sourceScreen = reference.getSourceScreen();
            try {
                screenCustomFontStack.push(sourceScreen);
                reference.widget().render(context);
            }
            finally {
                screenCustomFontStack.pop(sourceScreen);
            }
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        if (this.references.isEmpty()) {
            return false;
        }
        final WidgetReference[] references = this.references.toArray(new WidgetReference[0]);
        final boolean flag = super.mouseClicked(mouse, mouseButton);
        WidgetReference toRemove = null;
        for (final WidgetReference reference : references) {
            final WidgetReference.ClickRemoveStrategy strategy = reference.clickRemoveStrategy();
            final boolean hovered = reference.widget().isHovered();
            switch (strategy) {
                case ALWAYS: {
                    reference.remove();
                    return true;
                }
                case INSIDE: {
                    if (hovered) {
                        reference.remove();
                        return true;
                    }
                    break;
                }
                case OUTSIDE: {
                    if (hovered) {
                        toRemove = null;
                        break;
                    }
                    toRemove = reference;
                    break;
                }
            }
        }
        if (toRemove != null) {
            toRemove.remove();
            return true;
        }
        return flag;
    }
    
    @Override
    public void tick() {
        super.tick();
        if (!this.references.isEmpty() && this.labyAPI.minecraft().isMouseLocked()) {
            for (final WidgetReference reference : this.references.toArray(new WidgetReference[0])) {
                if (reference.clickRemoveStrategy() != WidgetReference.ClickRemoveStrategy.NEVER) {
                    reference.remove();
                }
            }
        }
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        if (this.references.isEmpty()) {
            return false;
        }
        for (final WidgetReference reference : this.references.toArray(new WidgetReference[0])) {
            final WidgetReference.ClickRemoveStrategy strategy = reference.clickRemoveStrategy();
            final boolean hovered = reference.widget().isHovered();
            if (hovered && reference.widget().mouseScrolled(mouse, scrollDelta)) {
                return true;
            }
            switch (strategy) {
                case ALWAYS: {
                    reference.remove();
                    break;
                }
                case INSIDE: {
                    if (hovered) {
                        reference.remove();
                        break;
                    }
                    break;
                }
                case OUTSIDE: {
                    if (!hovered) {
                        reference.remove();
                        break;
                    }
                    break;
                }
            }
        }
        for (final WidgetReference reference2 : this.references) {
            final boolean hovered2 = reference2.widget().isHovered();
            if (hovered2) {
                return super.mouseScrolled(mouse, scrollDelta);
            }
        }
        return super.mouseScrolled(mouse, scrollDelta);
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (this.references.isEmpty()) {
            return false;
        }
        for (final WidgetReference reference : this.references.toArray(new WidgetReference[0])) {
            switch (reference.keyPressRemoveStrategy()) {
                case ESCAPE: {
                    if (key == Key.ESCAPE) {
                        reference.remove();
                        return true;
                    }
                    break;
                }
                case ALWAYS: {
                    reference.remove();
                    return true;
                }
            }
        }
        return super.keyPressed(key, type);
    }
    
    public WidgetReference display(final List<StyleSheet> styles, final Widget widget) {
        return this.display(null, styles, widget);
    }
    
    public WidgetReference display(final LabyScreen sourceScreen, final List<StyleSheet> styles, final Widget widget) {
        ThreadSafe.ensureRenderThread();
        if (this.references.isEmpty()) {
            this.reload();
        }
        final WidgetReference reference = new WidgetReference(this, sourceScreen, styles, widget);
        final ScreenCustomFontStack screenCustomFontStack = Laby.references().screenCustomFontStack();
        try {
            screenCustomFontStack.push(sourceScreen);
            try {
                for (final StyleSheet styleSheet : styles) {
                    ((Document)this.document).applyStyleSheet(styleSheet);
                }
            }
            catch (final Exception exception) {
                WidgetScreenOverlay.LOGGER.error("Failed to apply style sheets", exception);
            }
            this.references.add(reference);
            this.document().addChildInitialized(widget);
        }
        finally {
            screenCustomFontStack.pop(sourceScreen);
        }
        return reference;
    }
    
    public void destroy(final WidgetReference reference) {
        ThreadSafe.ensureRenderThread();
        this.references.remove(reference);
        final List<StyleSheet> styles = reference.getStyleSheets();
        if (styles != null) {
            ((Document)this.document).getStyleSheets().removeAll(styles);
        }
        final Widget widget = reference.widget();
        this.document().removeChild(widget);
        widget.dispose();
        widget.destroy();
        for (final Runnable destroyHandler : reference.destroyHandlers()) {
            destroyHandler.run();
        }
    }
    
    public List<WidgetReference> getReferences() {
        return this.references;
    }
    
    @Override
    public boolean isHideGui() {
        return false;
    }
}
