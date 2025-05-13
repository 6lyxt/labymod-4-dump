// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx;

@Deprecated(forRemoval = true, since = "4.2.41")
public enum GFXBeginMode implements BeginMode
{
    LINES(DrawingMode.LINES), 
    TRIANGLES(DrawingMode.TRIANGLES), 
    QUADS(DrawingMode.QUADS);
    
    private final DrawingMode drawingMode;
    
    private GFXBeginMode(final DrawingMode drawingMode) {
        this.drawingMode = drawingMode;
    }
    
    @Override
    public int getHandle() {
        return this.drawingMode.getId();
    }
    
    @Override
    public int getIndexCount(final int vertices) {
        return this.drawingMode.getIndexCount(vertices);
    }
}
