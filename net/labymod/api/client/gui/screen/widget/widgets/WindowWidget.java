// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.util.TextFormat;
import java.awt.Color;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import java.util.function.Consumer;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.Parent;
import org.spongepowered.include.com.google.common.collect.Sets;
import java.util.Objects;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.util.bounds.Rectangle;
import java.util.Set;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

@Deprecated(forRemoval = true, since = "4.1.18")
@AutoWidget
public abstract class WindowWidget<T extends Widget, C extends Widget> extends InterpolateWidget
{
    private static final ModifyReason DRAGGING;
    private static final ModifyReason DRAGGING_RESET;
    private static final ModifyReason RESCALING;
    private static final ModifyReason RESCALING_RESET;
    private static final ModifyReason MIN_SIZE;
    private final Key dragButton;
    private final Key rescaleButton;
    private final DraggingType draggingType;
    private final RescaleType rescaleType;
    private final BoundsType rescaleBoundsType;
    private final Set<RescaleWidget> rescaleWidgets;
    private T tabWidget;
    private C contentWidget;
    private float dragOffsetX;
    private float dragOffsetY;
    private boolean dragging;
    private RescaleWidget rescaleWidget;
    private Rectangle startBounds;
    protected boolean renderWindow;
    private FlexibleContentWidget contentWrapper;
    private boolean hoverResize;
    
    protected WindowWidget() {
        this(null, DraggingType.NONE, RescaleType.NONE);
    }
    
    protected WindowWidget(@NotNull final Key dragButton, @NotNull final DraggingType draggingType) {
        this(dragButton, draggingType, RescaleType.NONE);
    }
    
    protected WindowWidget(@NotNull final Key rescaleButton, @NotNull final RescaleType rescaleType) {
        this(rescaleButton, DraggingType.NONE, rescaleType);
    }
    
    protected WindowWidget(@NotNull final Key rescaleButton, @NotNull final RescaleType rescaleType, @NotNull final BoundsType rescaleBoundsType) {
        this(rescaleButton, DraggingType.NONE, rescaleType, rescaleBoundsType);
    }
    
    protected WindowWidget(@Nullable final Key button, @NotNull final DraggingType draggingType, @NotNull final RescaleType rescaleType) {
        this(button, draggingType, rescaleType, BoundsType.INNER);
    }
    
    protected WindowWidget(@Nullable final Key button, @NotNull final DraggingType draggingType, @NotNull final RescaleType rescaleType, @NotNull final BoundsType rescaleBoundsType) {
        this(button, draggingType, button, rescaleType, rescaleBoundsType);
    }
    
    protected WindowWidget(@Nullable final Key dragButton, @NotNull final DraggingType draggingType, @Nullable final Key rescaleButton, @NotNull final RescaleType rescaleType, @NotNull final BoundsType rescaleBoundsType) {
        this.renderWindow = true;
        Objects.requireNonNull(draggingType, "DraggingType cannot be null. Use DraggingType.NONE instead");
        Objects.requireNonNull(rescaleType, "RescaleType cannot be null. Use RescaleType.NONE instead");
        Objects.requireNonNull(rescaleBoundsType, "RescaleBounds Type cannot be null. Use BoundsType.INNER instead");
        this.dragButton = dragButton;
        this.rescaleButton = rescaleButton;
        this.draggingType = draggingType;
        this.rescaleType = rescaleType;
        this.rescaleBoundsType = rescaleBoundsType;
        this.rescaleWidgets = Sets.newHashSet();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DivWidget stencilWrapper = new DivWidget().addId("stencil-wrapper");
        ((AbstractWidget<Widget>)(this.contentWrapper = new FlexibleContentWidget())).addId("window-content");
        this.tabWidget = this.createTabWidget();
        if (this.tabWidget != null) {
            this.tabWidget.addId("window-tab");
            this.contentWrapper.addContent(this.tabWidget);
        }
        if (this.contentWidget != null) {
            this.contentWidget.addId("window-main");
            this.contentWrapper.addFlexibleContent(this.contentWidget);
        }
        ((AbstractWidget<FlexibleContentWidget>)stencilWrapper).addChild(this.contentWrapper);
        this.rescaleWidgets.clear();
        RescalePosition.positions(this.rescaleType, rescalePosition -> {
            final RescaleWidget rescaleWidget = new RescaleWidget(this, this.rescaleType, rescalePosition);
            ((AbstractWidget<RescaleWidget>)stencilWrapper).addChild(rescaleWidget);
            this.rescaleWidgets.add(rescaleWidget);
            return;
        });
        this.addChild(stencilWrapper);
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.bounds().shiftToBounds(this.borderBounds(), WindowWidget.DRAGGING, false);
    }
    
