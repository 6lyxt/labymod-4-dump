// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.batch;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.render.GraphicsColor;
import net.labymod.api.client.render.shader.program.ShaderInstance;
import net.labymod.api.client.render.vertex.BufferBuilder;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import java.util.function.Function;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.AtlasRenderer;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface ResourceRenderContext extends RenderContext<ResourceRenderContext>
{
    public static final AtlasRenderer ATLAS_RENDERER = new AtlasRenderer();
    
    default ResourceRenderContext begin(final Stack stack, final ResourceLocation location) {
        return this.begin(stack, location, type -> this.getBuilder());
    }
    
    ResourceRenderContext begin(final Stack p0, final ResourceLocation p1, final Function<RenderPhase, BufferBuilder> p2);
    
    default ResourceRenderContext begin(final Stack stack, final ResourceLocation location, final ShaderInstance shaderInstance) {
        return this.begin(stack, location, shaderInstance, type -> this.getBuilder());
    }
    
    ResourceRenderContext begin(final Stack p0, final ResourceLocation p1, final ShaderInstance p2, final Function<RenderPhase, BufferBuilder> p3);
    
    default ResourceRenderContext blit(final float x, final float y, final float minU, final float minV, final float maxU, final float maxV) {
        return this.blit(x, y, maxU, maxV, minU, minV, maxU, maxV, 256.0f, 256.0f, -1);
    }
    
    default ResourceRenderContext blit(final float x, final float y, final float minU, final float minV, final float maxU, final float maxV, final int color) {
        return this.blit(x, y, maxU, maxV, minU, minV, maxU, maxV, 256.0f, 256.0f, color);
    }
    
    default void blitNineSliced(final float x, final float y, final float width, final float height, final int offset, final int spriteWidth, final int spriteHeight, final int texOffsetX, final int texOffsetY, final int color) {
        this.blitNineSliced(x, y, width, height, (float)offset, (float)offset, (float)offset, (float)offset, (float)spriteWidth, (float)spriteHeight, (float)texOffsetX, (float)texOffsetY, color);
    }
    
    default void blitNineSliced(final float x, final float y, final float width, final float height, final float left, final float top, final float right, final float bottom, final float spriteWidth, final float spriteHeight, final float texOffsetX, final float textOffsetY, final int color) {
        if (width == spriteWidth && height == spriteHeight) {
            this.blit(x, y, texOffsetX, textOffsetY, width, height, color);
        }
        else if (height == spriteHeight) {
            this.blit(x, y, texOffsetX, textOffsetY, left, height, color);
            this.blitRepeating(x + left, y, width - right - left, height, texOffsetX + left, textOffsetY, spriteWidth - right - left, spriteHeight, color);
            this.blit(x + width - right, y, texOffsetX + spriteWidth - right, textOffsetY, right, height, color);
        }
        else if (width == spriteWidth) {
            this.blit(x, y, texOffsetX, textOffsetY, width, top, color);
            this.blitRepeating(x, y + top, width, height - bottom - top, texOffsetX, textOffsetY + top, spriteWidth, spriteHeight - bottom - top, color);
            this.blit(x, y + height - bottom, texOffsetX, textOffsetY + spriteHeight - bottom, width, bottom, color);
        }
        else {
            this.blit(x, y, texOffsetX, textOffsetY, left, top, color);
            this.blitRepeating(x + left, y, width - right - left, top, texOffsetX + left, textOffsetY, spriteWidth - right - left, spriteHeight, color);
            this.blit(x + width - right, y, texOffsetX + spriteWidth - right, textOffsetY, right, top, color);
            this.blit(x, y + height - bottom, texOffsetX, textOffsetY + spriteHeight - bottom, left, bottom, color);
            this.blitRepeating(x + left, y + height - bottom, width - right - left, bottom, texOffsetX + left, textOffsetY + spriteHeight - bottom, spriteWidth - right - left, spriteHeight, color);
            this.blit(x + width - right, y + height - bottom, texOffsetX + spriteWidth - right, textOffsetY + spriteHeight - bottom, right, bottom, color);
            this.blitRepeating(x, y + top, left, height - bottom - top, texOffsetX, textOffsetY + top, spriteWidth, spriteHeight - bottom - top, color);
            this.blitRepeating(x + left, y + top, width - right - left, height - bottom - top, texOffsetX + left, textOffsetY + top, spriteWidth - right - left, spriteHeight - bottom - top, color);
            this.blitRepeating(x + width - right, y + top, left, height - bottom - top, texOffsetX + spriteWidth - right, textOffsetY + top, spriteWidth, spriteHeight - bottom - top, color);
        }
    }
    
    default void blitRepeating(final float startX, final float startY, final float endX, final float endY, final float minU, final float minV, final float maxU, final float maxV, final int color) {
        for (int i = 0; i < endX; i += (int)maxU) {
            final float x = startX + i;
            final float xMaxU = Math.min(maxU, endX - i);
            for (int j = 0; j < endY; j += (int)maxV) {
                final float y = startY + j;
                final float yMaxV = Math.min(maxV, endY - j);
                this.blit(x, y, minU, minV, xMaxU, yMaxV, color);
            }
        }
    }
    
    default ResourceRenderContext blit(final float x, final float y, final float width, final float height, final float minU, final float minV, final float maxU, final float maxV, final float resolutionWidth, final float resolutionHeight, final int color) {
        final GraphicsColor graphicsColor = GraphicsColor.DEFAULT_COLOR.update(color);
        return this.blit(x, y, width, height, minU, minV, maxU, maxV, resolutionWidth, resolutionHeight, graphicsColor.red(), graphicsColor.green(), graphicsColor.blue(), graphicsColor.alpha());
    }
    
    ResourceRenderContext blit(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8, final float p9, final float p10, final float p11, final float p12, final float p13);
    
    default ResourceRenderContext directBlit(final float x, final float y, final float width, final float height, final float offset, final int color) {
        final GraphicsColor graphicsColor = GraphicsColor.DEFAULT_COLOR.update(color);
        return this.directBlit(x, y, width, height, offset, graphicsColor.red(), graphicsColor.green(), graphicsColor.blue(), graphicsColor.alpha());
    }
    
    ResourceRenderContext blitSprite(final TextureUV p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6, final int p7, final int p8, final int p9, final int p10);
    
    ResourceRenderContext directBlit(final float p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7, final float p8);
    
    ResourceRenderContext blur(final boolean p0);
    
    ResourceRenderContext blitSprite(final int p0, final int p1, final int p2, final int p3, final int p4, final float p5, final float p6, final float p7, final float p8, final int p9);
}
