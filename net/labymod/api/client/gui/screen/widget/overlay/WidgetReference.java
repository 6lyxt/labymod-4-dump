// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.overlay;

import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.util.ThreadSafe;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.function.BiConsumer;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.LabyScreen;

public class WidgetReference
{
    private final WidgetScreenOverlay overlay;
    private final LabyScreen sourceScreen;
    private Widget widget;
    private final List<StyleSheet> styleSheets;
    private BiConsumer<WidgetReference, Bounds> boundsUpdater;
    private final Collection<Runnable> destroyHandlers;
    private ClickRemoveStrategy clickRemoveStrategy;
    private KeyPressRemoveStrategy keyPressRemoveStrategy;
    
    public WidgetReference(final WidgetScreenOverlay overlay, final LabyScreen sourceScreen, final List<StyleSheet> styleSheets, final Widget widget) {
        this.destroyHandlers = new ArrayList<Runnable>();
        this.clickRemoveStrategy = ClickRemoveStrategy.ALWAYS;
        this.keyPressRemoveStrategy = KeyPressRemoveStrategy.ALWAYS;
        this.overlay = overlay;
        this.sourceScreen = sourceScreen;
        this.styleSheets = styleSheets;
        this.widget = widget;
    }
    
    protected void onPreRender() {
        if (this.boundsUpdater != null) {
            this.boundsUpdater.accept(this, this.widget.bounds());
        }
    }
    
    public void remove() {
        if (!this.isAlive()) {
            return;
        }
        this.overlay.destroy(this);
    }
    
    public WidgetReference boundsUpdater(final BiConsumer<WidgetReference, Bounds> consumer) {
        this.boundsUpdater = consumer;
        if (consumer != null) {
            consumer.accept(this, this.widget.bounds());
        }
        return this;
    }
    
    public WidgetReference addDestroyHandler(final Runnable handler) {
        this.destroyHandlers.add(handler);
        return this;
    }
    
    public WidgetReference clickRemoveStrategy(final ClickRemoveStrategy strategy) {
        this.clickRemoveStrategy = strategy;
        return this;
    }
    
    public ClickRemoveStrategy clickRemoveStrategy() {
        return this.clickRemoveStrategy;
    }
    
    public WidgetReference keyPressRemoveStrategy(final KeyPressRemoveStrategy strategy) {
        this.keyPressRemoveStrategy = strategy;
        return this;
    }
    
    public KeyPressRemoveStrategy keyPressRemoveStrategy() {
        return this.keyPressRemoveStrategy;
    }
    
    public boolean isAlive() {
        return this.overlay.getReferences().contains(this);
    }
    
    @Nullable
    public LabyScreen getSourceScreen() {
        return this.sourceScreen;
    }
    
    public Widget widget() {
        return this.widget;
    }
    
    public Collection<Runnable> destroyHandlers() {
        return this.destroyHandlers;
    }
    
    public List<StyleSheet> getStyleSheets() {
        return this.styleSheets;
    }
    
    public void updateWidget(final Widget widget) {
        ThreadSafe.ensureRenderThread();
        this.overlay.document().removeChildImmediately(this.widget);
        this.overlay.document().addChildInitialized(widget);
        this.widget.dispose();
        this.widget.destroy();
        this.widget = widget;
    }
    
    @Deprecated
    public WidgetReference removeOnClick(final boolean removeOnClick) {
        if (removeOnClick) {
            this.clickRemoveStrategy = ClickRemoveStrategy.ALWAYS;
        }
        else {
            this.clickRemoveStrategy = ClickRemoveStrategy.NEVER;
        }
        return this;
    }
    
    @Deprecated
    public WidgetReference removeOnOutsideClick(final boolean removeOnOutsideClick) {
        if (removeOnOutsideClick) {
            this.clickRemoveStrategy = ClickRemoveStrategy.OUTSIDE;
        }
        return this;
    }
    
    @Deprecated
    public boolean isRemoveOnClick() {
        return this.clickRemoveStrategy == ClickRemoveStrategy.ALWAYS;
    }
    
    @Deprecated
    public boolean isRemoveOnOutsideClick() {
        return this.clickRemoveStrategy == ClickRemoveStrategy.OUTSIDE;
    }
    
    public enum ClickRemoveStrategy
    {
        ALWAYS, 
        INSIDE, 
        OUTSIDE, 
        NEVER;
    }
    
    public enum KeyPressRemoveStrategy
    {
        ALWAYS, 
        ESCAPE, 
        NEVER;
    }
}
