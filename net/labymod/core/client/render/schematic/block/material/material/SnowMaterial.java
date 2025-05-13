// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.SchematicAccessor;

public class SnowMaterial extends Material
{
    public SnowMaterial() {
        super("snow");
    }
    
    @Override
    public boolean isFullBlock() {
        return false;
    }
    
    @Override
    public boolean shouldRenderFace(final SchematicAccessor level, final int x, final int y, final int z, final Block block, final Face face) {
        final Material materialAtFace = level.getBlockAt(x, y, z, face).material();
        return face != Face.BOTTOM && materialAtFace != this && (!materialAtFace.isFullBlock() || materialAtFace.isTranslucent()) && super.shouldRenderFace(level, x, y, z, block, face);
    }
    
    @Override
    public BoundingBox getBoundingBox(final SchematicAccessor level, final int x, final int y, final int z, final Block block) {
        final int layers = block.getParameter("layers", 1);
        this.boundingBox.set(0.0f, 0.0f, 0.0f, 1.0f, layers / 8.0f, 1.0f);
        return super.getBoundingBox(level, x, y, z, block);
    }
    
    @Override
    public TextureUV getUV(final SchematicAccessor level, final int x, final int y, final int z, final TextureAtlas atlas, final Block block, final Face face) {
        return super.getUV(level, x, y, z, atlas, block, face);
    }
    
    @Override
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUBE;
    }
}
