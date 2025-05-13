// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.models;

import java.util.Arrays;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.main.user.shop.item.AbstractItem;
import net.labymod.core.main.user.shop.item.ItemService;
import net.labymod.core.main.user.shop.item.metadata.MetadataException;
import net.labymod.core.main.user.shop.item.metadata.util.ItemMetadataUtil;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.Textures;
import net.labymod.api.labynet.models.textures.Skin;
import net.labymod.api.client.gui.icon.Icon;
import com.google.gson.annotations.SerializedName;

public class ShopItem
{
    private int id;
    private String category;
    private double sale;
    private boolean limited;
    private String[] images;
    private boolean rare;
    @SerializedName("item_name")
    private String name;
    @SerializedName("item_type")
    private ItemType type;
    @SerializedName("on_sale")
    private boolean onSale;
    @SerializedName("on_preview")
    private boolean onPreview;
    @SerializedName("plus_only")
    private boolean plusOnly;
    @SerializedName("new")
    private boolean isNew;
    @SerializedName("flash_sale")
    private boolean flashSale;
    @SerializedName("placement_sales_30d")
    private int placementSales30d;
    @SerializedName("placement_sales_24h")
    private int placementSales24h;
    @SerializedName("creator_name")
    private String creatorName;
    @SerializedName("item_id")
    private int itemId;
    @SerializedName("season_name")
    private String seasonName;
    private ShopItemPartner partner;
    private transient boolean owned;
    private transient Icon icon;
    
    public int getId() {
        return this.id;
    }
    
    public int getItemId() {
        return this.itemId;
    }
    
    public String getCategory() {
        return this.category;
    }
    
    public void setCategory(final String category) {
        this.category = category;
    }
    
    public boolean isLimited() {
        return this.limited;
    }
    
    public String[] getImages() {
        return this.images;
    }
    
    public boolean isRare() {
        return this.rare;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ItemType getType() {
        return this.type;
    }
    
    public boolean isOnSale() {
        return this.onSale;
    }
    
    public boolean isOnPreview() {
        return this.onPreview;
    }
    
    public boolean isSoon() {
        return !this.onSale && this.onPreview;
    }
    
    public boolean isPlusOnly() {
        return this.plusOnly;
    }
    
    public boolean isNew() {
        return this.isNew;
    }
    
    public boolean isFlashSale() {
        return this.flashSale;
    }
    
    public int getPlacementSales30d() {
        return this.placementSales30d;
    }
    
    public int getPlacementSales24h() {
        return this.placementSales24h;
    }
    
    public String getCreatorName() {
        return this.creatorName;
    }
    
    public String getSeasonName() {
        return this.seasonName;
    }
    
    public boolean isOwned() {
        return this.owned;
    }
    
    public void setOwned(final boolean owned) {
        this.owned = owned;
    }
    
    public Icon getPrimaryIcon() {
        if (this.icon == null) {
            if (this.type == ItemType.EMOTE && this.category == null) {
                this.icon = Icon.texture(Skin.LOADING).resolution(135, 257);
            }
            else if (this.images == null || this.images.length == 0) {
                this.icon = Icon.texture(Textures.EMPTY);
            }
            else {
                this.icon = Icon.url("https://www.labymod.net/images/products/" + this.images[0]);
            }
        }
        return this.icon;
    }
    
    @Nullable
    public ShopItemPartner getPartner() {
        return this.partner;
    }
    
    public boolean isFree() {
        return (this.id == 2 || this.id == 9) && Laby.labyAPI().labyModLoader().isLabyModDevelopmentEnvironment() && !Laby.labyAPI().labyModLoader().isAddonDevelopmentEnvironment();
    }
    
    public GameUserItem asGameUserItem() {
        final ItemService itemService = ((DefaultGameUserService)LabyMod.references().gameUserService()).itemService();
        AbstractItem item = itemService.getItemById(this.getItemId());
        if (item != null) {
            item = item.copy();
            item.setShopItem(true);
            final ItemMetadata metadata = new ItemMetadata(item.itemDetails());
            final String[] defaultOptions = item.itemDetails().getDefaultData();
            if (defaultOptions != null) {
                try {
                    ItemMetadataUtil.serialize(metadata, defaultOptions);
                }
                catch (final MetadataException e) {
                    return null;
                }
            }
            return new GameUserItem(item, metadata);
        }
        return null;
    }
    
    public Cosmetic asCosmetic() {
        final ItemService itemService = ((DefaultGameUserService)LabyMod.references().gameUserService()).itemService();
        final AbstractItem item = itemService.getItemById(this.itemId);
        if (item == null) {
            throw new IllegalStateException();
        }
        String[] defaultData = item.itemDetails().getDefaultData();
        String[] optionList = item.itemDetails().getOptionList();
        if (defaultData == null || optionList == null) {
            defaultData = new String[0];
            optionList = new String[0];
        }
        return new Cosmetic(this.id, this.itemId, this.name, optionList, Arrays.copyOf(defaultData, defaultData.length), Arrays.copyOf(defaultData, defaultData.length), true, -1, this.category);
    }
}
