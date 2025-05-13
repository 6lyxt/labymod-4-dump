// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.PageWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;

public abstract class PagedActivity extends SimpleActivity
{
    protected final VerticalListWidget<Widget> pageMenu;
    protected final ScreenRendererWidget screenRenderer;
    protected PageWidget[] pages;
    
    protected PagedActivity() {
        this.pageMenu = new VerticalListWidget<Widget>();
        this.pageMenu.priorityLayer().set(PriorityLayer.VERY_FRONT);
        this.pageMenu.addId("paged-activity-page-menu");
        (this.screenRenderer = new ScreenRendererWidget()).addId("paged-activity");
        this.screenRenderer.addDisplayListener(screen -> {
            final PageWidget activePage = this.getPageByScreen(screen);
            final PageWidget[] pages = this.pages;
            int i = 0;
            for (int length = pages.length; i < length; ++i) {
                final PageWidget page = pages[i];
                page.setActive(page == activePage);
            }
            this.reload();
        });
    }
    
    private void initializePages() {
        this.pages = this.createPages();
        for (final PageWidget page : this.pages) {
            page.setPressable(() -> {
                if (!this.screenRenderer.getScreen().equals(page.getScreen())) {
                    this.screenRenderer.displayScreen(page.getScreen());
                }
                return;
            });
        }
        final int defaultTabIndex = this.getDefaultPageIndex();
        final ScreenInstance activeScreen = this.getPageAtIndex(defaultTabIndex).getScreen();
        this.screenRenderer.initializeScreen(activeScreen);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.pages == null) {
            this.initializePages();
        }
        final ScreenInstance screen = this.screenRenderer.getScreen();
        final PageWidget activePage = this.getPageByScreen(screen);
        for (final PageWidget page : this.pages) {
            page.setActive(activePage == page);
            this.pageMenu.addChild(page);
        }
    }
    
    public PageWidget getPageAtIndex(final int index) {
        return this.pages[index];
    }
    
    public PageWidget getPageByScreen(final ScreenInstance screen) {
        for (final PageWidget page : this.pages) {
            if (page.getScreen() == screen) {
                return page;
            }
        }
        return null;
    }
    
    protected abstract PageWidget[] createPages();
    
    protected int getDefaultPageIndex() {
        return 0;
    }
}
