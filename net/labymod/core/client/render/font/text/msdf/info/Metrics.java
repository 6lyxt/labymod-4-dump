// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text.msdf.info;

public class Metrics
{
    private float emSize;
    private float lineHeight;
    private float ascender;
    private float descender;
    private float underlineY;
    private float underlineThickness;
    
    public float emSize() {
        return this.emSize;
    }
    
    public void emSize(final float emSize) {
        this.emSize = emSize;
    }
    
    public float lineHeight() {
        return this.lineHeight;
    }
    
    public void lineHeight(final float lineHeight) {
        this.lineHeight = lineHeight;
    }
    
    public float ascender() {
        return this.ascender;
    }
    
    public void ascender(final float ascender) {
        this.ascender = ascender;
    }
    
    public float descender() {
        return this.descender;
    }
    
    public void descender(final float descender) {
        this.descender = descender;
    }
    
    public float underlineY() {
        return this.underlineY;
    }
    
    public void underlineY(final float underlineY) {
        this.underlineY = underlineY;
    }
    
    public float underlineThickness() {
        return this.underlineThickness;
    }
    
    public void underlineThickness(final float underlineThickness) {
        this.underlineThickness = underlineThickness;
    }
    
    public float baselineHeight() {
        return this.lineHeight + this.descender + this.underlineY / 2.0f;
    }
}
