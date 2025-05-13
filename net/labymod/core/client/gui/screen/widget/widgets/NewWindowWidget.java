// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.util.collection.Lists;
import java.util.List;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.widget.size.SizeType;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.key.MouseButton;
import java.util.Iterator;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.util.bounds.Point;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget;

@AutoWidget
public abstract class NewWindowWidget extends InterpolateWidget
{
    private static final ModifyReason DRAGGING;
    private static final ModifyReason DRAGGING_RESET;
    private static final ModifyReason RESCALING;
    private static final ModifyReason RESCALING_RESET;
    private static final ModifyReason MIN_SIZE;
    private static final ModifyReason SHIFT_TO_BOUNDS;
    @Nullable
    private Widget titleBarWidget;
    @NotNull
    private Widget contentWidget;
    private final Key draggingButton;
    private final DraggingType draggingType;
    private boolean dragging;
    private boolean draggingUpdated;
    private float dragOffsetX;
    private float dragOffsetY;
    private final Key rescaleButton;
    private final RescaleType rescaleType;
    protected BoundsType boundsType;
    private boolean rescaling;
    private Edge rescalingEdge;
    private boolean rescalingUpdated;
    private boolean hoverEdge;
    
    protected NewWindowWidget(@NotNull final DraggingType draggingType, @NotNull final RescaleType rescaleType, @NotNull final BoundsType rescaleBoundsType, @Nullable final Key rescaleButton, @Nullable final Key dragButton) {
        this.dragging = false;
        this.draggingUpdated = false;
        this.rescaling = false;
        this.rescalingEdge = null;
        this.rescalingUpdated = false;
        this.hoverEdge = false;
        this.draggingType = draggingType;
        this.rescaleType = rescaleType;
        this.boundsType = rescaleBoundsType;
        this.rescaleButton = rescaleButton;
        this.draggingButton = dragButton;
        this.draggable().set(true);
        this.contentWidget = new DivWidget();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DivWidget stencilWrapper = new DivWidget().addId("stencil-wrapper");
        final FlexibleContentWidget contentWrapper = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)contentWrapper).addId("window-content");
        (this.titleBarWidget = this.createTitleBar()).addId("title-bar", "window-tab");
        contentWrapper.addContent(this.titleBarWidget);
        this.contentWidget.addId("window-main");
        contentWrapper.addFlexibleContent(this.contentWidget);
        ((AbstractWidget<FlexibleContentWidget>)stencilWrapper).addChild(contentWrapper);
        ((AbstractWidget<DivWidget>)this).addChild(stencilWrapper);
        this.resetDraggingAndScaling();
    }
    
    @Override
    public void render(final ScreenContext context) {
        super.render(context);
        final MutableMouse mouse = context.mouse();
        if (this.rescaleType.isEdge() && this.isRescalingEnabled() && !mouse.isOutOfBounds()) {
            final Edge hoveringEdge = this.rescaling ? this.rescalingEdge : this.getHoveringEdge(mouse);
            this.hoverEdge = (hoveringEdge != null);
            if (this.hoverEdge) {
                this.renderEdge(context.stack(), mouse, hoveringEdge);
            }
        }
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.shiftToBounds();
        for (final Widget child : this.children) {
            child.bounds().set(this.bounds(), NewWindowWidget.DRAGGING);
        }
    }
    
    @Override
    public boolean mouseClicked(final MutableMouse mouse, final MouseButton button) {
        if (super.mouseClicked(mouse, button)) {
            return true;
        }
        if (this.rescaleType != RescaleType.NONE && button == this.rescaleButton && !this.rescaling && this.isRescalingEnabled()) {
            if (this.rescaleType.isEdge()) {
                final Edge edge = this.getHoveringEdge(mouse);
                if (edge != null) {
                    this.rescaling = true;
                    this.rescalingEdge = edge;
                    this.rescalingUpdated = false;
                    return true;
                }
            }
            final Rectangle corner = this.getCorner();
            if (this.rescaleType == RescaleType.CORNER && corner.isInRectangle(mouse)) {
                this.rescaling = true;
                this.rescalingUpdated = false;
                this.dragOffsetX = mouse.getX() - corner.getRight();
                this.dragOffsetY = mouse.getY() - corner.getBottom();
            }
        }
        if (this.draggingType != DraggingType.NONE && button == this.draggingButton && !this.dragging && this.isDraggingEnabled() && this.isInsideEdges(mouse)) {
            final boolean hover = (this.draggingType == DraggingType.CONTENT) ? this.isHovered() : (this.titleBarWidget != null && this.titleBarWidget.isHovered());
            if (hover) {
                this.dragging = true;
                this.draggingUpdated = false;
                final Bounds bounds = this.bounds();
                this.dragOffsetX = mouse.getX() - bounds.getLeft(BoundsType.OUTER);
                this.dragOffsetY = mouse.getY() - bounds.getTop(BoundsType.OUTER);
                return true;
            }
        }
        return false;
    }
    
    protected boolean isInsideEdges(final MutableMouse mouse) {
        final float left = this.getEdge(Edge.LEFT, Edge.LEFT);
        final float top = this.getEdge(Edge.TOP, Edge.TOP);
        final float right = this.getEdge(Edge.RIGHT, Edge.RIGHT);
        final float bottom = this.getEdge(Edge.BOTTOM, Edge.BOTTOM);
        return mouse.getX() >= left && mouse.getX() <= right && mouse.getY() >= top && mouse.getY() <= bottom;
    }
    
    @Override
    public boolean mouseDragged(final MutableMouse mouse, final MouseButton button, final double deltaX, final double deltaY) {
        if (this.rescaling) {
            final Bounds bounds = this.bounds();
            if (this.rescaleType.isEdge()) {
                final float offset = this.hasEdgeOffset() ? (this.getEdge(this.rescalingEdge, this.rescalingEdge) - this.getActualEdge(this.rescalingEdge, this.rescalingEdge)) : 0.0f;
                switch (this.rescalingEdge.ordinal()) {
                    case 0: {
                        bounds.setMiddleLeft(Math.max(this.borderBounds().getLeft(), mouse.getX() - offset), NewWindowWidget.RESCALING);
                        this.skipInterpolation();
                        break;
                    }
                    case 1: {
                        bounds.setMiddleTop(Math.max(this.borderBounds().getTop(), mouse.getY() - offset), NewWindowWidget.RESCALING);
                        this.skipInterpolation();
                        break;
                    }
                    case 2: {
                        bounds.setMiddleRight(mouse.getX() - offset, NewWindowWidget.RESCALING);
                        break;
                    }
                    case 3: {
                        bounds.setMiddleBottom(mouse.getY() - offset, NewWindowWidget.RESCALING);
                        break;
                    }
                }
            }
            if (this.rescaleType == RescaleType.CORNER) {
                bounds.setMiddleRight(mouse.getX() - this.dragOffsetX, NewWindowWidget.RESCALING);
                bounds.setMiddleBottom(mouse.getY() - this.dragOffsetY, NewWindowWidget.RESCALING);
            }
            final float minWidth = this.getSize(SizeType.MIN, WidgetSide.WIDTH);
            if (bounds.getWidth() < minWidth) {
                if (this.rescalingEdge == Edge.RIGHT) {
                    bounds.setWidth(minWidth, NewWindowWidget.MIN_SIZE);
                }
                else if (this.rescalingEdge == Edge.LEFT) {
                    bounds.setLeftWidth(minWidth, NewWindowWidget.MIN_SIZE);
                    this.skipInterpolation();
                }
            }
            final float minHeight = this.getSize(SizeType.MIN, WidgetSide.HEIGHT);
            if (bounds.getHeight() < minHeight) {
                if (this.rescalingEdge == Edge.BOTTOM) {
                    bounds.setHeight(minHeight, NewWindowWidget.MIN_SIZE);
                }
                else if (this.rescalingEdge == Edge.TOP) {
                    bounds.setTopHeight(minHeight, NewWindowWidget.MIN_SIZE);
                    this.skipInterpolation();
                }
            }
            this.cropToBounds();
            this.onWindowPositionChanged();
            return this.rescalingUpdated = true;
        }
        if (this.dragging) {
            final Bounds bounds = this.bounds();
            final float x = mouse.getX() - this.dragOffsetX;
            final float y = mouse.getY() - this.dragOffsetY;
            this.dragToPosition(x, y, bounds);
            this.shiftToBounds();
            this.onWindowPositionChanged();
            return this.draggingUpdated = true;
        }
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }
    
    protected void renderEdge(final Stack stack, final MutableMouse mouse, final Edge edge) {
        final boolean horizontal = edge == Edge.LEFT || edge == Edge.RIGHT;
        this.labyAPI.renderPipeline().textRenderer().pos((float)(mouse.getX() - (horizontal ? 1 : 2)), (float)(mouse.getY() - (horizontal ? 3 : 4))).text(horizontal ? "||" : "=").render(stack);
    }
    
    protected void onWindowPositionChanged() {
        this.contentWidget.reInitialize();
    }
    
    protected boolean isDraggingEnabled() {
        return true;
    }
    
    protected boolean isRescalingEnabled() {
        return true;
    }
    
    protected boolean hasEdgeOffset() {
        return false;
    }
    
    public void shiftToBounds() {
        this.bounds().shiftToBounds(this.borderBounds(), NewWindowWidget.SHIFT_TO_BOUNDS, false);
    }
    
    public void cropToBounds() {
        this.bounds().crop(this.borderBounds(), NewWindowWidget.SHIFT_TO_BOUNDS);
    }
    
    @Nullable
    protected Edge getHoveringEdge(final Point position) {
        final float range = 2.0f;
        final float x = (float)position.getX();
        final float y = (float)position.getY();
        for (final Edge edge : Edge.VALUES) {
            if (this.rescaleType.hasEdge(edge)) {
                final float left = this.getEdge(edge, Edge.LEFT);
                final float top = this.getEdge(edge, Edge.TOP);
                final float right = this.getEdge(edge, Edge.RIGHT);
                final float bottom = this.getEdge(edge, Edge.BOTTOM);
                if (x >= left - range && x <= right + range && y >= top - range && y <= bottom + range) {
                    return edge;
                }
            }
        }
        return null;
    }
    
    protected float getEdge(final Edge edge, final Edge side) {
        return this.getActualEdge(edge, side);
    }
    
    private float getActualEdge(final Edge edge, final Edge side) {
        final Rectangle rectangle = this.bounds().rectangle(this.boundsType);
        Label_0217: {
            switch (edge.ordinal()) {
                case 0: {
                    switch (side.ordinal()) {
                        case 0:
                        case 2: {
                            return rectangle.getLeft();
                        }
                        case 1: {
                            return rectangle.getTop();
                        }
                        case 3: {
                            return rectangle.getBottom();
                        }
                        default: {
                            break Label_0217;
                        }
                    }
                    break;
                }
                case 1: {
                    switch (side.ordinal()) {
                        case 0: {
                            return rectangle.getLeft();
                        }
                        case 1:
                        case 3: {
                            return rectangle.getTop();
                        }
                        case 2: {
                            return rectangle.getRight();
                        }
                        default: {
                            break Label_0217;
                        }
                    }
                    break;
                }
                case 2: {
                    switch (side.ordinal()) {
                        case 0:
                        case 2: {
                            return rectangle.getRight();
                        }
                        case 1: {
                            return rectangle.getTop();
                        }
                        case 3: {
                            return rectangle.getBottom();
                        }
                        default: {
                            break Label_0217;
                        }
                    }
                    break;
                }
                case 3: {
                    switch (side.ordinal()) {
                        case 0: {
                            return rectangle.getLeft();
                        }
                        case 1:
                        case 3: {
                            return rectangle.getBottom();
                        }
                        case 2: {
                            return rectangle.getRight();
                        }
                        default: {
                            break Label_0217;
                        }
                    }
                    break;
                }
            }
        }
        return 0.0f;
    }
    
    @NotNull
    public Rectangle getCorner() {
        final Bounds bounds = this.bounds();
        final float size = 10.0f;
        final float shift = 0.0f;
        return Rectangle.relative(bounds.getRight() - size + shift, bounds.getBottom() - size + shift, size, size);
    }
    
    @Override
    public boolean mouseReleased(final MutableMouse mouse, final MouseButton mouseButton) {
        this.resetDraggingAndScaling();
        return super.mouseReleased(mouse, mouseButton);
    }
    
    private void resetDraggingAndScaling() {
        this.dragging = false;
        this.rescaling = false;
        this.rescalingEdge = null;
        this.rescalingUpdated = false;
        this.draggingUpdated = false;
    }
    
    protected abstract Widget createTitleBar();
    
    protected void dragToPosition(final float x, final float y, final Bounds bounds) {
        bounds.setOuterX(x, NewWindowWidget.DRAGGING);
        bounds.setOuterY(y, NewWindowWidget.DRAGGING);
    }
    
    @Nullable
    public Widget titleBarWidget() {
        return this.titleBarWidget;
    }
    
    @NotNull
    public Widget contentWidget() {
        return this.contentWidget;
    }
    
    protected Rectangle borderBounds() {
        return this.parent.bounds();
    }
    
    @Override
    public boolean isDragging() {
        return this.dragging && this.draggingUpdated;
    }
    
    public boolean isRescaling() {
        return this.rescaling && this.rescalingUpdated;
    }
    
    @Override
    public boolean isHovered() {
        return this.hoverEdge || super.isHovered();
    }
    
    public RescaleType getRescaleType() {
        return this.rescaleType;
    }
    
    protected void setContentWidget(@NotNull final Widget widget) {
        this.contentWidget = widget;
    }
    
    static {
        DRAGGING = ModifyReason.of("dragging");
        DRAGGING_RESET = ModifyReason.of("draggingReset");
        RESCALING = ModifyReason.of("rescaling");
        RESCALING_RESET = ModifyReason.of("rescalingReset");
        MIN_SIZE = ModifyReason.of("minSize");
        SHIFT_TO_BOUNDS = ModifyReason.of("shiftToBounds");
    }
    
    public enum DraggingType
    {
        TITLE_BAR, 
        CONTENT, 
        NONE;
    }
    
    public enum RescaleType
    {
        CORNER(new Edge[0]), 
        EDGES(new Edge[] { Edge.LEFT, Edge.TOP, Edge.RIGHT, Edge.BOTTOM }), 
        TOP_RIGHT_EDGES(new Edge[] { Edge.TOP, Edge.RIGHT }), 
        TOP_RIGHT_BOTTOM_EDGES(new Edge[] { Edge.TOP, Edge.RIGHT, Edge.BOTTOM }), 
        NONE(new Edge[0]);
        
        private final List<Edge> edges;
        
        private RescaleType(final Edge[] edges) {
            this.edges = Lists.newArrayList(edges);
        }
        
        public boolean isEdge() {
            return !this.edges.isEmpty();
        }
        
        public List<Edge> getEdges() {
            return this.edges;
        }
        
        public boolean hasEdge(final Edge edge) {
            return this.edges.contains(edge);
        }
    }
    
    public enum Edge
    {
        LEFT, 
        TOP, 
        RIGHT, 
        BOTTOM;
        
        public static final Edge[] VALUES;
        
        static {
            VALUES = values();
        }
    }
}
