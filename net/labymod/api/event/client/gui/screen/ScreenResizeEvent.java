// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen;

import net.labymod.api.event.Event;

public class ScreenResizeEvent implements Event
{
    private final int width;
    private final int height;
    private final int rawWidth;
    private final int rawHeight;
    
    public ScreenResizeEvent(final int width, final int height, final int rawWidth, final int rawHeight) {
        this.width = width;
        this.height = height;
        this.rawWidth = rawWidth;
        this.rawHeight = rawHeight;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public int getRawWidth() {
        return this.rawWidth;
    }
    
    public int getRawHeight() {
        return this.rawHeight;
    }
}
