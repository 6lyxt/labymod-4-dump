// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.SchematicAccessor;

public class FloorPlaneMaterial extends Material
{
    public FloorPlaneMaterial(final String id) {
        super(id);
    }
    
    @Override
    public boolean isFullBlock() {
        return false;
    }
    
    @Override
    public boolean shouldRenderFace(final SchematicAccessor level, final int x, final int y, final int z, final Block block, final Face face) {
        return face == Face.TOP;
    }
    
    @Override
    public BoundingBox getBoundingBox(final SchematicAccessor level, final int x, final int y, final int z, final Block block) {
        this.boundingBox.set(0.0f, 0.0f, 0.0f, 1.0f, 0.01f, 1.0f);
        return super.getBoundingBox(level, x, y, z, block);
    }
    
    @Override
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUT_OUT;
    }
}
