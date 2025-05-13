// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.api.client.resources.ResourceLocation;

public class CraftingTableMaterial extends SolidMaterial
{
    private final ResourceLocation resourceTop;
    private final ResourceLocation resourceBottom;
    private final ResourceLocation resourceSide;
    
    public CraftingTableMaterial() {
        super("crafting_table");
        this.resourceTop = this.createResource("crafting_table_top");
        this.resourceBottom = this.createResource("crafting_table_bottom");
        this.resourceSide = this.createResource("crafting_table_side");
    }
    
    @Override
    public ResourceLocation getSpriteResource(final SchematicAccessor level, final int x, final int y, final int z, final Block block, final Face face) {
        if (face == Face.TOP) {
            return this.resourceTop;
        }
        if (face == Face.BOTTOM) {
            return this.resourceBottom;
        }
        return this.resourceSide;
    }
}
