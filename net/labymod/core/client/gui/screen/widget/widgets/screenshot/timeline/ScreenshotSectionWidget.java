// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import java.util.List;
import java.util.Collection;
import net.labymod.core.client.screenshot.Screenshot;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Iterator;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.component.Component;
import net.labymod.core.main.LabyMod;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.TextComponent;
import net.labymod.core.client.screenshot.ScreenshotBrowser;
import net.labymod.core.client.screenshot.ScreenshotSection;
import net.labymod.api.util.bounds.ModifyReason;
import java.text.SimpleDateFormat;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;

@AutoWidget
public class ScreenshotSectionWidget extends SimpleWidget
{
    private static final SimpleDateFormat DATE_FORMAT;
    private static final ModifyReason UPDATE_LAYOUT_REASON;
    private final ScreenshotContainerWidget containerWidget;
    private final ScreenshotSection section;
    private final ScreenshotTimelineWidget.Filter filter;
    private final ScreenshotBrowser browser;
    private final boolean showHeader;
    private final TextComponent titleComponent;
    private final RenderableComponent renderableTitleComponent;
    private DivWidget headerWidget;
    private boolean isInFrame;
    private Float cachedHeight;
    
    public ScreenshotSectionWidget(final ScreenshotContainerWidget containerWidget, final ScreenshotSection section, final ScreenshotTimelineWidget.Filter filter, final boolean showHeader) {
        this.isInFrame = false;
        this.cachedHeight = null;
        this.containerWidget = containerWidget;
        this.section = section;
        this.filter = filter;
        this.browser = LabyMod.references().screenshotBrowser();
        this.showHeader = showHeader;
        this.titleComponent = Component.text(ScreenshotSectionWidget.DATE_FORMAT.format(this.section.getTimestamp()));
        this.renderableTitleComponent = RenderableComponent.of(this.titleComponent);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.visible().set(this.isInFrame);
        (this.headerWidget = new DivWidget()).addId("header");
        if (this.showHeader) {
            final ComponentWidget titleWidget = ComponentWidget.component(this.title());
            titleWidget.addId("title");
            ((AbstractWidget<ComponentWidget>)this.headerWidget).addChild(titleWidget);
        }
        ((AbstractWidget<DivWidget>)this).addChild(this.headerWidget);
        if (this.isInFrame) {
            this.updateFilteredEntries();
        }
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
    }
    
    public void updateVisibleState() {
        final Rectangle window = this.parent.bounds();
        final Bounds sectionBounds = this.bounds();
        final boolean visible = window.isOverlapping(sectionBounds);
        if (this.visible().get() != visible) {
            final LssProperty<Boolean> visible2 = this.visible();
            final boolean b = visible;
            this.isInFrame = b;
            visible2.set(b);
            if (visible) {
                this.updateFilteredEntries();
            }
            else {
                for (final Widget widget : this.children) {
                    if (widget instanceof final ScreenshotTileWidget screenshotTileWidget) {
                        screenshotTileWidget.unload();
                    }
                }
                this.removeChildIf(child -> child instanceof ScreenshotTileWidget);
            }
        }
    }
    
    public void updateFilteredEntries() {
        final List<Screenshot> currentList = new ArrayList<Screenshot>();
        for (final Widget widget : this.children) {
            if (widget instanceof final ScreenshotTileWidget screenshotTileWidget) {
                currentList.add(screenshotTileWidget.getScreenshot());
            }
        }
        final List<Widget> widgetsToAdd = new ArrayList<Widget>();
        for (final Screenshot screenshot : this.section.getScreenshots().toArray(new Screenshot[0])) {
            if (this.filter.matches(screenshot)) {
                if (!currentList.remove(screenshot)) {
                    widgetsToAdd.add(new ScreenshotTileWidget(this, screenshot));
                }
            }
        }
        if (!currentList.isEmpty()) {
            this.removeChildIf(child -> child instanceof ScreenshotTileWidget && currentList.contains(((ScreenshotTileWidget)child).getScreenshot()));
        }
        if (this.initialized) {
            if (!widgetsToAdd.isEmpty()) {
                this.addChildrenInitialized(widgetsToAdd, true);
            }
        }
        else {
            for (final Widget widget2 : widgetsToAdd) {
                this.addChild(widget2, false);
            }
            this.sortChildren();
        }
        this.updateLayout(true);
    }
    
