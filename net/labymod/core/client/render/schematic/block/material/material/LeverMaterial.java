// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.core.client.render.schematic.block.BlockRenderer;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.api.client.resources.ResourceLocation;

public class LeverMaterial extends Material
{
    private final ResourceLocation lever;
    
    public LeverMaterial() {
        super("lever");
        this.resourceLocation = this.createResource("cobblestone");
        this.lever = this.createResource("lever");
    }
    
    @Override
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUT_OUT;
    }
    
    @Override
    public boolean isFullBlock() {
        return false;
    }
    
    @Override
    public BoundingBox getBoundingBox(final SchematicAccessor level, final int x, final int y, final int z, final Block block) {
        final String facing = block.getParameter("facing", "north");
        final String face = block.getParameter("face", "wall");
        final float unit = 0.0625f;
        final float baseWidth = unit * 6.0f;
        final float baseHeight = unit * 3.0f;
        final float baseDepth = unit * 8.0f;
        if (face.equals("floor")) {
            final float posX = unit * 8.0f - baseWidth / 2.0f;
            final float posY = 0.0f;
            final float posZ = unit * 8.0f - baseDepth / 2.0f;
            this.boundingBox.set(posX, posY, posZ, posX + baseWidth, posY + baseHeight, posZ + baseDepth);
        }
        if (face.equals("ceiling")) {
            final float posX = unit * 8.0f - baseWidth / 2.0f;
            final float posY = unit * 16.0f - baseHeight;
            final float posZ = unit * 8.0f - baseDepth / 2.0f;
            this.boundingBox.set(posX, posY, posZ, posX + baseWidth, posY + baseHeight, posZ + baseDepth);
        }
        if (face.equals("wall")) {
            if (facing.equals("north") || facing.equals("south")) {
                final float posX = unit * 8.0f - baseWidth / 2.0f;
                final float posY = unit * 8.0f - baseDepth / 2.0f;
                final float posZ = facing.equals("north") ? (unit * 16.0f - baseHeight) : 0.0f;
                this.boundingBox.set(posX, posY, posZ, posX + baseWidth, posY + baseDepth, posZ + baseHeight);
            }
            if (facing.equals("east") || facing.equals("west")) {
                final float posX = facing.equals("west") ? (unit * 16.0f - baseHeight) : 0.0f;
                final float posY = unit * 8.0f - baseDepth / 2.0f;
                final float posZ = unit * 8.0f - baseWidth / 2.0f;
                this.boundingBox.set(posX, posY, posZ, posX + baseHeight, posY + baseDepth, posZ + baseWidth);
            }
        }
        return super.getBoundingBox(level, x, y, z, block);
    }
    
    @Override
    public TextureUV getUV(final SchematicAccessor level, final int x, final int y, final int z, final TextureAtlas atlas, final Block block, final Face face) {
        return this.getUVForBoundingBox(atlas, this.resourceLocation, this.boundingBox, face);
    }
    
    @Override
    public void render(final BlockRenderer renderer, final TextureAtlas atlas, final BufferBuilder builder, final Block block, final SchematicAccessor level, final int x, final int y, final int z, final BoundingBox boundingBox) {
        super.render(renderer, atlas, builder, block, level, x, y, z, boundingBox);
        this.renderLever(renderer, atlas, builder, block, x, y, z);
    }
    
    private void renderLever(final BlockRenderer renderer, final TextureAtlas atlas, final BufferBuilder builder, final Block block, final int x, final int y, final int z) {
        final String faceType = block.getParameter("face", "wall");
        final boolean powered = block.getParameter("powered", false);
        final float unit = 16.0f;
        final float size = 2.0f / unit;
        final float height = 10.0f / unit;
        float posX = 0.0f;
        float posY = 0.0f;
        float posZ = 0.0f;
        BoundingBox bb = null;
        final float angle = 50.0f;
        if (faceType.equals("floor")) {
            posX = 0.5f - size / 2.0f;
            posY = 1.0f / unit;
            posZ = 0.5f - size / 2.0f;
            bb = new BoundingBox(posX, posY, posZ, posX + size, posY + height, posZ + size);
            bb.rotateX(0.5f, 1.0f / unit, 0.5f, angle * (powered ? -1 : 1));
        }
        if (faceType.equals("ceiling")) {
            posX = 0.5f - size / 2.0f;
            posY = 1.0f - height - 1.0f / unit;
            posZ = 0.5f - size / 2.0f;
            bb = new BoundingBox(posX, posY + height, posZ, posX + size, posY, posZ + size);
            bb.rotateX(0.5f, 1.0f - 1.0f / unit, 0.5f, angle * (powered ? -1 : 1));
        }
        if (faceType.equals("wall")) {
            final String facing = block.getParameter("facing", "north");
            final boolean south = facing.equals("south");
            if (facing.equals("north") || south) {
                posX = 0.5f - size / 2.0f;
                posY = 1.0f / unit;
                posZ = 0.5f - size / 2.0f;
                bb = new BoundingBox(posX, posY, posZ, posX + size, posY + height, posZ + size);
                bb.rotateX(0.5f, 0.5f, 0.5f, (float)(90 * (south ? 1 : -1)));
                bb.rotateX(0.5f, 0.5f, south ? (1.0f / unit) : (1.0f - 1.0f / unit), angle * ((powered ^ south) ? -1 : 1));
            }
            if (facing.equals("east") || facing.equals("west")) {
                final boolean west = facing.equals("west");
                posX = 0.5f - size / 2.0f;
                posY = 1.0f / unit;
                posZ = 0.5f - size / 2.0f;
                bb = new BoundingBox(posX, posY, posZ, posX + size, posY + height, posZ + size);
                bb.rotateZ(0.5f, 0.5f, 0.5f, (float)(-90 * (west ? -1 : 1)));
                bb.rotateZ(west ? (1.0f - 1.0f / unit) : (1.0f / unit), 0.5f, 0.0f, angle * ((powered ^ west) ? 1 : -1));
            }
        }
        if (bb == null) {
            return;
        }
        for (final Face face : Face.VALUES) {
            final TextureUV uv = this.getUVCut(atlas, this.lever, 7, (face == Face.BOTTOM) ? 14 : 6, 2, face.isYAxis() ? 2 : 10);
            renderer.renderFace(builder, block, face, x, y, z, bb, uv);
        }
    }
}
