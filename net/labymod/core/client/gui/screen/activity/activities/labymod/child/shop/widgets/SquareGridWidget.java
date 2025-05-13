// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets;

import net.labymod.api.property.Property;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import java.util.Iterator;
import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

@AutoWidget
public class SquareGridWidget<T extends Widget> extends ListWidget<T>
{
    private static final ModifyReason ENTRY_BOUNDS;
    private final LssProperty<Integer> preferredSquareHeight;
    private final LssProperty<Integer> spaceBetweenEntries;
    private float squareSize;
    private int squaresPerLine;
    
    public SquareGridWidget() {
        this.preferredSquareHeight = new LssProperty<Integer>(32);
        this.spaceBetweenEntries = new LssProperty<Integer>(1);
        this.squareSize = 0.0f;
        this.squaresPerLine = -1;
        this.translateX().addChangeListener((type, oldValue, newValue) -> this.updateVisibility());
        this.translateY().addChangeListener((type, oldValue, newValue) -> this.updateVisibility());
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.squaresPerLine = -1;
        this.update();
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        this.recalculateSquares();
        final int lines = MathHelper.ceil(this.children.size() / (float)this.squaresPerLine);
        return this.squareSize * lines + this.spaceBetweenEntries.get() * (lines - 1);
    }
    
    @Override
    public void updateVisibility(final ListWidget<?> list, final Parent parent) {
        super.updateVisibility(list, parent);
        if (this.parent == null) {
            return;
        }
        final ReasonableMutableRectangle parentBounds = parent.bounds().rectangle(BoundsType.MIDDLE);
        final float translateY = list.getTranslateY();
        for (final T child : this.children) {
            if (child.isDragging()) {
                child.setVisible(true);
            }
            else {
                final Bounds childBounds = child.bounds();
                final boolean isInRectangle = parentBounds.isYInRectangle(childBounds.getY() + translateY) || parentBounds.isYInRectangle(childBounds.getY() + childBounds.getHeight() + translateY);
                child.setVisible(isInRectangle);
            }
        }
    }
    
    private void recalculateSquares() {
        final Bounds bounds = this.bounds();
        final float width = bounds.getWidth();
        final int preferredHeight = this.preferredSquareHeight.get();
        if (preferredHeight > width) {
            this.squaresPerLine = 1;
            this.squareSize = width;
            return;
        }
        final int spaceBetween = this.spaceBetweenEntries.get();
        final float actualSquaresPerLine = width / (this.preferredSquareHeight.get() + spaceBetween);
        final int ceiledSquaresPerLine = MathHelper.ceil(actualSquaresPerLine);
        int squaresPerLine;
        if (ceiledSquaresPerLine - actualSquaresPerLine < 0.4) {
            squaresPerLine = ceiledSquaresPerLine;
        }
        else {
            squaresPerLine = MathHelper.floor(actualSquaresPerLine);
        }
        this.squaresPerLine = squaresPerLine;
        if (spaceBetween == 0) {
            this.squareSize = width / squaresPerLine;
        }
        else {
            this.squareSize = (width - (squaresPerLine - 1) * spaceBetween) / squaresPerLine;
        }
    }
    
    private void updateVisibility() {
        Parent parent = this.parent;
        ListWidget<?> list = this;
        final Widget closestContentWidget = this.getClosestContentWidget();
        if (closestContentWidget instanceof final ScrollWidget scroll) {
            parent = closestContentWidget;
            final Widget contentWidget = scroll.contentWidget();
            if (contentWidget instanceof ListWidget) {
                final ListWidget<?> content = list = (ListWidget<?>)contentWidget;
            }
        }
        this.updateVisibility(list, parent);
    }
    
    public LssProperty<Integer> preferredSquareHeight() {
        return this.preferredSquareHeight;
    }
    
    public LssProperty<Integer> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }
    
    protected void update() {
        int indexX = 0;
        int indexY = 0;
        this.recalculateSquares();
        final int spaceBetweenEntries = this.spaceBetweenEntries.get();
        for (final T child : this.children) {
            final Bounds bounds = child.bounds();
            final float horizontalOffset = bounds.getHorizontalOffset(BoundsType.BORDER);
            final float verticalOffset = bounds.getVerticalOffset(BoundsType.BORDER);
            bounds.setOuterPosition(this.bounds().getX() + indexX * (this.squareSize + spaceBetweenEntries), this.bounds().getY() + indexY * (this.squareSize + spaceBetweenEntries), SquareGridWidget.ENTRY_BOUNDS);
            final float height = bounds.getHeight();
            bounds.setOuterSize(this.squareSize - horizontalOffset, this.squareSize - verticalOffset, SquareGridWidget.ENTRY_BOUNDS);
            if (bounds.getHeight() != height) {
                child.updateBounds();
            }
            if (++indexX >= this.squaresPerLine) {
                indexX = 0;
                ++indexY;
            }
        }
        this.updateVisibility();
    }
    
    static {
        ENTRY_BOUNDS = ModifyReason.of("entryBounds");
    }
}
