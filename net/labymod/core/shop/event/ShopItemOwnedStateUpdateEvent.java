// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.event;

import net.labymod.core.shop.models.ShopItem;
import net.labymod.api.event.Event;

public class ShopItemOwnedStateUpdateEvent implements Event
{
    private final ShopItem shopItem;
    
    public ShopItemOwnedStateUpdateEvent(final ShopItem shopItem) {
        this.shopItem = shopItem;
    }
    
    public ShopItem item() {
        return this.shopItem;
    }
}
