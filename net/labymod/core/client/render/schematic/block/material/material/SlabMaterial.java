// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureUV;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.SchematicAccessor;

public class SlabMaterial extends Material
{
    public SlabMaterial(final String id) {
        super(id);
        this.resourceLocation = this.createResource(this.id.equals("oak_slab") ? "oak_planks" : "cobblestone");
    }
    
    @Override
    public boolean isFullBlock() {
        return false;
    }
    
    @Override
    public boolean shouldRenderFace(final SchematicAccessor level, final int x, final int y, final int z, final Block block, final Face face) {
        final String type = block.getParameter("type", "bottom");
        final Block blockAtFace = level.getBlockAt(x, y, z, face);
        final Material materialAtFace = blockAtFace.material();
        final String typeAtFace = blockAtFace.getParameter("type", "bottom");
        return (materialAtFace != this || !type.equals(typeAtFace)) && (!materialAtFace.isFullBlock() || materialAtFace.isTranslucent()) && super.shouldRenderFace(level, x, y, z, block, face);
    }
    
    @Override
    public BoundingBox getBoundingBox(final SchematicAccessor level, final int x, final int y, final int z, final Block block) {
        final String s;
        final String type = s = block.getParameter("type", "bottom");
        switch (s) {
            case "top": {
                this.boundingBox.set(0.0f, 0.5f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
            }
            case "double": {
                this.boundingBox.set(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
                break;
            }
            default: {
                this.boundingBox.set(0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 1.0f);
                break;
            }
        }
        return super.getBoundingBox(level, x, y, z, block);
    }
    
    @Override
    public TextureUV getUV(final SchematicAccessor level, final int x, final int y, final int z, final TextureAtlas atlas, final Block block, final Face face) {
        final TextureUV uv = super.getUV(level, x, y, z, atlas, block, face);
        if (uv == null) {
            return null;
        }
        final String type = block.getParameter("type", "bottom");
        if (type.equals("double")) {
            return uv;
        }
        final float height = uv.getMaxV() - uv.getMinV();
        return new DefaultTextureUV(uv.getMinU(), uv.getMinV(), uv.getMaxU(), uv.getMinV() + height / 16.0f * 8.0f);
    }
    
    @Override
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUBE;
    }
}
