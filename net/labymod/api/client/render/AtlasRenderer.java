// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render;

import net.labymod.api.client.gfx.pipeline.texture.data.scale.NineSpliceScaling;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.TileScaling;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.SpriteScaling;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureSprite;
import net.labymod.api.client.gfx.pipeline.texture.data.scale.StretchScaling;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.util.color.format.ColorFormat;

@Deprecated(forRemoval = true, since = "4.0.6")
public final class AtlasRenderer
{
    private int color;
    
    public AtlasRenderer() {
        this.color = ColorFormat.ARGB32.pack(255, 255, 255, 255);
    }
    
    public void blitSprite(final ResourceRenderContext context, final TextureAtlas atlas, final ResourceLocation location, final int spriteWidth, final int spriteHeight, final int spriteX, final int spriteY, final int x, final int y, final int width, final int height, final int color) {
        final TextureSprite sprite = atlas.findSprite(location);
        final SpriteScaling scaling = sprite.scaling();
        if (scaling instanceof StretchScaling) {
            context.blitSprite(sprite.uv(), spriteWidth, spriteHeight, spriteX, spriteY, x, y, 0, width, height, color);
        }
    }
    
    public void blitSprite(final ResourceRenderContext context, final TextureAtlas atlas, final ResourceLocation location, final int x, final int y, final int width, final int height, final int color) {
        final TextureSprite sprite = atlas.findSprite(location);
        this.blitSprite(context, sprite, x, y, width, height, color);
    }
    
    public void blitSprite(final ResourceRenderContext context, final TextureSprite sprite, final int x, final int y, final int width, final int height, final int color) {
        this.blitSprite(context, sprite.uv(), sprite.scaling(), x, y, width, height, color);
    }
    
    public void blitSprite(final ResourceRenderContext context, final TextureUV uv, final SpriteScaling scaling, final int x, final int y, final int width, final int height, final int color) {
        this.blitSprite(context, uv, scaling, x, y, width, height, 0, color);
    }
    
    public void blitSprite(final ResourceRenderContext context, final TextureUV uv, final SpriteScaling scaling, final int x, final int y, final int width, final int height, final int zOffset, final int color) {
        this.color = color;
        if (scaling instanceof StretchScaling) {
            context.blitSprite(x, y, width, height, zOffset, uv.getMinU(), uv.getMinV(), uv.getMaxU(), uv.getMaxV(), color);
        }
        else if (scaling instanceof final TileScaling tileScaling) {
            final int tileWidth = tileScaling.width();
            final int tileHeight = tileScaling.height();
            this.blitTiledSprite(context, uv, x, y, zOffset, width, height, 0, 0, tileWidth, tileHeight, tileWidth, tileHeight);
        }
        else if (scaling instanceof final NineSpliceScaling nineSpliceScaling) {
            this.blitNineSlicedSprite(context, uv, nineSpliceScaling, x, y, width, height, zOffset);
        }
        this.color = ColorFormat.ARGB32.pack(255, 255, 255, 255);
    }
    
    private void blitNineSlicedSprite(final ResourceRenderContext context, final TextureUV texture, final NineSpliceScaling scaling, final int x, final int y, final int width, final int height, final int zOffset) {
        final NineSpliceScaling.Border border = scaling.border();
        final int left = Math.min(border.left(), width / 2);
        final int right = Math.min(border.right(), width / 2);
        final int top = Math.min(border.top(), height / 2);
        final int bottom = Math.min(border.bottom(), height / 2);
        if (width == scaling.width() && height == scaling.height()) {
            this.blitSprite(context, texture, scaling.width(), scaling.height(), 0, 0, x, y, zOffset, width, height);
        }
        else if (height == scaling.height()) {
            this.applyNonStretchedHorizontal(context, texture, scaling, x, y, zOffset, width, height, left, right);
        }
        else if (width == scaling.width()) {
            this.applyNonStretchedVertical(context, texture, scaling, x, y, zOffset, width, height, top, bottom);
        }
        else {
            this.applyStretched(context, texture, scaling, x, y, zOffset, width, height, left, right, top, bottom);
        }
    }
    
