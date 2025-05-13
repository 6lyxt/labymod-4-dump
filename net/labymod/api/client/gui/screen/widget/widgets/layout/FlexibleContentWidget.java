// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.layout;

import net.labymod.api.client.gui.screen.widget.size.WidgetSize;
import net.labymod.api.client.gui.screen.widget.size.WidgetSide;
import net.labymod.api.client.gui.screen.AutoAlignType;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.util.ListOrder;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.FlexibleContentEntry;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class FlexibleContentWidget extends AbstractWidget<FlexibleContentEntry>
{
    private static final ModifyReason FLEX_ENTRY_POSITION;
    private static final ModifyReason FLEX_BOUNDS_VERTICAL;
    private static final ModifyReason FLEX_BOUNDS_HORIZONTAL;
    private final LssProperty<FlexibleContentOrientation> orientation;
    private final LssProperty<Integer> spaceBetweenEntries;
    private final LssProperty<ListOrder> listOrder;
    
    public FlexibleContentWidget() {
        this.orientation = new LssProperty<FlexibleContentOrientation>(FlexibleContentOrientation.VERTICAL);
        this.spaceBetweenEntries = new LssProperty<Integer>(0);
        this.listOrder = new LssProperty<ListOrder>(ListOrder.NORMAL);
    }
    
    @Override
    public void applyStyleSheet(final StyleSheet styleSheet) {
        for (int i = 0; i < 2; ++i) {
            super.applyStyleSheet(styleSheet);
        }
    }
    
    public FlexibleContentEntry addFlexibleContent(final Widget widget) {
        return this.addContent(widget, true);
    }
    
    public FlexibleContentEntry addContent(final Widget widget) {
        return this.addContent(widget, false);
    }
    
    private FlexibleContentEntry addContent(final Widget widget, final boolean flexible) {
        return this.addChild(new FlexibleContentEntry(widget, flexible));
    }
    
    public FlexibleContentEntry addFlexibleContentInitialized(final Widget widget) {
        return this.addContentInitialized(widget, true);
    }
    
    public FlexibleContentEntry addContentInitialized(final Widget widget) {
        return this.addContentInitialized(widget, false);
    }
    
    private FlexibleContentEntry addContentInitialized(final Widget widget, final boolean flexible) {
        return this.addChildInitialized(new FlexibleContentEntry(widget, flexible));
    }
    
    @Override
    protected void updateContentBounds() {
        final boolean verticalOrientation = this.orientation.get() == FlexibleContentOrientation.VERTICAL;
        final Bounds widgetBounds = this.bounds();
        final float innerWidgetHeight = widgetBounds.getHeight(BoundsType.INNER);
        final float innerWidgetWidth = widgetBounds.getWidth(BoundsType.INNER);
        float space = verticalOrientation ? innerWidgetHeight : innerWidgetWidth;
        int flexibleEntryAmount = 0;
        int visibleChildren = 0;
        if (space <= 0.0f) {
            return;
        }
        for (final FlexibleContentEntry entry : this.children) {
            if (entry.ignoreBounds().get()) {
                continue;
            }
            if (entry.isFlexible()) {
                ++flexibleEntryAmount;
                ++visibleChildren;
            }
            else {
                if (!entry.isVisible()) {
                    continue;
                }
                ++visibleChildren;
                space -= (verticalOrientation ? entry.bounds().getHeight(BoundsType.OUTER) : entry.bounds().getWidth(BoundsType.OUTER));
            }
        }
        if (visibleChildren != 0) {
            space -= (visibleChildren - 1) * this.spaceBetweenEntries.get();
        }
        if (flexibleEntryAmount != 0) {
            final float spacePerEntry = space / flexibleEntryAmount;
            for (final FlexibleContentEntry entry2 : this.children) {
                if (entry2.ignoreBounds().get()) {
                    continue;
                }
                final Bounds bounds = entry2.bounds();
                if (!entry2.isFlexible()) {
                    continue;
                }
                if (verticalOrientation) {
                    bounds.setOuterWidth(innerWidgetWidth, FlexibleContentWidget.FLEX_BOUNDS_VERTICAL);
                    bounds.setOuterHeight(spacePerEntry, FlexibleContentWidget.FLEX_BOUNDS_VERTICAL);
                }
                else {
                    bounds.setOuterWidth(spacePerEntry, FlexibleContentWidget.FLEX_BOUNDS_HORIZONTAL);
                    if (entry2.childWidget() instanceof ComponentWidget) {
                        continue;
                    }
                    bounds.setOuterHeight(innerWidgetHeight, FlexibleContentWidget.FLEX_BOUNDS_HORIZONTAL);
                }
            }
        }
        float offset = 0.0f;
        final boolean reverse = this.listOrder.get() == ListOrder.REVERSE;
        int i = reverse ? (this.children.size() - 1) : 0;
        while (true) {
            if (reverse) {
                if (i < 0) {
                    break;
                }
            }
            else if (i >= this.children.size()) {
                break;
            }
            final FlexibleContentEntry entry3 = (FlexibleContentEntry)this.children.get(i);
            if (!entry3.ignoreBounds().get()) {
                final Bounds bounds2 = entry3.bounds();
                float alignmentOffset = 0.0f;
                if (verticalOrientation) {
                    final float outerWidgetWidth = bounds2.getWidth(BoundsType.OUTER);
                    if (entry3.alignmentX().get() == WidgetAlignment.CENTER) {
                        alignmentOffset = innerWidgetWidth / 2.0f - outerWidgetWidth / 2.0f;
                    }
                    if (entry3.alignmentX().get() == WidgetAlignment.RIGHT) {
                        alignmentOffset = innerWidgetWidth - outerWidgetWidth;
                    }
                }
                else {
                    final float outerWidgetHeight = bounds2.getHeight(BoundsType.OUTER);
                    if (entry3.alignmentY().get() == WidgetAlignment.CENTER) {
                        alignmentOffset = innerWidgetHeight / 2.0f - outerWidgetHeight / 2.0f;
                    }
                    if (entry3.alignmentY().get() == WidgetAlignment.BOTTOM) {
                        alignmentOffset = innerWidgetHeight - outerWidgetHeight;
                    }
                }
                bounds2.setOuterX(widgetBounds.getX(BoundsType.INNER) + (verticalOrientation ? alignmentOffset : offset), FlexibleContentWidget.FLEX_ENTRY_POSITION);
                bounds2.setOuterY(widgetBounds.getY(BoundsType.INNER) + (verticalOrientation ? offset : alignmentOffset), FlexibleContentWidget.FLEX_ENTRY_POSITION);
                if (entry3.isVisible()) {
                    offset += (verticalOrientation ? entry3.bounds().getHeight(BoundsType.OUTER) : entry3.bounds().getWidth(BoundsType.OUTER));
                    offset += this.spaceBetweenEntries.get();
                }
            }
            i += (reverse ? -1 : 1);
        }
        this.updateChildren(this);
        super.updateContentBounds();
    }
    
    private void updateChildren(final Widget widget) {
        for (final Widget child : widget.getChildren()) {
            child.bounds().checkForChanges();
            this.updateChildren(child);
        }
    }
    
    @Override
    public float getContentWidth(final BoundsType type) {
        final Bounds bounds = this.bounds();
        if (this.orientation.get() == FlexibleContentOrientation.VERTICAL) {
            float maxWidth = 0.0f;
            for (final FlexibleContentEntry child : this.children) {
                if (child.ignoreBounds().get()) {
                    continue;
                }
                final Float childWidth = this.getFitWidth(child);
                maxWidth = Math.max(maxWidth, (childWidth != null) ? ((float)childWidth) : 0.0f);
            }
            return maxWidth + bounds.getHorizontalOffset(type);
        }
        float width = 0.0f;
        final int spaceBetweenEntries = this.spaceBetweenEntries.get();
        for (final FlexibleContentEntry child2 : this.children) {
            if (child2.ignoreBounds().get()) {
                continue;
            }
            final Float childWidth2 = this.getFitWidth(child2);
            width += ((childWidth2 != null) ? childWidth2 : 0.0f) + spaceBetweenEntries;
        }
        if (width != 0.0f) {
            width -= spaceBetweenEntries;
        }
        return width + bounds.getHorizontalOffset(type);
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        if (this.orientation.get() != FlexibleContentOrientation.VERTICAL) {
            float maxHeight = 0.0f;
            for (final FlexibleContentEntry child : this.children) {
                if (child.ignoreBounds().get()) {
                    continue;
                }
                final Float childHeight = this.getFitHeight(child);
                maxHeight = Math.max(maxHeight, (childHeight != null) ? ((float)childHeight) : 0.0f);
            }
            return Math.max(maxHeight, this.getDefaultContentHeight(type));
        }
        float height = 0.0f;
        final int spaceBetweenEntries = this.spaceBetweenEntries.get();
        for (final FlexibleContentEntry child2 : this.children) {
            if (child2.ignoreBounds().get()) {
                continue;
            }
            final Float childHeight2 = this.getFitHeight(child2);
            height += ((childHeight2 != null) ? childHeight2 : 0.0f) + spaceBetweenEntries;
        }
        return height - spaceBetweenEntries;
    }
    
    public LssProperty<Integer> spaceBetweenEntries() {
        return this.spaceBetweenEntries;
    }
    
    public LssProperty<FlexibleContentOrientation> orientation() {
        return this.orientation;
    }
    
    public LssProperty<ListOrder> listOrder() {
        return this.listOrder;
    }
    
    @Override
    public boolean hasAutoBounds(final Widget child, final AutoAlignType type) {
        if (type == AutoAlignType.WIDTH || type == AutoAlignType.HEIGHT) {
            return child instanceof FlexibleContentEntry && ((FlexibleContentEntry)child).isFlexible() && !((FlexibleContentEntry)child).ignoreBounds().get() && ((type == AutoAlignType.WIDTH && !this.hasSize(WidgetSide.WIDTH, WidgetSize.Type.FIT_CONTENT)) || (type == AutoAlignType.HEIGHT && !this.hasSize(WidgetSide.HEIGHT, WidgetSize.Type.FIT_CONTENT)));
        }
        return (type != AutoAlignType.POSITION || !(child instanceof FlexibleContentEntry) || !((FlexibleContentEntry)child).ignoreBounds().get()) && type == AutoAlignType.POSITION;
    }
    
    static {
        FLEX_ENTRY_POSITION = ModifyReason.of("flexEntryPosition");
        FLEX_BOUNDS_VERTICAL = ModifyReason.of("flexBoundsVertical");
        FLEX_BOUNDS_HORIZONTAL = ModifyReason.of("flexBoundsHorizontal");
    }
    
    public enum FlexibleContentOrientation
    {
        VERTICAL, 
        HORIZONTAL;
    }
}
