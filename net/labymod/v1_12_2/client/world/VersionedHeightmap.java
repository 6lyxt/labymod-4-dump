// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.world;

import net.labymod.api.client.world.chunk.Heightmap;

public class VersionedHeightmap implements Heightmap
{
    private final axw chunk;
    
    public VersionedHeightmap(final axw chunk) {
        this.chunk = chunk;
    }
    
    @Override
    public int getHeight(final int x, final int z) {
        return this.chunk.b(x, z);
    }
}
