// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_19_2.client.world.block;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.block.Block;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.world.block.Blocks;

@Singleton
@Implements(Blocks.class)
public class VersionedBlocks implements Blocks
{
    @Override
    public Block air() {
        return (Block)cju.a;
    }
    
    @Override
    public Block getBlock(final ResourceLocation location) {
        return (Block)hm.V.a((abb)location.getMinecraftLocation());
    }
}
