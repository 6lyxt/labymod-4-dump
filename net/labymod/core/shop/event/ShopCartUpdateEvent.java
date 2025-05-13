// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.event;

import net.labymod.api.event.Event;

public class ShopCartUpdateEvent implements Event
{
    public static final ShopCartUpdateEvent INSTANCE;
    
    private ShopCartUpdateEvent() {
    }
    
    static {
        INSTANCE = new ShopCartUpdateEvent();
    }
}