    private void applyNonStretchedHorizontal(final ResourceRenderContext context, final TextureUV texture, final NineSpliceScaling scaling, final int x, final int y, final int zOffset, final int width, final int height, final int left, final int right) {
        this.blitSprite(context, texture, scaling.width(), scaling.height(), 0, 0, x, y, zOffset, left, height);
        this.blitTiledSprite(context, texture, x + left, y, zOffset, width - right - left, height, left, 0, scaling.width() - right - left, scaling.height(), scaling.width(), scaling.height());
        this.blitSprite(context, texture, scaling.width(), scaling.height(), scaling.width() - right, 0, x + width - right, y, zOffset, right, height);
    }
    
    private void applyNonStretchedVertical(final ResourceRenderContext context, final TextureUV texture, final NineSpliceScaling scaling, final int x, final int y, final int zOffset, final int width, final int height, final int top, final int bottom) {
        this.blitSprite(context, texture, scaling.width(), scaling.height(), 0, 0, x, y, zOffset, width, top);
        this.blitTiledSprite(context, texture, x, y + top, zOffset, width, height - bottom - top, 0, top, scaling.width(), scaling.height() - bottom - top, scaling.width(), scaling.height());
        this.blitSprite(context, texture, scaling.width(), scaling.height(), 0, scaling.height() - bottom, x, y + height - bottom, zOffset, width, bottom);
    }
    
    private void applyStretched(final ResourceRenderContext context, final TextureUV texture, final NineSpliceScaling scaling, final int xPos, final int yPos, final int zOffset, final int width, final int height, final int left, final int right, final int top, final int bottom) {
        this.blitSprite(context, texture, scaling.width(), scaling.height(), 0, 0, xPos, yPos, zOffset, left, top);
        this.blitTiledSprite(context, texture, xPos + left, yPos, zOffset, width - right - left, top, left, 0, scaling.width() - right - left, top, scaling.width(), scaling.height());
        this.blitSprite(context, texture, scaling.width(), scaling.height(), scaling.width() - right, 0, xPos + width - right, yPos, zOffset, right, top);
        this.blitSprite(context, texture, scaling.width(), scaling.height(), 0, scaling.height() - bottom, xPos, yPos + height - bottom, zOffset, left, bottom);
        this.blitTiledSprite(context, texture, xPos + left, yPos + height - bottom, zOffset, width - right - left, bottom, left, scaling.height() - bottom, scaling.width() - right - left, bottom, scaling.width(), scaling.height());
        this.blitSprite(context, texture, scaling.width(), scaling.height(), scaling.width() - right, scaling.height() - bottom, xPos + width - right, yPos + height - bottom, zOffset, right, bottom);
        this.blitTiledSprite(context, texture, xPos, yPos + top, zOffset, left, height - bottom - top, 0, top, left, scaling.height() - bottom - top, scaling.width(), scaling.height());
        this.blitTiledSprite(context, texture, xPos + left, yPos + top, zOffset, width - right - left, height - bottom - top, left, top, scaling.width() - right - left, scaling.height() - bottom - top, scaling.width(), scaling.height());
        this.blitTiledSprite(context, texture, xPos + width - right, yPos + top, zOffset, left, height - bottom - top, scaling.width() - right, top, right, scaling.height() - bottom - top, scaling.width(), scaling.height());
    }
    
    private void blitTiledSprite(final ResourceRenderContext context, final TextureUV uv, final int x, final int y, final int zOffset, final int width, final int height, final int spriteX, final int spriteY, final int tileWidth, final int tileHeight, final int spriteWidth, final int spriteHeight) {
        if (width < 0 || height < 0) {
            return;
        }
        if (tileWidth < 0 || tileHeight < 0) {
            throw new IllegalStateException("Tiled sprite texture size must be positive, got " + tileWidth + "x" + tileHeight);
        }
        for (int currentWidth = 0; currentWidth < width; currentWidth += tileWidth) {
            final int toWidth = Math.min(tileWidth, width - currentWidth);
            for (int currentHeight = 0; currentHeight < height; currentHeight += tileHeight) {
                final int toHeight = Math.min(tileHeight, height - currentHeight);
                this.blitSprite(context, uv, spriteWidth, spriteHeight, spriteX, spriteY, x + currentWidth, y + currentHeight, zOffset, toWidth, toHeight);
            }
        }
    }
    
    private void blitSprite(final ResourceRenderContext context, final TextureUV uv, final int spriteWidth, final int spriteHeight, final int spriteX, final int spriteY, final int x, final int y, final int zOffset, final int width, final int height) {
        context.blitSprite(uv, spriteWidth, spriteHeight, spriteX, spriteY, x, y, zOffset, width, height, this.color);
    }
}
