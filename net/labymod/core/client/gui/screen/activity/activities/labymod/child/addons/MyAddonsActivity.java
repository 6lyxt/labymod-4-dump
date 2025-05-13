// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.core.flint.marketplace.FlintOrganization;
import net.labymod.api.util.io.web.result.ResultCallback;
import java.util.Optional;
import net.labymod.core.flint.marketplace.FlintModification;
import java.util.Iterator;
import java.util.Collection;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.core.client.gui.screen.widget.widgets.store.StoreItemWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;
import java.util.Locale;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.addon.AddonService;
import net.labymod.core.flint.FlintController;
import net.labymod.core.client.gui.screen.widget.widgets.store.InstalledItemWidget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.activity.Activity;

@Links({ @Link("activity/flint/installed.lss"), @Link("activity/flint/addon-item.lss") })
@AutoActivity
public class MyAddonsActivity extends Activity
{
    private final ListSession<InstalledItemWidget> session;
    private final FlintController flintController;
    private final AddonService addonService;
    private String searchQuery;
    
    public MyAddonsActivity(final FlintController flintController) {
        this.session = new ListSession<InstalledItemWidget>();
        this.searchQuery = "";
        this.flintController = flintController;
        this.addonService = this.labyAPI.addonService();
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final Collection<LoadedAddon> visibleAddons = this.addonService.getVisibleAddons();
        if (visibleAddons.isEmpty()) {
            final ComponentWidget noAddonsFound = ComponentWidget.i18n("labymod.addons.installed.none");
            noAddonsFound.addId("none-installed");
            ((AbstractWidget<ComponentWidget>)this.document()).addChild(noAddonsFound);
            return;
        }
        String query = this.searchQuery.toLowerCase(Locale.ROOT).replace(" ", "");
        if (query.isEmpty()) {
            query = null;
        }
        final TilesGridWidget<StoreItemWidget> grid = new TilesGridWidget<StoreItemWidget>();
        for (final LoadedAddon loadedAddon : visibleAddons) {
            final FlintModification flintModification = this.getFlintModification(loadedAddon);
            if (query != null) {
                final String name = flintModification.getName();
                final String description = flintModification.getShortDescription();
                final String author = flintModification.getAuthor();
                if (name != null) {
                    if (!name.toLowerCase(Locale.ROOT).replace(" ", "").contains(query)) {
                        if (description != null) {
                            if (!description.toLowerCase(Locale.ROOT).replace(" ", "").contains(query)) {
                                if (author != null && !author.toLowerCase(Locale.ROOT).replace(" ", "").contains(query)) {
                                    continue;
                                }
                            }
                        }
                    }
                }
            }
            final InstalledItemWidget installedItem = new InstalledItemWidget(this.flintController, loadedAddon, flintModification).addId("addon-item");
            grid.addTile(installedItem);
        }
        if (grid.getChildren().isEmpty()) {
            final ComponentWidget noAddonsFound2 = ComponentWidget.i18n("labymod.addons.installed.noSearch");
            noAddonsFound2.addId("none-installed");
            ((AbstractWidget<ComponentWidget>)this.document()).addChild(noAddonsFound2);
            return;
        }
        ((AbstractWidget<ScrollWidget>)this.document()).addChild(new ScrollWidget(grid, this.session));
    }
    
    private FlintModification getFlintModification(final LoadedAddon loadedAddon) {
        if (!loadedAddon.info().isFlintAddon()) {
            return new DummyModification(loadedAddon);
        }
        final Optional<FlintModification> optionalModification = this.flintController.getFlintIndex().filter().namespace(loadedAddon.info().getNamespace());
        return optionalModification.orElseGet(() -> new DummyModification(loadedAddon));
    }
    
    public String getSearchQuery() {
        return this.searchQuery;
    }
    
    public void search(final String query) {
        this.searchQuery = query;
        this.reload();
    }
    
    public class DummyModification extends FlintModification
    {
        private DummyModification(final MyAddonsActivity this$0, final LoadedAddon loadedAddon) {
            this.namespace = loadedAddon.info().getNamespace();
            this.name = loadedAddon.info().getDisplayName();
            this.author = loadedAddon.info().getAuthor();
            this.shortDescription = loadedAddon.info().getDescription();
        }
        
        @Override
        public FlintOrganization getOrganization(final ResultCallback<FlintOrganization> result) {
            return null;
        }
    }
}
