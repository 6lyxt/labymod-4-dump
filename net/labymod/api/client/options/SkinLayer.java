// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.options;

public enum SkinLayer
{
    CAPE(1, "cape"), 
    JACKET_BASE(2, "jacket_base"), 
    LEFT_SLEEVE(4, "left_sleeve"), 
    RIGHT_SLEEVE(8, "right_sleeve"), 
    LEFT_PANTS_LEG(16, "left_pants_leg"), 
    RIGHT_PANTS_LEG(32, "right_pants_leg"), 
    HAT(64, "hat"), 
    JACKET(14, "jacket"), 
    PANTS(48, "pants");
    
    private static final SkinLayer[] LAYERS;
    private final int bitMask;
    private final String resourceId;
    private final String localeId;
    
    private SkinLayer(final int bitMask, final String resourceId) {
        this.bitMask = bitMask;
        this.resourceId = resourceId;
        final StringBuilder id = new StringBuilder();
        boolean upperCase = false;
        for (final char c : this.resourceId.toCharArray()) {
            if (c == '_') {
                upperCase = true;
            }
            else {
                id.append(upperCase ? Character.toUpperCase(c) : Character.toLowerCase(c));
                upperCase = false;
            }
        }
        this.localeId = id.toString();
    }
    
    public int getBitMask() {
        return this.bitMask;
    }
    
    public String getResourceId() {
        return this.resourceId;
    }
    
    public String getLocaleId() {
        return this.localeId;
    }
    
    public boolean isEnabled(final int value) {
        return (value & this.bitMask) == this.bitMask;
    }
    
    public static SkinLayer[] layers() {
        return SkinLayer.LAYERS;
    }
    
    public boolean isPartlyEnabled(final int value) {
        for (final SkinLayer skinLayer : layers()) {
            if ((this.bitMask & skinLayer.bitMask) == skinLayer.bitMask && skinLayer.isEnabled(value)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        LAYERS = values();
    }
}
