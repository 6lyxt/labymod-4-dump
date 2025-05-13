// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.models.config;

import net.labymod.core.shop.models.ShopItem;
import net.labymod.core.shop.models.PriceItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class ShopCurrency
{
    private final transient Int2ObjectOpenHashMap<PriceItem> prices;
    private String id;
    private String code;
    private String symbol;
    private String name;
    private float translation;
    private final transient boolean dummy;
    
    public ShopCurrency() {
        this.prices = (Int2ObjectOpenHashMap<PriceItem>)new Int2ObjectOpenHashMap();
        this.dummy = false;
    }
    
    public ShopCurrency(final String code) {
        this.prices = (Int2ObjectOpenHashMap<PriceItem>)new Int2ObjectOpenHashMap();
        this.code = code;
        this.dummy = true;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public String getSymbol() {
        return this.symbol;
    }
    
    public String getName() {
        return this.name;
    }
    
    public float getTranslation() {
        return this.translation;
    }
    
    public Int2ObjectOpenHashMap<PriceItem> getPrices() {
        return this.prices;
    }
    
    public PriceItem getPriceFor(final int itemId) {
        return (PriceItem)this.prices.get(itemId);
    }
    
    public PriceItem getPriceFor(final ShopItem item) {
        return (PriceItem)this.prices.get(item.getId());
    }
    
    public boolean isDummy() {
        return this.dummy;
    }
    
    @Override
    public String toString() {
        if (this.dummy) {
            return "Default (" + this.code;
        }
        return this.name + " (" + this.code;
    }
}
