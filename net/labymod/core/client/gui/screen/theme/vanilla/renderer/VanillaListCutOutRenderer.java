// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.widget.util.WidgetUtil;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.OffsetSide;
import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.PositionedBounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.List;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

public class VanillaListCutOutRenderer extends VanillaBackgroundRenderer
{
    public VanillaListCutOutRenderer() {
        super("ListCutOut");
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        if (widget.backgroundImage != null) {
            super.renderPre(widget, context);
        }
        else if (widget.backgroundColor().get() != 0) {
            if (!(widget instanceof HorizontalListWidget) && !(widget instanceof VerticalListWidget)) {
                return;
            }
            final ListWidget<?> list = (ListWidget)widget;
            if (this.isAnyOutOfBounds(list.bounds(), list.getChildren())) {
                super.renderPre(widget, context);
                return;
            }
            final Bounds bounds = widget.bounds();
            final BatchRectangleRenderer renderer = this.labyAPI.renderPipeline().rectangleRenderer().beginBatch(context.stack());
            if (list instanceof HorizontalListWidget) {
                this.renderHorizontal(renderer, list, bounds, widget.backgroundColor().get());
            }
            else {
                this.renderVertical(renderer, list, bounds, widget.backgroundColor().get());
            }
            renderer.upload();
        }
    }
    
    private boolean isAnyOutOfBounds(final Bounds bounds, final List<? extends Widget> widgets) {
        for (final Widget widget : widgets) {
            if (widget.isVisible() && !widget.isOutOfBounds() && !this.isInBounds(bounds, widget.bounds().getLeft(), widget.bounds().getTop(), widget.bounds().getRight(), widget.bounds().getBottom())) {
                return true;
            }
        }
        return false;
    }
    
    private void renderHorizontal(final BatchRectangleRenderer renderer, final ListWidget<?> list, final Bounds bounds, final int bgColor) {
        final float top = bounds.getTop(BoundsType.INNER);
        final float bottom = bounds.getBottom(BoundsType.INNER);
        Bounds lastBounds = new PositionedBounds(bounds.getLeft(BoundsType.MIDDLE), bounds.getTop(BoundsType.MIDDLE), bounds.getLeft(BoundsType.MIDDLE), bounds.getTop(BoundsType.MIDDLE));
        for (final Widget child : list.getChildren()) {
            if (!child.isVisible()) {
                continue;
            }
            this.renderHorizontalBetween(renderer, bounds, lastBounds, top, bottom, child, child.bounds(), bgColor);
            this.renderOffset(renderer, bounds, child.bounds(), bgColor, Orientation.HORIZONTAL);
            lastBounds = child.bounds();
        }
        this.renderHorizontalBetween(renderer, bounds, lastBounds, top, bottom, null, bounds, bgColor);
    }
    
    private void renderHorizontalBetween(final BatchRectangleRenderer renderer, final Bounds parentBounds, final Bounds lastBounds, final float top, final float bottom, final Widget child, final Bounds childBounds, final int bgColor) {
        final float left = lastBounds.getRight(BoundsType.MIDDLE);
        final float right = this.isNotCutOut(child) ? childBounds.getRight(BoundsType.MIDDLE) : childBounds.getLeft(BoundsType.MIDDLE);
        if (left == right) {
            return;
        }
        if (!this.isInBounds(parentBounds, left, top, right, bottom)) {
            return;
        }
        renderer.pos(left, top, right, bottom).color(bgColor).build();
    }
    
    private void renderVertical(final BatchRectangleRenderer renderer, final ListWidget<?> list, final Bounds bounds, final int bgColor) {
        final float left = bounds.getLeft(BoundsType.INNER);
        final float right = bounds.getRight(BoundsType.INNER);
        Bounds lastBounds = new PositionedBounds(bounds.getLeft(BoundsType.MIDDLE), bounds.getTop(BoundsType.MIDDLE), bounds.getLeft(BoundsType.MIDDLE), bounds.getTop(BoundsType.MIDDLE));
        for (final Widget child : list.getChildren()) {
            if (!child.isVisible()) {
                continue;
            }
            this.renderVerticalBetween(renderer, bounds, lastBounds, left, right, child, child.bounds(), bgColor);
            this.renderOffset(renderer, bounds, child.bounds(), bgColor, Orientation.VERTICAL);
            lastBounds = child.bounds();
        }
        this.renderVerticalBetween(renderer, bounds, lastBounds, left, right, null, bounds, bgColor);
    }
    
