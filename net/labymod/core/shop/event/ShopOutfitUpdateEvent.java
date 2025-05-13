// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.event;

import net.labymod.api.event.Event;

public class ShopOutfitUpdateEvent implements Event
{
    public static final ShopOutfitUpdateEvent INSTANCE;
    
    private ShopOutfitUpdateEvent() {
    }
    
    static {
        INSTANCE = new ShopOutfitUpdateEvent();
    }
}
