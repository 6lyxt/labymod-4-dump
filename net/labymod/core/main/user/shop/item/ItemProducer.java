// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item;

@FunctionalInterface
public interface ItemProducer
{
    AbstractItem produce(final int p0, final ItemDetails p1);
}