    private void renderVerticalBetween(final BatchRectangleRenderer renderer, final Bounds parentBounds, final Bounds lastBounds, final float left, final float right, final Widget child, final Bounds childBounds, final int bgColor) {
        final float top = lastBounds.getBottom(BoundsType.MIDDLE);
        final float bottom = this.isNotCutOut(child) ? childBounds.getBottom(BoundsType.MIDDLE) : childBounds.getTop(BoundsType.MIDDLE);
        if (top == bottom || !this.isInBounds(parentBounds, left, top, right, bottom)) {
            return;
        }
        final float leftOffset = parentBounds.getOffset(BoundsType.MIDDLE, OffsetSide.LEFT);
        final float rightOffset = parentBounds.getOffset(BoundsType.MIDDLE, OffsetSide.RIGHT);
        renderer.pos(left - leftOffset, top, right + rightOffset, bottom).color(bgColor).build();
    }
    
    private boolean isNotCutOut(Widget widget) {
        if (widget == null) {
            return true;
        }
        if (!widget.isVisible()) {
            return true;
        }
        widget = WidgetUtil.unwrapWidget(widget);
        return widget instanceof AbstractWidget && (int)((AbstractWidget)widget).backgroundColor().get() == 0;
    }
    
    private boolean isInBounds(final Bounds bounds, final float left, final float top, final float right, final float bottom) {
        return left >= bounds.getLeft(BoundsType.MIDDLE) && top >= bounds.getTop(BoundsType.MIDDLE) && right <= bounds.getRight(BoundsType.MIDDLE) && bottom <= bounds.getBottom(BoundsType.MIDDLE);
    }
    
    private void renderOffset(final BatchRectangleRenderer renderer, final Bounds bounds, final Bounds childBounds, final int bgColor, final Orientation orientation) {
        if (orientation == Orientation.HORIZONTAL) {
            final float topOffset = bounds.getOffset(BoundsType.MIDDLE, OffsetSide.TOP);
            final float bottomOffset = bounds.getOffset(BoundsType.MIDDLE, OffsetSide.BOTTOM);
            if (topOffset != 0.0f) {
                final float top = childBounds.getTop(BoundsType.INNER);
                renderer.pos(childBounds.getLeft(BoundsType.MIDDLE), top - topOffset, childBounds.getRight(BoundsType.MIDDLE), top).color(bgColor).build();
            }
            if (bottomOffset != 0.0f) {
                final float bottom = childBounds.getBottom(BoundsType.INNER);
                renderer.pos(childBounds.getLeft(BoundsType.MIDDLE), bottom, childBounds.getRight(BoundsType.MIDDLE), bottom + bottomOffset).color(bgColor).build();
            }
            return;
        }
        final float leftOffset = bounds.getOffset(BoundsType.MIDDLE, OffsetSide.LEFT);
        final float rightOffset = bounds.getOffset(BoundsType.MIDDLE, OffsetSide.RIGHT);
        if (leftOffset != 0.0f) {
            final float left = childBounds.getLeft(BoundsType.INNER);
            renderer.pos(left - leftOffset, childBounds.getTop(BoundsType.MIDDLE), left, childBounds.getBottom(BoundsType.MIDDLE)).color(bgColor).build();
        }
        if (rightOffset != 0.0f) {
            final float right = childBounds.getRight(BoundsType.INNER);
            renderer.pos(right, childBounds.getTop(BoundsType.MIDDLE), right + rightOffset, childBounds.getBottom(BoundsType.MIDDLE)).color(bgColor).build();
        }
    }
}
