// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop;

import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import java.util.List;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import java.util.function.Predicate;
import net.labymod.core.shop.event.ShopLabyPlusToggleEvent;
import net.labymod.api.property.Property;
import it.unimi.dsi.fastutil.ints.IntIterator;
import net.labymod.core.shop.models.config.ShopCurrency;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.core.shop.models.ShopItem;
import net.labymod.api.event.Subscribe;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.ShopItemWidget;
import net.labymod.core.shop.event.ShopItemOwnedStateUpdateEvent;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.SessionedListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import java.util.Locale;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.core.shop.models.ItemType;
import java.util.function.Supplier;
import net.labymod.core.shop.models.ItemCategory;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.SquareGridWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.SectionedListWidget;
import net.labymod.core.shop.ShopController;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Activity;

@AutoActivity
@Link("activity/shop/overview.lss")
public class ShopOverviewActivity extends Activity
{
    private final ShopController shopController;
    private final SectionedListWidget<SquareGridWidget<Widget>, ItemCategory> sectionedListWidget;
    private final Supplier<String> searchQuerySupplier;
    private ItemType itemType;
    
    public ShopOverviewActivity(final ShopController shopController, final Supplier<String> searchQuerySupplier, final Runnable onFocusedSectionChanged) {
        this.shopController = shopController;
        this.searchQuerySupplier = searchQuerySupplier;
        (this.sectionedListWidget = new SectionedListWidget<SquareGridWidget<Widget>, ItemCategory>()).onFocusedSectionChanged(onFocusedSectionChanged);
        shopController.previewedItem().addChangeListener(this::refreshSelectedActiveState);
    }
    
    @Override
    public void initialize(final Parent parent) {
        super.initialize(parent);
        if (this.itemType == null) {
            return;
        }
        String searchQuery = this.searchQuerySupplier.get();
        if (searchQuery != null) {
            searchQuery = searchQuery.toLowerCase(Locale.ROOT).replace(" ", "");
        }
        for (final ItemCategory category : this.shopController.getCategories()) {
            if (!category.getType().isVisible()) {
                continue;
            }
            if (category.getType() != this.itemType && searchQuery == null) {
                continue;
            }
            this.addSection(this.sectionedListWidget, category, searchQuery);
        }
        ((AbstractWidget<ScrollWidget>)this.document).addChild(new ScrollWidget(this.sectionedListWidget));
    }
    
    public void focusCategory(final ItemCategory itemCategory, final Runnable runnable) {
        if (this.itemType != itemCategory.getType()) {
            this.itemType = itemCategory.getType();
            this.reload();
            this.labyAPI.minecraft().executeNextTick(() -> {
                this.sectionedListWidget.setFocusedSection(itemCategory, true);
                runnable.run();
            });
        }
        else {
            this.sectionedListWidget.setFocusedSection(itemCategory, true);
            runnable.run();
        }
    }
    
    public ItemCategory getFocusedCategory() {
        return (this.sectionedListWidget != null) ? this.sectionedListWidget.getFocusedSection() : null;
    }
    
    public void focusType(final ItemType itemType) {
        if (this.itemType == itemType) {
            return;
        }
        this.itemType = itemType;
        if (((Document)this.document).isInitialized()) {
            this.reload();
        }
    }
    
    public ItemType getItemType() {
        return this.itemType;
    }
    
    public SectionedListWidget<SquareGridWidget<Widget>, ItemCategory> getSectionedListWidget() {
        return this.sectionedListWidget;
    }
    
    @Subscribe
    public void onItemOwnedUpdate(final ShopItemOwnedStateUpdateEvent event) {
        final ShopItemWidget widgetFor = this.getWidgetFor(event.item());
        if (widgetFor != null) {
            widgetFor.reInitialize();
        }
    }
    
