// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.lightning.legacy;

import net.labymod.core.client.render.schematic.block.Block;

public interface WorldLightAccessor
{
    Block getBlockAt(final int p0, final int p1, final int p2);
    
    boolean isTranslucent(final int p0, final int p1, final int p2);
    
    boolean isFullBlock(final int p0, final int p1, final int p2);
    
    boolean isLightSource(final int p0, final int p1, final int p2);
    
    default int getLightLevel(final DefaultLegacyLightEngine engine, final Block block) {
        return block.material().getLight(block);
    }
}
