// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.draw.builder;

import net.labymod.core.client.render.draw.property.IntProperty;
import net.labymod.api.client.render.draw.builder.RectangleBuilder;

public class DefaultRectangleBuilder<T extends RectangleBuilder<T>> extends DefaultRendererBuilder<T> implements RectangleBuilder<T>
{
    protected float width;
    protected float height;
    protected float leftTopRadius;
    protected float rightTopRadius;
    protected float leftBottomRadius;
    protected float rightBottomRadius;
    protected float lowerEdgeSoftness;
    protected float upperEdgeSoftness;
    protected float borderThickness;
    protected float borderSoftness;
    protected int borderColor;
    protected final IntProperty leftColor;
    protected final IntProperty rightColor;
    protected final IntProperty topColor;
    protected final IntProperty bottomColor;
    
    protected DefaultRectangleBuilder() {
        this.leftColor = new IntProperty();
        this.rightColor = new IntProperty();
        this.topColor = new IntProperty();
        this.bottomColor = new IntProperty();
        this.resetBuilder();
    }
    
    @Override
    public T size(final float width, final float height) {
        this.width = width;
        this.height = height;
        return (T)this;
    }
    
    @Override
    public T rounded(final float leftTopRadius, final float rightTopRadius, final float leftBottomRadius, final float rightBottomRadius) {
        this.leftTopRadius = leftTopRadius;
        this.rightTopRadius = rightTopRadius;
        this.leftBottomRadius = leftBottomRadius;
        this.rightBottomRadius = rightBottomRadius;
        return (T)this;
    }
    
    @Override
    public T lowerEdgeSoftness(final float softness) {
        this.lowerEdgeSoftness = softness;
        return (T)this;
    }
    
    @Override
    public T upperEdgeSoftness(final float softness) {
        this.upperEdgeSoftness = softness;
        return (T)this;
    }
    
    @Override
    public T borderThickness(final float thickness) {
        this.borderThickness = thickness;
        return (T)this;
    }
    
    @Override
    public T borderSoftness(final float softness) {
        this.borderSoftness = softness;
        return (T)this;
    }
    
    @Override
    public T borderColor(final int color) {
        this.borderColor = color;
        return (T)this;
    }
    
    @Override
    public T gradientHorizontal(final int leftColor, final int rightColor) {
        this.leftColor.setValue(leftColor);
        this.rightColor.setValue(rightColor);
        return (T)this;
    }
    
    @Override
    public T gradientVertical(final int topColor, final int bottomColor) {
        this.topColor.setValue(topColor);
        this.bottomColor.setValue(bottomColor);
        return (T)this;
    }
    
    @Override
    public void validateBuilder() {
        super.validateBuilder();
        if (this.leftColor.isSet() && this.topColor.isSet()) {
            throw new IllegalStateException("Cannot use horizontalGradient() and verticalGradient() at the same time");
        }
    }
    
    @Override
    public void resetBuilder() {
        super.resetBuilder();
        this.width = 0.0f;
        this.height = 0.0f;
        this.leftTopRadius = 0.0f;
        this.rightTopRadius = 0.0f;
        this.leftBottomRadius = 0.0f;
        this.rightBottomRadius = 0.0f;
        this.lowerEdgeSoftness = 0.0f;
        this.upperEdgeSoftness = 0.0f;
        this.borderThickness = 0.0f;
        this.borderSoftness = 0.0f;
        this.borderColor = 0;
        if (this.leftColor != null) {
            this.leftColor.reset();
        }
        if (this.rightColor != null) {
            this.rightColor.reset();
        }
        if (this.topColor != null) {
            this.topColor.reset();
        }
        if (this.bottomColor != null) {
            this.bottomColor.reset();
        }
    }
}
