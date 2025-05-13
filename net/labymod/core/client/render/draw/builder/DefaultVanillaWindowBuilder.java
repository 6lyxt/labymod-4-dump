// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.builder;

import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import java.util.Objects;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.Textures;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.draw.builder.VanillaWindowBuilder;

@Singleton
@Implements(VanillaWindowBuilder.class)
public final class DefaultVanillaWindowBuilder implements VanillaWindowBuilder
{
    private static final float TILE_SIZE = 16.0f;
    private static final float SEGMENT_HEIGHT = 8.0f;
    private final ResourceLocation textureLocation;
    private final ResourceRenderer resourceRenderer;
    private boolean topLarge;
    private boolean bottomLarge;
    private boolean rescalable;
    private Rectangle position;
    
    @Inject
    public DefaultVanillaWindowBuilder(final ResourceRenderer resourceRenderer) {
        this.resourceRenderer = resourceRenderer;
        this.textureLocation = Textures.POPUP_BACKGROUND;
    }
    
    @Override
    public VanillaWindowBuilder top(final boolean large) {
        this.topLarge = large;
        return this;
    }
    
    @Override
    public VanillaWindowBuilder bottom(final boolean large) {
        this.bottomLarge = large;
        return this;
    }
    
    @Override
    public VanillaWindowBuilder rescalable(final boolean rescalable) {
        this.rescalable = rescalable;
        return this;
    }
    
    @Override
    public VanillaWindowBuilder position(final Rectangle rectangle) {
        this.position = rectangle;
        return this;
    }
    
    @Override
    public VanillaWindowBuilder render(final Stack stack, final MutableMouse mouse) {
        Objects.requireNonNull(this.position, "position must be non null");
        final BatchResourceRenderer renderer = this.resourceRenderer.beginBatch(stack, this.textureLocation);
        final float x = this.position.getX();
        float y = this.position.getY();
        final float width = this.position.getWidth();
        final float height = this.position.getHeight();
        final int segments = (int)Math.floor((height - 32.0f) / 8.0f);
        WindowSection section = this.topLarge ? WindowSection.TOP_LARGE : WindowSection.TOP;
        section.renderLine(renderer, x, y, width);
        y += 16.0f;
        section = WindowSection.MIDDLE;
        for (int i = 0; i < segments; ++i) {
            section.renderLine(renderer, x, y, width);
            y += 8.0f;
        }
        section = (this.bottomLarge ? WindowSection.BOTTOM_LARGE : WindowSection.BOTTOM);
        section.renderLine(renderer, x, this.position.getY() + height - 16.0f, width);
        if (this.rescalable) {
            final Rectangle rescaleRect = Rectangle.relative(x + width - 10.0f, this.position.getY() + height - 10.0f, 8.0f, 8.0f);
            final boolean mouseOverRescale = mouse.isInside(rescaleRect);
            renderer.pos(rescaleRect.getX(), rescaleRect.getY()).size(rescaleRect.getWidth(), rescaleRect.getHeight()).sprite((float)(96 + (mouseOverRescale ? 8 : 0)), 0.0f, rescaleRect.getWidth(), rescaleRect.getHeight()).resolution(128.0f, 64.0f).build();
        }
        renderer.upload();
        this.reset();
        return this;
    }
    
    private void reset() {
        this.topLarge = false;
        this.bottomLarge = false;
        this.position = null;
    }
    
    enum WindowSection
    {
        TOP(0.0f, 0.0f, 16.0f, 0.0f, 32.0f, 0.0f), 
        TOP_LARGE(48.0f, 0.0f, 64.0f, 0.0f, 80.0f, 0.0f), 
        MIDDLE(0.0f, 16.0f, 16.0f, 16.0f, 32.0f, 16.0f), 
        BOTTOM(0.0f, 32.0f, 16.0f, 32.0f, 32.0f, 32.0f), 
        BOTTOM_LARGE(48.0f, 16.0f, 64.0f, 16.0f, 80.0f, 16.0f);
        
        private final float leftX;
        private final float leftY;
        private final float middleX;
        private final float middleY;
        private final float rightX;
        private final float rightY;
        
        private WindowSection(final float leftX, final float leftY, final float middleX, final float middleY, final float rightX, final float rightY) {
            this.leftX = leftX;
            this.leftY = leftY;
            this.middleX = middleX;
            this.middleY = middleY;
            this.rightX = rightX;
            this.rightY = rightY;
        }
        
        public void renderLine(final BatchResourceRenderer renderer, final float x, final float y, final float width) {
            renderer.pos(x, y).size(16.0f, 16.0f).sprite(this.leftX, this.leftY, 16.0f).resolution(128.0f, 64.0f).build();
            renderer.pos(x + 16.0f, y).size(width - 32.0f, 16.0f).sprite(this.middleX, this.middleY, 16.0f).resolution(128.0f, 64.0f).build();
            renderer.pos(x + width - 16.0f, y).size(16.0f, 16.0f).sprite(this.rightX, this.rightY, 16.0f).resolution(128.0f, 64.0f).build();
        }
    }
}
