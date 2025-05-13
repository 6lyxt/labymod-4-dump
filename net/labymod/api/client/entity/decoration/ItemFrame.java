// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.decoration;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.entity.Entity;

public interface ItemFrame extends Entity
{
    @Nullable
    ItemStack getItem();
}
