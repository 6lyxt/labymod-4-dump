// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.navigation;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.context.ContextMenuEntry;
import net.labymod.api.client.gui.screen.widget.context.ContextMenu;
import net.labymod.api.client.gui.navigation.NavigationElement;
import java.util.Iterator;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement;
import net.labymod.api.client.gui.navigation.elements.ScreenNavigationElement;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.NavigationElementWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.collection.Lists;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement;
import java.util.List;
import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;

@Deprecated
@AutoWidget
public class NavigationListWidget extends HorizontalListWidget
{
    private static final ModifyReason LIST_POSITION;
    private final LssProperty<HorizontalAlignment> priorityAlignment;
    private final LssProperty<Float> maxPadding;
    private final NavigationWrapper wrapper;
    private final List<AbstractNavigationElement<?>> overflowElements;
    private HorizontalListEntry overflowWidget;
    private ButtonWidget triggerWidget;
    
    public NavigationListWidget(final NavigationWrapper wrapper) {
        this.priorityAlignment = new LssProperty<HorizontalAlignment>(null);
        this.maxPadding = new LssProperty<Float>(0.0f);
        this.wrapper = wrapper;
        this.overflowElements = (List<AbstractNavigationElement<?>>)Lists.newArrayList();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.triggerWidget = ButtonWidget.component(Component.text("..."));
        this.setContextMenu();
        final ButtonWidget triggerWidget = this.triggerWidget;
        final ButtonWidget triggerWidget2 = this.triggerWidget;
        Objects.requireNonNull(triggerWidget2);
        triggerWidget.setPressable(triggerWidget2::openContextMenu);
        (this.overflowWidget = this.addEntry(this.triggerWidget)).addId("overflow-widget");
    }
    
