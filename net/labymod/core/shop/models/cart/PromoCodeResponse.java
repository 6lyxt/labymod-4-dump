// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.models.cart;

import com.google.gson.annotations.SerializedName;

public class PromoCodeResponse
{
    private boolean successful;
    private String message;
    @SerializedName("promo_code")
    private PromoCode promoCode;
    @SerializedName("ref")
    private RefCode refCode;
    
    public boolean isSuccessful() {
        return this.successful;
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public Code getCode() {
        if (this.refCode != null) {
            return this.refCode;
        }
        return this.promoCode;
    }
    
    public class PromoCode implements Code
    {
        private String code;
        private String name;
        private String sale;
        @SerializedName("shop_item_id")
        private String shopItemId;
        
        public PromoCode(final PromoCodeResponse this$0) {
        }
        
        @Override
        public String getCode() {
            return this.code;
        }
        
        @Override
        public String getName() {
            return this.name;
        }
        
        @Override
        public float getMultiplier() {
            try {
                return Float.parseFloat(this.sale);
            }
            catch (final Exception e) {
                return 1.0f;
            }
        }
        
        @Override
        public int getShopItemId() {
            if (this.shopItemId == null) {
                return -1;
            }
            try {
                return Integer.parseInt(this.shopItemId);
            }
            catch (final Exception e) {
                return -1;
            }
        }
    }
    
    public class RefCode implements Code
    {
        @SerializedName("user_name")
        private String userName;
        @SerializedName("user_discount")
        private String userDiscount;
        @SerializedName("shop_item_id")
        private String shopItemId;
        private String code;
        
        public RefCode(final PromoCodeResponse this$0) {
        }
        
        @Override
        public String getCode() {
            return this.code;
        }
        
        public void setCode(final String code) {
            this.code = code;
        }
        
        @Override
        public String getName() {
            return this.userName;
        }
        
        @Override
        public float getMultiplier() {
            try {
                return Float.parseFloat(this.userDiscount);
            }
            catch (final Exception e) {
                return 1.0f;
            }
        }
        
        @Override
        public int getShopItemId() {
            if (this.shopItemId == null) {
                return -1;
            }
            try {
                return Integer.parseInt(this.shopItemId);
            }
            catch (final Exception e) {
                return -1;
            }
        }
    }
    
    public interface Code
    {
        String getCode();
        
        String getName();
        
        float getMultiplier();
        
        int getShopItemId();
    }
}
