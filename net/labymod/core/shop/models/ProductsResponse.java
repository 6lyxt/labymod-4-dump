// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.models;

import java.util.HashMap;
import com.google.gson.JsonObject;
import java.util.Map;

public class ProductsResponse
{
    private ShopItem[] items;
    private Map<String, JsonObject> prices;
    
    public ProductsResponse() {
        this.items = new ShopItem[0];
        this.prices = new HashMap<String, JsonObject>();
    }
    
    public ShopItem[] getItems() {
        return this.items;
    }
    
    public Map<String, JsonObject> getPrices() {
        return this.prices;
    }
}
