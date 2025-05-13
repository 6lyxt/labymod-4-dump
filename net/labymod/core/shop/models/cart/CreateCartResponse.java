// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.models.cart;

import java.util.UUID;
import com.google.gson.annotations.SerializedName;

public class CreateCartResponse
{
    @SerializedName("cart_uuid")
    private String cartId;
    
    public UUID getCartId() {
        return UUID.fromString(this.cartId);
    }
}
