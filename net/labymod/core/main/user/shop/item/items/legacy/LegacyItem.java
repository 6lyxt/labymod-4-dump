// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.items.legacy;

import net.labymod.api.client.render.model.Model;
import net.labymod.core.main.user.shop.item.ItemDetails;
import net.labymod.core.main.user.shop.item.AbstractItem;

public abstract class LegacyItem extends AbstractItem
{
    public LegacyItem(final int listId, final ItemDetails itemDetails) {
        super(listId, itemDetails);
        this.model = this.buildGeometry();
    }
    
    public abstract Model buildGeometry();
    
    @Override
    public boolean isRemote() {
        return false;
    }
}
