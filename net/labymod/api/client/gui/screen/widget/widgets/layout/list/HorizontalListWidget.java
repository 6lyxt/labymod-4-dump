// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout.list;

import net.labymod.api.client.gui.screen.AutoAlignType;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;

@AutoWidget
public class HorizontalListWidget extends ListWidget<HorizontalListEntry>
{
    private static final ModifyReason LIST_POSITION;
    private static final ModifyReason FILL_WIDTH;
    private final LssProperty<Float> spaceLeft;
    private final LssProperty<Float> spaceRight;
    private final LssProperty<Integer> spaceBetweenEntries;
    private final LssProperty<HorizontalListLayout> layout;
    
    public HorizontalListWidget() {
        this.spaceLeft = new LssProperty<Float>(0.0f);
        this.spaceRight = new LssProperty<Float>(0.0f);
        this.spaceBetweenEntries = new LssProperty<Integer>(1);
        this.layout = new LssProperty<HorizontalListLayout>(HorizontalListLayout.STACK);
    }
    
    @Override
    protected void updateContentBounds() {
        for (final HorizontalAlignment alignment : HorizontalAlignment.VALUES) {
            this.updateContentBounds(alignment);
        }
        super.updateContentBounds();
    }
    
    protected void updateContentBounds(final HorizontalAlignment alignment) {
        if (alignment == HorizontalAlignment.NONE) {
            return;
        }
        final Bounds bounds = this.bounds();
        final float spaceLeft = this.spaceLeft.get();
        final float boundsWidth = bounds.getWidth(BoundsType.INNER);
        final float width = boundsWidth - spaceLeft - this.spaceRight.get();
        final float height = bounds.getHeight(BoundsType.INNER);
        float x = bounds.getX(BoundsType.INNER) + spaceLeft;
        final float y = bounds.getY(BoundsType.INNER);
        float totalWeight = 0.0f;
        float skipWidth = 0.0f;
        int totalChildren = 0;
        for (final HorizontalListEntry child : this.children) {
            if (!child.ignoreWidth().get()) {
                if (!child.isVisible()) {
                    continue;
                }
                ++totalChildren;
                if (this.layout.get() != HorizontalListLayout.FILL || !child.skipFill().get()) {
                    totalWeight += child.flex().get();
                }
                else {
                    skipWidth += child.bounds().getWidth(BoundsType.OUTER);
                }
            }
        }
        final int amountEntries = this.children.size();
        final int amountGaps = amountEntries - 1;
        final float totalFillWidth = width - amountGaps * this.spaceBetweenEntries.get() - skipWidth;
        final float totalSpreadWidth = this.getTotalWidth(alignment, 0.0f);
        final float space = (float)((this.layout.get() == HorizontalListLayout.SPREAD) ? ((boundsWidth - totalSpreadWidth) / (totalChildren - 1)) : this.spaceBetweenEntries.get());
        final float totalSpacedWidth = this.getTotalWidth(alignment, space);
        switch (alignment) {
            case CENTER: {
                x += width / 2.0f - totalSpacedWidth / 2.0f;
                break;
            }
            case RIGHT: {
                x += width;
                break;
            }
        }
        for (final HorizontalListEntry entry : this.children) {
            if (!entry.ignoreHeight().get() && entry.isVisible()) {
                if (entry.alignment().get() != alignment) {
                    continue;
                }
                final float weight = entry.flex().get() / totalWeight;
                final Bounds childBounds = entry.bounds();
                final float entryWidth = (this.layout.get() == HorizontalListLayout.FILL && !entry.skipFill().get()) ? (totalFillWidth * weight) : childBounds.getWidth(BoundsType.OUTER);
                if (alignment == HorizontalAlignment.RIGHT) {
                    x -= entryWidth;
                }
                float entryY = y;
                switch (entry.alignmentY().get()) {
                    case CENTER: {
                        entryY += height / 2.0f - childBounds.getHeight(BoundsType.OUTER) / 2.0f;
                        break;
                    }
                    case BOTTOM: {
                        entryY += height - childBounds.getHeight(BoundsType.OUTER);
                        break;
                    }
                }
                final boolean useFloatingPointPosition = this.useFloatingPointPosition().getOrDefault(false);
                childBounds.setOuterPosition(MathHelper.applyFloatingPointPosition(useFloatingPointPosition, x), MathHelper.applyFloatingPointPosition(useFloatingPointPosition, entryY), HorizontalListWidget.LIST_POSITION);
                if (this.layout.get() == HorizontalListLayout.FILL && !entry.skipFill().get()) {
                    childBounds.setOuterWidth(entryWidth, HorizontalListWidget.FILL_WIDTH);
                }
                if (alignment == HorizontalAlignment.RIGHT) {
                    x -= space;
                }
                else {
                    x += entryWidth + space;
                }
                entry.updateBounds();
            }
        }
        this.handleSizeAttributes();
    }
    
