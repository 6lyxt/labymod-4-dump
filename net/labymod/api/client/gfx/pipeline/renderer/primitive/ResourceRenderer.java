// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.renderer.primitive;

import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.batch.BufferBatchable;
import net.labymod.api.client.gfx.pipeline.renderer.Renderer;

public class ResourceRenderer implements Renderer
{
    private static final float TEXTURE_PADDING = 0.025f;
    private static final float DEFAULT_SPRITE_SIZE = 256.0f;
    private final BufferBatchable batchable;
    
    public ResourceRenderer(final BufferBatchable batchable) {
        this.batchable = batchable;
    }
    
    public void draw(final float x, final float y, final float width, final float height) {
        this.draw(x, y, width, height, 0.0f, 0.0f, 256.0f, 256.0f, 256.0f, 256.0f);
    }
    
    public void draw(final float x, final float y, final float width, final float height, final float spriteOffsetX, final float spriteOffsetY, final float spriteWidth, final float spriteHeight, final float spriteMapWidth, final float spriteMapHeight) {
        this.draw(x, y, x + width, y + height, spriteOffsetX / spriteMapWidth, spriteOffsetY / spriteMapHeight, (spriteOffsetX + spriteWidth) / spriteMapWidth, (spriteOffsetY + spriteHeight) / spriteMapHeight, -1);
    }
    
    private void draw(float left, float top, float right, float bottom, final float minU, final float minV, final float maxU, final float maxV, final int color) {
        final BufferBuilder builder = this.batchable.bufferBuilder();
        final int light = Laby.references().renderEnvironmentContext().getPackedLightWithCondition();
        left += 0.025f;
        top += 0.025f;
        right += 0.025f;
        bottom += 0.025f;
        this.writeVertex(builder, left, bottom, minU, maxV, color, light);
        this.writeVertex(builder, right, bottom, maxU, maxV, color, light);
        this.writeVertex(builder, right, top, maxU, minV, color, light);
        this.writeVertex(builder, left, top, minU, minV, color, light);
    }
    
    private void writeVertex(final BufferBuilder bufferBuilder, final float x, final float y, final float u, final float v, final int color, final int light) {
        bufferBuilder.putFloat(x, y, this.batchable.getZOffset()).uv(u, v).color(color).endVertex();
    }
}
