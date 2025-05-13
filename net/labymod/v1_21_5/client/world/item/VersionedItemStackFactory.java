// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_5.client.world.item;

import net.labymod.v1_21_5.client.util.MinecraftUtil;
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
        final dag item = (dag)mh.g.a((alr)location.getMinecraftLocation());
        final dak stack = new dak((dki)item);
        stack.e(count);
        return MinecraftUtil.fromMinecraft(stack);
    }
}
