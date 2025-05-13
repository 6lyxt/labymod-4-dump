// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.util.io.web.result.ResultCallback;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import com.google.gson.JsonArray;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.core.flint.marketplace.FlintTag;
import java.util.Iterator;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.models.addon.info.AddonMeta;
import net.labymod.core.flint.marketplace.FlintModification;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.Parent;
import java.util.HashMap;
import net.labymod.api.Laby;
import net.labymod.core.flint.FlintSortBy;
import java.util.Map;
import net.labymod.core.flint.FlintController;
import net.labymod.api.localization.Internationalization;
import net.labymod.core.client.gui.screen.widget.widgets.store.StoreItemWidget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.activity.Activity;

@Links({ @Link("activity/flint/store.lss"), @Link("activity/flint/addon-item.lss") })
@AutoActivity
public class MarketplaceActivity extends Activity
{
    private final ListSession<StoreItemWidget> session;
    private final Internationalization internationalization;
    private final FlintController flintController;
    private final Map<Context, Object> contexts;
    private FlintSortBy sortBy;
    
    public MarketplaceActivity(final FlintController flintController) {
        this.session = new ListSession<StoreItemWidget>();
        this.flintController = flintController;
        this.internationalization = Laby.references().internationalization();
        this.contexts = new HashMap<Context, Object>();
        this.sortBy = FlintSortBy.TRENDING;
        this.contexts.put(Context.TRENDING, Context.TRENDING);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        final Result<List<FlintModification>> indexResult = this.getModifications();
        String errorString = null;
        if (this.flintController.getTags() == null) {
            errorString = this.internationalization.translate("labymod.addons.store.marketplace.noResponse", new Object[0]);
        }
        if (indexResult.isEmpty()) {
            if (this.flintController.getFlintIndex().getIndex().isEmpty()) {
                errorString = this.internationalization.translate("labymod.addons.store.marketplace.noIndex", new Object[0]);
            }
            else if (this.contexts.containsKey(Context.SEARCH)) {
                errorString = this.internationalization.translate("labymod.addons.store.marketplace.noSearch", new Object[0]);
            }
        }
        if (indexResult.hasException()) {
            errorString = this.internationalization.translate("labymod.misc.errorWithArgs", indexResult.exception().getMessage());
        }
        if (errorString != null) {
            final ComponentWidget error = ComponentWidget.text(errorString);
            error.addId("error");
            ((AbstractWidget<ComponentWidget>)this.document()).addChild(error);
            return;
        }
        if (!indexResult.isPresent()) {
            final ComponentWidget error = ComponentWidget.i18n("labymod.addons.store.marketplace.noFilter");
            error.addId("error");
            ((AbstractWidget<ComponentWidget>)this.document()).addChild(error);
        }
        final TilesGridWidget<StoreItemWidget> grid = new TilesGridWidget<StoreItemWidget>();
        final List<FlintModification> modifications = indexResult.get();
        for (final FlintModification modification : modifications) {
            if (modification.hasMeta(AddonMeta.HIDDEN)) {
                continue;
            }
            final StoreItemWidget widget = new StoreItemWidget(modification, this.openProfile(modification)).addId("addon-item");
            grid.addTile(widget);
        }
        this.document().addChild(new ScrollWidget(grid, this.session).addId("scroll"));
    }
    
    public String getSearchQuery() {
        final Object o = this.contexts.get(Context.SEARCH);
        if (o == null) {
            return "";
        }
        return (String)o;
    }
    
    public void search(final String search) {
        this.setContext(Context.SEARCH, search);
    }
    
    public boolean isSearch(final String search) {
        return this.contexts.containsKey(Context.SEARCH) && (search == null || this.contexts.containsValue(search));
    }
    
    public void category(final FlintTag flintTag) {
        this.setContext(Context.CATEGORY, flintTag);
    }
    
    public boolean isCategory(final FlintTag flintTag) {
        return this.contexts.containsKey(Context.CATEGORY) && this.contexts.containsValue(flintTag);
    }
    
    public FlintTag getCategory() {
        return this.contexts.get(Context.CATEGORY);
    }
    
    public void trending() {
        this.setContext(Context.TRENDING, null);
    }
    
    public boolean isTrending() {
        return this.contexts.containsKey(Context.TRENDING) && !this.contexts.containsKey(Context.SEARCH);
    }
    
    public FlintSortBy getSortBy() {
        return this.sortBy;
    }
    
    public void setSortBy(final FlintSortBy sortBy) {
        this.sortBy = sortBy;
    }
    
    private void setContext(final Context context, final Object value) {
        if (context != Context.SUBCATEGORY) {
            this.contexts.clear();
        }
        if (context != Context.SEARCH || (value != null && String.valueOf(value).length() != 0)) {
            this.contexts.put(context, value);
        }
        if (this.contexts.isEmpty()) {
            this.contexts.put(Context.TRENDING, Context.TRENDING);
        }
    }
    
    private Result<List<FlintModification>> getModifications() {
        final FlintIndex flintIndex = this.flintController.getFlintIndex();
        final Result<JsonArray> index = flintIndex.getIndex();
        if (index == null) {
            return Result.empty();
        }
        if (index.hasException()) {
            return Result.ofException(index.exception());
        }
        if (index.isEmpty()) {
            return Result.empty();
        }
        List<FlintModification> modifications = null;
        final FlintIndex.IndexFilter filter = this.flintController.getFlintIndex().filter();
        if (this.contexts.containsKey(Context.SEARCH)) {
            modifications = filter.search(this.sortBy, String.valueOf(this.contexts.get(Context.SEARCH)));
        }
        else {
            modifications = filter.sortBy(this.sortBy);
        }
        if (modifications == null || modifications.isEmpty()) {
            return Result.empty();
        }
        return Result.of(modifications);
    }
    
    private Pressable openProfile(FlintModification modification) {
        if (modification instanceof FlintIndex.FlintIndexModification) {
            final FlintModification mod = this.flintController.loadModification(modification.getNamespace(), null);
            if (mod != null) {
                modification = mod;
            }
        }
        final FlintModification finalModification = modification;
        return () -> this.displayScreen(new AddonProfileActivity(this, this.flintController, finalModification));
    }
    
    private String getContextTitle() {
        final Object searchValue = this.contexts.get(Context.SEARCH);
        final String search = (searchValue == null) ? null : String.valueOf(searchValue);
        String title;
        if (search != null) {
            title = "Search ";
        }
        else {
            title = "";
        }
        for (Map.Entry<Context, Object> entry : this.contexts.entrySet()) {
            final Object value = entry.getValue();
            if (entry.getKey() == Context.CATEGORY) {
                title = title + ((FlintTag)value).getName() + "  ";
            }
            else {
                if (entry.getKey() != Context.TRENDING) {
                    continue;
                }
                title += "Trending Addons ";
            }
        }
        if (search != null) {
            title = title + "for: " + search;
        }
        return title;
    }
    
    public enum Context
    {
        SEARCH, 
        TRENDING, 
        CATEGORY, 
        SUBCATEGORY;
    }
}
