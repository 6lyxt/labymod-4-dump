// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.overlay;

import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.property.Property;
import java.util.Iterator;
import java.util.Collection;
import org.spongepowered.include.com.google.common.collect.Lists;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.lss.meta.LinkReference;
import net.labymod.api.event.Phase;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.Orientation;
import net.labymod.api.util.TextFormat;
import java.util.Objects;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import java.util.List;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public abstract class OverlayWidget extends AbstractWidget<Widget>
{
    protected static final ModifyReason OVERLAY_POSITION;
    private final List<StyleSheet> overlayStyleSheets;
    private final WidgetReference.ClickRemoveStrategy clickRemoveStrategy;
    private final WidgetReference.KeyPressRemoveStrategy keyPressRemoveStrategy;
    private final String identifier;
    private final boolean forwardStyles;
    private final LssProperty<OverlayPositionStrategy> strategyX;
    private final LssProperty<OverlayPositionStrategy> strategyY;
    private final LssProperty<Boolean> playInteractSound;
    private WidgetReference reference;
    
    protected OverlayWidget(@Nullable final String identifier, @NotNull final WidgetReference.ClickRemoveStrategy clickRemoveStrategy, @NotNull final WidgetReference.KeyPressRemoveStrategy keyPressRemoveStrategy, final boolean forwardStyles) {
        this.overlayStyleSheets = new ArrayList<StyleSheet>();
        this.strategyX = new LssProperty<OverlayPositionStrategy>(OverlayPositionStrategy.MAXX_MAXX);
        this.strategyY = new LssProperty<OverlayPositionStrategy>(OverlayPositionStrategy.MAXY_Y);
        this.playInteractSound = new LssProperty<Boolean>(true);
        Objects.requireNonNull(clickRemoveStrategy, "clickRemoveStrategy");
        Objects.requireNonNull(keyPressRemoveStrategy, "keyPressRemoveStrategy");
        this.clickRemoveStrategy = clickRemoveStrategy;
        this.keyPressRemoveStrategy = keyPressRemoveStrategy;
        this.identifier = ((identifier == null) ? TextFormat.CAMEL_CASE.toDashCase(this.getTypeName()) : identifier);
        this.forwardStyles = forwardStyles;
        this.strategyX.addChangeListener((property, prev, next) -> {
            if (next.orientation() != Orientation.HORIZONTAL && next != OverlayPositionStrategy.CUSTOM) {
                property.set(prev);
                throw new IllegalStateException("Strategy " + String.valueOf(next) + " is not horizontal!");
            }
            else {
                return;
            }
        });
        this.strategyY.addChangeListener((property, prev, next) -> {
            if (next.orientation() != Orientation.VERTICAL && next != OverlayPositionStrategy.CUSTOM) {
                property.set(prev);
                throw new IllegalStateException("Strategy " + String.valueOf(next) + " is not vertical!");
            }
        });
    }
    
    protected OverlayWidget(@NotNull final WidgetReference.ClickRemoveStrategy clickRemoveStrategy, @NotNull final WidgetReference.KeyPressRemoveStrategy keyPressRemoveStrategy, final boolean forwardStyles) {
        this(null, clickRemoveStrategy, keyPressRemoveStrategy, forwardStyles);
    }
    
    protected OverlayWidget(@NotNull final WidgetReference.ClickRemoveStrategy clickRemoveStrategy, final boolean forwardStyles) {
        this(null, clickRemoveStrategy, WidgetReference.KeyPressRemoveStrategy.ALWAYS, forwardStyles);
    }
    
    protected OverlayWidget(@NotNull final WidgetReference.KeyPressRemoveStrategy keyPressRemoveStrategy, final boolean forwardStyles) {
        this(null, WidgetReference.ClickRemoveStrategy.ALWAYS, keyPressRemoveStrategy, forwardStyles);
    }
    
    protected OverlayWidget(final boolean forwardStyles) {
        this(null, WidgetReference.ClickRemoveStrategy.ALWAYS, WidgetReference.KeyPressRemoveStrategy.ALWAYS, forwardStyles);
    }
    
    @NotNull
    protected abstract Parent content();
    
    @Override
    public boolean onPress() {
        if (this.playInteractSound.get()) {
            this.labyAPI.minecraft().sounds().playButtonPress();
        }
        this.toggle();
        return true;
    }
    
    @Override
    public void dispose() {
        super.dispose();
        if (this.initialized) {
            this.close();
        }
    }
    
    public boolean isOpen() {
        return this.reference != null && this.reference.isAlive();
    }
    
    public void toggle() {
        if (this.isOpen()) {
            this.close();
        }
        else {
            this.open();
        }
    }
    
    public void close() {
        if (this.isOpen()) {
            this.reference.remove();
            this.reference = null;
        }
    }
    
    public void open() {
        Laby.references().linkMetaLoader().loadMeta(this.getClass(), this::addStyleSheet);
        final Parent parent = this.content();
        Widget stencilWrapper;
        if (parent instanceof Activity) {
            final ScreenRendererWidget screenRendererWidget = new ScreenRendererWidget();
            screenRendererWidget.displayScreen((ScreenInstance)parent);
            stencilWrapper = screenRendererWidget;
        }
        else {
            if (!(parent instanceof Widget)) {
                throw new IllegalStateException("Supplied value is not a widget or activity");
            }
            final OverlayContentWrapperWidget divWidget = new OverlayContentWrapperWidget();
            divWidget.addChild((Widget)parent);
            stencilWrapper = divWidget;
        }
        stencilWrapper.addId(this.identifier + "-overlay-wrapper");
        stencilWrapper.priorityLayer().set(PriorityLayer.VERY_FRONT);
        this.onOpen(Phase.PRE);
        (this.reference = this.displayInOverlay(this.getOverlayStyleSheets(), stencilWrapper).addDestroyHandler(this::onClose).boundsUpdater((reference, bounds) -> this.applyPosition(reference.widget(), bounds))).clickRemoveStrategy(this.clickRemoveStrategy);
        this.reference.keyPressRemoveStrategy(this.keyPressRemoveStrategy);
        this.onOpen(Phase.POST);
    }
    
    public void addStyleSheet(final String namespace, final String link) {
        final StyleSheet styleSheet = new LinkReference(namespace, "lss/" + link).loadStyleSheet();
        if (styleSheet == null) {
            throw new IllegalStateException("Could not load stylesheet " + link);
        }
        this.addStyleSheet(styleSheet);
    }
    
    public void addStyleSheet(final String link) {
        this.addStyleSheet(Laby.labyAPI().getNamespace(this), link);
    }
    
    public void addStyleSheet(final StyleSheet styleSheet) {
        if (!this.overlayStyleSheets.contains(styleSheet)) {
            this.overlayStyleSheets.add(styleSheet);
        }
    }
    
    public LssProperty<OverlayPositionStrategy> strategyX() {
        return this.strategyX;
    }
    
    public LssProperty<OverlayPositionStrategy> strategyY() {
        return this.strategyY;
    }
    
    public LssProperty<Boolean> playInteractSound() {
        return this.playInteractSound;
    }
    
    protected void onOpen(@NotNull final Phase phase) {
    }
    
    protected void onClose() {
        if (!this.playInteractSound.get() || !this.labyAPI.minecraft().mouse().isInside(Bounds.absoluteBounds(this))) {
            return;
        }
        Laby.references().soundService().play(SoundType.BUTTON_CLICK);
    }
    
    protected boolean onPositionApply(final OverlayPositionStrategy strategyX, final OverlayPositionStrategy strategyY, final float x, final float y, final Widget stencilWrapper, final Rectangle outerOverlayRectangle, final Bounds overlayBounds) {
        return true;
    }
    
    protected Rectangle absoluteBounds() {
        return Bounds.absoluteBounds(this, BoundsType.OUTER);
    }
    
    protected WidgetReference getOverlayReference() {
        return this.reference;
    }
    
    protected List<StyleSheet> getOverlayStyleSheets() {
        if (!this.forwardStyles) {
            return this.overlayStyleSheets;
        }
        final List<StyleSheet> references = Lists.newArrayList();
        references.addAll(this.overlayStyleSheets);
        final List<StyleSheet> widgetStyleSheets = this.getStyleSheets();
        for (final StyleSheet widgetStyleSheet : widgetStyleSheets) {
            if (!references.contains(widgetStyleSheet)) {
                references.add(widgetStyleSheet);
            }
        }
        return references;
    }
    
    protected void applyPosition(final Widget stencilWrapper, final Bounds bounds) {
        final Rectangle absoluteBounds = this.absoluteBounds();
        final Bounds windowBounds = this.labyAPI.minecraft().minecraftWindow().absoluteBounds();
        final Rectangle rectangle = bounds.rectangle(BoundsType.OUTER);
        OverlayPositionStrategy strategyX = this.strategyX.get();
        float x = -1.0f;
        final float width = rectangle.getWidth();
        if (strategyX != OverlayPositionStrategy.CUSTOM) {
            float tempX = strategyX.position().get(width, absoluteBounds);
            if (!this.testPosition(tempX, width, windowBounds, Orientation.HORIZONTAL)) {
                for (final OverlayPositionStrategy strategy : OverlayPositionStrategy.horizontalValues()) {
                    if (strategy == strategyX) {
                        continue;
                    }
                    tempX = strategy.position().get(width, absoluteBounds);
                    if (this.testPosition(tempX, width, windowBounds, Orientation.HORIZONTAL)) {
                        strategyX = strategy;
                        x = tempX;
                        break;
                    }
                }
                if (x == -1.0f) {
                    x = strategyX.position().get(width, absoluteBounds);
                }
            }
            else {
                x = tempX;
            }
        }
        OverlayPositionStrategy strategyY = this.strategyY.get();
        float y = -1.0f;
        final float height = rectangle.getHeight();
        if (strategyY != OverlayPositionStrategy.CUSTOM) {
            float tempY = strategyY.position().get(height, absoluteBounds);
            if (!this.testPosition(tempY, height, windowBounds, Orientation.VERTICAL)) {
                for (final OverlayPositionStrategy strategy2 : OverlayPositionStrategy.verticalValues()) {
                    if (strategy2 == strategyY) {
                        continue;
                    }
                    tempY = strategy2.position().get(height, absoluteBounds);
                    if (this.testPosition(tempY, height, windowBounds, Orientation.VERTICAL)) {
                        strategyY = strategy2;
                        y = tempY;
                        break;
                    }
                }
                if (y == -1.0f) {
                    y = strategyY.position().get(height, absoluteBounds);
                }
            }
            else {
                y = tempY;
            }
        }
        if (this.onPositionApply(strategyX, strategyY, x, y, stencilWrapper, rectangle, bounds)) {
            bounds.setOuterPosition(x, y, OverlayWidget.OVERLAY_POSITION);
        }
    }
    
    private boolean testPosition(final float value, final float dimension, final Rectangle window, final Orientation orientation) {
        final float min = (orientation == Orientation.HORIZONTAL) ? window.getX() : window.getY();
        final float max = (orientation == Orientation.HORIZONTAL) ? window.getMaxX() : window.getMaxY();
        return value >= min && value + dimension <= max;
    }
    
    static {
        OVERLAY_POSITION = ModifyReason.of("overlayPosition");
    }
    
    @AutoWidget
    public static class OverlayContentWrapperWidget extends AbstractWidget<Widget>
    {
        @Override
        public float getContentHeight(final BoundsType type) {
            final List<Widget> children = this.getChildren();
            float height = 0.0f;
            for (final Widget child : children) {
                height += child.bounds().getHeight(BoundsType.OUTER);
            }
            return height + this.bounds().getVerticalOffset(type);
        }
        
        @Override
        public float getContentWidth(final BoundsType type) {
            final List<Widget> children = this.getChildren();
            float height = 0.0f;
            for (final Widget child : children) {
                height += child.bounds().getWidth(BoundsType.OUTER);
            }
            return height + this.bounds().getHorizontalOffset(type);
        }
    }
}
