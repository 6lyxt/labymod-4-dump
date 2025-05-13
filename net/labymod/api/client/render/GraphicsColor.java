// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render;

public class GraphicsColor
{
    public static final GraphicsColor DEFAULT_COLOR;
    private int color;
    private int shadowColor;
    
    public GraphicsColor(final int color) {
        this.updateColor(color);
    }
    
    public GraphicsColor update(final int color) {
        this.updateColor(color);
        return this;
    }
    
    public float red() {
        return this.red(false);
    }
    
    public float shadowRed() {
        return this.red(true);
    }
    
    public float red(final boolean shadow) {
        return (this.color(shadow) >> 16 & 0xFF) / 255.0f;
    }
    
    public float green() {
        return this.green(false);
    }
    
    public float shadowGreen() {
        return this.green(true);
    }
    
    public float green(final boolean shadow) {
        return (this.color(shadow) >> 8 & 0xFF) / 255.0f;
    }
    
    public float blue() {
        return this.blue(false);
    }
    
    public float shadowBlue() {
        return this.blue(true);
    }
    
    public float blue(final boolean shadow) {
        return (this.color(shadow) & 0xFF) / 255.0f;
    }
    
    public float alpha() {
        return this.alpha(false);
    }
    
    public float shadowAlpha() {
        return this.alpha(true);
    }
    
    public float alpha(final boolean shadow) {
        return (this.color(shadow) >> 24 & 0xFF) / 255.0f;
    }
    
    public int color() {
        return this.color(false);
    }
    
    public int shadowColor() {
        return this.color(true);
    }
    
    public int color(final boolean shadow) {
        return shadow ? this.shadowColor : this.color;
    }
    
    private void updateColor(final int color) {
        this.color = color;
        this.shadowColor = ((color & 0xFCFCFC) >> 2 | (color & 0xFF000000));
    }
    
    static {
        DEFAULT_COLOR = new GraphicsColor(-1);
    }
}
