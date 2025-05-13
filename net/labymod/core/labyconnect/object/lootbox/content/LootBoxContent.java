// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.lootbox.content;

import net.labymod.api.client.component.Component;
import java.util.List;

record LootBoxContent(List<LootBoxShopItem> pool, int durationInDays) {
    public static final int PRICE_INDEX = 29;
    public static final long REVEAL_TIME = 14500L;
    
    public LootBoxShopItem getPriceShopItem() {
        return this.pool.get(29);
    }
    
    public Component getDurationComponent() {
        final String key = "labymod.activity.lootBox.duration.";
        if (this.durationInDays == -1) {
            return Component.translatable(key + "lifetime", new Component[0]);
        }
        if (this.durationInDays == 1) {
            return Component.translatable(key + "day", new Component[0]);
        }
        return Component.translatable(key + "days", new Component[0]).argument(Component.text(this.durationInDays));
    }
    
    public boolean hasDuration() {
        return this.durationInDays != -2;
    }
}
