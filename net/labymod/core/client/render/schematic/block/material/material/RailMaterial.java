// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.api.client.resources.ResourceLocation;

public class RailMaterial extends FloorPlaneMaterial
{
    private final ResourceLocation resourceStraight;
    private final ResourceLocation resourceCorner;
    
    public RailMaterial(final String id) {
        super(id);
        this.resourceStraight = this.createResource("rail");
        this.resourceCorner = this.createResource("rail_corner");
    }
    
    @Override
    public ResourceLocation getSpriteResource(final SchematicAccessor level, final int x, final int y, final int z, final Block block, final Face face) {
        final String shape = block.getParameter("shape", "north");
        if (shape.equals("north_west") || shape.equals("north_east") || shape.equals("south_west") || shape.equals("south_east")) {
            return this.resourceCorner;
        }
        return this.resourceStraight;
    }
    
    @Override
    public BoundingBox getBoundingBox(final SchematicAccessor level, final int x, final int y, final int z, final Block block) {
        final String shape = block.getParameter("shape", "north_south");
        this.boundingBox.set(0.0f, 0.0f, 0.0f, 1.0f, 0.01f, 1.0f);
        final String s = shape;
        switch (s) {
            case "east_west":
            case "north_west": {
                this.boundingBox.rotateY(0.5f, 0.0f, 0.5f, 90.0f);
                break;
            }
            case "south_west": {
                this.boundingBox.rotateY(0.5f, 0.0f, 0.5f, 180.0f);
                break;
            }
        }
        return this.boundingBox;
    }
}
