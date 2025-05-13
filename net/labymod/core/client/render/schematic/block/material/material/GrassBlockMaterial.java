// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.material.MaterialRegistry;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.api.client.resources.ResourceLocation;

public class GrassBlockMaterial extends SolidMaterial
{
    private final ResourceLocation resourceTop;
    private final ResourceLocation resourceBottom;
    private final ResourceLocation resourceSide;
    private final ResourceLocation resourceSideSnow;
    
    public GrassBlockMaterial() {
        super("grass_block");
        this.resourceTop = this.createResource("grass_block_top");
        this.resourceBottom = this.createResource("dirt");
        this.resourceSide = this.createResource("grass_block_side");
        this.resourceSideSnow = this.createResource("grass_block_snow");
    }
    
    @Override
    public ResourceLocation getSpriteResource(final SchematicAccessor level, final int x, final int y, final int z, final Block block, final Face face) {
        if (face == Face.TOP) {
            return this.resourceTop;
        }
        if (face == Face.BOTTOM) {
            return this.resourceBottom;
        }
        final Block blockAtTop = level.getBlockAt(x, y, z, Face.TOP);
        if (blockAtTop.material() == MaterialRegistry.SNOW) {
            return this.resourceSideSnow;
        }
        return this.resourceSide;
    }
    
    @Override
    public int getColor(final Block block, final Face face) {
        if (face != Face.TOP) {
            return 16777215;
        }
        return 8174955;
    }
}
