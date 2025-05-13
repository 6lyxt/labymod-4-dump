// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.types;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.TabWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.Parent;
import java.util.ArrayList;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.util.KeyValue;
import java.util.List;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.tab.Tab;
import net.labymod.api.service.Registry;

@Link("tabbed.lss")
@AutoActivity
public abstract class TabbedActivity extends SimpleActivity implements Registry<Tab>
{
    private final List<KeyValue<Tab>> elements;
    private Tab activeTab;
    private ScreenInstance tempActiveScreen;
    private HorizontalListWidget tabMenu;
    private ScreenRendererWidget screenRenderer;
    private ScreenRendererWidget prevScreenRenderer;
    private int animationProgress;
    private boolean animationDirectionRight;
    
    public TabbedActivity() {
        this.elements = new ArrayList<KeyValue<Tab>>();
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
            tabWidget.setPressListener(() -> {
                if (tab != this.activeTab) {
                    this.switchTab(tab);
                    return true;
                }
                else {
                    return false;
                }
            });
            this.tabMenu.addEntry(tabWidget);
        }
        ((AbstractWidget<HorizontalListWidget>)this.document).addChild(this.tabMenu);
        (this.prevScreenRenderer = new ScreenRendererWidget()).addId("tabbed-activity");
        this.prevScreenRenderer.setVisible(false);
        this.prevScreenRenderer.setTicking(false);
        this.prevScreenRenderer.interactable().set(false);
        ((AbstractWidget<ScreenRendererWidget>)this.document).addChild(this.prevScreenRenderer);
        (this.screenRenderer = new ScreenRendererWidget()).addId("tabbed-activity");
        this.screenRenderer.addPostDisplayListener(screen -> {
            final Tab newTab = this.getTabByScreen(screen);
            if (newTab != this.activeTab && newTab != null) {
                this.updateActiveTab(newTab);
                this.activeTab = newTab;
            }
            return;
        });
        ((AbstractWidget<ScreenRendererWidget>)this.document).addChild(this.screenRenderer);
        if (!this.elements.isEmpty()) {
            Tab target = this.activeTab;
            if (target == null) {
                target = this.values().get(0);
            }
            this.switchTab(target);
        }
    }
    
    @Override
    public void render(final ScreenContext context) {
        if (this.prevScreenRenderer.isVisible()) {
            final int animationProgress = (int)(this.animationProgress + TimeUtil.convertDeltaToMilliseconds(context.getTickDelta()));
            this.animationProgress = animationProgress;
            final long timePassed = animationProgress;
            float progress = MathHelper.clamp(timePassed / 200.0f, 0.0f, 1.0f);
            progress = (float)CubicBezier.EASE_IN_OUT.curve(progress);
            this.prevScreenRenderer.opacity().set(1.0f - progress);
            this.screenRenderer.opacity().set(progress);
            final float offset = 50.0f;
            this.prevScreenRenderer.translateX().set(progress * offset * (this.animationDirectionRight ? -1 : 1));
            this.screenRenderer.translateX().set((1.0f - progress) * offset * (this.animationDirectionRight ? 1 : -1));
        }
        super.render(context);
    }
    
    @Nullable
    public ScreenInstance switchTab(final String id) {
        final Tab tab = this.getById(id);
        return (tab == null) ? null : this.switchTab(tab);
    }
    
    public <T extends ScreenInstance> T switchTab(final Class<? extends T> screenClass) {
        final Tab tab = this.getTabByScreen(screenClass);
        return (T)((tab == null) ? null : this.switchTab(tab));
    }
    
    public ScreenInstance switchTab(@NotNull final Tab tab) {
        final Tab prevTab = this.activeTab;
        if (this.tabMenu == null) {
            this.activeTab = tab;
            return this.tempActiveScreen = tab.provideScreen();
        }
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
        this.activeTab = tab;
        final boolean hasTransition = this.labyAPI.themeService().currentTheme().metadata().getBoolean("transition");
        if (hasTransition) {
            this.prevScreenRenderer.setScreenFrom(this.screenRenderer);
        }
        final ScreenInstance screen = (this.tempActiveScreen != null) ? this.tempActiveScreen : tab.provideScreen();
        this.tempActiveScreen = null;
        if (screen != this.screenRenderer.getScreen()) {
            this.screenRenderer.displayScreen(screen);
        }
        if (hasTransition && prevTab != tab) {
            this.handleTransition(prevTab, tab);
        }
        this.updateActiveTab(tab);
        return screen;
    }
    
    private void updateActiveTab(final Tab activeTab) {
        for (final HorizontalListEntry entry : this.tabMenu.getChildren()) {
            final TabWidget tabWidget = (TabWidget)entry.childWidget();
            final Tab tab = tabWidget.getTab();
            tabWidget.setActive(activeTab == tab);
            tabWidget.setVisible(!tab.isHidden());
            if (!tab.isHidden()) {
                tabWidget.reInitialize();
            }
        }
    }
    
    private void handleTransition(final Tab tabFrom, final Tab tabTo) {
        this.prevScreenRenderer.setVisible(true);
        this.animationDirectionRight = (this.indexOf(tabFrom) < this.indexOf(tabTo));
        this.animationProgress = 0;
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
    
    public TabWidget getTabWidget(final Tab tab) {
        for (final HorizontalListEntry entry : this.tabMenu.getChildren()) {
            final TabWidget tabWidget = (TabWidget)entry.childWidget();
            if (tabWidget.getTab() == tab) {
                return tabWidget;
            }
        }
        return null;
    }
    
    @Nullable
    public Tab getActiveTab() {
        return this.activeTab;
    }
    
    @Nullable
    public String getActiveTabId() {
        return this.getId(this.activeTab);
    }
    
    @Override
    public List<KeyValue<Tab>> getElements() {
        return this.elements;
    }
}
