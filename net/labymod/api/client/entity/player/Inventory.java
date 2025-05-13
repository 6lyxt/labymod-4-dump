// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player;

import net.labymod.api.client.world.item.ItemStack;

public interface Inventory
{
    int getSelectedIndex();
    
    void setSelectedIndex(final int p0);
    
    ItemStack itemStackAt(final int p0);
    
    void setCreativeModeItemStack(final int p0, final ItemStack p1);
    
    int countAllArrows();
    
    ItemStack getNextArrows();
}
