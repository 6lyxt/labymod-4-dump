// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.models;

import com.google.gson.annotations.SerializedName;

public class PriceItem
{
    private boolean sale;
    @SerializedName("price_1")
    private float oneMonth;
    @SerializedName("price_1_old")
    private float oneMonthOld;
    @SerializedName("price_3")
    private float threeMonths;
    @SerializedName("price_3_old")
    private float threeMonthsOld;
    @SerializedName("price_6")
    private float sixMonths;
    @SerializedName("price_6_old")
    private float sixMonthsOld;
    @SerializedName("price_12")
    private float twelveMonths;
    @SerializedName("price_12_old")
    private float twelveMonthsOld;
    @SerializedName("price_0")
    private float lifetime;
    @SerializedName("price_0_old")
    private float lifetimeOld;
    @SerializedName("price_custom")
    private float custom;
    @SerializedName("price_custom_old")
    private float customOld;
    
    public PriceItem() {
        this.oneMonth = -1.0f;
        this.oneMonthOld = -1.0f;
        this.threeMonths = -1.0f;
        this.threeMonthsOld = -1.0f;
        this.sixMonths = -1.0f;
        this.sixMonthsOld = -1.0f;
        this.twelveMonths = -1.0f;
        this.twelveMonthsOld = -1.0f;
        this.lifetime = -1.0f;
        this.lifetimeOld = -1.0f;
        this.custom = -1.0f;
        this.customOld = -1.0f;
    }
    
    public boolean isSale() {
        return this.sale;
    }
    
    public float getOneMonth() {
        return this.oneMonth;
    }
    
    public float getOneMonthOld() {
        return this.oneMonthOld;
    }
    
    public float getThreeMonths() {
        return this.threeMonths;
    }
    
    public float getThreeMonthsOld() {
        return this.threeMonthsOld;
    }
    
    public float getSixMonths() {
        return this.sixMonths;
    }
    
    public float getSixMonthsOld() {
        return this.sixMonthsOld;
    }
    
    public float getTwelveMonths() {
        return this.twelveMonths;
    }
    
    public float getTwelveMonthsOld() {
        return this.twelveMonthsOld;
    }
    
    public float getLifetime() {
        return this.lifetime;
    }
    
    public float getLifetimeOld() {
        return this.lifetimeOld;
    }
    
    public float getCustom() {
        return this.custom;
    }
    
    public float getCustomOld() {
        return this.customOld;
    }
    
    public float getCheapestPrice() {
        float cheapest = Float.MAX_VALUE;
        if (this.oneMonth != -1.0f && this.oneMonth < cheapest) {
            cheapest = this.oneMonth;
        }
        if (this.threeMonths != -1.0f && this.threeMonths < cheapest) {
            cheapest = this.threeMonths;
        }
        if (this.sixMonths != -1.0f && this.sixMonths < cheapest) {
            cheapest = this.sixMonths;
        }
        if (this.twelveMonths != -1.0f && this.twelveMonths < cheapest) {
            cheapest = this.twelveMonths;
        }
        if (this.lifetime != -1.0f && this.lifetime < cheapest) {
            cheapest = this.lifetime;
        }
        return cheapest;
    }
    
    public boolean isOnlyLifetime() {
        return this.lifetime != -1.0f && this.oneMonth == -1.0f && this.threeMonths == -1.0f && this.sixMonths == -1.0f && this.twelveMonths == -1.0f && this.custom == -1.0f;
    }
}
