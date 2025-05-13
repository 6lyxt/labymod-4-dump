// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.color.format;

public class ABGR32ColorFormat implements ColorFormat
{
    private static final int ALPHA_COMPONENT_OFFSET = 24;
    private static final int RED_COMPONENT_OFFSET = 0;
    private static final int GREEN_COMPONENT_OFFSET = 8;
    private static final int BLUE_COMPONENT_OFFSET = 16;
    
    @Override
    public int red(final int value) {
        return value >> 0 & 0xFF;
    }
    
    @Override
    public int green(final int value) {
        return value >> 8 & 0xFF;
    }
    
    @Override
    public int blue(final int value) {
        return value >> 16 & 0xFF;
    }
    
    @Override
    public int alpha(final int value) {
        return value >> 24 & 0xFF;
    }
    
    @Override
    public int pack(final int red, final int green, final int blue, final int alpha) {
        return (alpha & 0xFF) << 24 | (blue & 0xFF) << 16 | (green & 0xFF) << 8 | (red & 0xFF) << 0;
    }
    
    @Override
    public int withAlpha(final int value, final int alpha) {
        return (alpha & 0xFF) << 24 | value;
    }
}
