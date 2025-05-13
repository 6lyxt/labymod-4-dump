// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.core.labynet.DefaultLabyNetController;
import net.labymod.api.Laby;
import java.util.Locale;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import net.labymod.api.labynet.models.textures.Skin;
import java.util.function.Consumer;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.util.Debounce;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridFeedWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.skin.SkinPreviewWidget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.labynet.models.textures.TextureResult;
import java.util.Map;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;

@AutoActivity
@Link("activity/player/skin-browse.lss")
public class SkinBrowseActivity extends PlayerActivity.Child
{
    private static final int PAGE_SIZE = 42;
    private static final Map<TextureResult.Order, SkinBrowserOrderCache> ORDER_CACHES;
    private final ListSession<SkinPreviewWidget> session;
    private final TilesGridFeedWidget<SkinPreviewWidget> feedWidget;
    private final SkinActivity skinActivity;
    private final TextFieldWidget searchField;
    private SkinBrowserOrderCache selectedCache;
    private SkinCacheCollection selectedCollection;
    private int page;
    
    public SkinBrowseActivity(final SkinActivity skinActivity) {
        super(skinActivity.playerActivity(), skinActivity.getTranslationKeyPrefix() + "browse.", skinActivity.getGroupIdentifier());
        this.selectedCache = SkinBrowseActivity.ORDER_CACHES.get(TextureResult.Order.TRENDING);
        this.selectedCollection = this.selectedCache.collection("");
        this.skinActivity = skinActivity;
        this.session = new ListSession<SkinPreviewWidget>();
        (this.feedWidget = new TilesGridFeedWidget<SkinPreviewWidget>(this::refreshFeed)).addId("feed");
        this.feedWidget.doRefresh(false);
        (this.searchField = new TextFieldWidget()).addId("search-field");
        this.searchField.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        this.searchField.updateListener(string -> Debounce.of("skins-browse-search", 300L, () -> this.labyAPI.minecraft().executeOnRenderThread(() -> {
            this.selectedCollection = this.selectedCache.collection(string);
            this.session.setScrollPositionY(0.0f);
            this.reload();
        })));
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        this.loadPage(this.page = 0);
        final FlexibleContentWidget contentWidget = new FlexibleContentWidget();
        ((AbstractWidget<Widget>)contentWidget).addId("content");
        final HorizontalListWidget contentHeader = new HorizontalListWidget();
        ((AbstractWidget<Widget>)contentHeader).addId("content-header");
        final ButtonWidget backButton = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON);
        ((AbstractWidget<Widget>)backButton).addId("back-button");
        backButton.setPressable(() -> this.playerActivity.displayScreen(this.skinActivity));
        contentHeader.addEntry(backButton);
        contentHeader.addEntry(ComponentWidget.i18n(this.translationKeyPrefix + "title"));
        contentHeader.addEntry(this.searchField);
        final DropdownWidget<TextureResult.Order> orderDropdown = new DropdownWidget<TextureResult.Order>();
        orderDropdown.addId("order-dropdown");
        orderDropdown.addAll(TextureResult.Order.VALUES);
        orderDropdown.setSelected(this.selectedCache.order);
        orderDropdown.setTranslationKeyPrefix(this.translationKeyPrefix + "order");
        orderDropdown.setChangeListener(order -> {
            if (this.selectedCache.order != order) {
                this.selectedCache = SkinBrowseActivity.ORDER_CACHES.get(order);
                this.selectedCollection = this.selectedCache.collection(this.searchField.getText());
                this.session.setScrollPositionY(0.0f);
                this.reload();
            }
            return;
        });
        contentHeader.addEntry(orderDropdown);
        contentWidget.addContent(contentHeader);
        contentWidget.addFlexibleContent(new ScrollWidget(this.feedWidget, this.session).addId("player-scroll"));
        ((AbstractWidget<FlexibleContentWidget>)this.document).addChild(contentWidget);
    }
    
    @Override
    public boolean shouldHandleEscape() {
        return true;
    }
    
    @Override
    public boolean keyPressed(final Key key, final InputType type) {
        if (key == Key.ESCAPE) {
            this.playerActivity.displayScreen(this.skinActivity);
            return true;
        }
        return super.keyPressed(key, type);
    }
    
    @Override
    protected boolean allowModifiedModel() {
        return true;
    }
    
    private boolean refreshFeed(final TilesGridFeedWidget<SkinPreviewWidget> feedWidget, final Consumer<SkinPreviewWidget> add) {
        return this.loadPage(++this.page);
    }
    
    private boolean loadPage(final int page) {
        final SkinCache skinCache = this.selectedCollection.getOrLoadPage(page, cache -> {
            if (cache.textures.isEmpty()) {
                return;
            }
            else {
                this.labyAPI.minecraft().executeOnRenderThread(() -> this.fillFeed(cache, true));
                return;
            }
        });
        if (skinCache == null || skinCache.textures.isEmpty()) {
            return false;
        }
        this.fillFeed(skinCache, false);
        return true;
    }
    
    private void fillFeed(final SkinCache skinCache, final boolean withDebounce) {
        for (final Skin texture : skinCache.textures) {
            final SkinPreviewWidget skinPreviewWidget = new SkinPreviewWidget(this.skinActivity, texture);
            if (this.feedWidget.isInitialized()) {
                this.feedWidget.addTileInitialized(skinPreviewWidget);
            }
            else {
                this.feedWidget.addTile(skinPreviewWidget);
            }
        }
        if (this.feedWidget.isInitialized()) {
            this.feedWidget.updateBounds();
        }
        if (!withDebounce) {
            this.feedWidget.doRefresh(skinCache.textures.size() == 42);
            return;
        }
        Debounce.of("skins-browse-refresh", 500L, () -> this.labyAPI.minecraft().executeOnRenderThread(() -> this.feedWidget.doRefresh(skinCache.textures.size() == 42)));
    }
    
    static {
        ORDER_CACHES = new HashMap<TextureResult.Order, SkinBrowserOrderCache>();
        for (final TextureResult.Order order : TextureResult.Order.VALUES) {
            SkinBrowseActivity.ORDER_CACHES.put(order, new SkinBrowserOrderCache(order));
        }
    }
    
    private static class SkinBrowserOrderCache
    {
        private final List<SkinCacheCollection> skinBrowserCaches;
        private final SkinCacheCollection defaultBrowserCache;
        private final TextureResult.Order order;
        
        private SkinBrowserOrderCache(final TextureResult.Order order) {
            this.skinBrowserCaches = new ArrayList<SkinCacheCollection>();
            this.order = order;
            this.defaultBrowserCache = new SkinCacheCollection(order, null);
        }
        
        public SkinCacheCollection collection(final String query) {
            final String trim = query.toLowerCase(Locale.ROOT).trim();
            if (trim.length() == 0) {
                return this.defaultBrowserCache;
            }
            for (final SkinCacheCollection skinBrowserCache : this.skinBrowserCaches) {
                if (skinBrowserCache.search.equals(trim)) {
                    return skinBrowserCache;
                }
            }
            final SkinCacheCollection skinBrowserCache2 = new SkinCacheCollection(this.order, trim);
            this.skinBrowserCaches.add(skinBrowserCache2);
            return skinBrowserCache2;
        }
    }
    
    private static class SkinCacheCollection
    {
        private final List<SkinCache> skinCaches;
        private final String search;
        private final TextureResult.Order order;
        
        private SkinCacheCollection(final TextureResult.Order order, final String search) {
            this.skinCaches = new ArrayList<SkinCache>();
            this.search = search;
            this.order = order;
        }
        
        private SkinCache getOrLoadPage(final int page, final Consumer<SkinCache> callback) {
            final int offset = page * 42;
            for (final SkinCache skinCache : this.skinCaches) {
                if (skinCache.offset == offset) {
                    return skinCache;
                }
            }
            final LabyNetController labyNetController = Laby.references().labyNetController();
            ((DefaultLabyNetController)labyNetController).loadTextures(TextureResult.Type.SKIN, this.search, this.order, offset, 42, response -> {
                SkinCache skinCache2;
                if (response.isPresent()) {
                    skinCache2 = new SkinCache(offset, ((TextureResult)response.get()).getTextures());
                }
                else {
                    new SkinCache(offset, new ArrayList<Skin>());
                    final SkinCache skinCache3;
                    skinCache2 = skinCache3;
                }
                this.skinCaches.add(skinCache2);
                callback.accept(skinCache2);
                return;
            });
            return null;
        }
    }
    
    private static class SkinCache
    {
        private final int offset;
        private final List<Skin> textures;
        
        private SkinCache(final int offset, final List<Skin> textures) {
            this.offset = offset;
            this.textures = textures;
        }
        
        public List<Skin> getTextures() {
            return this.textures;
        }
        
        public int getOffset() {
            return this.offset;
        }
    }
}
