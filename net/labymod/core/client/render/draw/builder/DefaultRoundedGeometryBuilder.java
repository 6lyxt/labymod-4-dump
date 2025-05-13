// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.builder;

import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.util.bounds.Rectangle;
import javax.inject.Inject;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder;

@Singleton
@Implements(RoundedGeometryBuilder.class)
public class DefaultRoundedGeometryBuilder implements RoundedGeometryBuilder
{
    private float left;
    private float top;
    private float right;
    private float bottom;
    private float leftTopRadius;
    private float rightTopRadius;
    private float leftBottomRadius;
    private float rightBottomRadius;
    private float lowerEdgeSoftness;
    private float upperEdgeSoftness;
    private float borderThickness;
    private float borderSoftness;
    private int borderColor;
    
    @Inject
    public DefaultRoundedGeometryBuilder() {
    }
    
    @Override
    public RoundedGeometryBuilder left(final float left) {
        this.left = left;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder top(final float top) {
        this.top = top;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder right(final float right) {
        this.right = right;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder bottom(final float bottom) {
        this.bottom = bottom;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder pos(final Rectangle rectangle) {
        return this.pos(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom());
    }
    
    @Override
    public RoundedGeometryBuilder pos(final float left, final float top, final float right, final float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder leftTopRadius(final float leftTopRadius) {
        this.leftTopRadius = leftTopRadius;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder rightTopRadius(final float rightTopRadius) {
        this.rightTopRadius = rightTopRadius;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder leftBottomRadius(final float leftBottomRadius) {
        this.leftBottomRadius = leftBottomRadius;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder rightBottomRadius(final float rightBottomRadius) {
        this.rightBottomRadius = rightBottomRadius;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder radius(final float leftTopRadius, final float rightTopRadius, final float leftBottomRadius, final float rightBottomRadius) {
        this.leftTopRadius = leftTopRadius;
        this.rightTopRadius = rightTopRadius;
        this.leftBottomRadius = leftBottomRadius;
        this.rightBottomRadius = rightBottomRadius;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder lowerEdgeSoftness(final float lowerEdgeSoftness) {
        this.lowerEdgeSoftness = lowerEdgeSoftness;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder upperEdgeSoftness(final float upperEdgeSoftness) {
        this.upperEdgeSoftness = upperEdgeSoftness;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder borderThickness(final float borderThickness) {
        this.borderThickness = borderThickness;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder borderSoftness(final float borderSoftness) {
        this.borderSoftness = borderSoftness;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder borderColor(final int borderColor) {
        this.borderColor = borderColor;
        return this;
    }
    
    @Override
    public RoundedGeometryBuilder borderRadius(final BorderRadius borderRadius) {
        if (borderRadius == null) {
            return this;
        }
        this.leftTopRadius = borderRadius.getLeftTop();
        this.rightTopRadius = borderRadius.getRightTop();
        this.leftBottomRadius = borderRadius.getLeftBottom();
        this.rightBottomRadius = borderRadius.getRightBottom();
        this.upperEdgeSoftness = borderRadius.getUpperEdgeSoftness();
        this.lowerEdgeSoftness = borderRadius.getLowerEdgeSoftness();
        return this;
    }
    
    @Override
    public RoundedData build() {
        final RoundedData data = new RoundedData(this.left, this.top, this.right, this.bottom, this.leftTopRadius, this.rightTopRadius, this.leftBottomRadius, this.rightBottomRadius, this.lowerEdgeSoftness, this.upperEdgeSoftness, this.borderThickness, this.borderSoftness, this.borderColor);
        this.invalidate();
        return data;
    }
    
    private void invalidate() {
        this.left = 0.0f;
        this.top = 0.0f;
        this.right = 0.0f;
        this.bottom = 0.0f;
        this.leftTopRadius = 0.0f;
        this.rightTopRadius = 0.0f;
        this.leftBottomRadius = 0.0f;
        this.rightBottomRadius = 0.0f;
        this.lowerEdgeSoftness = 0.0f;
        this.upperEdgeSoftness = 0.0f;
        this.borderThickness = 0.0f;
        this.borderSoftness = 0.0f;
        this.borderColor = -1;
    }
}