    private void addSection(final SectionedListWidget<SquareGridWidget<Widget>, ItemCategory> sectionedListWidget, final ItemCategory itemCategory, final String searchQuery) {
        final ShopCurrency selectedCurrency = this.shopController.getSelectedCurrency();
        final ShopItem previewedItem = this.shopController.previewedItem().get();
        final SquareGridWidget<Widget> itemGrid = new SquareGridWidget<Widget>();
        itemGrid.addId("item-grid");
        for (final Integer id : itemCategory.getItems()) {
            final ShopItem item = this.shopController.getItem(id);
            if (item != null) {
                if (searchQuery != null && !this.doesCosmeticMatchQuery(searchQuery, item)) {
                    continue;
                }
                final ShopItemWidget itemWidget = new ShopItemWidget(item, selectedCurrency, this.shopController);
                ((AbstractWidget<Widget>)itemWidget).addId("item");
                if (previewedItem != null && previewedItem.getId() == item.getId()) {
                    itemWidget.setActive(true);
                }
                itemWidget.setPressListener(() -> {
                    final Property<ShopItem> shopItemProperty = this.shopController.previewedItem();
                    if (shopItemProperty.get() != item) {
                        shopItemProperty.set(item);
                        return true;
                    }
                    else {
                        return false;
                    }
                });
                final DivWidget stencilWrapper = new DivWidget();
                stencilWrapper.addId("stencil-wrapper");
                ((AbstractWidget<ShopItemWidget>)stencilWrapper).addChild(itemWidget);
                itemGrid.addChild(stencilWrapper);
            }
        }
        if (itemGrid.getChildren().isEmpty()) {
            return;
        }
        final String key = itemCategory.getLocalizedIdentifier();
        sectionedListWidget.addSection(Component.text((key == null) ? itemCategory.getIdentifier() : key), itemCategory, itemGrid);
    }
    
    @Subscribe
    public void onShopLabyPlusUpdate(final ShopLabyPlusToggleEvent event) {
        this.forEachAndGetItemWidget(widget -> {
            if (widget.item().isPlusOnly()) {
                widget.reInitialize();
            }
            return false;
        });
    }
    
    private boolean doesCosmeticMatchQuery(final String searchQuery, final ShopItem shopItem) {
        return this.matches(searchQuery, shopItem.getName()) || this.matches(searchQuery, shopItem.getCreatorName()) || this.matches(searchQuery, shopItem.getSeasonName());
    }
    
    private boolean matches(final String searchQuery, final String value) {
        return value != null && value.toLowerCase(Locale.ROOT).replace(" ", "").contains(searchQuery);
    }
    
    private void refreshSelectedActiveState(final ShopItem shopItem) {
        final int id = (shopItem == null) ? Integer.MIN_VALUE : shopItem.getId();
        this.forEachAndGetItemWidget(widget -> {
            widget.setActive(widget.item().getId() == id);
            return false;
        });
    }
    
    private ShopItemWidget getWidgetFor(final ShopItem shopItem) {
        final int id = (shopItem == null) ? Integer.MIN_VALUE : shopItem.getId();
        return this.forEachAndGetItemWidget(widget -> widget.item().getId() == id);
    }
    
    private ShopItemWidget forEachAndGetItemWidget(final Predicate<ShopItemWidget> predicate) {
        final List<Widget> children = this.sectionedListWidget.getChildren();
        for (final Widget child : children) {
            if (!(child instanceof SquareGridWidget)) {
                continue;
            }
            for (Widget childChild : child.getChildren()) {
                if (childChild instanceof final WrappedWidget wrappedWidget) {
                    childChild = wrappedWidget.childWidget();
                }
                if (childChild.hasId("stencil-wrapper")) {
                    childChild = (Widget)childChild.getChildren().get(0);
                }
                if (childChild instanceof final ShopItemWidget itemWidget) {
                    if (predicate.test(itemWidget)) {
                        return itemWidget;
                    }
                    continue;
                }
            }
        }
        return null;
    }
}
