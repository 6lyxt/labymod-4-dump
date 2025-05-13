// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes;

import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.attributes.animation.Interpolatable;

public class BorderRadius implements Interpolatable<BorderRadius>
{
    public static final BorderRadius EMPTY;
    public static float DEFAULT_EDGE_SOFTNESS;
    private float leftTop;
    private float rightTop;
    private float leftBottom;
    private float rightBottom;
    private float lowerEdgeSoftness;
    private float upperEdgeSoftness;
    private float borderSoftness;
    private float thickness;
    private int borderBackground;
    
    public BorderRadius() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public BorderRadius(final float leftTop, final float rightTop, final float leftBottom, final float rightBottom) {
        this.lowerEdgeSoftness = BorderRadius.DEFAULT_EDGE_SOFTNESS;
        this.upperEdgeSoftness = 0.0f;
        this.leftTop = leftTop;
        this.rightTop = rightTop;
        this.leftBottom = leftBottom;
        this.rightBottom = rightBottom;
    }
    
    private BorderRadius(final float leftTop, final float rightTop, final float leftBottom, final float rightBottom, final float lowerEdgeSoftness, final float upperEdgeSoftness, final float borderSoftness, final float thickness, final int borderBackground) {
        this.lowerEdgeSoftness = BorderRadius.DEFAULT_EDGE_SOFTNESS;
        this.upperEdgeSoftness = 0.0f;
        this.leftTop = leftTop;
        this.rightTop = rightTop;
        this.leftBottom = leftBottom;
        this.rightBottom = rightBottom;
        this.lowerEdgeSoftness = lowerEdgeSoftness;
        this.upperEdgeSoftness = upperEdgeSoftness;
        this.borderSoftness = borderSoftness;
        this.thickness = thickness;
        this.borderBackground = borderBackground;
    }
    
    public void setLeftTop(final float leftTop) {
        this.leftTop = leftTop;
    }
    
    public float getLeftTop() {
        return this.leftTop;
    }
    
    public void setRightTop(final float rightTop) {
        this.rightTop = rightTop;
    }
    
    public float getRightTop() {
        return this.rightTop;
    }
    
    public void setLeftBottom(final float leftBottom) {
        this.leftBottom = leftBottom;
    }
    
    public float getLeftBottom() {
        return this.leftBottom;
    }
    
    public void setRightBottom(final float rightBottom) {
        this.rightBottom = rightBottom;
    }
    
    public float getRightBottom() {
        return this.rightBottom;
    }
    
    public void setLowerEdgeSoftness(final float lowerEdgeSoftness) {
        this.lowerEdgeSoftness = lowerEdgeSoftness;
    }
    
    public float getLowerEdgeSoftness() {
        return this.lowerEdgeSoftness;
    }
    
    public void setUpperEdgeSoftness(final float upperEdgeSoftness) {
        this.upperEdgeSoftness = upperEdgeSoftness;
    }
    
    public float getUpperEdgeSoftness() {
        return this.upperEdgeSoftness;
    }
    
    public void setRadius(final float radius) {
        this.leftTop = radius;
        this.rightTop = radius;
        this.leftBottom = radius;
        this.rightBottom = radius;
    }
    
    public void setRadius(final float leftTop, final float rightTop, final float leftBottom, final float rightBottom) {
        this.leftTop = leftTop;
        this.rightTop = rightTop;
        this.leftBottom = leftBottom;
        this.rightBottom = rightBottom;
    }
    
    public float getThickness() {
        return this.thickness;
    }
    
    public void setThickness(final float thickness) {
        this.thickness = thickness;
    }
    
    public float getBorderSoftness() {
        return this.borderSoftness;
    }
    
    public void setBorderSoftness(final float borderSoftness) {
        this.borderSoftness = borderSoftness;
    }
    
    public int getBorderBackground() {
        return this.borderBackground;
    }
    
    public void setBorderBackground(final int borderBackground) {
        this.borderBackground = borderBackground;
    }
    
    public void set(final BorderRadius radius) {
        this.leftTop = radius.leftTop;
        this.rightTop = radius.rightTop;
        this.leftBottom = radius.leftBottom;
        this.rightBottom = radius.rightBottom;
        this.lowerEdgeSoftness = radius.lowerEdgeSoftness;
        this.upperEdgeSoftness = radius.upperEdgeSoftness;
        this.borderSoftness = radius.borderSoftness;
        this.thickness = radius.thickness;
        this.borderBackground = radius.borderBackground;
    }
    
    public boolean isSet() {
        return this.leftTop > 0.0f || this.rightTop > 0.0f || this.leftBottom > 0.0f || this.rightBottom > 0.0f;
    }
    
    @Override
    public BorderRadius interpolate(final CubicBezier interpolator, final BorderRadius from, final long fromMillis, final long toMillis, final long currentMillis) {
        return new BorderRadius(interpolator.interpolate(from.leftTop, this.leftTop, fromMillis, toMillis, currentMillis), interpolator.interpolate(from.rightTop, this.rightTop, fromMillis, toMillis, currentMillis), interpolator.interpolate(from.leftBottom, this.leftBottom, fromMillis, toMillis, currentMillis), interpolator.interpolate(from.rightBottom, this.rightBottom, fromMillis, toMillis, currentMillis), interpolator.interpolate(from.lowerEdgeSoftness, this.lowerEdgeSoftness, fromMillis, toMillis, currentMillis), interpolator.interpolate(from.upperEdgeSoftness, this.upperEdgeSoftness, fromMillis, toMillis, currentMillis), interpolator.interpolate(from.borderSoftness, this.borderSoftness, fromMillis, toMillis, currentMillis), interpolator.interpolate(from.thickness, this.thickness, fromMillis, toMillis, currentMillis), (int)interpolator.interpolate(from.borderBackground, (double)this.borderBackground, fromMillis, toMillis, currentMillis));
    }
    
    static {
        EMPTY = new BorderRadius();
        BorderRadius.DEFAULT_EDGE_SOFTNESS = -0.5f;
    }
}