    @Override
    public void render(final ScreenContext context) {
        final Bounds bounds = this.bounds();
        final MutableMouse mouse = context.mouse();
        if (this.rescaleWidget != null) {
            final RescalePosition pos = this.rescaleWidget.rescalePosition;
            switch (pos.ordinal()) {
                case 1: {
                    bounds.setMiddleRight(this.acceptRescaledPosition(pos, mouse.getX() - this.dragOffsetX, -1.0f), WindowWidget.RESCALING);
                    bounds.setMiddleBottom(this.acceptRescaledPosition(pos, -1.0f, mouse.getY() - this.dragOffsetY), WindowWidget.RESCALING);
                    break;
                }
                case 2: {
                    bounds.setMiddleLeft(this.acceptRescaledPosition(pos, Math.max(this.borderBounds().getLeft(), mouse.getX() - this.dragOffsetX), -1.0f), WindowWidget.RESCALING);
                    this.skipInterpolation();
                    break;
                }
                case 3: {
                    bounds.setMiddleTop(this.acceptRescaledPosition(pos, -1.0f, Math.max(this.borderBounds().getTop(), mouse.getY() - this.dragOffsetY)), WindowWidget.RESCALING);
                    this.skipInterpolation();
                    break;
                }
                case 4: {
                    bounds.setMiddleRight(this.acceptRescaledPosition(pos, mouse.getX() - this.dragOffsetX, -1.0f), WindowWidget.RESCALING);
                    break;
                }
                case 5: {
                    bounds.setMiddleBottom(this.acceptRescaledPosition(pos, -1.0f, mouse.getY() - this.dragOffsetY), WindowWidget.RESCALING);
                    break;
                }
            }
            final float minWidth = this.getSize(SizeType.MIN, WidgetSide.WIDTH);
            if (bounds.getWidth() < minWidth) {
                if (pos == RescalePosition.RIGHT || pos == RescalePosition.BOTTOM_RIGHT) {
                    bounds.setWidth(minWidth, WindowWidget.MIN_SIZE);
                }
                else if (pos == RescalePosition.LEFT) {
                    bounds.setLeftWidth(minWidth, WindowWidget.MIN_SIZE);
                    this.skipInterpolation();
                }
            }
            final float minHeight = this.getSize(SizeType.MIN, WidgetSide.HEIGHT);
            if (bounds.getHeight() < minHeight) {
                if (pos == RescalePosition.BOTTOM || pos == RescalePosition.BOTTOM_RIGHT) {
                    bounds.setHeight(minHeight, WindowWidget.MIN_SIZE);
                }
                else if (pos == RescalePosition.TOP) {
                    bounds.setTopHeight(minHeight, WindowWidget.MIN_SIZE);
                    this.skipInterpolation();
                }
            }
            bounds.shiftToBounds(this.borderBounds(), WindowWidget.RESCALING, true);
        }
        else if (this.dragging) {
            final float x = mouse.getX() - this.dragOffsetX;
            final float y = mouse.getY() - this.dragOffsetY;
            final float newX = this.acceptDraggedXPosition(bounds.getX(BoundsType.OUTER), x, x + bounds.getWidth(BoundsType.OUTER));
            final float newY = this.acceptDraggedYPosition(bounds.getY(BoundsType.OUTER), y, y + bounds.getHeight(BoundsType.OUTER));
            bounds.setOuterX(newX, WindowWidget.DRAGGING);
            bounds.setOuterY(newY, WindowWidget.DRAGGING);
            bounds.shiftToBounds(this.borderBounds(), WindowWidget.DRAGGING, false);
        }
        if (!this.useFloatingPointPosition().get()) {
            bounds.setLeft((float)(int)bounds.getLeft(), WindowWidget.DRAGGING);
            bounds.setTop((float)(int)bounds.getTop(), WindowWidget.DRAGGING);
            bounds.setRight((float)(int)bounds.getRight(), WindowWidget.DRAGGING);
            bounds.setBottom((float)(int)bounds.getBottom(), WindowWidget.DRAGGING);
        }
        this.hoverResize = false;
        if (this.renderWindow) {
            super.render(context);
        }
        else {
            this.renderWindow = true;
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton mouseButton) {
        this.rescaleWidget = (this.isWindowResizeable() ? this.rescalePosition(mouseButton, mouse) : null);
        if (this.rescaleWidget == null && super.mouseClicked(mouse, mouseButton)) {
            return true;
        }
        final Bounds bounds = this.bounds();
        if (this.rescaleWidget != null) {
            switch (this.rescaleWidget.rescalePosition.ordinal()) {
                case 2: {
                    this.dragOffsetX = mouse.getX() - bounds.getX(BoundsType.OUTER);
                    break;
                }
                case 3: {
                    this.dragOffsetY = mouse.getY() - bounds.getY(BoundsType.OUTER);
                    break;
                }
                case 4: {
                    this.dragOffsetX = mouse.getX() - bounds.getMaxX(BoundsType.OUTER);
                    break;
                }
                case 5: {
                    this.dragOffsetY = mouse.getY() - bounds.getMaxY(BoundsType.OUTER);
                    break;
                }
                case 1: {
                    this.dragOffsetX = mouse.getX() - bounds.getMaxX(BoundsType.OUTER);
                    this.dragOffsetY = mouse.getY() - bounds.getMaxY(BoundsType.OUTER);
                    break;
                }
            }
            this.rescaleWidget.setDragging(true);
            this.startBounds = bounds.rectangle(BoundsType.OUTER);
            return true;
        }
        if (this.startDragging(mouse, mouseButton, false)) {
            this.startBounds = bounds.rectangle(BoundsType.OUTER);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        this.resetStates(true);
        return super.mouseReleased(mouse, mouseButton);
    }
    
    public void resetStates(final boolean apply) {
        this.setWindowDragging(false);
        if (apply) {
            final Bounds bounds = this.bounds();
            if (this.dragging && !this.acceptWindowDragged(this.startBounds, bounds)) {
                bounds.setOuterPosition(this.startBounds.getX(), this.startBounds.getY(), WindowWidget.DRAGGING_RESET);
            }
            if (this.rescaleWidget != null && !this.acceptWindowScaled(this.startBounds, bounds)) {
                bounds.setOuterSize(this.startBounds.getWidth(), this.startBounds.getHeight(), WindowWidget.RESCALING_RESET);
            }
        }
        this.dragging = false;
        if (this.rescaleWidget != null) {
            this.rescaleWidget.setDragging(false);
            this.rescaleWidget = null;
        }
    }
    
    public void setContentWidget(final C contentWidget) {
        this.contentWidget = contentWidget;
    }
    
    public boolean dragging() {
        return this.dragging;
    }
    
    public boolean rescaling() {
        return this.rescaleWidget != null;
    }
    
    @NotNull
    public DraggingType draggingType() {
        return this.draggingType;
    }
    
    @NotNull
    public RescaleType rescaleType() {
        return this.rescaleType;
    }
    
    @Nullable
    public T tabWidget() {
        return this.tabWidget;
    }
    
    @Nullable
    public C contentWidget() {
        return this.contentWidget;
    }
    
    @NotNull
    protected abstract T createTabWidget();
    
    protected boolean acceptWindowDragged(final Rectangle outerStartBounds, final Bounds finalBounds) {
        return true;
    }
    
    protected boolean acceptWindowScaled(final Rectangle outerStartBounds, final Bounds finalBounds) {
        return true;
    }
    
    protected boolean handleCustomDragType() {
        throw new UnsupportedOperationException("Custom Drag Type is not implemented by the child");
    }
    
    protected float acceptDraggedXPosition(final float fromX, final float x, final float maxX) {
        return x;
    }
    
    protected float acceptDraggedYPosition(final float fromY, final float y, final float maxY) {
        return y;
    }
    
    protected float acceptRescaledPosition(final RescalePosition rescalePosition, final float x, final float y) {
        return (x == -1.0f) ? y : x;
    }
    
    public boolean isWindowDraggable() {
        return true;
    }
    
    public boolean isWindowResizeable() {
        return true;
    }
    
    @Override
    public boolean isHovered() {
        for (final RescaleWidget rescaleWidget : this.rescaleWidgets) {
            if (rescaleWidget.isHovered()) {
                return true;
            }
        }
        return super.isHovered();
    }
    
    protected Rectangle borderBounds() {
        return this.parent.bounds();
    }
    
    protected void renderSuper(final ScreenContext context, final boolean actuallyRender) {
        if (actuallyRender) {
            super.render(context);
            return;
        }
        this.renderChildren().set(false);
        super.render(context);
        this.renderChildren().set(true);
    }
    
    public boolean startDragging(final MutableMouse mouse, final Key mouseButton, final boolean forceButton) {
        if (!this.isWindowDraggable()) {
            return false;
        }
        if (this.dragging) {
            return true;
        }
        if (this.isHoveringDraggableWidget(forceButton ? this.dragButton : mouseButton, mouse)) {
            this.setWindowDragging(true);
            this.dragging = true;
            final Bounds bounds = this.bounds();
            this.dragOffsetX = mouse.getX() - bounds.getLeft(BoundsType.OUTER);
            this.dragOffsetY = mouse.getY() - bounds.getTop(BoundsType.OUTER);
            if (forceButton) {
                this.startBounds = bounds.rectangle(BoundsType.OUTER);
            }
            return true;
        }
        return false;
    }
    
    private void setWindowDragging(final boolean dragging) {
        this.setDragging(dragging);
        this.setChildrenDragging(this.getChildren(), dragging);
        if (Objects.nonNull(this.contentWidget)) {
            this.contentWidget.setDragging(true);
        }
    }
    
    private void setChildrenDragging(final List<? extends Widget> children, final boolean dragging) {
        for (final Widget child : children) {
            if (child instanceof RescaleWidget) {
                continue;
            }
            child.setDragging(dragging);
            this.setChildrenDragging(child.getChildren(), dragging);
        }
    }
    
    private RescaleWidget rescalePosition(final MouseButton mouseButton, final MutableMouse mouse) {
        if (!mouseButton.equals(this.rescaleButton) || this.rescaleType == RescaleType.NONE) {
            return null;
        }
        for (final RescaleWidget rescaleWidget : this.rescaleWidgets) {
            if (rescaleWidget.isHovered()) {
                return rescaleWidget;
            }
        }
        return null;
    }
    
    private boolean isHoveringDraggableWidget(final Key key, final MutableMouse mouse) {
        if (!key.equals(this.dragButton) || this.draggingType == DraggingType.NONE) {
            return false;
        }
        boolean hovered = false;
        switch (this.draggingType.ordinal()) {
            case 0: {
                hovered = (Objects.nonNull(this.tabWidget) && this.tabWidget.isHovered());
                break;
            }
            case 1: {
                hovered = (Objects.nonNull(this.contentWidget) && this.contentWidget.isHovered());
                break;
            }
            case 2: {
                hovered = this.bounds().isInRectangle((float)mouse.getX(), (float)mouse.getY());
                break;
            }
            case 3: {
                hovered = this.handleCustomDragType();
                break;
            }
        }
        return hovered;
    }
    
    public FlexibleContentWidget getContentWrapper() {
        return this.contentWrapper;
    }
    
    static {
        DRAGGING = ModifyReason.of("dragging");
        DRAGGING_RESET = ModifyReason.of("draggingReset");
        RESCALING = ModifyReason.of("rescaling");
        RESCALING_RESET = ModifyReason.of("rescalingReset");
        MIN_SIZE = ModifyReason.of("minSize");
    }
    
    public enum DraggingType
    {
        TAB_WIDGET, 
        CONTENT_WIDGET, 
        ALL, 
        CUSTOM, 
        NONE;
    }
    
    public enum RescaleType
    {
        BOTTOM_RIGHT_CORNER, 
        EDGES, 
        CONTENT_EDGES, 
        NONE;
    }
    
    public enum RescalePosition
    {
        NONE(new RescaleType[] { RescaleType.NONE }), 
        BOTTOM_RIGHT(new RescaleType[] { RescaleType.BOTTOM_RIGHT_CORNER }), 
        LEFT(new RescaleType[] { RescaleType.EDGES, RescaleType.CONTENT_EDGES }), 
        TOP(new RescaleType[] { RescaleType.EDGES, RescaleType.CONTENT_EDGES }), 
        RIGHT(new RescaleType[] { RescaleType.EDGES, RescaleType.CONTENT_EDGES }), 
        BOTTOM(new RescaleType[] { RescaleType.EDGES, RescaleType.CONTENT_EDGES });
        
        private static final RescalePosition[] VALUES;
        private final List<RescaleType> types;
        
        private RescalePosition(final RescaleType[] types) {
            this.types = Arrays.asList(types);
        }
        
        public static RescalePosition[] getValues() {
            return RescalePosition.VALUES;
        }
        
        public static void positions(final RescaleType type, final Consumer<RescalePosition> consumer) {
            for (final RescalePosition rescalePosition : RescalePosition.VALUES) {
                if (rescalePosition.types.contains(type)) {
                    consumer.accept(rescalePosition);
                }
            }
        }
        
        public List<RescaleType> getTypes() {
            return this.types;
        }
        
        static {
            VALUES = values();
        }
    }
    
    @AutoWidget
    public static class RescaleWidget extends SimpleWidget
    {
        private final WindowWidget<?, ?> widget;
        private final RescaleType rescaleType;
        private final RescalePosition rescalePosition;
        private final LssProperty<Boolean> render;
        private final LssProperty<Integer> idleColor;
        private final LssProperty<Integer> hoverColor;
        private boolean hovered;
        private boolean dragging;
        private double offset;
        
        public RescaleWidget(final WindowWidget<?, ?> widget, final RescaleType rescaleType, final RescalePosition rescalePosition) {
            this.render = new LssProperty<Boolean>(true);
            this.idleColor = new LssProperty<Integer>(Color.GRAY.getRGB());
            this.hoverColor = new LssProperty<Integer>(Color.WHITE.getRGB());
            this.widget = widget;
            this.rescaleType = rescaleType;
            this.rescalePosition = rescalePosition;
            this.shouldUseStencilBuffer = false;
        }
        
        @Override
        public String getDefaultRendererName() {
            return "Resize";
        }
        
        @Override
        public void initialize(final Parent parent) {
            super.initialize(parent);
            this.addId("rescale", "rescale-" + TextFormat.SNAKE_CASE.toDashCase(this.rescalePosition.name()));
        }
        
        @Override
        public void render(final ScreenContext context) {
            super.render(context);
            final MutableMouse mouse = context.mouse();
            if (this.rescaleType == RescaleType.NONE) {
                this.hovered = false;
                return;
            }
            if (this.rescaleType == RescaleType.BOTTOM_RIGHT_CORNER) {
                this.hovered = super.isHovered();
            }
            if (this.rescaleType == RescaleType.EDGES || this.rescaleType == RescaleType.CONTENT_EDGES) {
                double distance = -1.0;
                final Rectangle bounds = this.widget.bounds().rectangle(BoundsType.MIDDLE);
                switch (this.rescalePosition.ordinal()) {
                    case 2: {
                        distance = mouse.getXDouble() - bounds.getX();
                        this.calculateOffset(bounds.getX(), mouse.getXDouble());
                        break;
                    }
                    case 3: {
                        float y = bounds.getY();
                        if (this.rescaleType == RescaleType.CONTENT_EDGES) {
                            final Widget bar = (Widget)this.widget.tabWidget();
                            if (bar != null && bar.isVisible()) {
                                y += bar.bounds().getHeight();
                            }
                        }
                        distance = mouse.getYDouble() - y;
                        this.calculateOffset(y, mouse.getYDouble());
                        break;
                    }
                    case 4: {
                        distance = bounds.getMaxX() - mouse.getXDouble();
                        this.calculateOffset(mouse.getXDouble(), bounds.getMaxX());
                        break;
                    }
                    case 5: {
                        distance = bounds.getMaxY() - mouse.getYDouble();
                        this.calculateOffset(mouse.getYDouble(), bounds.getMaxY());
                        break;
                    }
                }
                this.hovered = (distance >= -2.0 && distance <= 2.0 && !this.widget.hoverResize && !this.widget.rescaling());
                if (this.hovered) {
                    this.widget.hoverResize = true;
                }
            }
        }
        
        @Override
        public boolean isHovered() {
            return this.hovered;
        }
        
        public int getCurrentColor() {
            return this.isHovered() ? this.hoverColor.get() : this.idleColor.get();
        }
        
        @Override
        public boolean isDragging() {
            return this.dragging;
        }
        
        @Override
        public void setDragging(final boolean dragging) {
            if (!dragging) {
                this.offset = -1.0;
            }
            this.dragging = dragging;
        }
        
        public WindowWidget<?, ?> widget() {
            return this.widget;
        }
        
        public RescalePosition position() {
            return this.rescalePosition;
        }
        
        public RescaleType type() {
            return this.rescaleType;
        }
        
        public LssProperty<Boolean> render() {
            return this.render;
        }
        
        public LssProperty<Integer> idleColor() {
            return this.idleColor;
        }
        
        public LssProperty<Integer> hoverColor() {
            return this.hoverColor;
        }
        
        private void calculateOffset(final double min, final double max) {
            if (!this.dragging || this.offset != -1.0) {
                return;
            }
            this.offset = min - max;
        }
    }
}
