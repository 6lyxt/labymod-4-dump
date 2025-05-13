// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.object.lootbox;

import java.util.Objects;
import net.labymod.api.util.Lazy;

public final class LootBoxInventoryItem
{
    private final int type;
    private final Lazy<LootBox> lootBox;
    private boolean available;
    
    private LootBoxInventoryItem(final int type, final boolean available, final Lazy<LootBox> lootBox) {
        this.type = type;
        this.lootBox = lootBox;
        this.available = available;
    }
    
    public static LootBoxInventoryItem create(final LootBoxService service, final int type, final boolean available) {
        return new LootBoxInventoryItem(type, available, Lazy.of(() -> {
            final LootBox lootBox = service.byId(type);
            if (lootBox == null) {
                return null;
            }
            else {
                return lootBox.copy();
            }
        }));
    }
    
    public int getType() {
        return this.type;
    }
    
    public boolean isAvailable() {
        return this.available;
    }
    
    public void setAvailable(final boolean available) {
        this.available = available;
    }
    
    public LootBox getLootBox() {
        return this.lootBox.get();
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        final LootBoxInventoryItem that = (LootBoxInventoryItem)obj;
        return this.type == that.type && this.available == that.available;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.available);
    }
    
    @Override
    public String toString() {
        return "LootBoxInventoryItem[type=" + this.type + ", available=" + this.available;
    }
}
