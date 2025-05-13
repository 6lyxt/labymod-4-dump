// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user;

import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.core.main.user.shop.item.AbstractItem;

public class GameUserItem
{
    private final AbstractItem item;
    private ItemMetadata itemMetadata;
    
    public GameUserItem(final AbstractItem item, final ItemMetadata itemMetadata) {
        this.item = item;
        this.itemMetadata = itemMetadata;
    }
    
    public AbstractItem item() {
        return this.item;
    }
    
    public ItemMetadata itemMetadata() {
        return this.itemMetadata;
    }
    
    public void itemMetadata(final ItemMetadata itemMetadata) {
        this.itemMetadata = itemMetadata;
    }
}
