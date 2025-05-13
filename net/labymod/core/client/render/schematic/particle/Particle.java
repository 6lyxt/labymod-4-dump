// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.particle;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.AnimatedTextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.Random;
import net.labymod.core.client.render.schematic.SchematicAccessor;

public abstract class Particle
{
    protected final SchematicAccessor level;
    protected final Random random;
    protected double x;
    protected double y;
    protected double z;
    protected double prevX;
    protected double prevY;
    protected double prevZ;
    protected double randomX;
    protected double randomY;
    protected double randomZ;
    protected double motionX;
    protected double motionY;
    protected double motionZ;
    protected int maxTicksExisted;
    protected int ticksExisted;
    protected int color;
    protected float size;
    protected ResourceLocation resourceLocation;
    protected boolean isDead;
    
    public Particle(final SchematicAccessor level, final double x, final double y, final double z, final float motionX, final float motionY, final float motionZ) {
        this.level = level;
        this.x = x;
        this.y = y;
        this.z = z;
        this.random = new Random();
        this.randomX = this.random.nextFloat() * 3.0f;
        this.randomY = this.random.nextFloat() * 3.0f;
        this.randomZ = (this.random.nextFloat() * 0.5f + 0.5f) * 2.0f;
        this.motionX = motionX + (Math.random() * 2.0 - 1.0) * 0.4;
        this.motionY = motionY + (Math.random() * 2.0 - 1.0) * 0.4;
        this.motionZ = motionZ + (Math.random() * 2.0 - 1.0) * 0.4;
        final double strength = (Math.random() + Math.random() + 1.0) * 0.15000000596046448;
        final double length = Math.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
        this.motionX = this.motionX / length * strength * 0.4;
        this.motionY = this.motionY / length * strength * 0.4 + 0.1;
        this.motionZ = this.motionZ / length * strength * 0.4;
        this.size = (this.random.nextFloat() * 0.5f + 0.5f) * 2.0f;
        this.maxTicksExisted = MathHelper.floor(4.0 / (this.random.nextFloat() * 0.9 + 0.1));
        this.color = -1;
    }
    
    protected ResourceLocation createResource(final String id) {
        return ResourceLocation.create("labymod", "particle/" + id);
    }
    
    public void onUpdate() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        if (this.ticksExisted >= this.maxTicksExisted) {
            this.kill();
        }
        this.motionY -= 0.04;
        this.x += this.motionX;
        this.y += this.motionY;
        this.z += this.motionZ;
        this.motionX *= 0.98;
        this.motionY *= 0.98;
        this.motionZ *= 0.98;
    }
    
    public boolean isDead() {
        return this.isDead;
    }
    
    public void render(final BufferBuilder builder, final TextureAtlas atlas, final float partialTicks, final float offsetX, final float offsetY, final float offsetZ, final float offset2X, final float offset2Z) {
        if (this.resourceLocation == null) {
            return;
        }
        final TextureSprite sprite = atlas.findSprite(this.resourceLocation);
        if (sprite == null) {
            return;
        }
        TextureUV uv = sprite.uv();
        if (sprite instanceof final AnimatedTextureSprite animatedTextureSprite) {
            uv = animatedTextureSprite.uv(this.getFrame(animatedTextureSprite.getFrames()));
        }
        final float minU = uv.getMinU();
        final float minV = uv.getMinV();
        final float maxU = uv.getMaxU();
        final float maxV = uv.getMaxV();
        final float size = 0.1f * this.size;
        final float r = (this.color >> 16 & 0xFF) / 255.0f;
        final float g = (this.color >> 8 & 0xFF) / 255.0f;
        final float b = (this.color & 0xFF) / 255.0f;
        final float x = (float)(this.prevX + (this.x - this.prevX) * partialTicks);
        final float y = (float)(this.prevY + (this.y - this.prevY) * partialTicks);
        final float z = (float)(this.prevZ + (this.z - this.prevZ) * partialTicks);
        this.addVertexWithUV(builder, x - offsetX * size - offset2X * size, y - offsetY * size, z - offsetZ * size - offset2Z * size, minU, maxV, r, g, b);
        this.addVertexWithUV(builder, x - offsetX * size + offset2X * size, y + offsetY * size, z - offsetZ * size + offset2Z * size, minU, minV, r, g, b);
        this.addVertexWithUV(builder, x + offsetX * size + offset2X * size, y + offsetY * size, z + offsetZ * size + offset2Z * size, maxU, minV, r, g, b);
        this.addVertexWithUV(builder, x + offsetX * size - offset2X * size, y - offsetY * size, z + offsetZ * size - offset2Z * size, maxU, maxV, r, g, b);
        builder.endVertex();
    }
    
    protected int getFrame(final int frames) {
        return 0;
    }
    
    private void addVertexWithUV(final BufferBuilder builder, final float x, final float y, final float z, final float u, final float v, final float r, final float g, final float b) {
        builder.pos(x, y, z).uv(u, v).color(r, g, b, 1.0f).light(15728880).normal(0.0f, 0.0f, 0.0f);
    }
    
    public void kill() {
        this.isDead = true;
    }
}
