// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.shop.event;

import net.labymod.api.event.Event;

public class ShopLabyPlusToggleEvent implements Event
{
    public static ShopLabyPlusToggleEvent INSTANCE;
    
    private ShopLabyPlusToggleEvent() {
    }
    
    static {
        ShopLabyPlusToggleEvent.INSTANCE = new ShopLabyPlusToggleEvent();
    }
}
