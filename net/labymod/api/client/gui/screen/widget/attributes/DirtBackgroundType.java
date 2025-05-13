// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes;

public enum DirtBackgroundType
{
    LIST(32.0f), 
    MENU(64.0f);
    
    private final float brightness;
    
    private DirtBackgroundType(final float brightness) {
        this.brightness = brightness;
    }
    
    public float getBrightness() {
        return this.brightness;
    }
}
