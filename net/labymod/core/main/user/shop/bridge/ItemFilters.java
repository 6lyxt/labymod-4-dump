// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.bridge;

import net.labymod.core.main.user.shop.item.items.pet.WalkingPet;
import net.labymod.core.main.user.shop.item.items.minecraft.MinecraftItem;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.core.main.user.shop.item.AbstractItem;

public class ItemFilters
{
    private static final ItemFilter DEFAULT_FILTER;
    private static final ItemFilter NO_FILTER;
    private static final ItemFilter NO_WALKING_PETS;
    
    public static ItemFilter defaultFilter() {
        return ItemFilters.DEFAULT_FILTER;
    }
    
    public static ItemFilter noFilter() {
        return ItemFilters.NO_FILTER;
    }
    
    public static ItemFilter noWalkingPets() {
        return ItemFilters.NO_WALKING_PETS;
    }
    
    public static boolean filterItem(final AbstractItem item, final ItemStack itemStack) {
        if (item instanceof final MinecraftItem minecraftItem) {
            return !minecraftItem.resolved() || !itemStack.getIdentifier().equals(minecraftItem.getItemIdentifier());
        }
        return true;
    }
    
    static {
        DEFAULT_FILTER = (item -> {
            if (item instanceof final MinecraftItem minecraftItem) {
                return minecraftItem.isAvailable();
            }
            else {
                return item instanceof WalkingPet;
            }
        });
        NO_FILTER = (item -> false);
        NO_WALKING_PETS = (item -> item instanceof WalkingPet);
    }
}
