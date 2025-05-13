// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.screenshot.Screenshot;
import net.labymod.core.client.screenshot.ScreenshotSection;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class ScreenshotContainerWidget extends AbstractWidget<ScreenshotSectionWidget>
{
    private static final ModifyReason UPDATE_LAYOUT_REASON;
    private final ScreenshotTimelineWidget timelineWidget;
    
    public ScreenshotContainerWidget(final ScreenshotTimelineWidget timelineWidget) {
        this.timelineWidget = timelineWidget;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        this.updateLayout(true);
    }
    
    public void updateLayout(final boolean purgeCache) {
        final Rectangle timelineRectangle = this.bounds().rectangle(BoundsType.INNER);
        if (timelineRectangle.getWidth() == 0.0f) {
            return;
        }
        final float x = timelineRectangle.getX();
        float y = timelineRectangle.getY() - this.timelineWidget.getScrollOffset();
        final float spaceBetweenEntries = 5.0f;
        for (final ScreenshotSectionWidget widget : this.children) {
            widget.bounds().setPosition(x, y, ScreenshotContainerWidget.UPDATE_LAYOUT_REASON);
            y += widget.getContentHeight(BoundsType.OUTER) + spaceBetweenEntries;
        }
        for (final ScreenshotSectionWidget widget : this.children) {
            widget.updateLayout(purgeCache);
        }
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        if (this.bounds().getWidth() == 0.0f) {
            return 0.0f;
        }
        float height = 0.0f;
        final float spaceBetweenEntries = 5.0f;
        for (final ScreenshotSectionWidget widget : this.children) {
            height += widget.getContentHeight(BoundsType.OUTER) + spaceBetweenEntries;
        }
        return height;
    }
    
    @Override
    public ScreenshotSectionWidget addChildInitialized(final ScreenshotSectionWidget widget) {
        super.addChildInitialized(widget);
        this.updateLayout(true);
        return widget;
    }
    
    public ScreenshotSectionWidget getCurrentSectionWidget() {
        ScreenshotSectionWidget target = null;
        final Iterator<T> iterator = this.children.iterator();
        while (iterator.hasNext()) {
            final ScreenshotSectionWidget widget = target = (ScreenshotSectionWidget)iterator.next();
            if (widget.bounds().getBottom() >= this.bounds().getTop()) {
                break;
            }
        }
        return target;
    }
    
    public float getOffsetOfSection(final ScreenshotSection section) {
        float height = 0.0f;
        final float spaceBetweenEntries = 5.0f;
        for (final ScreenshotSectionWidget widget : this.children) {
            height += widget.getContentHeight(BoundsType.OUTER) + spaceBetweenEntries;
            if (widget.section() == section) {
                break;
            }
        }
        return height;
    }
    
    public ScreenshotSectionWidget getSectionWidget(final ScreenshotSection section) {
        for (final ScreenshotSectionWidget widget : this.children) {
            if (widget.section() == section) {
                return widget;
            }
        }
        return null;
    }
    
    public ScreenshotSectionWidget getSectionAtOffset(final float offset) {
        float height = 0.0f;
        final float spaceBetweenEntries = 5.0f;
        for (final ScreenshotSectionWidget widget : this.children) {
            height += widget.getContentHeight(BoundsType.OUTER) + spaceBetweenEntries;
            if (height >= offset) {
                return widget;
            }
        }
        return this.children.isEmpty() ? null : ((ScreenshotSectionWidget)this.children.get(this.children.size() - 1));
    }
    
    public void purgeCache() {
        for (final ScreenshotSectionWidget widget : this.children) {
            widget.purgeCache();
        }
    }
    
    public float getOffsetOfCurrentSection() {
        final ScreenshotSectionWidget widget = this.getCurrentSectionWidget();
        return (widget == null) ? 0.0f : this.getOffsetOfSection(widget.section());
    }
    
    public ScreenshotTileWidget getTileWidget(final Screenshot screenshot) {
        final ScreenshotSection section = this.timelineWidget().holder().browser().getSectionOf(screenshot);
        if (section == null) {
            return null;
        }
        final ScreenshotSectionWidget sectionWidget = this.getSectionWidget(section);
        return (sectionWidget == null) ? null : sectionWidget.getTileWidget(screenshot);
    }
    
    public ScreenshotTimelineWidget timelineWidget() {
        return this.timelineWidget;
    }
    
    static {
        UPDATE_LAYOUT_REASON = ModifyReason.of("updateLayout");
    }
}
