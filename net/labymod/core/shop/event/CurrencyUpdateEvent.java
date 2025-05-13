// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.event;

import net.labymod.core.shop.models.config.ShopCurrency;
import net.labymod.api.event.Event;

public class CurrencyUpdateEvent implements Event
{
    private final ShopCurrency currency;
    
    public CurrencyUpdateEvent(final ShopCurrency currency) {
        this.currency = currency;
    }
    
    public ShopCurrency getCurrency() {
        return this.currency;
    }
}
