// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.particle;

import java.util.Iterator;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateRenderer;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.core.client.render.schematic.block.material.material.Material;
import net.labymod.core.client.render.schematic.block.Block;
import net.labymod.core.util.camera.spline.position.Location;
import net.labymod.core.client.render.schematic.block.material.MaterialRegistry;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.Laby;
import java.util.ArrayList;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import java.util.Random;
import net.labymod.core.util.camera.CinematicCamera;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import java.util.List;

public class ParticleRenderer
{
    private final List<Particle> particles;
    private final SchematicAccessor level;
    private final CinematicCamera camera;
    private final Random random;
    private final BufferBuilder bufferBuilder;
    
    public ParticleRenderer(final SchematicAccessor level, final CinematicCamera camera) {
        this.particles = new ArrayList<Particle>();
        this.level = level;
        this.camera = camera;
        this.random = new Random();
        this.bufferBuilder = Laby.references().gfxRenderPipeline().createBufferBuilder(262144);
    }
    
    public void spawnParticle(final Particle particle) {
        this.particles.add(particle);
    }
    
    public void onTick() {
        final List<Particle> particleList = this.particles;
        for (int i = 0; i < particleList.size(); ++i) {
            final Particle particle = particleList.get(i);
            particle.onUpdate();
            if (particle.isDead()) {
                this.particles.remove(particle);
                --i;
            }
        }
        final Location position = this.camera.location();
        final int playerPosX = MathHelper.floor(position.getX());
        final int playerPosY = MathHelper.floor(position.getY());
        final int playerPosZ = MathHelper.floor(position.getZ());
        final float maxWidth = this.level.getWidth();
        final float maxHeight = this.level.getHeight();
        final float maxLength = this.level.getLength();
        final byte rangeXZ = 64;
        final byte rangeY = 5;
        for (int l = 0; l < 30000; ++l) {
            final int x = playerPosX + this.random.nextInt(rangeXZ) - this.random.nextInt(rangeXZ);
            final int y = playerPosY + this.random.nextInt(rangeY) - this.random.nextInt(rangeY);
            final int z = playerPosZ + this.random.nextInt(rangeXZ) - this.random.nextInt(rangeXZ);
            if (x >= 0 && x < maxWidth && y >= 0 && y < maxHeight && z >= 0) {
                if (z < maxLength) {
                    final Block block = this.level.getBlockAt(x, y, z);
                    final Material material = block.material();
                    if (material != MaterialRegistry.AIR) {
                        material.randomDisplayTick(this.level, this, block, x, y, z);
                    }
                }
            }
        }
    }
    
    public void render(final Stack stack, final TextureAtlas atlas, final float partialTicks) {
        final Location position = this.camera.location();
        final float yaw = MathHelper.toRadiansFloat((float)position.getYaw());
        final float pitch = MathHelper.toRadiansFloat((float)position.getPitch());
        final float offsetX = MathHelper.cos(yaw);
        final float offsetY = MathHelper.cos(pitch);
        final float offsetZ = MathHelper.sin(yaw);
        final float rotX = -offsetZ * MathHelper.sin(pitch);
        final float rotZ = offsetX * MathHelper.sin(pitch);
        this.bufferBuilder.begin(RenderPrograms.getSchematic(atlas.resource()), () -> "Schematic particles");
        for (final Particle particle : this.particles) {
            particle.render(this.bufferBuilder, atlas, partialTicks, offsetX, offsetY, offsetZ, rotX, rotZ);
        }
        ImmediateRenderer.drawWithProgram(this.bufferBuilder.end());
    }
    
    public void close() {
        if (this.bufferBuilder != null) {
            this.bufferBuilder.close();
        }
    }
    
    public void clear() {
        this.particles.clear();
    }
}
