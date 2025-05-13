// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.world.block;

import net.labymod.api.util.math.vector.IntVector3;
import net.labymod.core.client.world.color.BlockColor;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.v1_20_4.client.world.LabyBiomeBlender;
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
        final ahg blockId = kd.e.b((Object)state.block());
        final BlockColor blockColor = this.blockColorCache.getBlockColor(blockId.toString());
        if (blockColor != null) {
            return blockColor.color();
        }
        return state.getTopColor();
    }
    
    @Override
    public int getColorMultiplier(final BlockState state, final int minX, final int minZ, final int maxX, final int maxZ) {
        final fns level = evi.O().r;
        if (level == null) {
            return 0;
        }
        final ewl blockColors = evi.O().au();
        return blockColors.a((djh)state, (css)this.biomeBlender.setLevel((ctp)level).setArea(minX, minZ, maxX, maxZ), this.toBlockPos(state.position()), 0);
    }
    
    private hx toBlockPos(final IntVector3 vec) {
        return new hx(vec.getX(), vec.getY(), vec.getZ());
    }
}
