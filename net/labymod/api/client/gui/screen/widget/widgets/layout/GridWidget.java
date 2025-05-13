// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout;

import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

@AutoWidget
public class GridWidget<T extends Widget> extends AbstractWidget<T>
{
    private static final ModifyReason ENTRY_BOUNDS;
    private final LssProperty<Integer> outlineThickness;
    private final LssProperty<Integer> columns;
    private final LssProperty<Integer> rows;
    private final LssProperty<Float> maxColumnWidth;
    private final LssProperty<Float> maxRowHeight;
    private final LssProperty<Orientation> iterate;
    private final LssProperty<Orientation> auto;
    
    public GridWidget() {
        this.outlineThickness = new LssProperty<Integer>(0);
        this.columns = new LssProperty<Integer>(0);
        this.rows = new LssProperty<Integer>(0);
        this.maxColumnWidth = new LssProperty<Float>(null);
        this.maxRowHeight = new LssProperty<Float>(null);
        this.iterate = new LssProperty<Orientation>(Orientation.VERTICAL);
        this.auto = new LssProperty<Orientation>(Orientation.NONE);
    }
    
    @Override
    protected void updateContentBounds() {
        final int rows = this.calculateRows();
        final int columns = this.calculateColumns();
        if (rows == 0 || columns == 0) {
            super.updateContentBounds();
            return;
        }
        final float columnWidth = this.getColumnWidth();
        final float rowHeight = this.getRowHeight();
        for (int i = 0; i < this.children.size(); ++i) {
            int xIndex = 0;
            int yIndex = 0;
            switch (this.iterate.get()) {
                case HORIZONTAL: {
                    xIndex = i % columns;
                    yIndex = i / columns;
                    break;
                }
                case VERTICAL: {
                    xIndex = i / rows;
                    yIndex = i % rows;
                    break;
                }
                default: {
                    continue;
                }
            }
            final Widget entry = this.children.get(i);
            final Bounds entryBounds = entry.bounds();
            final Integer outlineThickness = this.outlineThickness.get();
            final Bounds bounds = this.bounds();
            entryBounds.setOuterPosition(bounds.getX(BoundsType.INNER) + outlineThickness + xIndex * (columnWidth + outlineThickness), bounds.getY(BoundsType.INNER) + outlineThickness + yIndex * (rowHeight + outlineThickness), GridWidget.ENTRY_BOUNDS);
            entryBounds.setOuterSize(columnWidth, rowHeight, GridWidget.ENTRY_BOUNDS);
        }
        super.updateContentBounds();
    }
    
    public float getColumnWidth() {
        float width = 0.0f;
        for (final T child : this.children) {
            width = Math.max(width, child.getContentWidth(BoundsType.OUTER));
        }
        final Float maxColumnWidth = this.maxColumnWidth.get();
        return (maxColumnWidth == null) ? width : Math.min(maxColumnWidth, width);
    }
    
    public float getRowHeight() {
        float height = 0.0f;
        for (final T child : this.children) {
            height = Math.max(height, child.getContentHeight(BoundsType.OUTER));
        }
        final Float maxRowHeight = this.maxRowHeight.get();
        return (maxRowHeight == null) ? height : Math.min(maxRowHeight, height);
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        final int columns = this.calculateColumns();
        final Integer outlineThickness = this.outlineThickness.get();
        return this.getColumnWidth() * columns + outlineThickness + outlineThickness * columns;
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        final int rows = this.calculateRows();
        final Integer outlineThickness = this.outlineThickness.get();
        return this.getRowHeight() * rows + outlineThickness + outlineThickness * rows;
    }
    
    private int calculateColumns() {
        switch (this.auto.get()) {
            case VERTICAL: {
                final Bounds bounds = this.bounds();
                return (int)Math.floor(bounds.getWidth(BoundsType.INNER) / this.getColumnWidth());
            }
            case HORIZONTAL: {
                final int rows = this.calculateRows();
                if (rows != 0) {
                    return this.children.size() / rows + 1;
                }
                return 0;
            }
            default: {
                return this.columns.get();
            }
        }
    }
    
    private int calculateRows() {
        switch (this.auto.get()) {
            case VERTICAL: {
                final int columns = this.calculateColumns();
                if (columns != 0) {
                    return this.children.size() / columns + 1;
                }
                return 0;
            }
            case HORIZONTAL: {
                final Bounds bounds = this.bounds();
                return (int)Math.floor(bounds.getHeight(BoundsType.INNER) / this.getRowHeight());
            }
            default: {
                return this.rows.get();
            }
        }
    }
    
    public int getActualColumns() {
        return this.calculateColumns();
    }
    
    public int getActualRows() {
        return this.calculateRows();
    }
    
    public LssProperty<Integer> outlineThickness() {
        return this.outlineThickness;
    }
    
    public LssProperty<Integer> columns() {
        return this.columns;
    }
    
    public LssProperty<Integer> rows() {
        return this.rows;
    }
    
    public LssProperty<Float> maxColumnWidth() {
        return this.maxColumnWidth;
    }
    
    public LssProperty<Float> maxRowHeight() {
        return this.maxRowHeight;
    }
    
    public LssProperty<Orientation> iterate() {
        return this.iterate;
    }
    
    public LssProperty<Orientation> auto() {
        return this.auto;
    }
    
    static {
        ENTRY_BOUNDS = ModifyReason.of("entryBounds");
    }
}