    protected float getTotalWidth(final HorizontalAlignment alignment, final float spaceBetweenEntries) {
        float totalWidth = 0.0f;
        for (final HorizontalListEntry entry : this.children) {
            if (!entry.ignoreWidth().get() && entry.isVisible()) {
                if (entry.alignment().get() != alignment) {
                    continue;
                }
                totalWidth += entry.bounds().getWidth(BoundsType.OUTER) + spaceBetweenEntries;
            }
        }
        return Math.max(0.0f, totalWidth - spaceBetweenEntries);
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        if (this.children.isEmpty()) {
            return 0.0f;
        }
        final float[] width = new float[HorizontalAlignment.VALUES.length];
        for (final HorizontalAlignment value : HorizontalAlignment.VALUES) {
            width[value.ordinal()] = 0.0f;
        }
        final int spaceBetweenEntries = this.spaceBetweenEntries.get();
        for (final HorizontalListEntry child : this.children) {
            if (!child.ignoreWidth().get() && child.isVisible()) {
                final float value2 = child.bounds().getWidth(BoundsType.OUTER) + spaceBetweenEntries;
                final float[] array = width;
                final int ordinal = child.alignment().get().ordinal();
                array[ordinal] += value2;
            }
        }
        float leftWidth = width[HorizontalAlignment.LEFT.ordinal()];
        float centerWidth = width[HorizontalAlignment.CENTER.ordinal()];
        float rightWidth = width[HorizontalAlignment.RIGHT.ordinal()];
        if (leftWidth == 0.0f && centerWidth == 0.0f && rightWidth == 0.0f) {
            return 0.0f;
        }
        float onlyWidth = 0.0f;
        if (leftWidth == 0.0f && rightWidth == 0.0f) {
            onlyWidth = centerWidth;
        }
        else if (leftWidth == 0.0f && centerWidth == 0.0f) {
            onlyWidth = rightWidth;
        }
        else if (centerWidth == 0.0f && rightWidth == 0.0f) {
            onlyWidth = leftWidth;
        }
        if (onlyWidth != 0.0f) {
            return onlyWidth - spaceBetweenEntries + this.bounds().getHorizontalOffset(type);
        }
        if (centerWidth == 0.0f) {
            return leftWidth + rightWidth;
        }
        if (leftWidth != 0.0f) {
            leftWidth -= spaceBetweenEntries;
        }
        centerWidth -= spaceBetweenEntries;
        if (rightWidth != 0.0f) {
            rightWidth -= spaceBetweenEntries;
        }
        final float sideWidth = Math.max(leftWidth, rightWidth);
        return sideWidth * 2.0f + centerWidth + this.bounds().getHorizontalOffset(type);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        float height = 0.0f;
        for (final HorizontalListEntry child : this.children) {
            if (!child.ignoreHeight().get()) {
                height = Math.max(height, child.bounds().getHeight(BoundsType.OUTER));
            }
        }
        return height + this.bounds().getVerticalOffset(type);
    }
    
    public HorizontalListEntry addEntry(final Widget widget) {
        final HorizontalListEntry entry = new HorizontalListEntry(widget);
        this.addChild(entry);
        return entry;
    }
    
    public HorizontalListEntry addEntry(final int index, final Widget widget) {
        final HorizontalListEntry entry = new HorizontalListEntry(widget);
        this.addChild(index, entry);
        return entry;
    }
    
    public HorizontalListEntry addEntryInitialized(final Widget widget) {
        final HorizontalListEntry entry = new HorizontalListEntry(widget);
        this.addChildInitialized(entry);
        return entry;
    }
    
    public LssProperty<Float> spaceLeft() {
        return this.spaceLeft;
    }
    
    public LssProperty<Float> spaceRight() {
        return this.spaceRight;
    }
    
    public LssProperty<Integer> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }
    
    public LssProperty<HorizontalListLayout> layout() {
        return this.layout;
    }
    
    @Override
    public boolean hasAutoBounds(final Widget child, final AutoAlignType type) {
        return type == AutoAlignType.POSITION || (type == AutoAlignType.WIDTH && child instanceof HorizontalListEntry && !((HorizontalListEntry)child).skipFill().get() && this.layout.get() == HorizontalListLayout.FILL);
    }
    
    static {
        LIST_POSITION = ModifyReason.of("listPosition");
        FILL_WIDTH = ModifyReason.of("fillWidth");
    }
    
    public enum HorizontalListLayout
    {
        SPREAD, 
        FILL, 
        STACK;
    }
}
