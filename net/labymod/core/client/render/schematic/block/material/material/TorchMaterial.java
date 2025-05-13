// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.block.material.RenderLayer;
import net.labymod.core.client.render.schematic.particle.particle.FlameParticle;
import net.labymod.core.client.render.schematic.particle.Particle;
import net.labymod.core.client.render.schematic.particle.particle.SmokeParticle;
import net.labymod.core.client.render.schematic.particle.ParticleRenderer;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.core.client.render.schematic.block.Face;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.core.client.render.schematic.lightning.PointLightSource;
import net.labymod.core.client.render.schematic.lightning.LightSource;
import net.labymod.core.client.render.schematic.block.material.BoundingBox;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.block.Block;

public class TorchMaterial extends Material
{
    private final Type type;
    
    public TorchMaterial(final String id, final Type type) {
        super(id);
        this.type = type;
        this.resourceLocation = this.createResource(type.getBlockResourceName());
    }
    
    @Override
    public int getLight(final Block block) {
        return 15;
    }
    
    @Override
    public int getLightColor(final Block block) {
        return this.type.getColor();
    }
    
    @Override
    public boolean isFullBlock() {
        return false;
    }
    
    @Override
    public BoundingBox getBoundingBox(final SchematicAccessor level, final int x, final int y, final int z, final Block block) {
        final float size = 0.0625f;
        final float height = 0.625f;
        final float center = 0.5f;
        this.boundingBox.setCorner(4, center - size, height, center - size);
        this.boundingBox.setCorner(5, center - size, height, center + size);
        this.boundingBox.setCorner(6, center + size, height, center - size);
        this.boundingBox.setCorner(7, center + size, height, center + size);
        this.boundingBox.setCorner(0, center - size, 0.0f, center - size);
        this.boundingBox.setCorner(1, center - size, 0.0f, center + size);
        this.boundingBox.setCorner(2, center + size, 0.0f, center - size);
        this.boundingBox.setCorner(3, center + size, 0.0f, center + size);
        final String facing = block.getParameter("facing", "none");
        final boolean south = facing.equals("south");
        if (facing.equals("north") || south) {
            this.boundingBox.rotateX(0.5f, 1.0f, 0.5f + 0.1f * (south ? -1 : 1), (float)(30 * (south ? 1 : -1)));
        }
        else {
            final boolean west = facing.equals("west");
            this.boundingBox.rotateZ(0.5f + 0.1f * (west ? 1 : -1), 1.0f, 0.5f, (float)(30 * (west ? 1 : -1)));
        }
        return super.getBoundingBox(level, x, y, z, block);
    }
    
    @Override
    public LightSource createLightSource(final int x, final int y, final int z, final Block block) {
        final float centerX = x + 0.5f;
        final float centerY = y + 0.75f;
        final float centerZ = z + 0.5f;
        final int color = this.getLightColor(block);
        final PointLightSource lightSource = new PointLightSource();
        lightSource.getColor().set((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f);
        lightSource.getPosition().set(centerX, centerY, centerZ);
        lightSource.setConstant(0.12f);
        lightSource.setQuadratic(0.256f);
        lightSource.setLinear(0.2f);
        return lightSource;
    }
    
    @Override
    public TextureUV getUV(final SchematicAccessor level, final int x, final int y, final int z, final TextureAtlas atlas, final Block block, final Face face) {
        return this.getUVCut(atlas, this.resourceLocation, 7, (face == Face.BOTTOM) ? 14 : 6, 2, face.isYAxis() ? 2 : 10);
    }
    
    @Override
    public void randomDisplayTick(final SchematicAccessor level, final ParticleRenderer particleRenderer, final Block block, final int x, final int y, final int z) {
        final double centerX = x + 0.5f;
        final double centerY = y + 0.7f;
        final double centerZ = z + 0.5f;
        final double height = 0.0;
        final double width = 0.2;
        final String s;
        final String facing = s = block.getParameter("facing", "none");
        switch (s) {
            case "east": {
                particleRenderer.spawnParticle(new SmokeParticle(level, centerX - width, centerY + height, centerZ));
                particleRenderer.spawnParticle(new FlameParticle(level, centerX - width, centerY + height, centerZ, 0.0f, 0.0f, 0.0f, this.type));
                break;
            }
            case "west": {
                particleRenderer.spawnParticle(new SmokeParticle(level, centerX + width, centerY + height, centerZ));
                particleRenderer.spawnParticle(new FlameParticle(level, centerX + width, centerY + height, centerZ, 0.0f, 0.0f, 0.0f, this.type));
                break;
            }
            case "south": {
                particleRenderer.spawnParticle(new SmokeParticle(level, centerX, centerY + height, centerZ - width));
                particleRenderer.spawnParticle(new FlameParticle(level, centerX, centerY + height, centerZ - width, 0.0f, 0.0f, 0.0f, this.type));
                break;
            }
            case "north": {
                particleRenderer.spawnParticle(new SmokeParticle(level, centerX, centerY + height, centerZ + width));
                particleRenderer.spawnParticle(new FlameParticle(level, centerX, centerY + height, centerZ + width, 0.0f, 0.0f, 0.0f, this.type));
                break;
            }
            case "none": {
                particleRenderer.spawnParticle(new SmokeParticle(level, centerX, centerY + height, centerZ));
                particleRenderer.spawnParticle(new FlameParticle(level, centerX, centerY + height, centerZ, 0.0f, 0.0f, 0.0f, this.type));
                break;
            }
        }
    }
    
    @Override
    public RenderLayer getRenderLayer() {
        return RenderLayer.CUBE;
    }
    
    public enum Type
    {
        DEFAULT("torch", "flame", 13395456), 
        SOUL("soul_torch", "soul_fire_flame", 677541);
        
        private final String blockResourceName;
        private final String particleResourceName;
        private final int color;
        
        private Type(final String blockResourceName, final String particleResourceName, final int color) {
            this.blockResourceName = blockResourceName;
            this.particleResourceName = particleResourceName;
            this.color = color;
        }
        
        public String getBlockResourceName() {
            return this.blockResourceName;
        }
        
        public String getParticleResourceName() {
            return this.particleResourceName;
        }
        
        public int getColor() {
            return this.color;
        }
    }
}
