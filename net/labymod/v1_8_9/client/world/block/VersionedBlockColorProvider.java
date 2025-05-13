// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_8_9.client.world.block;

import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.core.client.world.color.BlockColor;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.core.client.world.color.BlockColorCache;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.world.block.BlockColorProvider;

@Singleton
@Implements(BlockColorProvider.class)
public class VersionedBlockColorProvider implements BlockColorProvider
{
    private final BlockColorCache blockColorCache;
    
    public VersionedBlockColorProvider() {
        this.blockColorCache = new BlockColorCache();
    }
    
    @Override
    public int getColor(final BlockState state) {
        final jy blockId = (jy)afh.c.c((Object)state.block());
        final BlockColor blockColor = this.blockColorCache.getBlockColor(blockId.toString());
        if (blockColor != null) {
            return blockColor.color();
        }
        return state.getTopColor();
    }
    
    @Override
    public int getColorMultiplier(final BlockState state) {
        final bdb level = ave.A().f;
        if (level == null) {
            return 0;
        }
        final afh block = (afh)state.block();
        return block.a((adq)level, this.toBlockPos(state.position()), 0);
    }
    
    @Override
    public int getColorMultiplier(final BlockState state, final int minX, final int minZ, final int maxX, final int maxZ) {
        return this.getColorMultiplier(state);
    }
    
    private cj toBlockPos(final IntVector3 vec) {
        return new cj(vec.getX(), vec.getY(), vec.getZ());
    }
}
