// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.particle.particle;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.core.client.render.schematic.block.material.material.TorchMaterial;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.particle.Particle;

public class FlameParticle extends Particle
{
    private final float initialSize;
    
    public FlameParticle(final SchematicAccessor level, double x, double y, double z, final float motionX, final float motionY, final float motionZ, final TorchMaterial.Type type) {
        super(level, x, y, z, motionX, motionY, motionZ);
        this.motionX = this.motionX * 0.0099 + motionX;
        this.motionY = this.motionY * 0.0099 + motionY;
        this.motionZ = this.motionZ * 0.0099 + motionZ;
        x += (this.random.nextFloat() - this.random.nextFloat()) * 0.05f;
        y += (this.random.nextFloat() - this.random.nextFloat()) * 0.05f;
        z += (this.random.nextFloat() - this.random.nextFloat()) * 0.05f;
        this.color = 16777215;
        this.maxTicksExisted = (int)(8.0 / (Math.random() * 0.8 + 0.2)) + 4;
        this.initialSize = this.size;
        this.resourceLocation = this.createResource(type.getParticleResourceName());
    }
    
    @Override
    public void onUpdate() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        if (this.ticksExisted++ >= this.maxTicksExisted) {
            this.kill();
        }
        this.x += this.motionX;
        this.y += this.motionY;
        this.z += this.motionZ;
        this.motionX *= 0.95;
        this.motionY *= 0.95;
        this.motionZ *= 0.95;
    }
    
    @Override
    public void render(final BufferBuilder builder, final TextureAtlas atlas, final float partialTicks, final float offsetX, final float offsetY, final float offsetZ, final float offset2X, final float offset2Z) {
        final float progress = (this.ticksExisted + partialTicks) / this.maxTicksExisted;
        this.size = this.initialSize * (1.0f - progress * progress * 0.5f);
        super.render(builder, atlas, partialTicks, offsetX, offsetY, offsetZ, offset2X, offset2Z);
    }
}
