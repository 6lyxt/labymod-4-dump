// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.bounds;

public interface Point
{
    int getX();
    
    int getY();
    
    default boolean matches(final Point point) {
        return this.getX() == point.getX() && this.getY() == point.getY();
    }
    
    default Point fixed(final int x, final int y) {
        return new FixedPoint(x, y);
    }
    
    default Point add(final int dx, final int dy) {
        return fixed(this.getX() + dx, this.getY() + dy);
    }
    
    default Point subtract(final int dx, final int dy) {
        return this.add(-dx, -dy);
    }
}
