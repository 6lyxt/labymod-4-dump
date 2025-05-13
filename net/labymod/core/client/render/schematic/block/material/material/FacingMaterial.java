// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import java.util.Locale;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.api.client.resources.ResourceLocation;

public class FacingMaterial extends SolidMaterial
{
    protected ResourceLocation resourceTop;
    protected ResourceLocation resourceBottom;
    protected ResourceLocation resourceFront;
    protected ResourceLocation resourceSide;
    
    public FacingMaterial(final String id) {
        super(id);
        this.resourceTop = this.createResource(id + "_top");
        this.resourceBottom = this.createResource(id + "_bottom");
        this.resourceFront = this.createResource(id + "_front");
        this.resourceSide = this.createResource(id + "_side");
    }
    
    @Override
    public ResourceLocation getSpriteResource(final SchematicAccessor level, final int x, final int y, final int z, final Block block, final Face face) {
        if (face == Face.TOP) {
            return this.resourceTop;
        }
        if (face == Face.BOTTOM) {
            return this.resourceBottom;
        }
        final String facing = block.getParameter("facing", "north");
        if (Face.valueOf(facing.toUpperCase(Locale.ENGLISH)) == face) {
            return this.getFrontSpriteResource(block);
        }
        return this.resourceSide;
    }
    
    protected ResourceLocation getFrontSpriteResource(final Block block) {
        return this.resourceFront;
    }
}