    public void updateLayout(final boolean purgeCache) {
        this.updateVisibleState();
        final Rectangle sectionBounds = this.bounds().rectangle(BoundsType.INNER);
        if (sectionBounds.getWidth() == 0.0f) {
            return;
        }
        float x = sectionBounds.getX();
        float y = sectionBounds.getY() + this.headerWidget.bounds().getHeight(BoundsType.OUTER);
        float maxHeight = 0.0f;
        final int tilesPerRow = this.containerWidget.timelineWidget().getTilesPerRow();
        for (final Widget widget : this.children) {
            if (widget instanceof final ScreenshotTileWidget screenshotWidget) {
                final float width = sectionBounds.getWidth() / tilesPerRow;
                final float height = width / screenshotWidget.getScreenshot().getAspectRatio();
                if (x + width > sectionBounds.getRight() + 1.0f) {
                    x = sectionBounds.getX();
                    y += maxHeight;
                    maxHeight = 0.0f;
                }
                final Bounds bounds = widget.bounds();
                bounds.setPosition(x, y, ScreenshotSectionWidget.UPDATE_LAYOUT_REASON);
                bounds.setSize(width, height, ScreenshotSectionWidget.UPDATE_LAYOUT_REASON);
                maxHeight = Math.max(maxHeight, height);
                x += width;
            }
        }
        if (purgeCache) {
            this.purgeCache();
        }
    }
    
    @Override
    public float getContentHeight(final BoundsType type) {
        if (this.cachedHeight != null) {
            return this.cachedHeight;
        }
        final Rectangle sectionBounds = this.bounds().rectangle(BoundsType.INNER);
        if (sectionBounds.getWidth() == 0.0f) {
            return 0.0f;
        }
        final float headerHeight = this.headerWidget.bounds().getHeight(BoundsType.OUTER);
        if (headerHeight == 0.0f) {
            return 0.0f;
        }
        float x = sectionBounds.getX();
        float y = sectionBounds.getY() + headerHeight;
        float maxHeight = 0.0f;
        final int tilesPerRow = this.containerWidget.timelineWidget().getTilesPerRow();
        for (final Screenshot screenshot : this.section.getScreenshots().toArray(new Screenshot[0])) {
            if (this.filter.matches(screenshot)) {
                final float width = sectionBounds.getWidth() / tilesPerRow;
                final float height = width / screenshot.getAspectRatio();
                if (x + width > sectionBounds.getRight() + 1.0f) {
                    x = sectionBounds.getX();
                    y += maxHeight;
                    maxHeight = 0.0f;
                }
                maxHeight = Math.max(maxHeight, height);
                x += width;
            }
        }
        final Float value = y + maxHeight - sectionBounds.getY();
        this.cachedHeight = value;
        return value;
    }
    
    public ScreenshotTileWidget getTileWidget(final Screenshot screenshot) {
        for (final Widget widget : this.children) {
            if (widget instanceof final ScreenshotTileWidget tileWidget) {
                if (tileWidget.getScreenshot().equals(screenshot)) {
                    return tileWidget;
                }
                continue;
            }
        }
        return null;
    }
    
    public void purgeCache() {
        this.cachedHeight = null;
    }
    
    @Override
    public int getSortingValue() {
        return (int)(-this.section.getTimestamp() / 1000L);
    }
    
    @Override
    public int hashCode() {
        return Long.hashCode(this.section.getTimestamp());
    }
    
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof ScreenshotSectionWidget && ((ScreenshotSectionWidget)obj).section.equals(this.section);
    }
    
    public Component title() {
        return this.titleComponent;
    }
    
    public RenderableComponent renderableTitleComponent() {
        return this.renderableTitleComponent;
    }
    
    public ScreenshotSection section() {
        return this.section;
    }
    
    public ScreenshotContainerWidget containerWidget() {
        return this.containerWidget;
    }
    
    public ScreenshotBrowser browser() {
        return this.browser;
    }
    
    static {
        DATE_FORMAT = new SimpleDateFormat("MMMM yyyy");
        UPDATE_LAYOUT_REASON = ModifyReason.of("updateLayout");
    }
}
