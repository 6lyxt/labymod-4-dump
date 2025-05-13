// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_2.client.world.block;

import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.core.client.world.color.BlockColor;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.v1_20_2.client.world.LabyBiomeBlender;
import net.labymod.core.client.world.color.BlockColorCache;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.world.block.BlockColorProvider;

@Singleton
@Implements(BlockColorProvider.class)
public class VersionedBlockColorProvider implements BlockColorProvider
{
    private final BlockColorCache blockColorCache;
    private final LabyBiomeBlender biomeBlender;
    
    public VersionedBlockColorProvider() {
        this.blockColorCache = new BlockColorCache();
        this.biomeBlender = new LabyBiomeBlender();
    }
    
    @Override
    public int getColor(final BlockState state) {
        final aew blockId = jb.f.b((Object)state.block());
        final BlockColor blockColor = this.blockColorCache.getBlockColor(blockId.toString());
        if (blockColor != null) {
            return blockColor.color();
        }
        return state.getTopColor();
    }
    
    @Override
    public int getColorMultiplier(final BlockState state, final int minX, final int minZ, final int maxX, final int maxZ) {
        final fix level = eqv.O().r;
        if (level == null) {
            return 0;
        }
        final erw blockColors = eqv.O().aw();
        return blockColors.a((dfj)state, (coy)this.biomeBlender.setLevel((cpv)level).setArea(minX, minZ, maxX, maxZ), this.toBlockPos(state.position()), 0);
    }
    
    private gw toBlockPos(final IntVector3 vec) {
        return new gw(vec.getX(), vec.getY(), vec.getZ());
    }
}
