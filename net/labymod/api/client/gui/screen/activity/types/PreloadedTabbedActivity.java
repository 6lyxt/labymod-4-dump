// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.ScreenInstance;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.util.KeyValue;
import java.util.List;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab;
import net.labymod.api.service.Registry;

@Deprecated
@Link("tabbed.lss")
@AutoActivity
public abstract class PreloadedTabbedActivity extends SimpleActivity implements Registry<Tab>
{
    private final List<KeyValue<Tab>> elements;
    private final List<ScreenRendererWidget> screenRenderers;
    private Tab activeTab;
    private HorizontalListWidget tabMenu;
    
    public PreloadedTabbedActivity() {
        this.elements = new ArrayList<KeyValue<Tab>>();
        this.screenRenderers = new ArrayList<ScreenRendererWidget>();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final DivWidget dirtHeader = new DivWidget();
        dirtHeader.addId("background-header");
        ((AbstractWidget<DivWidget>)this.document).addChild(dirtHeader);
        ((AbstractWidget<Widget>)(this.tabMenu = new HorizontalListWidget())).addId("menu");
        for (final Tab tab : this.values()) {
            final TabWidget tabWidget = new TabWidget(tab);
            tabWidget.setPressable(() -> this.switchTab(tab));
            this.tabMenu.addEntry(tabWidget);
        }
        ((AbstractWidget<HorizontalListWidget>)this.document).addChild(this.tabMenu);
        for (final Tab tab : this.values()) {
            final ScreenRendererWidget screenRenderer = new ScreenRendererWidget();
            screenRenderer.addId("tabbed-activity");
            screenRenderer.displayScreen(tab.provideScreen());
            screenRenderer.addPostDisplayListener(screen -> {
                if (tab != this.activeTab) {
                    return;
                }
                else {
                    final Tab newTab = this.getTabByScreen(screen);
                    if (newTab != this.activeTab && newTab != null) {
                        this.updateActiveTab(newTab);
                        this.activeTab = newTab;
                    }
                    return;
                }
            });
            this.screenRenderers.add(screenRenderer);
            ((AbstractWidget<ScreenRendererWidget>)this.document).addChild(screenRenderer);
        }
    }
    
    @Override
    protected void postInitialize() {
        super.postInitialize();
        if (!this.elements.isEmpty()) {
            Tab target = this.activeTab;
            if (target == null) {
                target = this.values().get(0);
            }
            this.switchTab(target);
        }
    }
    
    @Nullable
    public ScreenInstance switchTab(final String id) {
        final Tab tab = this.getById(id);
        return (tab == null) ? null : this.switchTab(tab);
    }
    
    @Nullable
    public ScreenInstance switchTab(final Class<? extends ScreenInstance> screenClass) {
        final Tab tab = this.getTabByScreen(screenClass);
        return (tab == null) ? null : this.switchTab(tab);
    }
    
    private ScreenInstance switchTab(final Tab tab) {
        if (this.tabMenu == null) {
            this.activeTab = tab;
            return null;
        }
        final boolean hasTransition = this.labyAPI.themeService().currentTheme().metadata().getBoolean("transition");
        this.updateActiveTab(tab);
        for (int i = 0; i < this.elements.size(); ++i) {
            if (this.elements.get(i).getValue() == tab) {
                tab.setIndex(i);
                break;
            }
        }
        if (this.activeTab != null) {
            final boolean transitionToRight = tab.getIndex() > this.activeTab.getIndex();
            this.activeTab.setTransitionToRight(transitionToRight);
            tab.setTransitionToRight(!transitionToRight);
        }
        final ScreenInstance activeScreen = this.getScreenByTab(tab);
        for (final ScreenRendererWidget screenRenderer : this.screenRenderers) {
            final ScreenInstance screen = screenRenderer.getScreen();
            final boolean isActive = screen == activeScreen;
            screenRenderer.setVisible(isActive);
        }
        this.activeTab = tab;
        return activeScreen;
    }
    
    private void updateActiveTab(final Tab tab) {
        for (final HorizontalListEntry entry : this.tabMenu.getGenericChildren()) {
            final TabWidget tabWidget = (TabWidget)entry.childWidget();
            final boolean active = tab == tabWidget.getTab();
            tabWidget.setActive(tab == tabWidget.getTab());
            if (active) {
                tabWidget.addId("active");
            }
            else {
                tabWidget.removeId("active");
            }
        }
    }
    
    public Tab getTabByScreen(final ScreenInstance screen) {
        return this.getTabByScreen(screen.getClass());
    }
    
    public Tab getTabByScreen(final Class<? extends ScreenInstance> screen) {
        for (final Tab tab : this.values()) {
            if (tab.isScreen(screen)) {
                return tab;
            }
        }
        return null;
    }
    
    public ScreenInstance getScreenByTab(final Tab tab) {
        for (final ScreenRendererWidget screenRenderer : this.screenRenderers) {
            final ScreenInstance screen = screenRenderer.getScreen();
            final Tab tabOfScreen = this.getTabByScreen(screen);
            if (tabOfScreen == tab) {
                return screen;
            }
        }
        return null;
    }
    
    @Override
    public List<KeyValue<Tab>> getElements() {
        return this.elements;
    }
}
