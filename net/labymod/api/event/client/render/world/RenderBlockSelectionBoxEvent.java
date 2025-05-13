// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.world;

import net.labymod.api.util.Color;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class RenderBlockSelectionBoxEvent extends DefaultCancellable implements Event
{
    private static final Color DEFAULT_LINE_COLOR;
    private Color lineColor;
    private Color overlayColor;
    private final int x;
    private final int y;
    private final int z;
    
    public RenderBlockSelectionBoxEvent(final Color lineColor, final Color overlayColor, final int x, final int y, final int z) {
        this.lineColor = lineColor;
        this.overlayColor = overlayColor;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public RenderBlockSelectionBoxEvent(final Color lineColor, final int x, final int y, final int z) {
        this(lineColor, null, x, y, z);
    }
    
    public RenderBlockSelectionBoxEvent(final int x, final int y, final int z) {
        this(RenderBlockSelectionBoxEvent.DEFAULT_LINE_COLOR, x, y, z);
    }
    
    public Color getLineColor() {
        return this.lineColor;
    }
    
    public Color getOverlayColor() {
        return this.overlayColor;
    }
    
    public void setLineColor(final Color lineColor) {
        this.lineColor = lineColor;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    @Deprecated(forRemoval = true, since = "4.2.51")
    public void setLineColor(final java.awt.Color color) {
        this.lineColor = Color.of(color);
    }
    
    public void setOverlayColor(final Color overlayColor) {
        this.overlayColor = overlayColor;
    }
    
    @Deprecated(forRemoval = true, since = "4.2.52")
    public void setOverlayColor(final java.awt.Color color) {
        this.overlayColor = Color.of(color);
    }
    
    static {
        DEFAULT_LINE_COLOR = Color.ofRGB(0, 0, 0, 102);
    }
}
