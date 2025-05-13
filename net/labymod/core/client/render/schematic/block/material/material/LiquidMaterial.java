// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.core.client.render.schematic.lightning.PointLightSource;
import net.labymod.core.client.render.schematic.lightning.LightSource;
import net.labymod.core.client.render.schematic.block.material.MaterialRegistry;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.render.schematic.block.Block;

public class LiquidMaterial extends Material
{
    private int rotation;
    
    public LiquidMaterial(final String id) {
        super(id);
        this.resourceLocation = this.createResource("lava_flow");
    }
    
    @Override
    public boolean isFullBlock() {
        return false;
    }
    
    @Override
    public int getLight(final Block block) {
        return 15;
    }
    
    @Override
    public int getLightColor(final Block block) {
        return ColorFormat.ARGB32.pack(1.0f, 0.44f, 0.05f, 1.0f);
    }
    
    @Override
    public boolean shouldRenderFace(final SchematicAccessor level, final int x, final int y, final int z, final Block block, final Face face) {
        final Block blockAtFace = level.getBlockAt(x, y, z, face);
        final Material materialAtFace = blockAtFace.material();
        if (materialAtFace == this) {
            return face != Face.TOP && face != Face.BOTTOM;
        }
        return !materialAtFace.isFullBlock();
    }
    
    @Override
    public BoundingBox getBoundingBox(final SchematicAccessor level, final int x, final int y, final int z, final Block block) {
        int length = block.getParameter("level", 15);
        if (level.getBlockAt(x, y + 1, z).material() == this) {
            length = 0;
        }
        this.boundingBox.setCorner(0, 0.0f, 0.0f, 0.0f);
        this.boundingBox.setCorner(1, 0.0f, 0.0f, 1.0f);
        this.boundingBox.setCorner(2, 1.0f, 0.0f, 0.0f);
        this.boundingBox.setCorner(3, 1.0f, 0.0f, 1.0f);
        this.rotation = -1;
        int edge1 = length;
        int edge2 = length;
        int edge3 = length;
        int edge4 = length;
        final int fall = 6;
        Block otherBlock = level.getBlockAt(x - 1, y, z);
        if (otherBlock.material() == this) {
            final int otherLength = (level.getBlockAt(x - 1, y + 1, z).material() == this) ? 0 : otherBlock.getParameter("level", 15);
            edge1 = Math.min(edge1, otherLength);
            edge2 = Math.min(edge2, otherLength);
            if (otherLength < length) {
                this.rotation = 1;
            }
        }
        else if (otherBlock.material() == MaterialRegistry.AIR && level.getBlockAt(x - 1, y - 1, z).material() == this && level.getBlockAt(x, y + 1, z).material() != this) {
            edge1 = fall;
            edge2 = fall;
        }
        otherBlock = level.getBlockAt(x + 1, y, z);
        if (otherBlock.material() == this) {
            final int otherLength = (level.getBlockAt(x + 1, y + 1, z).material() == this) ? 0 : otherBlock.getParameter("level", 15);
            edge3 = Math.min(edge3, otherLength);
            edge4 = Math.min(edge4, otherLength);
            if (otherLength < length) {
                this.rotation = 3;
            }
        }
        else if (otherBlock.material() == MaterialRegistry.AIR && level.getBlockAt(x + 1, y - 1, z).material() == this && level.getBlockAt(x, y + 1, z).material() != this) {
            edge3 = fall;
            edge4 = fall;
        }
        otherBlock = level.getBlockAt(x, y, z + 1);
        if (otherBlock.material() == this) {
            final int otherLength = (level.getBlockAt(x, y + 1, z + 1).material() == this) ? 0 : otherBlock.getParameter("level", 15);
            edge2 = Math.min(edge2, otherLength);
            edge4 = Math.min(edge4, otherLength);
            if (otherLength < length) {
                this.rotation = 0;
            }
        }
        else if (otherBlock.material() == MaterialRegistry.AIR && level.getBlockAt(x, y - 1, z + 1).material() == this && level.getBlockAt(x, y + 1, z).material() != this) {
            edge2 = fall;
            edge4 = fall;
        }
        otherBlock = level.getBlockAt(x, y, z - 1);
        if (otherBlock.material() == this) {
            final int otherLength = (level.getBlockAt(x, y + 1, z - 1).material() == this) ? 0 : otherBlock.getParameter("level", 15);
            edge1 = Math.min(edge1, otherLength);
            edge3 = Math.min(edge3, otherLength);
            if (otherLength < length) {
                this.rotation = 2;
            }
        }
        else if (otherBlock.material() == MaterialRegistry.AIR && level.getBlockAt(x, y - 1, z - 1).material() == this && level.getBlockAt(x, y + 1, z).material() != this) {
            edge1 = fall;
            edge3 = fall;
        }
        this.boundingBox.setCorner(4, 0.0f, 1.0f - edge1 / 8.0f, 0.0f);
        this.boundingBox.setCorner(5, 0.0f, 1.0f - edge2 / 8.0f, 1.0f);
        this.boundingBox.setCorner(6, 1.0f, 1.0f - edge3 / 8.0f, 0.0f);
        this.boundingBox.setCorner(7, 1.0f, 1.0f - edge4 / 8.0f, 1.0f);
        return super.getBoundingBox(level, x, y, z, block);
    }
    
    @Override
    public LightSource createLightSource(final int x, final int y, final int z, final Block block) {
        if (x % 4 != 0 && y % 4 != 0 && z % 4 != 0) {
            return null;
        }
        final int color = this.getLightColor(block);
        final PointLightSource lightSource = new PointLightSource();
        lightSource.getPosition().set(x + 0.5f, y + 0.5f, z + 0.5f);
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        lightSource.getColor().set(colorFormat.normalizedRed(color), colorFormat.normalizedGreen(color), colorFormat.normalizedBlue(color));
        lightSource.setQuadratic(0.44f);
        lightSource.setLinear(0.35f);
        lightSource.setConstant(1.0f);
        return lightSource;
    }
    
    @Override
    public TextureUV getUV(final SchematicAccessor level, final int x, final int y, final int z, final TextureAtlas atlas, final Block block, final Face face) {
        final long time = (this.rotation == -1 && face == Face.TOP) ? 0L : (TimeUtil.getMillis() / 300L);
        return super.getUV(level, x, y, z, atlas, block, face, time);
    }
    
    @Override
    public int getTextureRotation() {
        return (this.rotation == -1) ? 0 : this.rotation;
    }
    
    @Override
    public RenderLayer getRenderLayer() {
        return RenderLayer.LIQUID;
    }
    
    @Override
    public int getVertexType() {
        return 2;
    }
}
