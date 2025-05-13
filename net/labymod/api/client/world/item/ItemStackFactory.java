// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.item;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ItemStackFactory
{
    default ItemStack create(final String namespace, final String path) {
        return this.create(namespace, path, 1);
    }
    
    default ItemStack create(final String namespace, final String path, final int count) {
        return this.create(ResourceLocation.create(namespace, path), count);
    }
    
    default ItemStack create(final ResourceLocation location) {
        return this.create(location, 1);
    }
    
    ItemStack create(final ResourceLocation p0, final int p1);
}
