// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.SchematicAccessor;

public class CrossPlanesMaterial extends Material
{
    public CrossPlanesMaterial(final String id) {
        super(id);
    }
    
    @Override
    public boolean isFullBlock() {
        return false;
    }
    
    @Override
    public boolean shouldRenderFace(final SchematicAccessor level, final int x, final int y, final int z, final Block block, final Face face) {
        return face == Face.NORTH || face == Face.SOUTH;
    }
    
    @Override
    public BoundingBox getBoundingBox(final SchematicAccessor level, final int x, final int y, final int z, final Block block) {
        final float centerX = 0.5f;
        final float centerY = 0.5f;
        final float size = 0.38f;
        this.boundingBox.set(centerX - size, 0.0f, centerY - size, centerX + size, 1.0f, centerY + size);
        final FloatVector3 corner1 = this.boundingBox.getCorner(0).copy();
        final FloatVector3 corner2 = this.boundingBox.getCorner(1).copy();
        final FloatVector3 corner3 = this.boundingBox.getCorner(4).copy();
        final FloatVector3 corner4 = this.boundingBox.getCorner(5).copy();
        this.boundingBox.setCorner(1, corner1.getX(), corner1.getY(), corner1.getZ());
        this.boundingBox.setCorner(0, corner2.getX(), corner2.getY(), corner2.getZ());
        this.boundingBox.setCorner(5, corner3.getX(), corner3.getY(), corner3.getZ());
        this.boundingBox.setCorner(4, corner4.getX(), corner4.getY(), corner4.getZ());
        return this.boundingBox;
    }
    
    @Override
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUT_OUT;
    }
}
