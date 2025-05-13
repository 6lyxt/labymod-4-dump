// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.hudwidget;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.event.client.gui.hud.HudWidgetDestroyedEvent;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.hud.HudWidgetCreatedEvent;
import net.labymod.api.client.component.Component;
import java.util.Iterator;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.WidgetsEditorActivity;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.core.client.gui.screen.widget.widgets.hud.window.grid.HudWidgetTypeInfoWidget;
import net.labymod.api.client.render.font.RenderableComponent;
import java.util.Locale;
import net.labymod.api.client.gui.hud.hudwidget.HudWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.transform.InterpolateWidget;
import net.labymod.core.client.gui.screen.widget.widgets.hud.window.grid.HudWidgetTilesGridWidget;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.widget.widgets.hud.window.HudWidgetWindowWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.hud.HudWidgetRegistry;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;

@Link("activity/hudwidget/window/hud-widget-list.lss")
@AutoActivity
public class ListHudWindowActivity extends HudWindowActivity
{
    private final HudWidgetRegistry registry;
    private final ListSession<Widget> session;
    private VerticalListWidget<Widget> listWidget;
    
    public ListHudWindowActivity(final HudWidgetWindowWidget window) {
        super(window);
        this.registry = Laby.references().hudWidgetRegistry();
        this.session = new ListSession<Widget>();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        String filterText = this.window.getSearchQuery();
        final WidgetsEditorActivity editor = this.window.editor();
        (this.listWidget = new VerticalListWidget<Widget>(this.session)).addId("list");
        for (final HudWidgetCategory category : this.registry.categoryRegistry().values()) {
            final HudWidgetTilesGridWidget gridWidget = new HudWidgetTilesGridWidget(editor);
            ((AbstractWidget<Widget>)gridWidget).addId("grid");
            final InterpolateWidget categoryWidget = new InterpolateWidget(50.0f);
            categoryWidget.addId("category");
            final ComponentWidget categoryTitleWidget = ComponentWidget.component(category.title());
            categoryTitleWidget.addId("title");
            ((AbstractWidget<ComponentWidget>)categoryWidget).addChild(categoryTitleWidget);
            final ComponentWidget categoryDescriptionWidget = ComponentWidget.component(category.description());
            categoryDescriptionWidget.addId("description");
            ((AbstractWidget<ComponentWidget>)categoryWidget).addChild(categoryDescriptionWidget);
            for (final HudWidget<?> hudWidget : this.registry.values()) {
                if (hudWidget.category().equals(category)) {
                    if (!hudWidget.isHolderEnabled()) {
                        continue;
                    }
                    if (filterText != null) {
                        filterText = filterText.toLowerCase(Locale.ROOT);
                        final Component title = hudWidget.displayName();
                        final String titleCharacters = RenderableComponent.of(title).getText();
                        final String titleString = titleCharacters.toLowerCase(Locale.ROOT);
                        if (!titleString.contains(filterText)) {
                            continue;
                        }
                    }
                    final HudWidgetTypeInfoWidget widget = new HudWidgetTypeInfoWidget(hudWidget, gridWidget);
                    widget.addId("entry");
                    gridWidget.addChild(widget);
                }
            }
            if (!gridWidget.getChildren().isEmpty()) {
                this.listWidget.addChild(categoryWidget);
                this.listWidget.addChild(gridWidget);
            }
        }
        final ScrollWidget scrollWidget = new ScrollWidget(this.listWidget);
        scrollWidget.addId("scroll");
        ((AbstractWidget<ScrollWidget>)this.document).addChild(scrollWidget);
    }
    
    @Subscribe
    public void onHudWidgetCreated(final HudWidgetCreatedEvent event) {
        this.updateHudWidgetTypeInfo(event.hudWidget());
    }
    
    @Subscribe
    public void onHudWidgetDestroyed(final HudWidgetDestroyedEvent event) {
        this.updateHudWidgetTypeInfo(event.hudWidget());
    }
    
    private void updateHudWidgetTypeInfo(final HudWidget<?> hudWidget) {
        for (final Widget child : this.listWidget.getChildren()) {
            if (!(child instanceof HudWidgetTilesGridWidget)) {
                continue;
            }
            final HudWidgetTilesGridWidget grid = (HudWidgetTilesGridWidget)child;
            for (final HudWidgetTypeInfoWidget typeInfoWidget : grid.getChildren()) {
                if (typeInfoWidget.hudWidget() != hudWidget) {
                    continue;
                }
                typeInfoWidget.reInitialize();
            }
        }
    }
    
    @Override
    public boolean canDropHudWidget() {
        for (final Widget child : this.listWidget.getChildren()) {
            if (!(child instanceof HudWidgetTilesGridWidget)) {
                continue;
            }
            final HudWidgetTilesGridWidget grid = (HudWidgetTilesGridWidget)child;
            if (grid.draggingWidget() != null) {
                return false;
            }
        }
        return true;
    }
}
