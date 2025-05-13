// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block.material.material;

import net.labymod.core.client.render.schematic.particle.particle.FlameParticle;
import net.labymod.core.client.render.schematic.particle.Particle;
import net.labymod.core.client.render.schematic.particle.particle.SmokeParticle;
import net.labymod.core.client.render.schematic.particle.ParticleRenderer;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.lightning.PointLightSource;
import net.labymod.core.client.render.schematic.lightning.LightSource;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.api.client.resources.ResourceLocation;

public class FurnaceMaterial extends FacingMaterial
{
    private final ResourceLocation resourceFrontLit;
    
    public FurnaceMaterial() {
        super("furnace");
        this.resourceFrontLit = this.createResource("furnace_front_lit");
    }
    
    @Override
    protected ResourceLocation getFrontSpriteResource(final Block block) {
        if (block.getParameter("lit", false)) {
            return this.resourceFrontLit;
        }
        return super.getFrontSpriteResource(block);
    }
    
    @Override
    public LightSource createLightSource(final int x, final int y, final int z, final Block block) {
        final boolean lit = block.getParameter("lit", false);
        if (!lit) {
            return null;
        }
        final int color = this.getLightColor(block);
        final PointLightSource lightSource = new PointLightSource();
        lightSource.getColor().set((color >> 16 & 0xFF) / 255.0f, (color >> 8 & 0xFF) / 255.0f, (color & 0xFF) / 255.0f);
        lightSource.getPosition().set(x + 0.5f, y + 0.5f, z + 0.5f);
        lightSource.setConstant(0.12f);
        lightSource.setQuadratic(0.256f);
        lightSource.setLinear(0.2f);
        return lightSource;
    }
    
    @Override
    public void randomDisplayTick(final SchematicAccessor level, final ParticleRenderer particleRenderer, final Block block, final int x, final int y, final int z) {
        final boolean lit = block.getParameter("lit", false);
        if (!lit) {
            return;
        }
        final float posX = x + 0.5f;
        final float posY = y + 0.0f + FurnaceMaterial.RANDOM.nextFloat() * 6.0f / 16.0f;
        final float posZ = z + 0.5f;
        final float blockSize = 0.52f;
        final float edgeOffset = FurnaceMaterial.RANDOM.nextFloat() * 0.6f - 0.3f;
        final String s;
        final String facing = s = block.getParameter("facing", "none");
        switch (s) {
            case "east": {
                particleRenderer.spawnParticle(new SmokeParticle(level, posX + blockSize, posY, posZ + edgeOffset));
                particleRenderer.spawnParticle(new FlameParticle(level, posX + blockSize, posY, posZ + edgeOffset, 0.0f, 0.0f, 0.0f, TorchMaterial.Type.DEFAULT));
                break;
            }
            case "west": {
                particleRenderer.spawnParticle(new SmokeParticle(level, posX - blockSize, posY, posZ + edgeOffset));
                particleRenderer.spawnParticle(new FlameParticle(level, posX - blockSize, posY, posZ + edgeOffset, 0.0f, 0.0f, 0.0f, TorchMaterial.Type.DEFAULT));
                break;
            }
            case "south": {
                particleRenderer.spawnParticle(new SmokeParticle(level, posX + edgeOffset, posY, posZ + blockSize));
                particleRenderer.spawnParticle(new FlameParticle(level, posX + edgeOffset, posY, posZ + blockSize, 0.0f, 0.0f, 0.0f, TorchMaterial.Type.DEFAULT));
                break;
            }
            case "north": {
                particleRenderer.spawnParticle(new SmokeParticle(level, posX + edgeOffset, posY, posZ - blockSize));
                particleRenderer.spawnParticle(new FlameParticle(level, posX + edgeOffset, posY, posZ - blockSize, 0.0f, 0.0f, 0.0f, TorchMaterial.Type.DEFAULT));
                break;
            }
        }
    }
    
    @Override
    public int getLight(final Block block) {
        return block.getParameter("lit", false) ? 13 : 0;
    }
    
    @Override
    public int getLightColor(final Block block) {
        return 13395456;
    }
}
