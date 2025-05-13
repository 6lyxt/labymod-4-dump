// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.state.data;

public class ClearColorData
{
    private float red;
    private float green;
    private float blue;
    private float alpha;
    
    public ClearColorData() {
        this(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public ClearColorData(final float red, final float green, final float blue, final float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    
    public float getRed() {
        return this.red;
    }
    
    public void setRed(final float red) {
        this.red = red;
    }
    
    public float getGreen() {
        return this.green;
    }
    
    public void setGreen(final float green) {
        this.green = green;
    }
    
    public float getBlue() {
        return this.blue;
    }
    
    public void setBlue(final float blue) {
        this.blue = blue;
    }
    
    public float getAlpha() {
        return this.alpha;
    }
    
    public void setAlpha(final float alpha) {
        this.alpha = alpha;
    }
    
    public ClearColorData copy() {
        return new ClearColorData(this.red, this.green, this.blue, this.alpha);
    }
}
