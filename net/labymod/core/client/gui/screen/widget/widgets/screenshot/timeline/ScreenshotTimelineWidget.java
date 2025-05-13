// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.screenshot.timeline;

import java.util.Locale;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.OffsetSide;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import java.util.ArrayList;
import net.labymod.core.client.screenshot.Screenshot;
import java.util.List;
import java.util.Iterator;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.KeyHandler;
import net.labymod.api.client.gui.mouse.MutableMouse;
import java.util.Collection;
import net.labymod.core.client.screenshot.ScreenshotBrowser;
import net.labymod.core.client.screenshot.ScreenshotSection;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.util.bounds.ModifyReason;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class ScreenshotTimelineWidget extends AbstractWidget<Widget>
{
    private static final ModifyReason UPDATE_LAYOUT_REASON;
    private final ScreenshotTimelineHolder holder;
    private final Filter filter;
    private ComponentWidget titleWidget;
    private ScreenshotContainerWidget container;
    private ScreenshotScrollbarWidget scrollbarWidget;
    private float scrollOffset;
    private int tilesPerRow;
    private boolean postInitialized;
    
    public ScreenshotTimelineWidget(final ScreenshotTimelineHolder holder) {
        this.filter = new Filter();
        this.scrollOffset = 0.0f;
        this.tilesPerRow = 10;
        this.postInitialized = false;
        this.holder = holder;
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.postInitialized = false;
        (this.titleWidget = ComponentWidget.empty()).addId("title");
        ((AbstractWidget<ComponentWidget>)this).addChild(this.titleWidget);
        ((AbstractWidget<Widget>)(this.container = new ScreenshotContainerWidget(this))).addId("container");
        final ScreenshotBrowser browser = this.holder.browser();
        final Collection<ScreenshotSection> sections = browser.getSectionMap().values();
        for (final ScreenshotSection section : sections.toArray(new ScreenshotSection[0])) {
            if (this.filter.matches(section)) {
                final boolean showHeader = browser.getLatestSection() != section;
                this.container.addChild(new ScreenshotSectionWidget(this.container, section, this.filter, showHeader));
            }
        }
        ((AbstractWidget<ScreenshotContainerWidget>)this).addChild(this.container);
        (this.scrollbarWidget = new ScreenshotScrollbarWidget(this)).addId("scrollbar");
        ((AbstractWidget<ScreenshotScrollbarWidget>)this).addChild(this.scrollbarWidget);
        this.updateTitle();
    }
    
    @Override
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        this.postInitialized = true;
    }
    
    @Override
    public boolean mouseScrolled(final MutableMouse mouse, final double scrollDelta) {
        if (KeyHandler.isLeftControlDown()) {
            this.updateTileCount((int)(this.tilesPerRow - scrollDelta));
        }
        else {
            this.scrollOffset -= (float)(scrollDelta * 45.0);
            this.updateScrollOffset();
        }
        return super.mouseScrolled(mouse, scrollDelta);
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (KeyHandler.isLeftControlDown()) {
            if (key == Key.SLASH || key == Key.SUBTRACT) {
                this.updateTileCount(++this.tilesPerRow);
                return true;
            }
            if (key == Key.R_BRACKET || key == Key.ADD) {
                this.updateTileCount(--this.tilesPerRow);
                return true;
            }
            if (key == Key.NUM0 || key == Key.NUMPAD0) {
                this.updateTileCount(10);
                return true;
            }
        }
        return super.keyPressed(key, type);
    }
    
    public void updateTileCount(final int tilesPerRow) {
        this.tilesPerRow = tilesPerRow;
        final float previousScrollOffset = this.scrollOffset - 10.0f;
        final ScreenshotSectionWidget widget = this.container.getSectionAtOffset(previousScrollOffset);
        final ScreenshotSection section = (widget == null) ? null : widget.section();
        this.tilesPerRow = MathHelper.clamp(tilesPerRow, 5, 30);
        this.container.purgeCache();
        if (section != null) {
            this.scrollOffset = this.container.getOffsetOfSection(section);
            this.updateScrollOffset();
        }
    }
    
    @Override
    public void onBoundsChanged(final Rectangle previousRect, final Rectangle newRect) {
        super.onBoundsChanged(previousRect, newRect);
        if (this.postInitialized) {
            this.updateScrollOffset();
        }
    }
    
    private void updateScrollOffset() {
        final float maxScrollOffset = this.getMaxScrollOffset();
        this.scrollOffset = MathHelper.clamp(this.scrollOffset, 0.0f, maxScrollOffset);
        this.container.updateLayout(false);
        this.scrollbarWidget.updateAttention();
        this.updateTitle();
    }
    
    private void updateTitle() {
        if (this.container == null) {
            return;
        }
        final ScreenshotSectionWidget currentSectionWidget = this.container.getCurrentSectionWidget();
        if (currentSectionWidget != null) {
            this.titleWidget.setComponent(currentSectionWidget.title());
        }
    }
    
    public void updateAllSections() {
        if (this.container == null) {
            return;
        }
        for (final ScreenshotSectionWidget section : this.container.getGenericChildren()) {
            section.updateFilteredEntries();
        }
    }
    
    public void updateSection(final ScreenshotSection section) {
        if (this.container == null) {
            return;
        }
        final ScreenshotSectionWidget widget = this.container.getSectionWidget(section);
        if (widget != null) {
            widget.updateFilteredEntries();
        }
    }
    
    public void addSection(final ScreenshotSection section) {
        if (this.container == null || !this.filter.matches(section)) {
            return;
        }
        final boolean showHeader = this.holder.browser().getLatestSection() != section;
        this.container.addChildInitialized(new ScreenshotSectionWidget(this.container, section, this.filter, showHeader));
        this.updateTitle();
    }
    
    public void removeSection(final ScreenshotSection section) {
        if (this.container == null) {
            return;
        }
        final ScreenshotSectionWidget widget = this.container.getSectionWidget(section);
        if (widget != null) {
            this.container.removeChild(widget);
        }
        this.updateScrollOffset();
        this.updateTitle();
    }
    
    public void updateQuery(final String text) {
        this.filter.updateQuery(text);
        this.scrollOffset = 0.0f;
        this.reInitialize();
        this.container.updateLayout(true);
    }
    
    public boolean isEmpty() {
        return this.getFilteredScreenshots().isEmpty();
    }
    
    public List<Screenshot> getFilteredScreenshots() {
        final List<Screenshot> allScreenshots = this.holder.browser().getScreenshots();
        if (!this.filter.hasQuery()) {
            return allScreenshots;
        }
        final List<Screenshot> filteredScreenshots = new ArrayList<Screenshot>();
        for (final Screenshot screenshot : allScreenshots.toArray(new Screenshot[0])) {
            if (this.filter.matches(screenshot)) {
                filteredScreenshots.add(screenshot);
            }
        }
        return filteredScreenshots;
    }
    
    public float getMaxScrollOffset() {
        return Math.max(0.0f, this.bounds().getOffset(BoundsType.OUTER, OffsetSide.TOP) + this.container.getContentHeight(BoundsType.OUTER) - this.bounds().getHeight(BoundsType.INNER));
    }
    
    public Screenshot.QualityType getQuality() {
        return (this.tilesPerRow < 8) ? Screenshot.QualityType.MEDIUM : Screenshot.QualityType.LOW;
    }
    
    public Filter screenshotFilter() {
        return this.filter;
    }
    
    public ScreenshotTimelineHolder holder() {
        return this.holder;
    }
    
    public ScreenshotContainerWidget getContainer() {
        return this.container;
    }
    
    public void setScrollOffset(final float offset) {
        this.scrollOffset = offset;
        this.updateScrollOffset();
        this.container.updateLayout(false);
        this.updateTitle();
    }
    
    public float getScrollOffset() {
        return this.scrollOffset;
    }
    
    public int getTilesPerRow() {
        return this.tilesPerRow;
    }
    
    static {
        UPDATE_LAYOUT_REASON = ModifyReason.of("updateLayout");
    }
    
    public static class Filter
    {
        private String query;
        
        protected Filter() {
        }
        
        public void updateQuery(final String query) {
            this.query = query;
        }
        
        public String getQuery() {
            return this.query;
        }
        
        public boolean matches(final Screenshot screenshot) {
            if (this.query == null || this.query.isEmpty()) {
                return true;
            }
            final String query = this.query.toLowerCase(Locale.ROOT);
            if (screenshot.getMeta().getIdentifier().toLowerCase(Locale.ROOT).contains(query)) {
                return true;
            }
            final String json = screenshot.getMeta().get("Insight");
            if (json != null) {
                return json.toLowerCase(Locale.ROOT).contains(query);
            }
            final String legacyJson = screenshot.getMeta().get("Screenshot Metadata");
            return legacyJson != null && legacyJson.toLowerCase(Locale.ROOT).contains(query);
        }
        
        public boolean matches(final ScreenshotSection section) {
            for (final Screenshot screenshot : section.getScreenshots().toArray(new Screenshot[0])) {
                if (this.matches(screenshot)) {
                    return true;
                }
            }
            return false;
        }
        
        public boolean hasQuery() {
            return this.query != null && !this.query.isEmpty();
        }
    }
    
    public interface ScreenshotTimelineHolder
    {
        ScreenshotBrowser browser();
        
        void open(final Screenshot p0);
        
        Screenshot getOpenScreenshot();
    }
}
