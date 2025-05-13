// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.core.client.render.schematic.particle.ParticleRenderer;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.core.client.render.schematic.block.BlockRenderer;
import net.labymod.core.client.render.schematic.lightning.LightSource;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.client.gfx.pipeline.texture.atlas.DefaultTextureUV;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.pipeline.texture.atlas.AnimatedTextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.material.MaterialRegistry;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Random;

public abstract class Material
{
    protected static final Random RANDOM;
    protected String id;
    protected ResourceLocation resourceLocation;
    protected final BoundingBox boundingBox;
    
    public Material(final String id) {
        this.id = id;
        this.resourceLocation = this.createResource(id);
        this.boundingBox = new BoundingBox(0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
        MaterialRegistry.register(this);
    }
    
    protected ResourceLocation createResource(final String id) {
        return ResourceLocation.create("labymod", "block/" + id);
    }
    
    public ResourceLocation getSpriteResource(final SchematicAccessor level, final int x, final int y, final int z, final Block block, final Face face) {
        return this.resourceLocation;
    }
    
    public int getColor(final Block block, final Face face) {
        return 16777215;
    }
    
    public BoundingBox getBoundingBox(final SchematicAccessor level, final int x, final int y, final int z, final Block block) {
        return this.boundingBox;
    }
    
    public boolean shouldRenderFace(final SchematicAccessor level, final int x, final int y, final int z, final Block block, final Face face) {
        final Block facingBlock = level.getBlockAt(x, y, z, face);
        return !this.isFullBlock() || !facingBlock.material().isFullBlock() || facingBlock.material().isTranslucent();
    }
    
    public TextureUV getUV(final SchematicAccessor level, final int x, final int y, final int z, final TextureAtlas atlas, final Block block, final Face face) {
        final TextureSprite sprite = this.getSprite(level, x, y, z, atlas, block, face);
        return (sprite == null) ? null : sprite.uv();
    }
    
    protected TextureUV getUV(final SchematicAccessor level, final int x, final int y, final int z, final TextureAtlas atlas, final Block block, final Face face, final long time) {
        final TextureSprite sprite = this.getSprite(level, x, y, z, atlas, block, face);
        if (sprite instanceof final AnimatedTextureSprite animatedTextureSprite) {
            return animatedTextureSprite.uv((int)(time % animatedTextureSprite.getFrames()));
        }
        return (sprite == null) ? null : sprite.uv();
    }
    
    @Nullable
    public TextureSprite getSprite(final SchematicAccessor level, final int x, final int y, final int z, final TextureAtlas atlas, final Block block, final Face face) {
        final ResourceLocation spriteResource = block.material().getSpriteResource(level, x, y, z, block, face);
        return (spriteResource == null) ? null : atlas.findSprite(spriteResource);
    }
    
    protected TextureUV getUVForBoundingBox(final TextureAtlas atlas, final ResourceLocation resourceLocation, final BoundingBox boundingBox, final Face face) {
        final TextureSprite sprite = atlas.findSprite(resourceLocation);
        if (sprite == null) {
            return null;
        }
        final TextureUV uv = sprite.uv();
        if (uv == null) {
            return null;
        }
        final FloatVector3 bottom0 = boundingBox.getCorner(0);
        final FloatVector3 top0 = boundingBox.getCorner(4);
        final FloatVector3 top2 = boundingBox.getCorner(5);
        final FloatVector3 top3 = boundingBox.getCorner(6);
        float minU = uv.getMinU();
        float minV = uv.getMinV();
        float maxU = uv.getMaxU();
        float maxV = uv.getMaxV();
        final float ratioU = (maxU - minU) / 16.0f;
        final float ratioV = (maxV - minV) / 16.0f;
        final float unit = 16.0f;
        float x1 = 0.0f;
        float z1 = 0.0f;
        float x2 = 0.0f;
        float z2 = 0.0f;
        if (face == Face.TOP || face == Face.BOTTOM) {
            x1 = top0.getX();
            z1 = top0.getZ();
            x2 = top3.getX();
            z2 = top2.getZ();
        }
        if (face == Face.NORTH || face == Face.SOUTH) {
            x1 = top0.getX();
            z1 = bottom0.getY();
            x2 = top3.getX();
            z2 = top0.getY();
        }
        if (face == Face.EAST || face == Face.WEST) {
            x1 = top0.getZ();
            z1 = bottom0.getY();
            x2 = top2.getZ();
            z2 = top0.getY();
        }
        final float minX = Math.min(x1, x2) * unit;
        final float minY = Math.min(z1, z2) * unit;
        final float maxX = Math.max(x1, x2) * unit;
        final float maxY = Math.max(z1, z2) * unit;
        final float width = maxX - minX;
        final float height = maxY - minY;
        minU += ratioU * minX;
        minV += ratioV * minY;
        maxU = minU + ratioU * width;
        maxV = minV + ratioV * height;
        return new DefaultTextureUV(minU, minV, maxU, maxV);
    }
    
    protected TextureUV getUVCut(final TextureAtlas atlas, final ResourceLocation resourceLocation, final int x, final int y, final int width, final int height) {
        final TextureSprite sprite = atlas.findSprite(resourceLocation);
        if (sprite == null) {
            return null;
        }
        final TextureUV uv = sprite.uv();
        if (uv == null) {
            return null;
        }
        float minU = uv.getMinU();
        float minV = uv.getMinV();
        float maxU = uv.getMaxU();
        float maxV = uv.getMaxV();
        final float ratioU = (maxU - minU) / 16.0f;
        final float ratioV = (maxV - minV) / 16.0f;
        minU += ratioU * x;
        minV += ratioV * y;
        maxU = minU + ratioU * width;
        maxV = minV + ratioV * height;
        return new DefaultTextureUV(minU, minV, maxU, maxV);
    }
    
    public int getTextureRotation() {
        return 0;
    }
    
    public LightSource createLightSource(final int x, final int y, final int z, final Block block) {
        return null;
    }
    
    public void render(final BlockRenderer renderer, final TextureAtlas atlas, final BufferBuilder builder, final Block block, final SchematicAccessor level, final int x, final int y, final int z, final BoundingBox boundingBox) {
        for (final Face face : Face.VALUES) {
            if (this.shouldRenderFace(level, x, y, z, block, face)) {
                renderer.renderFace(atlas, builder, block, face, x, y, z, boundingBox);
            }
        }
    }
    
    public float getTransparency() {
        return 0.0f;
    }
    
    public boolean isTranslucent() {
        return this.getTransparency() > 0.0f || !this.isFullBlock();
    }
    
    public boolean isFullBlock() {
        return true;
    }
    
    public int getLight(final Block block) {
        return 0;
    }
    
    public int getLightColor(final Block block) {
        return 0;
    }
    
    public Boolean isLightSource(final Block block) {
        return this.getLight(block) > 0;
    }
    
    public void randomDisplayTick(final SchematicAccessor level, final ParticleRenderer particleRenderer, final Block block, final int x, final int y, final int z) {
    }
    
    public abstract RenderLayer getRenderLayer();
    
    public int getVertexType() {
        return 0;
    }
    
    public String getId() {
        return this.id;
    }
    
    @Override
    public String toString() {
        return this.getId();
    }
    
    static {
        RANDOM = new Random();
    }
}
