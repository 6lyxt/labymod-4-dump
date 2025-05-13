// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.player.inventory;

import net.labymod.api.event.Subscribe;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.Minecraft;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import net.labymod.api.Laby;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.client.world.item.ItemStack;

public class InventoryTracker
{
    private final ItemStack[] prevContent;
    
    public InventoryTracker() {
        this.prevContent = new ItemStack[36];
    }
    
    @Subscribe
    public void onTick(final GameTickEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        if (minecraft == null) {
            return;
        }
        final ClientPlayer player = minecraft.getClientPlayer();
        if (player == null) {
            return;
        }
        final Inventory inventory = player.inventory();
        for (int slot = 0; slot < this.prevContent.length; ++slot) {
            final ItemStack prevItemStack = this.prevContent[slot];
            final ItemStack itemStack = inventory.itemStackAt(slot);
            if (!this.compare(prevItemStack, itemStack)) {
                this.prevContent[slot] = itemStack;
                Laby.fireEvent(new InventorySetSlotEvent(inventory, slot, itemStack));
            }
        }
    }
    
    private boolean compare(final ItemStack prevItemStack, final ItemStack itemStack) {
        return (prevItemStack == null && itemStack == null) || (prevItemStack != null && itemStack != null && prevItemStack.getSize() == itemStack.getSize() && prevItemStack.getAsItem() == itemStack.getAsItem());
    }
}
