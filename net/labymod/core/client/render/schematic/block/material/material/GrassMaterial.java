// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.Block;

public class GrassMaterial extends CrossPlanesMaterial
{
    public GrassMaterial() {
        super("grass");
    }
    
    @Override
    public int getColor(final Block block, final Face face) {
        return 8174955;
    }
}
