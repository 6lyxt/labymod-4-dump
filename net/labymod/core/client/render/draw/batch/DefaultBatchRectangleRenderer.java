// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.batch;

import net.labymod.api.client.render.draw.builder.RectangleBuilder;
import net.labymod.api.client.render.vertex.phase.RenderPhase;
import net.labymod.api.client.render.matrix.Stack;
import javax.inject.Inject;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.core.client.render.draw.builder.DefaultRectangleBuilder;

@Singleton
@Implements(BatchRectangleRenderer.class)
public class DefaultBatchRectangleRenderer extends DefaultRectangleBuilder<BatchRectangleRenderer> implements BatchRectangleRenderer
{
    private final RectangleRenderContext rectangleRenderContext;
    
    @Inject
    public DefaultBatchRectangleRenderer(final RectangleRenderContext rectangleRenderContext) {
        this.rectangleRenderContext = rectangleRenderContext;
    }
    
    @Override
    public BatchRectangleRenderer beginBatch(final Stack stack) {
        this.rectangleRenderContext.begin(stack);
        return this;
    }
    
    @Override
    public BatchRectangleRenderer beginBatch(final Stack stack, final RenderPhase phase) {
        this.rectangleRenderContext.begin(stack, phase);
        return this;
    }
    
    @Override
    public BatchRectangleRenderer outline(final int outerColor, final int innerColor, final float thickness) {
        this.rectangleRenderContext.renderOutline(this.x, this.y, this.x + this.width, this.y + this.height, thickness, outerColor, innerColor);
        return this;
    }
    
    @Override
    public void upload() {
        this.rectangleRenderContext.uploadToBuffer();
        this.rectangleRenderContext.zOffset(0.0f);
    }
    
    @Override
    public BatchRectangleRenderer rounded(final float leftTopRadius, final float rightTopRadius, final float leftBottomRadius, final float rightBottomRadius) {
        throw new UnsupportedOperationException("Cannot render rounded rectangles with the MultiRectangleRenderer");
    }
    
    @Override
    public BatchRectangleRenderer lowerEdgeSoftness(final float softness) {
        throw new UnsupportedOperationException("Cannot render rounded rectangles with the MultiRectangleRenderer");
    }
    
    @Override
    public BatchRectangleRenderer upperEdgeSoftness(final float softness) {
        throw new UnsupportedOperationException("Cannot render rounded rectangles with the MultiRectangleRenderer");
    }
    
    @Override
    public BatchRectangleRenderer borderThickness(final float thickness) {
        throw new UnsupportedOperationException("Cannot render rounded rectangles with the MultiRectangleRenderer");
    }
    
    @Override
    public BatchRectangleRenderer borderSoftness(final float softness) {
        throw new UnsupportedOperationException("Cannot render rounded rectangles with the MultiRectangleRenderer");
    }
    
    @Override
    public BatchRectangleRenderer borderColor(final int color) {
        throw new UnsupportedOperationException("Cannot render rounded rectangles with the MultiRectangleRenderer");
    }
    
    @Override
    public BatchRectangleRenderer build() {
        this.validateBuilder();
        if (this.topColor.isSet()) {
            this.rectangleRenderContext.renderGradientVertically(this.x, this.y, this.x + this.width, this.y + this.height, this.topColor.getValue(), this.bottomColor.getValue());
        }
        else if (this.leftColor.isSet()) {
            this.rectangleRenderContext.renderGradientHorizontally(this.x, this.y, this.x + this.width, this.y + this.height, this.leftColor.getValue(), this.rightColor.getValue());
        }
        else {
            this.rectangleRenderContext.render(this.x, this.y, this.x + this.width, this.y + this.height, this.color);
        }
        this.resetBuilder();
        return this;
    }
    
    @Override
    public BatchRectangleRenderer build(final float left, final float top, final float right, final float bottom, final int color) {
        this.rectangleRenderContext.render(left, top, right, bottom, color);
        return this;
    }
    
    @Override
    public void zOffset(final float offset) {
        this.rectangleRenderContext.zOffset(offset);
    }
}
