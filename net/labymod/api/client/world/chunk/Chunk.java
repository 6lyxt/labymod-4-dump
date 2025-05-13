// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.chunk;

import net.labymod.api.client.blockentity.BlockEntity;
import net.labymod.api.client.world.block.BlockState;

public interface Chunk
{
    int getChunkX();
    
    int getChunkZ();
    
    int getAbsoluteBlockX(final int p0);
    
    int getAbsoluteBlockZ(final int p0);
    
    Heightmap heightmap(final HeightmapType p0);
    
    boolean isLoaded();
    
    BlockState getBlockState(final int p0, final int p1, final int p2);
    
    BlockEntity[] getBlockEntities();
    
    int getHeightBasedOnSection(final int p0);
}
