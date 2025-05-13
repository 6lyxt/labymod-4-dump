// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font.text.glyph;

import net.labymod.api.util.color.format.ColorFormat;

public class GlyphStyle
{
    private int packedColor;
    private boolean italic;
    private boolean bold;
    private float fontWeight;
    
    public GlyphStyle() {
        this(ColorFormat.ABGR32.pack(1.0f, 1.0f, 1.0f, 1.0f), false, false);
    }
    
    public GlyphStyle(final int packedColor, final boolean italic, final boolean bold) {
        this.packedColor = packedColor;
        this.italic = italic;
        this.bold = bold;
    }
    
    public int getPackedColor() {
        return this.packedColor;
    }
    
    public void setPackedColor(final int red, final int green, final int blue, final int alpha) {
        this.packedColor = ColorFormat.ABGR32.pack(red, green, blue, alpha);
    }
    
    public void setPackedColor(final float red, final float green, final float blue, final float alpha) {
        this.packedColor = ColorFormat.ABGR32.pack(red, green, blue, alpha);
    }
    
    public boolean isItalic() {
        return this.italic;
    }
    
    public void setItalic(final boolean italic) {
        this.italic = italic;
    }
    
    public boolean isBold() {
        return this.bold;
    }
    
    public void setBold(final boolean bold) {
        this.bold = bold;
    }
    
    public float getFontWeight() {
        return this.fontWeight;
    }
    
    public void setFontWeight(final float fontWeight) {
        this.fontWeight = fontWeight;
    }
}
