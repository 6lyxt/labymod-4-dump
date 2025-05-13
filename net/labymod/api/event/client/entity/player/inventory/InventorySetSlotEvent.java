// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.entity.player.inventory;

import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.player.Inventory;
import net.labymod.api.event.Event;

record InventorySetSlotEvent(Inventory inventory, int index, ItemStack itemStack) implements Event {}
