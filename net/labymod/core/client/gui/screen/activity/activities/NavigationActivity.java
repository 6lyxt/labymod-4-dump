// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.widgets.layout.entry.HorizontalListEntry;
import java.util.Arrays;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.config.SettingUpdateEvent;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gfx.pipeline.post.processors.PostProcessors;
import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.client.gui.screen.ScreenContext;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.navigation.NavigationElementWidget;
import net.labymod.api.client.gui.navigation.elements.AbstractNavigationElement;
import net.labymod.api.client.gui.navigation.NavigationElement;
import net.labymod.api.util.KeyValue;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.core.client.gui.screen.widget.widgets.navigation.NavigationListWidget;
import net.labymod.api.client.gui.navigation.elements.ScreenBaseNavigationElement;
import net.labymod.api.client.gui.screen.activity.overlay.OverlayRegistry;
import net.labymod.core.client.gui.navigation.DefaultNavigationRegistry;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.navigation.NavigationWrapper;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;

@Link("extension/navigation.lss")
@AutoActivity
public class NavigationActivity extends SimpleActivity implements NavigationWrapper
{
    private final DefaultNavigationRegistry navigationRegistry;
    private final OverlayRegistry overlayRegistry;
    private ScreenBaseNavigationElement<?> element;
    private ScreenBaseNavigationElement<?> fallbackElement;
    private final NavigationListWidget header;
    private final NavigationListWidget footer;
    private boolean playAnimations;
    
    public NavigationActivity(final ScreenBaseNavigationElement<?> element) {
        if (element == null) {
            throw new NullPointerException("element is null");
        }
        this.navigationRegistry = (DefaultNavigationRegistry)this.labyAPI.navigationService();
        this.overlayRegistry = this.labyAPI.activityOverlayRegistry();
        this.element = element;
        if (NamedScreen.MAIN_MENU.isScreen(this.previousScreen)) {
            this.playAnimations = true;
        }
        if (element.isFallback()) {
            this.fallbackElement = element;
        }
        ((AbstractWidget<Widget>)(this.header = new NavigationListWidget(this))).addId("navigation", "header");
        ((AbstractWidget<Widget>)(this.footer = new NavigationListWidget(this))).addId("navigation", "footer");
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final Activity overlay = this.overlayRegistry.toOverlay(this.element.getScreen());
        this.element.onUpdate((overlay == null) ? this.element.getScreen() : overlay);
        final ScreenRendererWidget rendererWidget = new ScreenRendererWidget();
        rendererWidget.addId("navigation-window");
        rendererWidget.displayScreen(this.element.getScreen());
        ((AbstractWidget<ScreenRendererWidget>)this.document).addChild(rendererWidget);
        final NavigationElement<?> activeElement = this.navigationRegistry.getActiveScreenElement();
        for (int i = 0; i < 2; ++i) {
            final NavigationListWidget nav = (i == 0) ? this.header : this.footer;
            nav.priorityLayer().set(PriorityLayer.VERY_FRONT);
            for (final KeyValue<NavigationElement<?>> keyElement : this.navigationRegistry.getElements()) {
                final NavigationElement<?> element = keyElement.getValue();
                if (element instanceof final AbstractNavigationElement abstractNavigationElement) {
                    abstractNavigationElement.setNavigation(this);
                }
                final NavigationElementWidget widget = new NavigationElementWidget(this, element);
                widget.addId("nav-entry", element.getWidgetId());
                nav.addEntry(widget);
                if (element == activeElement) {
                    widget.setActive(true);
                }
            }
            ((AbstractWidget<NavigationListWidget>)this.document()).addChild(nav);
        }
    }
    
    @Override
    public void render(final ScreenContext context) {
        PostProcessors.processMenuBlur(MenuBlurConfig.ScreenType.PAUSE_MENU, context.getTickDelta());
        super.render(context);
    }
    
    @Override
    public boolean shouldRenderBackground() {
        final ScreenInstance screen = this.element.getScreen();
        if (screen instanceof final SimpleActivity activity) {
            return activity.shouldRenderBackground();
        }
        return false;
    }
    
    @Override
    public boolean renderBackground(final ScreenContext context) {
        return super.renderBackground(context);
    }
    
    @Override
    protected void postInitialize() {
        super.postInitialize();
        if (this.playAnimations) {
            this.playAnimations = false;
            final Widget header = this.document().getChild("header");
            if (header != null) {
                header.playAnimation("header-fade-in-nav");
            }
            final Widget footer = this.document().getChild("footer");
            if (footer != null) {
                footer.playAnimation("footer-fade-in-nav");
            }
        }
    }
    
    @Override
    public boolean shouldDocumentHandleKey(final Key key, final InputType type) {
        return key != Key.ESCAPE || this.element.shouldDocumentHandleEscape();
    }
    
    @Override
    public boolean displayPreviousScreen() {
        if (this.labyAPI.minecraft().isIngame()) {
            this.labyAPI.minecraft().minecraftWindow().displayScreenRaw(null);
        }
        else if (this.element == this.fallbackElement) {
            this.labyAPI.minecraft().minecraftWindow().displayScreenRaw(null);
        }
        else if (this.fallbackElement != null) {
            this.displayScreen(this.fallbackElement);
        }
        return true;
    }
    
    @Override
    public void displayScreen(final ScreenBaseNavigationElement<?> element) {
        if (element == null) {
            throw new NullPointerException("element is null");
        }
        if (element.isFallback()) {
            this.fallbackElement = element;
        }
        if (!element.equals(this.element)) {
            this.element.onCloseScreen();
            this.element = element;
            this.navigationRegistry.setActiveElement(element);
        }
        this.reload();
    }
    
    @Subscribe
    public void onSettingUpdate(final SettingUpdateEvent event) {
        if (event.setting().getPath().startsWith("settings.appearance.navigation")) {
            this.reload();
        }
    }
    
    @Override
    public void updateElement(final NavigationElement<?> element) {
        for (final NavigationListWidget list : Arrays.asList(this.header, this.footer)) {
            for (final HorizontalListEntry child : list.getChildren()) {
                final Widget inner = child.childWidget();
                if (!(inner instanceof NavigationElementWidget)) {
                    continue;
                }
                final NavigationElementWidget widget = (NavigationElementWidget)inner;
                if (widget.getElement() != element) {
                    continue;
                }
                child.reInitialize();
            }
            list.updateBounds();
        }
    }
    
    @NotNull
    @Override
    public ScreenInstance mostInnerScreenInstance() {
        return this.element.getScreen().mostInnerScreenInstance();
    }
    
    @NotNull
    @Override
    public Object mostInnerScreen() {
        return this.element.getScreen().mostInnerScreen();
    }
    
    @Override
    public void onOpenScreen() {
        PostProcessors.resetMenuBlur();
        super.onOpenScreen();
    }
    
    @Override
    public void onCloseScreen() {
        super.onCloseScreen();
        this.element.onCloseScreen();
    }
}
