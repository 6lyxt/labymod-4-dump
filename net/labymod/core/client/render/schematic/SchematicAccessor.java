// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic;

import net.labymod.core.client.render.schematic.lightning.legacy.LegacyLightEngine;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.lightning.legacy.WorldLightAccessor;

public interface SchematicAccessor extends WorldLightAccessor
{
    default Block getBlockAt(final int x, final int y, final int z, final Face face) {
        return this.getBlockAt(x + face.getX(), y + face.getY(), z + face.getZ());
    }
    
    short getWidth();
    
    short getHeight();
    
    short getLength();
    
    LegacyLightEngine legacyLightEngine();
}
