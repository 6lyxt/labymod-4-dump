// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag.tags;

public class NameTagBackground
{
    private boolean enabled;
    private int color;
    
    private NameTagBackground(final boolean enabled, final int color) {
        this.enabled = enabled;
        this.color = color;
    }
    
    private NameTagBackground() {
    }
    
    public static NameTagBackground custom(final boolean enabled, final int color) {
        return new NameTagBackground(enabled, color);
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public void setColor(final int color) {
        this.color = color;
    }
}
