// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text.model;

public final class StringWidth
{
    private final String value;
    private final float width;
    private final float boldWidth;
    
    public StringWidth(final String value, final float width, final float boldWidth) {
        this.value = value;
        this.width = width;
        this.boldWidth = boldWidth;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public float getWidth() {
        return this.width;
    }
    
    public float getBoldWidth() {
        return this.boldWidth;
    }
    
    @Override
    public int hashCode() {
        return (this.value == null) ? 0 : this.value.hashCode();
    }
    
    @Override
    public String toString() {
        return (this.value == null) ? super.toString() : this.value;
    }
}
