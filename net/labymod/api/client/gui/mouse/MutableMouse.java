// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.mouse;

import java.util.function.BooleanSupplier;
import net.labymod.api.util.bounds.Rectangle;

public class MutableMouse extends Mouse
{
    public MutableMouse() {
    }
    
    public MutableMouse(final Mouse mouse) {
        super(mouse.x, mouse.y);
    }
    
    public MutableMouse(final double x, final double y) {
        super(x, y);
    }
    
    public void transform(final Rectangle rectangle, final Runnable runnable) {
        final float dx = rectangle.getX();
        final float dy = rectangle.getY();
        try {
            this.x -= dx;
            this.y -= dy;
            runnable.run();
        }
        finally {
            this.x += dx;
            this.y += dy;
        }
    }
    
    public void set(final double x, final double y, final Runnable runnable) {
        final double px = this.x;
        final double py = this.y;
        try {
            this.set(x, y);
            runnable.run();
        }
        finally {
            this.set(px, py);
        }
    }
    
    public boolean set(final double x, final double y, final BooleanSupplier transformHandler) {
        final double px = this.x;
        final double py = this.y;
        try {
            this.set(x, y);
            return transformHandler.getAsBoolean();
        }
        finally {
            this.set(px, py);
        }
    }
    
    public void set(final double x, final double y) {
        this.x = x;
        this.y = y;
    }
    
    @Override
    public String toString() {
        return "Mouse{x=" + this.x + ",y=" + this.y + ",mutable}";
    }
}
