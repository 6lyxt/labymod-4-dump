// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop;

import net.labymod.core.shop.event.ShopLabyPlusToggleEvent;
import net.labymod.api.client.component.Component;
import net.labymod.api.notification.Notification;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import java.util.Set;
import net.labymod.core.labymodnet.models.CosmeticOption;
import net.labymod.core.labymodnet.models.CosmeticOptionEntry;
import net.labymod.core.shop.models.PriceItem;
import com.google.gson.JsonElement;
import java.util.Map;
import com.google.gson.JsonObject;
import java.util.Collection;
import net.labymod.api.util.StringUtil;
import net.labymod.core.shop.models.config.ShopSeason;
import java.util.TreeMap;
import net.labymod.core.shop.models.ProductsResponse;
import net.labymod.api.util.io.web.request.Response;
import net.labymod.api.util.io.web.request.Request;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.request.AbstractRequest;
import java.util.Iterator;
import net.labymod.core.shop.event.CurrencyUpdateEvent;
import java.lang.reflect.Type;
import net.labymod.core.shop.models.ItemType;
import com.google.gson.GsonBuilder;
import net.labymod.api.Laby;
import net.labymod.core.shop.event.ShopOutfitUpdateEvent;
import java.util.ArrayList;
import net.labymod.core.labymodnet.models.CosmeticOptions;
import net.labymod.core.labymodnet.widgetoptions.WidgetOptionService;
import net.labymod.api.property.Property;
import net.labymod.core.shop.models.config.ShopCurrency;
import net.labymod.core.shop.models.config.ShopConfig;
import com.google.gson.Gson;
import net.labymod.core.shop.models.ItemCategory;
import java.util.List;
import net.labymod.core.shop.models.ShopItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;
import net.labymod.core.labymodnet.widgetoptions.OptionProvider;

@Singleton
@Referenceable
public class ShopController implements OptionProvider
{
    private static final Logging LOGGER;
    private final Int2ObjectOpenHashMap<ShopItem> shopItems;
    private final List<ItemCategory> categories;
    private final Gson gson;
    private final ShopListener shopListener;
    private ShopConfig config;
    private ShopCurrency selectedCurrency;
    private final Property<Boolean> connectedToLabyConnect;
    private final ShoppingCart shoppingCart;
    private final ShopItemStorage currentOutfit;
    private final Property<ShopItem> previewedItem;
    private final WidgetOptionService widgetOptionService;
    private CosmeticOptions cosmeticOptions;
    private boolean isLabyPlus;
    
    public ShopController() {
        this.shopItems = (Int2ObjectOpenHashMap<ShopItem>)new Int2ObjectOpenHashMap();
        this.categories = new ArrayList<ItemCategory>();
        this.shopListener = new ShopListener(this);
        this.connectedToLabyConnect = new Property<Boolean>(false);
        this.shoppingCart = new ShoppingCart(this);
        this.currentOutfit = new ShopItemStorage(() -> Laby.fireEvent(ShopOutfitUpdateEvent.INSTANCE));
        this.previewedItem = new Property<ShopItem>(null);
        this.widgetOptionService = new WidgetOptionService(this);
        this.gson = new GsonBuilder().registerTypeAdapter((Type)ItemType.class, (Object)new ItemType.ItemTypeTypeAdapter()).create();
        Laby.references().eventBus().registerListener(this.shopListener);
        Laby.labyAPI().config().other().preferredCurrency().addChangeListener(newValue -> {
            if (this.config != null) {
                final String identifier = newValue.equals("null") ? this.config.getDefaultCurrency() : newValue;
                this.config.getCurrencies().iterator();
                final Iterator iterator;
                while (iterator.hasNext()) {
                    final ShopCurrency currency = iterator.next();
                    if (currency.getCode().equals(identifier)) {
                        this.selectedCurrency = currency;
                        Laby.fireEvent(new CurrencyUpdateEvent(this.selectedCurrency));
                    }
                }
            }
        });
    }
    
    public void reload() {
        this.reloadConfig();
        this.reloadProducts();
    }
    
    public void reloadConfig() {
        final Response<ShopConfig> response = Request.ofGson(ShopConfig.class).url("https://www.labymod.net/api/shop/config", new Object[0]).readTimeout(5000).connectTimeout(5000).executeSync();
        if (response.hasException()) {
            ShopController.LOGGER.error("Failed to load shop config", response.exception());
            return;
        }
        if (!response.isPresent()) {
            ShopController.LOGGER.error("Failed to load shop config (no exception)", new Object[0]);
            return;
        }
        this.config = response.get();
    }
    
