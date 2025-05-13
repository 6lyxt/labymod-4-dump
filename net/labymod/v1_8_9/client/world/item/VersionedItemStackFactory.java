// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.world.item;

import net.labymod.v1_8_9.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.resources.ResourceLocation;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import net.labymod.api.client.world.item.ItemStackFactory;

@Implements(ItemStackFactory.class)
public class VersionedItemStackFactory implements ItemStackFactory
{
    private static final zx FALLBACK_ITEM;
    
    @Inject
    public VersionedItemStackFactory() {
    }
    
    @Override
    public ItemStack create(final ResourceLocation location, final int count) {
        final zw item = (zw)zw.e.a((Object)location.getMinecraftLocation());
        if (item == null) {
            return MinecraftUtil.fromMinecraft(VersionedItemStackFactory.FALLBACK_ITEM);
        }
        final zx stack = new zx(item);
        stack.b = count;
        return MinecraftUtil.fromMinecraft(stack);
    }
    
    static {
        FALLBACK_ITEM = new zx(zy.e);
    }
}
