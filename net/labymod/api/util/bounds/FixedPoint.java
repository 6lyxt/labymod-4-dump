// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.bounds;

public class FixedPoint implements Point
{
    private final int x;
    private final int y;
    
    FixedPoint(final int x, final int y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public int getX() {
        return this.x;
    }
    
    @Override
    public int getY() {
        return this.y;
    }
}