    @Override
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        this.setContextMenu();
        for (final HorizontalListEntry child : this.children) {
            final Widget widget = child.childWidget();
            if (!(widget instanceof NavigationElementWidget)) {
                continue;
            }
            final NavigationElementWidget elementWidget = (NavigationElementWidget)widget;
            final NavigationElement<?> element = elementWidget.getElement();
            if (!(elementWidget.getWidget() instanceof ButtonWidget)) {
                continue;
            }
            if (!(element instanceof ScreenNavigationElement)) {
                continue;
            }
            final ScreenNavigationElement screenNavigationElement = (ScreenNavigationElement)element;
            if (!this.wrapper.isActive(screenNavigationElement)) {
                continue;
            }
            if (!this.overflowElements.contains(screenNavigationElement)) {
                continue;
            }
            this.triggerWidget.updateIcon(((ButtonWidget)elementWidget.getWidget()).icon().get());
            break;
        }
    }
    
    @Override
    public HorizontalListEntry addEntry(final Widget widget) {
        return super.addEntry(widget);
    }
    
    @Override
    protected void updateContentBounds() {
        final HorizontalAlignment priorityAlignment = this.priorityAlignment.get();
        if (priorityAlignment == null) {
            if (this.overflowWidget != null) {
                this.overflowWidget.setVisible(false);
            }
            super.updateContentBounds();
            return;
        }
        if (this.overflowWidget != null) {
            this.overflowWidget.alignment().set((priorityAlignment == HorizontalAlignment.RIGHT) ? HorizontalAlignment.LEFT : HorizontalAlignment.CENTER);
        }
        this.updateContentBounds(priorityAlignment);
        for (final HorizontalAlignment alignment : HorizontalAlignment.VALUES) {
            if (alignment != priorityAlignment) {
                this.updateContentBounds(alignment);
            }
        }
        super.updateContentBounds();
    }
    
    private void setContextMenu() {
        final ContextMenu contextMenu = new ContextMenu();
        for (final AbstractNavigationElement<?> element : this.overflowElements) {
            final ContextMenuEntry.Builder builder = ContextMenuEntry.builder();
            builder.icon(element.getIcon());
            builder.text(element.getDisplayName());
            if (element instanceof final ScreenNavigationElement screenNavigationElement) {
                builder.disabled(() -> this.wrapper.isActive(screenNavigationElement));
                builder.clickHandler(entry -> {
                    this.wrapper.displayScreen(screenNavigationElement);
                    return true;
                });
            }
            contextMenu.addEntry(builder.build());
        }
        this.triggerWidget.setContextMenu(contextMenu);
    }
    
    @Override
    protected void updateContentBounds(final HorizontalAlignment alignment) {
        final HorizontalAlignment priorityAlignment = this.priorityAlignment.get();
        if (priorityAlignment == null || alignment == HorizontalAlignment.NONE || alignment == HorizontalAlignment.RIGHT) {
            super.updateContentBounds(alignment);
            return;
        }
        if (priorityAlignment == HorizontalAlignment.CENTER && alignment == HorizontalAlignment.CENTER) {
            this.priorityCenter();
        }
        else if (priorityAlignment == HorizontalAlignment.RIGHT && alignment == HorizontalAlignment.LEFT) {
            this.priorityRight();
        }
        else {
            super.updateContentBounds(alignment);
        }
    }
    
    private void priorityCenter() {
        final float spaceLeft = this.spaceLeft().get();
        final Bounds bounds = this.bounds();
        final float width = bounds.getWidth(BoundsType.INNER) - spaceLeft - this.spaceRight().get();
        final float height = bounds.getHeight(BoundsType.INNER);
        int children = 0;
        float minWidth = 0.0f;
        for (final HorizontalListEntry entry : this.children) {
            final Widget widget = this.getWidgetFromEntry(entry, HorizontalAlignment.CENTER);
            if (widget == null) {
                continue;
            }
            ++children;
            final float childWidth = widget.getContentWidth(BoundsType.INNER);
            if (minWidth >= childWidth) {
                continue;
            }
            minWidth = childWidth;
        }
        minWidth = ((minWidth == 0.0f) ? (width / children) : minWidth);
        if (minWidth == 0.0f) {
            return;
        }
        float maxPadding;
        float spaceBetweenEntries;
        float padding;
        for (maxPadding = this.maxPadding.get(), spaceBetweenEntries = this.spaceBetweenEntries().get(), padding = 0.0f; padding < maxPadding && (minWidth + spaceBetweenEntries + padding * 2.0f) * children < width; ++padding) {}
        float preTotalWidth = 0.0f;
        for (int i = 0; i < children; ++i) {
            preTotalWidth += minWidth + spaceBetweenEntries + padding * 2.0f;
        }
        float totalWidth = 0.0f;
        final float entryWidth = minWidth + padding * 2.0f;
        final float x = bounds.getX() + spaceLeft + (width - preTotalWidth) / 2.0f;
        final float y = bounds.getY();
        for (final HorizontalListEntry entry2 : this.children) {
            final AbstractWidget<?> widget2 = this.getWidgetFromEntry(entry2, HorizontalAlignment.CENTER);
            if (widget2 == null) {
                continue;
            }
            float entryY = y;
            final Bounds childBounds = entry2.bounds();
            switch (entry2.alignmentY().get()) {
                case CENTER: {
                    entryY += height / 2.0f - childBounds.getHeight(BoundsType.OUTER) / 2.0f;
                    break;
                }
                case BOTTOM: {
                    entryY += height - childBounds.getHeight(BoundsType.OUTER);
                    break;
                }
            }
            final float entryX = x + totalWidth;
            childBounds.setMiddleLeft(((boolean)this.useFloatingPointPosition().get()) ? entryX : ((float)(int)entryX), NavigationListWidget.LIST_POSITION);
            childBounds.setMiddleTop(((boolean)this.useFloatingPointPosition().get()) ? entryY : ((float)(int)entryY), NavigationListWidget.LIST_POSITION);
            childBounds.setMiddleWidth(entryWidth, NavigationListWidget.LIST_POSITION);
            childBounds.setMiddleHeight(height, NavigationListWidget.LIST_POSITION);
            entry2.updateBounds();
            totalWidth += entryWidth + spaceBetweenEntries;
        }
    }
    
    private AbstractWidget<?> getWidgetFromEntry(final HorizontalListEntry entry, final HorizontalAlignment alignment) {
        final Widget widget = entry.childWidget();
        if (!(widget instanceof AbstractWidget) || entry == this.overflowWidget || (!(boolean)((AbstractWidget)widget).visible().get() && !widget.hasId("overflown"))) {
            return null;
        }
        if (entry.ignoreWidth().get() || entry.alignment().get() != alignment) {
            return null;
        }
        return (AbstractWidget)widget;
    }
    
    private void priorityRight() {
        final Bounds bounds = this.bounds();
        final float used = this.getTotalWidth(HorizontalAlignment.RIGHT, this.spaceBetweenEntries().get());
        final float spaceLeft = this.spaceLeft().get();
        final float width = bounds.getWidth(BoundsType.INNER) - spaceLeft - this.spaceRight().get() - used;
        final float height = bounds.getHeight(BoundsType.INNER);
        final float x = bounds.getX(BoundsType.INNER) + spaceLeft;
        final float y = bounds.getY(BoundsType.INNER);
        final float spaceBetweenEntries = this.spaceBetweenEntries().get();
        float totalWidth = 0.0f;
        final float overflowWidth = (this.overflowWidget != null && !this.overflowElements.isEmpty()) ? (this.overflowWidget.bounds().getWidth(BoundsType.OUTER) + spaceBetweenEntries) : 0.0f;
        boolean overflow = false;
        this.overflowElements.clear();
        for (final HorizontalListEntry entry : this.children) {
            final AbstractWidget<?> widget = this.getWidgetFromEntry(entry, HorizontalAlignment.LEFT);
            if (widget == null) {
                continue;
            }
            final float entryWidth = entry.bounds().getWidth(BoundsType.OUTER);
            if (totalWidth + entryWidth + spaceBetweenEntries + overflowWidth > width || overflow) {
                overflow = true;
                widget.opacity().set(0.0f);
                if (!(widget instanceof NavigationElementWidget)) {
                    continue;
                }
                final NavigationElementWidget element = (NavigationElementWidget)widget;
                final NavigationElement<?> navigationElement = element.getElement();
                if (!(navigationElement instanceof AbstractNavigationElement)) {
                    continue;
                }
                this.overflowElements.add((AbstractNavigationElement)navigationElement);
            }
            else {
                float entryY = y;
                switch (entry.alignmentY().get()) {
                    case CENTER: {
                        entryY += height / 2.0f - entry.bounds().getHeight(BoundsType.OUTER) / 2.0f;
                        break;
                    }
                    case BOTTOM: {
                        entryY += height - entry.bounds().getHeight(BoundsType.OUTER);
                        break;
                    }
                }
                final float entryX = x + totalWidth;
                entry.bounds().setOuterPosition(((boolean)this.useFloatingPointPosition().get()) ? entryX : ((float)(int)entryX), ((boolean)this.useFloatingPointPosition().get()) ? entryY : ((float)(int)entryY), NavigationListWidget.LIST_POSITION);
                totalWidth += entryWidth + spaceBetweenEntries;
                widget.opacity().set(1.0f);
            }
        }
        totalWidth -= spaceBetweenEntries;
        if (this.overflowWidget != null) {
            if (overflow) {
                this.overflowWidget.setVisible(true);
                float entryY2 = y;
                switch (this.overflowWidget.alignmentY().get()) {
                    case CENTER: {
                        entryY2 += height / 2.0f - this.overflowWidget.bounds().getHeight(BoundsType.OUTER) / 2.0f;
                        break;
                    }
                    case BOTTOM: {
                        entryY2 += height - this.overflowWidget.bounds().getHeight(BoundsType.OUTER);
                        break;
                    }
                }
                final float entryX2 = x + totalWidth + spaceBetweenEntries;
                this.overflowWidget.bounds().setOuterPosition(((boolean)this.useFloatingPointPosition().get()) ? entryX2 : ((float)(int)entryX2), ((boolean)this.useFloatingPointPosition().get()) ? entryY2 : ((float)(int)entryY2), NavigationListWidget.LIST_POSITION);
            }
            else {
                this.overflowWidget.setVisible(false);
            }
        }
    }
    
    public LssProperty<HorizontalAlignment> priorityAlignment() {
        return this.priorityAlignment;
    }
    
    public LssProperty<Float> maxPadding() {
        return this.maxPadding;
    }
    
    static {
        LIST_POSITION = ModifyReason.of("listPosition");
    }
}
