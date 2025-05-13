// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.particle.particle;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.core.client.render.schematic.SchematicAccessor;
import net.labymod.core.client.render.schematic.particle.Particle;

public class SmokeParticle extends Particle
{
    private final float initialSize;
    
    public SmokeParticle(final SchematicAccessor level, final double x, final double y, final double z) {
        this(level, x, y, z, 1.0f);
    }
    
    public SmokeParticle(final SchematicAccessor level, final double x, final double y, final double z, final float scale) {
        super(level, x, y, z, 0.0f, 0.0f, 0.0f);
        this.motionX *= 0.1;
        this.motionY *= 0.1;
        this.motionZ *= 0.1;
        final float brightness = (float)(Math.random() * 0.3);
        this.color = ColorFormat.ARGB32.pack(brightness, brightness, brightness, 1.0f);
        this.size *= 0.75f;
        this.size *= scale;
        this.initialSize = this.size;
        this.maxTicksExisted = (int)(8.0 / (Math.random() * 0.8 + 0.2));
        this.maxTicksExisted *= (int)scale;
        this.onUpdate();
        this.resourceLocation = this.createResource("generic");
    }
    
    @Override
    public void onUpdate() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        if (this.ticksExisted++ >= this.maxTicksExisted) {
            this.kill();
        }
        this.motionY += 0.001;
        this.x += this.motionX;
        this.y += this.motionY;
        this.z += this.motionZ;
        if (this.y == this.prevY) {
            this.motionX *= 1.1;
            this.motionZ *= 1.1;
        }
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
    
    @Override
    protected int getFrame(final int frames) {
        final float progress = this.ticksExisted / (float)this.maxTicksExisted;
        return frames - (int)(progress * 7.0f) - 1;
    }
}