    public void reloadProducts() {
        this.shopItems.clear();
        this.categories.clear();
        final Response<ProductsResponse> response = Request.ofGson(ProductsResponse.class, this.gson).url("https://www.labymod.net/api/shop/products", new Object[0]).connectTimeout(5000).readTimeout(5000).executeSync();
        if (response.hasException()) {
            ShopController.LOGGER.error("Failed to load shop products", response.exception());
            return;
        }
        if (!response.isPresent()) {
            ShopController.LOGGER.error("Failed to load shop products (no exception)", new Object[0]);
            return;
        }
        this.shopListener.refreshOwnedShopItems();
        final Map<String, ItemCategory> categories = new TreeMap<String, ItemCategory>();
        final ProductsResponse productsResponse = response.get();
        final ShopItem[] items = productsResponse.getItems();
        for (int length = items.length, j = 0; j < length; ++j) {
            final ShopItem item = items[j];
            final ItemType type = item.getType();
            item.setOwned(this.shopListener.isOwned(item));
            if (item.isOnSale() || item.isOnPreview()) {
                if (type != null) {
                    this.shopItems.put(item.getId(), (Object)item);
                    final String category = this.modifyCategory(item);
                    categories.computeIfAbsent(type.getType() + "#" + category, identifier -> new ItemCategory(type, category)).addItem(item.getId());
                    final String seasonName = item.getSeasonName();
                    if (seasonName != null && (type == ItemType.EMOTE || type == ItemType.COSMETIC)) {
                        categories.computeIfAbsent(String.valueOf(ItemType.FEATURED) + "#" + seasonName, identifier -> {
                            final ItemCategory seasonCategory = new ItemCategory(ItemType.FEATURED, ItemType.SEASON, seasonName);
                            String niceName = seasonName;
                            if (this.config != null) {
                                this.config.getSeasons().iterator();
                                final Iterator iterator6;
                                while (iterator6.hasNext()) {
                                    final ShopSeason season = iterator6.next();
                                    if (seasonName.equals(season.getName())) {
                                        niceName = season.getNiceName();
                                        break;
                                    }
                                }
                            }
                            if (niceName != null && niceName.length() > 0) {
                                seasonCategory.setLocalizedIdentifier(StringUtil.capitalizeWords(niceName));
                            }
                            return seasonCategory;
                        }).addItem(item.getId());
                    }
                }
            }
        }
        this.categories.addAll(categories.values());
        this.categories.sort((o1, o2) -> {
            final ItemType firstType = o1.getType();
            final ItemType secondType = o2.getType();
            if (firstType != secondType) {
                return 0;
            }
            else {
                final String firstIdentifier = o1.getIdentifier();
                final String secondIdentifier = o2.getIdentifier();
                if (firstIdentifier.equals("null")) {
                    if (firstType == ItemType.COSMETIC) {
                        return 1;
                    }
                    else if (firstType == ItemType.EMOTE) {
                        return -1;
                    }
                }
                if (firstType == ItemType.COSMETIC) {
                    if (firstIdentifier.equals("partner")) {
                        return -1;
                    }
                    else if (secondIdentifier.equals("partner")) {
                        return 1;
                    }
                }
                return firstIdentifier.compareTo(secondIdentifier);
            }
        });
        ShopController.LOGGER.info("Loaded " + this.shopItems.size() + " shop items", new Object[0]);
        if (this.config == null) {
            return;
        }
        final String preferredCurrency = Laby.labyAPI().config().other().preferredCurrency().get();
        for (ShopCurrency currency : this.config.getCurrencies()) {
            final String code = currency.getCode();
            if ((this.selectedCurrency == null && code.equals(this.config.getDefaultCurrency())) || (this.selectedCurrency != null && this.selectedCurrency.getCode().equals(code))) {
                this.selectedCurrency = currency;
            }
            if (code.equals(preferredCurrency)) {
                this.selectedCurrency = currency;
            }
            currency.getPrices().clear();
            final JsonObject jsonObject = productsResponse.getPrices().get(code);
            if (jsonObject == null) {
                continue;
            }
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                final JsonElement value = entry.getValue();
                if (!value.isJsonObject()) {
                    continue;
                }
                try {
                    final int i = Integer.parseInt(entry.getKey());
                    currency.getPrices().put(i, (Object)this.gson.fromJson(value, (Class)PriceItem.class));
                }
                catch (final Exception e) {
                    ShopController.LOGGER.error("Failed to parse price for " + code + " " + (String)entry.getKey(), (Throwable)e);
                }
            }
        }
        ShopController.LOGGER.info("Loaded " + this.config.getCurrencies().size() + " shop currencies", new Object[0]);
        final Response<JsonObject> optionsResponse = Request.ofGson(JsonObject.class, this.gson).url("https://www.labymod.net/api/shop/cosmetics/options", new Object[0]).connectTimeout(5000).readTimeout(5000).executeSync();
        if (optionsResponse.hasException()) {
            ShopController.LOGGER.error("Failed to load shop cosmetics options", optionsResponse.exception());
            return;
        }
        if (!optionsResponse.isPresent()) {
            ShopController.LOGGER.error("Failed to load shop cosmetics options (no exception)", new Object[0]);
            return;
        }
        final List<CosmeticOptionEntry> list = new ArrayList<CosmeticOptionEntry>();
        final Set<Map.Entry<String, JsonElement>> options = optionsResponse.get().getAsJsonObject("options").entrySet();
        for (final Map.Entry<String, JsonElement> entry2 : options) {
            final String key = entry2.getKey();
            for (final JsonElement element : entry2.getValue().getAsJsonArray()) {
                final JsonObject object = element.getAsJsonObject();
                final JsonElement name = object.get("name");
                final JsonElement data = object.get("data");
                list.add(new CosmeticOptionEntry(key, name.isJsonNull() ? null : name.getAsString(), data.isJsonNull() ? null : data.getAsString()));
            }
        }
        final CosmeticOptions cosmeticOptions = new CosmeticOptions();
        final Map<String, CosmeticOption> map = cosmeticOptions.getMap();
        for (final CosmeticOptionEntry option : list) {
            map.computeIfAbsent(option.getCustomKey(), k -> new CosmeticOption()).push(option);
        }
        this.cosmeticOptions = cosmeticOptions;
        ShopController.LOGGER.info("Loaded " + list.size() + " shop cosmetics options", new Object[0]);
    }
    
    public ObjectCollection<ShopItem> getShopItems() {
        return (ObjectCollection<ShopItem>)this.shopItems.values();
    }
    
    public ShopItem getItem(final int id) {
        return (ShopItem)this.shopItems.get(id);
    }
    
    public ItemCategory findCategoryByType(final ItemType type) {
        for (final ItemCategory category : this.categories) {
            if (category.getType() == type) {
                return category;
            }
        }
        return null;
    }
    
    public List<ItemCategory> getCategories() {
        return this.categories;
    }
    
    public ShopCurrency getSelectedCurrency() {
        return this.selectedCurrency;
    }
    
    public Property<ShopItem> previewedItem() {
        return this.previewedItem;
    }
    
    public WidgetOptionService getWidgetOptionService() {
        return this.widgetOptionService;
    }
    
    public void pushNotification(final String translationKey) {
        Laby.references().notificationController().push(Notification.builder().title(Component.translatable("labymod.activity.shop.name", new Component[0])).text(Component.translatable(translationKey, new Component[0])).build());
    }
    
    private String modifyCategory(final ShopItem shopItem) {
        if (shopItem.getPartner() != null) {
            return "PARTNER";
        }
        String actualCategory = shopItem.getCategory();
        if (actualCategory == null) {
            return null;
        }
        String category;
        if (shopItem.getType() == ItemType.COSMETIC) {
            final String s = actualCategory;
            final String s3;
            actualCategory = (s3 = switch (s) {
                case "HATS" -> "HAT";
                case "WINGS" -> "WING";
                default -> actualCategory;
            });
            category = switch (s3) {
                case "HAT",  "HEADGEAR",  "FACE" -> "HEAD";
                case "BACK",  "LANYARD",  "CLOAK",  "ARMS" -> "BODY";
                default -> null;
            };
        }
        else {
            category = null;
        }
        if (category != null) {
            shopItem.setCategory(category);
            return category;
        }
        return actualCategory;
    }
    
    public ShopItemStorage currentOutfit() {
        return this.currentOutfit;
    }
    
    public ShoppingCart shoppingCart() {
        return this.shoppingCart;
    }
    
    public ShopConfig getConfig() {
        return this.config;
    }
    
    public Property<Boolean> connectedToLabyConnect() {
        return this.connectedToLabyConnect;
    }
    
    @Override
    public CosmeticOptions getCosmeticOptions() {
        return this.cosmeticOptions;
    }
    
    public boolean isSelectedCurrencyDefault() {
        return this.selectedCurrency == null || this.config == null || this.config.getDefaultCurrency() == null || this.selectedCurrency.getCode().equals(this.config.getDefaultCurrency());
    }
    
    public boolean isLabyPlus() {
        return this.isLabyPlus;
    }
    
    public void setLabyPlus(final boolean isLabyPlus) {
        if (this.isLabyPlus == isLabyPlus) {
            return;
        }
        this.isLabyPlus = isLabyPlus;
        Laby.fireEvent(ShopLabyPlusToggleEvent.INSTANCE);
    }
    
    static {
        LOGGER = Logging.create(ShopController.class);
    }
}
