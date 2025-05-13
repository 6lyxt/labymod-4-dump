// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.models.config;

import java.util.ArrayList;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ShopConfig
{
    private List<ShopSeason> seasons;
    private List<ShopCurrency> currencies;
    @SerializedName("default_currency")
    private String defaultCurrency;
    
    public ShopConfig() {
        this.seasons = new ArrayList<ShopSeason>();
        this.currencies = new ArrayList<ShopCurrency>();
    }
    
    public List<ShopSeason> getSeasons() {
        return this.seasons;
    }
    
    public List<ShopCurrency> getCurrencies() {
        return this.currencies;
    }
    
    public String getDefaultCurrency() {
        return this.defaultCurrency;
    }
}
