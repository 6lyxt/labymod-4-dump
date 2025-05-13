// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;

public class IceMaterial extends Material
{
    public IceMaterial(final String id) {
        super(id);
    }
    
    @Override
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUT_OUT;
    }
    
    @Override
    public float getTransparency() {
        return 0.75f;
    }
    
    @Override
    public boolean shouldRenderFace(final SchematicAccessor level, final int x, final int y, final int z, final Block block, final Face face) {
        return level.getBlockAt(x, y, z, face).material() != this && super.shouldRenderFace(level, x, y, z, block, face);
    }
}
