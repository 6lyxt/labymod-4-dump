// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.models.cart;

import com.google.gson.annotations.SerializedName;

public class AddToCartResponse
{
    private boolean successful;
    private AddToCartItemResponse item;
    
    public boolean isSuccessful() {
        return this.successful;
    }
    
    public AddToCartItemResponse getItem() {
        return this.item;
    }
    
    public static class AddToCartItemResponse
    {
        @SerializedName("cart_item_id")
        private String cartItemId;
        
        public String getCartItemId() {
            return this.cartItemId;
        }
    }
}
