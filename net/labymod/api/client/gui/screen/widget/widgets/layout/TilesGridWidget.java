// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.property.Property;
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
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.TileWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.ListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

@AutoWidget
public class TilesGridWidget<T extends Widget> extends ListWidget<TileWidget<T>>
{
    private static final ModifyReason ENTRY_BOUNDS;
    private final LssProperty<Float> spaceBetweenEntries;
    private final LssProperty<Integer> tilesPerLine;
    private final LssProperty<Float> tileHeight;
    
    public TilesGridWidget() {
        this.spaceBetweenEntries = new LssProperty<Float>(0.0f);
        this.tilesPerLine = new LssProperty<Integer>(3);
        this.tileHeight = new LssProperty<Float>(-1.0f);
        this.translateX().addChangeListener((type, oldValue, newValue) -> this.updateVisibility());
        this.translateY().addChangeListener((type, oldValue, newValue) -> this.updateVisibility());
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.updateTiles();
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        final int tilesPerLine = this.tilesPerLine.get();
        final int lines = MathHelper.ceil(this.children.size() / (float)tilesPerLine);
        return this.getTileHeight() * lines + this.spaceBetweenEntries.get() * (lines - 1);
    }
    
    @Override
    public void updateVisibility(final ListWidget<?> list, final Parent parent) {
        super.updateVisibility(list, parent);
        if (this.parent == null) {
            return;
        }
        final ReasonableMutableRectangle parentBounds = parent.bounds().rectangle(BoundsType.MIDDLE);
        final float translateY = list.getTranslateY();
        for (final TileWidget<T> tile : this.children) {
            if (tile.isDragging()) {
                tile.setVisible(true);
            }
            else {
                final Bounds tileBounds = tile.bounds();
                final boolean isInRectangle = parentBounds.isYInRectangle(tileBounds.getY() + translateY) || parentBounds.isYInRectangle(tileBounds.getY() + tileBounds.getHeight() + translateY);
                tile.setVisible(isInRectangle);
            }
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
    
    public void updateTiles() {
        final boolean useFloatingPoint = this.useFloatingPointPosition().get();
        final float gridTileWidth = this.transformFloatingPoint(useFloatingPoint, this.getTileWidth());
        final float gridTileHeight = this.transformFloatingPoint(useFloatingPoint, this.getTileHeight());
        int indexX = 0;
        int indexY = 0;
        final float x = this.transformFloatingPoint(useFloatingPoint, this.bounds().getX());
        final float y = this.transformFloatingPoint(useFloatingPoint, this.bounds().getY());
        for (final TileWidget<T> tile : this.children) {
            final Bounds bounds = tile.bounds();
            final float horizontalOffset = this.transformFloatingPoint(useFloatingPoint, bounds.getHorizontalOffset(BoundsType.BORDER));
            final float verticalOffset = this.transformFloatingPoint(useFloatingPoint, bounds.getVerticalOffset(BoundsType.BORDER));
            final float spaceBetweenEntries = this.transformFloatingPoint(useFloatingPoint, this.spaceBetweenEntries.get());
            bounds.setOuterPosition(x + indexX * (gridTileWidth + spaceBetweenEntries), y + indexY * (gridTileHeight + spaceBetweenEntries), TilesGridWidget.ENTRY_BOUNDS);
            bounds.setOuterSize(gridTileWidth - horizontalOffset, gridTileHeight - verticalOffset, TilesGridWidget.ENTRY_BOUNDS);
            if (++indexX >= this.tilesPerLine.get()) {
                indexX = 0;
                ++indexY;
            }
        }
        this.updateVisibility();
    }
    
    public float getSpaceAvailable() {
        final float spaceBetween = this.spaceBetweenEntries.get();
        final Bounds bounds = this.bounds();
        if (spaceBetween == 0.0f) {
            return bounds.getWidth();
        }
        final float width = bounds.getWidth(BoundsType.INNER);
        return width - spaceBetween * (this.tilesPerLine.get() - 1);
    }
    
    public float getTileWidth() {
        return this.getSpaceAvailable() / this.tilesPerLine.get();
    }
    
    public float getTileHeight() {
        return (this.tileHeight.get() < 0.0f) ? this.getTileWidth() : this.tileHeight.get();
    }
    
    public void addTile(final T widget) {
        this.addChild(new TileWidget<T>(widget, 1, 1), false);
    }
    
    public void addTileInitialized(final T widget) {
        this.addChildInitialized(new TileWidget<T>(widget, 1, 1), false);
    }
    
    @Deprecated
    public void addUnsortedTile(final T widget) {
        this.addChild(new TileWidget<T>(widget, 1, 1), false);
    }
    
    public void addSortedTile(final T widget) {
        this.addChild(new TileWidget<T>(widget, 1, 1), true);
    }
    
    public void addSortedTileInitialized(final T widget) {
        this.addChildInitialized(new TileWidget<T>(widget, 1, 1), true);
    }
    
    public LssProperty<Float> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }
    
    public LssProperty<Integer> tilesPerLine() {
        return this.tilesPerLine;
    }
    
    public LssProperty<Float> tileHeight() {
        return this.tileHeight;
    }
    
    static {
        ENTRY_BOUNDS = ModifyReason.of("entryBounds");
    }
}
