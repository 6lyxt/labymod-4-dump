// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.bridge;

import net.labymod.core.main.user.shop.item.AbstractItem;

@FunctionalInterface
public interface ItemFilter
{
    boolean filter(final AbstractItem p0);
}
