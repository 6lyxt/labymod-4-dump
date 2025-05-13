// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_3.client.world.item;

import net.labymod.v1_21_3.client.util.MinecraftUtil;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.resources.ResourceLocation;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.models.Implements;
import net.labymod.api.client.world.item.ItemStackFactory;

@Implements(ItemStackFactory.class)
@Singleton
public class VersionedItemStackFactory implements ItemStackFactory
{
    @Inject
    public VersionedItemStackFactory() {
    }
    
    @Override
    public ItemStack create(final ResourceLocation location, final int count) {
        final cxl item = (cxl)ma.g.a((alz)location.getMinecraftLocation());
        final cxp stack = new cxp((dhh)item);
        stack.e(count);
        return MinecraftUtil.fromMinecraft(stack);
    }
}
