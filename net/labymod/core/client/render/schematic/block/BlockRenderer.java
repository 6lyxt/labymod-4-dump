// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block;

import net.labymod.core.client.render.schematic.lightning.legacy.LegacyLightEngine;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.core.client.render.schematic.lightning.LightSource;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.material.material.Material;
import net.labymod.core.client.render.schematic.lightning.LightSourceRegistry;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.core.client.render.schematic.SchematicAccessor;

public class BlockRenderer
{
    private final SchematicAccessor schematic;
    
    public BlockRenderer(final SchematicAccessor level) {
        this.schematic = level;
    }
    
    public void renderCube(final TextureAtlas atlas, final BufferBuilder builder, final Block block, final int x, final int y, final int z) {
        final Material material = block.material();
        final BoundingBox boundingBox = material.getBoundingBox(this.schematic, x, y, z, block);
        final LightSource lightSource = material.createLightSource(x, y, z, block);
        if (lightSource != null) {
            LightSourceRegistry.getInstance().addLightSource(lightSource);
            block.setLightSource(lightSource);
        }
        material.render(this, atlas, builder, block, this.schematic, x, y, z, boundingBox);
    }
    
    public void renderFace(final TextureAtlas atlas, final BufferBuilder builder, final Block block, final Face face, final int x, final int y, final int z, final BoundingBox bb) {
        final Material material = block.material();
        final TextureUV uv = material.getUV(this.schematic, x, y, z, atlas, block, face);
        if (uv == null) {
            return;
        }
        this.renderFace(builder, block, face, x, y, z, bb, uv);
    }
    
    public void renderFace(final BufferBuilder builder, final Block block, final Face face, final int x, final int y, final int z, final BoundingBox bb, final TextureUV uv) {
        final Material material = block.material();
        final float minU = uv.getMinU();
        final float minV = uv.getMinV();
        final float maxU = uv.getMaxU();
        final float maxV = uv.getMaxV();
        final int color = material.getColor(block, face);
        final float r = (color >> 16 & 0xFF) / 255.0f;
        final float g = (color >> 8 & 0xFF) / 255.0f;
        final float b = (color & 0xFF) / 255.0f;
        final int type = material.getVertexType();
        final int rot = material.getTextureRotation();
        switch (face) {
            case TOP: {
                this.renderCorner(builder, face, x, y, z, bb.getCorner(4, rot), minU, maxV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(5, rot), minU, minV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(7, rot), maxU, minV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(6, rot), maxU, maxV, r, g, b, type);
                break;
            }
            case BOTTOM: {
                this.renderCorner(builder, face, x, y, z, bb.getCorner(1, rot), maxU, maxV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(0, rot), maxU, minV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(2, rot), minU, minV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(3, rot), minU, maxV, r, g, b, type);
                break;
            }
            case NORTH: {
                this.renderCorner(builder, face, x, y, z, bb.getCorner(0), minU, maxV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(2), maxU, maxV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(6), maxU, minV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(4), minU, minV, r, g, b, type);
                break;
            }
            case EAST: {
                this.renderCorner(builder, face, x, y, z, bb.getCorner(2), minU, maxV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(3), maxU, maxV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(7), maxU, minV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(6), minU, minV, r, g, b, type);
                break;
            }
            case SOUTH: {
                this.renderCorner(builder, face, x, y, z, bb.getCorner(1), maxU, maxV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(3), minU, maxV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(7), minU, minV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(5), maxU, minV, r, g, b, type);
                break;
            }
            case WEST: {
                this.renderCorner(builder, face, x, y, z, bb.getCorner(0), maxU, maxV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(1), minU, maxV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(5), minU, minV, r, g, b, type);
                this.renderCorner(builder, face, x, y, z, bb.getCorner(4), maxU, minV, r, g, b, type);
                break;
            }
        }
    }
    
    private void renderCorner(final BufferBuilder builder, final Face face, final int x, final int y, final int z, final FloatVector3 offset, final float u, final float v, float red, float green, float blue, final int vertexType) {
        final float posX = x + offset.getX();
        final float posY = y + offset.getY();
        final float posZ = z + offset.getZ();
        builder.pos(posX, posY, posZ);
        builder.uv(u, v);
        final int blockX = MathHelper.floor(posX);
        final int blockY = MathHelper.floor(posY);
        final int blockZ = MathHelper.floor(posZ);
        final LegacyLightEngine engine = this.schematic.legacyLightEngine();
        red *= engine.getRedStrengthAt(blockX, blockY, blockZ);
        green *= engine.getGreenStrengthAt(blockX, blockY, blockZ);
        blue *= engine.getBlueStrengthAt(blockX, blockY, blockZ);
        builder.color(red, green, blue, 1.0f);
        builder.putFloat((float)vertexType);
        builder.normal((float)face.getX(), (float)face.getY(), (float)face.getZ());
        builder.endVertex();
    }
}
