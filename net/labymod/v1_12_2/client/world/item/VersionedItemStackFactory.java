// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.world.item;

import net.labymod.v1_12_2.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.resources.ResourceLocation;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import net.labymod.api.client.world.item.ItemStackFactory;

@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory
{
    private static final aip FALLBACK_ITEM;
    
    @Inject
    public VersionedItemStackFactory() {
    }
    
    @Override
    public ItemStack create(final ResourceLocation location, final int count) {
        final ain item = (ain)ain.g.c((Object)location.getMinecraftLocation());
        if (item == null) {
            return MinecraftUtil.fromMinecraft(VersionedItemStackFactory.FALLBACK_ITEM);
        }
        final aip stack = new aip(item, count);
        return MinecraftUtil.fromMinecraft(stack);
    }
    
    static {
        FALLBACK_ITEM = new aip(air.f);
    }
}
