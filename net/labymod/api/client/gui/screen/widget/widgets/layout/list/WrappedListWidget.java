// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout.list;

import net.labymod.api.util.bounds.ReasonableMutableRectangle;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;

@AutoWidget
public class WrappedListWidget<T extends Widget> extends ListWidget<T>
{
    private static final ModifyReason MODIFY_REASON;
    private final LssProperty<Float> spaceBetweenEntries;
    private final LssProperty<Float> verticalSpaceBetweenEntries;
    private final LssProperty<Float> horizontalSpaceBetweenEntries;
    private final LssProperty<Integer> maxLines;
    
    public WrappedListWidget() {
        this.spaceBetweenEntries = new LssProperty<Float>(0.0f);
        this.verticalSpaceBetweenEntries = new LssProperty<Float>(0.0f);
        this.horizontalSpaceBetweenEntries = new LssProperty<Float>(0.0f);
        this.maxLines = new LssProperty<Integer>(0);
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.updateChildren();
    }
    
    @Override
    public boolean hasAutoBounds(final Widget child, final AutoAlignType type) {
        return type == AutoAlignType.POSITION;
    }
    
    @Deprecated
    public LssProperty<Float> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }
    
    public LssProperty<Float> verticalSpaceBetweenEntries() {
        return this.verticalSpaceBetweenEntries;
    }
    
    public LssProperty<Float> horizontalSpaceBetweenEntries() {
        return this.horizontalSpaceBetweenEntries;
    }
    
    public LssProperty<Integer> maxLines() {
        return this.maxLines;
    }
    
    protected void updateChildren() {
        final int maxLines = this.maxLines.get();
        final float spaceBetweenEntries = this.spaceBetweenEntries.get();
        final float verticalSpaceBetweenEntries = this.verticalSpaceBetweenEntries.get();
        final float horizontalSpaceBetweenEntries = this.horizontalSpaceBetweenEntries.get();
        final Bounds bounds = this.bounds();
        final float maxX = bounds.getMaxX();
        final float startX = bounds.getX();
        final float startY = bounds.getY();
        float x = startX;
        float y = startY;
        float rowHeight = 0.0f;
        boolean overflow = false;
        for (final T child : this.children) {
            final ReasonableMutableRectangle childBounds = child.bounds().rectangle(BoundsType.OUTER);
            final float childWidth = childBounds.getWidth();
            if (!overflow && (x + childWidth > maxX || this.forceNewLine(child))) {
                x = startX;
                final float offsetY = rowHeight + spaceBetweenEntries + verticalSpaceBetweenEntries;
                y += offsetY;
                final float nextY = (float)MathHelper.ceil(y - startY);
                final float lineOffset = (float)MathHelper.ceil(maxLines * offsetY);
                if (maxLines > 0 && nextY >= lineOffset) {
                    y -= offsetY;
                    overflow = true;
                }
            }
            child.setVisible(!overflow);
            if (overflow) {
                continue;
            }
            childBounds.setPosition(x, y, WrappedListWidget.MODIFY_REASON);
            rowHeight = Math.max(childBounds.getHeight(), rowHeight);
            x += childWidth + spaceBetweenEntries + horizontalSpaceBetweenEntries;
        }
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        final float spaceBetweenEntries = this.spaceBetweenEntries.get();
        final float verticalSpaceBetweenEntries = this.verticalSpaceBetweenEntries.get();
        final Bounds bounds = this.bounds();
        final float maxX = bounds.getMaxX();
        final float startX = bounds.getX();
        final float startY = bounds.getY();
        float x = startX;
        float y = startY;
        float rowHeight = 0.0f;
        for (final T child : this.children) {
            if (!child.isVisible()) {
                continue;
            }
            final ReasonableMutableRectangle childBounds = child.bounds().rectangle(BoundsType.OUTER);
            final float childWidth = childBounds.getWidth();
            if (x + childWidth > maxX || this.forceNewLine(child)) {
                x = startX;
                y += rowHeight + spaceBetweenEntries + verticalSpaceBetweenEntries;
            }
            rowHeight = Math.max(childBounds.getHeight(), rowHeight);
            x += childWidth + spaceBetweenEntries + verticalSpaceBetweenEntries;
        }
        return y - startY + rowHeight + bounds.getVerticalOffset(type);
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        return this.bounds().getWidth(type);
    }
    
    protected boolean forceNewLine(final Widget child) {
        return false;
    }
    
    static {
        MODIFY_REASON = ModifyReason.of("ListBounds");
    }
}
