// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_18_2.client.player.inventory;

import java.util.Arrays;
import org.apache.commons.lang3.Validate;
import net.labymod.api.Laby;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import net.labymod.api.client.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import net.labymod.api.client.entity.player.Inventory;

public class InventoryTracker<E> extends gx<E>
{
    private final Inventory inventory;
    
    protected InventoryTracker(final List<E> list, @Nullable final E object, final Inventory inventory) {
        super((List)list, (Object)object);
        this.inventory = inventory;
    }
    
    @NotNull
    public E set(final int index, @NotNull final E object) {
        final E set = (E)super.set(index, (Object)object);
        Laby.fireEvent(new InventorySetSlotEvent(this.inventory, index, (ItemStack)object));
        return set;
    }
    
    public static <E> InventoryTracker<E> withSize(final int size, final E object, final Inventory inventory) {
        Validate.notNull((Object)object);
        final Object[] array = new Object[size];
        Arrays.fill(array, object);
        return new InventoryTracker<E>(Arrays.asList((E[])array), object, inventory);
    }
}
