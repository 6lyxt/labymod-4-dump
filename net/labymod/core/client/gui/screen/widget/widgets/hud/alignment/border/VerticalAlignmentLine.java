// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.hud.alignment.border;

import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.matrix.Stack;
import java.util.function.Supplier;

public class VerticalAlignmentLine extends RangeAlignmentLine
{
    private final Supplier<VerticalCoordinates> coordinates;
    private final boolean center;
    
    public VerticalAlignmentLine(final boolean center, final float range, final Supplier<VerticalCoordinates> coordinates) {
        super(range);
        this.center = center;
        this.coordinates = coordinates;
    }
    
    @Override
    public void render(final Stack stack, final Rectangle chain, final float tickDelta) {
        final VerticalCoordinates coordinates = this.coordinates.get();
        final float opacity = this.getOpacity(tickDelta);
        this.rectangleRenderer.pos(coordinates.x, coordinates.y1).size(1.0f, Math.abs(coordinates.y2 - coordinates.y1) + 1.0f).color(ColorFormat.ARGB32.pack(255, 255, 255, (int)(opacity * 255.0f))).render(stack);
        super.render(stack, chain, tickDelta);
    }
    
    @Override
    protected float getDistance(final Rectangle rectangle) {
        final VerticalCoordinates coordinates = this.coordinates.get();
        if (rectangle.getLeft() < coordinates.x && rectangle.getRight() > coordinates.x) {
            return 0.0f;
        }
        return Math.min(Math.abs(rectangle.getLeft() - coordinates.x), Math.abs(rectangle.getRight() - coordinates.x));
    }
    
    @Override
    public void align(final MutableRectangle rectangle) {
        final VerticalCoordinates coordinates = this.coordinates.get();
        if (this.center) {
            if (coordinates.x > rectangle.getLeft() - this.range && coordinates.x < rectangle.getRight() + this.range) {
                rectangle.setX(coordinates.x - rectangle.getWidth() / 2.0f);
            }
        }
        else {
            if (coordinates.x > rectangle.getLeft() - this.range && coordinates.x < rectangle.getLeft() + this.range) {
                rectangle.setX(coordinates.x);
            }
            if (coordinates.x > rectangle.getRight() - this.range && coordinates.x < rectangle.getRight() + this.range) {
                rectangle.setX(coordinates.x - rectangle.getWidth());
            }
        }
    }
    
    public static VerticalAlignmentLine border(final float range, final Supplier<VerticalCoordinates> coordinates) {
        return new VerticalAlignmentLine(false, range, coordinates);
    }
    
    public static VerticalAlignmentLine center(final float range, final Supplier<VerticalCoordinates> coordinates) {
        return new VerticalAlignmentLine(true, range, coordinates);
    }
    
    public static class VerticalCoordinates
    {
        private final float x;
        private final float y1;
        private final float y2;
        
        private VerticalCoordinates(final float x, final float y1, final float y2) {
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
        }
        
        public static VerticalCoordinates of(final float x, final float y1, final float y2) {
            return new VerticalCoordinates(x, y1, y2);
        }
    }
}
