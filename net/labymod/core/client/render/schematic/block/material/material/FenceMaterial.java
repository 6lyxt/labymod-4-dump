// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.SchematicAccessor;

public class FenceMaterial extends Material
{
    public FenceMaterial(final String id) {
        super(id);
        this.resourceLocation = this.createResource("oak_planks");
        final float centerX = 0.5f;
        final float centerY = 0.5f;
        final float size = 0.125f;
        this.boundingBox.set(centerX - size, 0.0f, centerY - size, centerX + size, 1.0f, centerY + size);
    }
    
    @Override
    public boolean shouldRenderFace(final SchematicAccessor level, final int x, final int y, final int z, final Block block, final Face face) {
        if (face.isYAxis()) {
            final Block blockAtFace = level.getBlockAt(x, y, z, face);
            final Material materialAtFace = blockAtFace.material();
            if (materialAtFace == this || (materialAtFace.isFullBlock() && !materialAtFace.isTranslucent())) {
                return false;
            }
        }
        return super.shouldRenderFace(level, x, y, z, block, face);
    }
    
    @Override
    public boolean isFullBlock() {
        return false;
    }
    
    @Override
    public TextureUV getUV(final SchematicAccessor level, final int x, final int y, final int z, final TextureAtlas atlas, final Block block, final Face face) {
        return super.getUVForBoundingBox(atlas, this.resourceLocation, this.boundingBox, face);
    }
    
    @Override
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUBE;
    }
}
