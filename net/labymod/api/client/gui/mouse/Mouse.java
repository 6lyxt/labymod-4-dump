// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.mouse;

import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.bounds.Point;

public class Mouse implements Point
{
    public static final Mouse OUT_OF_BOUNDS;
    protected double x;
    protected double y;
    protected boolean grabbed;
    
    public Mouse() {
    }
    
    public Mouse(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public int getX() {
        return (int)this.x;
    }
    
    @Override
    public int getY() {
        return (int)this.y;
    }
    
    public double getXDouble() {
        return this.x;
    }
    
    public double getYDouble() {
        return this.y;
    }
    
    public MutableMouse mutable() {
        return new MutableMouse(this);
    }
    
    public Mouse copy() {
        return new Mouse(this.x, this.y);
    }
    
    public boolean isInside(final double x, final double y, final double width, final double height) {
        return this.x >= x && this.x <= x + width && this.y >= y && this.y <= y + height;
    }
    
    public boolean isInside(final Rectangle rectangle) {
        return this.isInside(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
    }
    
    public boolean isOutOfBounds() {
        return this.x < 0.0 && this.y < 0.0;
    }
    
    public boolean isGrabbed() {
        return this.grabbed;
    }
    
    public void setGrabbed(final boolean grabbed) {
        this.grabbed = grabbed;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Mouse mouse = (Mouse)o;
        return Double.compare(mouse.x, this.x) == 0 && Double.compare(mouse.y, this.y) == 0;
    }
    
    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(this.x);
        int result = (int)(temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.y);
        result = 31 * result + (int)(temp ^ temp >>> 32);
        return result;
    }
    
    @Override
    public String toString() {
        return "Mouse{x=" + this.x + ",y=" + this.y;
    }
    
    static {
        OUT_OF_BOUNDS = new Mouse(-1.0, -1.0);
    }
}
