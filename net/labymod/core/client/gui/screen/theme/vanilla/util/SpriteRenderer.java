// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.util;

import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.matrix.Stack;

public class SpriteRenderer
{
    public static void renderInBounds16(final Stack stack, final Bounds bounds, final ResourceLocation resourceLocation, final int spriteX, final int spriteY, final int resolutionWidth, final int resolutionHeight) {
        renderInBounds16(stack, bounds, resourceLocation, spriteX, spriteY, resolutionWidth, resolutionHeight, -1);
    }
    
    public static void renderInBounds16(final Stack stack, final Bounds bounds, final ResourceLocation resourceLocation, final int spriteX, final int spriteY, final int resolutionWidth, final int resolutionHeight, final int color) {
        final ResourceRenderer resourceRenderer = Laby.labyAPI().renderPipeline().resourceRenderer();
        final BatchResourceRenderer renderer = resourceRenderer.beginBatch(stack, resourceLocation);
        final float x = bounds.getX(BoundsType.MIDDLE);
        final float y = bounds.getY(BoundsType.MIDDLE);
        final float width = bounds.getWidth(BoundsType.MIDDLE);
        final float height = bounds.getHeight(BoundsType.MIDDLE);
        renderer.pos(x, y).size(4.0f, 4.0f).sprite((float)spriteX, (float)spriteY, 4.0f).resolution((float)resolutionWidth, (float)resolutionHeight).color(color).build();
        renderer.pos(x + 4.0f, y).size(width - 8.0f, 4.0f).sprite((float)(spriteX + 4), (float)spriteY, 8.0f, 4.0f).resolution((float)resolutionWidth, (float)resolutionHeight).color(color).build();
        renderer.pos(x + width - 4.0f, y).size(4.0f, 4.0f).sprite((float)(spriteX + 12), (float)spriteY, 4.0f).resolution((float)resolutionWidth, (float)resolutionHeight).color(color).build();
        final float middleHeight = y + height - 4.0f - (y + 4.0f);
        renderer.pos(x, y + 4.0f).size(4.0f, middleHeight).sprite((float)spriteX, (float)(spriteY + 4), 4.0f, 8.0f).resolution((float)resolutionWidth, (float)resolutionHeight).color(color).build();
        renderer.pos(x + 4.0f, y + 4.0f).size(width - 8.0f, middleHeight).sprite((float)(spriteX + 4), (float)(spriteY + 4), 4.0f, 8.0f).resolution((float)resolutionWidth, (float)resolutionHeight).color(color).build();
        renderer.pos(x + width - 4.0f, y + 4.0f).size(4.0f, middleHeight).sprite((float)(spriteX + 12), (float)(spriteY + 4), 4.0f, 8.0f).resolution((float)resolutionWidth, (float)resolutionHeight).color(color).build();
        renderer.pos(x, y + height - 4.0f).size(4.0f, 4.0f).sprite((float)spriteX, (float)(spriteY + 12), 4.0f).resolution((float)resolutionWidth, (float)resolutionHeight).color(color).build();
        renderer.pos(x + 4.0f, y + height - 4.0f).size(width - 8.0f, 4.0f).sprite((float)(spriteX + 4), (float)(spriteY + 12), 8.0f, 4.0f).resolution((float)resolutionWidth, (float)resolutionHeight).color(color).build();
        renderer.pos(x + width - 4.0f, y + height - 4.0f).size(4.0f, 4.0f).sprite((float)(spriteX + 12), (float)(spriteY + 12), 4.0f).resolution((float)resolutionWidth, (float)resolutionHeight).color(color).build();
        renderer.upload();
    }
}
