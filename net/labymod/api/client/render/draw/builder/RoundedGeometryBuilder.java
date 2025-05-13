// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw.builder;

import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface RoundedGeometryBuilder
{
    RoundedGeometryBuilder left(final float p0);
    
    RoundedGeometryBuilder top(final float p0);
    
    RoundedGeometryBuilder right(final float p0);
    
    RoundedGeometryBuilder bottom(final float p0);
    
    RoundedGeometryBuilder pos(final Rectangle p0);
    
    RoundedGeometryBuilder pos(final float p0, final float p1, final float p2, final float p3);
    
    RoundedGeometryBuilder leftTopRadius(final float p0);
    
    RoundedGeometryBuilder rightTopRadius(final float p0);
    
    RoundedGeometryBuilder leftBottomRadius(final float p0);
    
    RoundedGeometryBuilder rightBottomRadius(final float p0);
    
    default RoundedGeometryBuilder radius(final float radius) {
        return this.radius(radius, radius, radius, radius);
    }
    
    RoundedGeometryBuilder radius(final float p0, final float p1, final float p2, final float p3);
    
    RoundedGeometryBuilder lowerEdgeSoftness(final float p0);
    
    RoundedGeometryBuilder upperEdgeSoftness(final float p0);
    
    RoundedGeometryBuilder borderThickness(final float p0);
    
    RoundedGeometryBuilder borderSoftness(final float p0);
    
    RoundedGeometryBuilder borderColor(final int p0);
    
    RoundedGeometryBuilder borderRadius(final BorderRadius p0);
    
    RoundedData build();
    
    public static class RoundedData
    {
        private final float left;
        private final float top;
        private final float right;
        private final float bottom;
        private final float leftTopRadius;
        private final float rightTopRadius;
        private final float leftBottomRadius;
        private final float rightBottomRadius;
        private final float lowerEdgeSoftness;
        private final float upperEdgeSoftness;
        private final float borderThickness;
        private final float borderSoftness;
        private final int borderColor;
        
        public RoundedData(final float left, final float top, final float right, final float bottom, final float leftTopRadius, final float rightTopRadius, final float leftBottomRadius, final float rightBottomRadius, final float lowerEdgeSoftness, final float upperEdgeSoftness, final float borderThickness, final float borderSoftness, final int borderColor) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.leftTopRadius = leftTopRadius;
            this.rightTopRadius = rightTopRadius;
            this.leftBottomRadius = leftBottomRadius;
            this.rightBottomRadius = rightBottomRadius;
            this.lowerEdgeSoftness = lowerEdgeSoftness;
            this.upperEdgeSoftness = upperEdgeSoftness;
            this.borderThickness = borderThickness;
            this.borderSoftness = borderSoftness;
            this.borderColor = borderColor;
        }
        
        public float left() {
            return this.left;
        }
        
        public float top() {
            return this.top;
        }
        
        public float right() {
            return this.right;
        }
        
        public float bottom() {
            return this.bottom;
        }
        
        public float leftTopRadius() {
            return this.leftTopRadius;
        }
        
        public float rightTopRadius() {
            return this.rightTopRadius;
        }
        
        public float leftBottomRadius() {
            return this.leftBottomRadius;
        }
        
        public float rightBottomRadius() {
            return this.rightBottomRadius;
        }
        
        public float lowerEdgeSoftness() {
            return this.lowerEdgeSoftness;
        }
        
        public float upperEdgeSoftness() {
            return this.upperEdgeSoftness;
        }
        
        public float borderThickness() {
            return this.borderThickness;
        }
        
        public float borderSoftness() {
            return this.borderSoftness;
        }
        
        public int borderColor() {
            return this.borderColor;
        }
    }
}
