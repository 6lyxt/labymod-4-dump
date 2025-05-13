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

public class HorizontalAlignmentLine extends RangeAlignmentLine
{
    private final Supplier<HorizontalCoordinates> coordinates;
    private final boolean center;
    
    private HorizontalAlignmentLine(final boolean center, final float range, final Supplier<HorizontalCoordinates> coordinates) {
        super(range);
        this.center = center;
        this.coordinates = coordinates;
    }
    
    @Override
    public void render(final Stack stack, final Rectangle chain, final float tickDelta) {
        final HorizontalCoordinates coordinates = this.coordinates.get();
        final float opacity = this.getOpacity(tickDelta);
        this.rectangleRenderer.pos(coordinates.x1, coordinates.y - (float)(this.center ? 1 : 0)).size(Math.abs(coordinates.x2 - coordinates.x1) + 1.0f, 1.0f).color(ColorFormat.ARGB32.pack(255, 255, 255, (int)(opacity * 255.0f))).render(stack);
        super.render(stack, chain, tickDelta);
    }
    
    @Override
    protected float getDistance(final Rectangle rectangle) {
        final HorizontalCoordinates coordinates = this.coordinates.get();
        if (rectangle.getTop() < coordinates.y && rectangle.getBottom() > coordinates.y) {
            return 0.0f;
        }
        return Math.min(Math.abs(rectangle.getTop() - coordinates.y), Math.abs(rectangle.getBottom() - coordinates.y));
    }
    
    @Override
    public void align(final MutableRectangle rectangle) {
        final HorizontalCoordinates coordinates = this.coordinates.get();
        if (this.center) {
            if (coordinates.y > rectangle.getTop() - this.range && coordinates.y < rectangle.getBottom() + this.range) {
                rectangle.setY(coordinates.y - rectangle.getHeight() / 2.0f);
            }
        }
        else {
            if (coordinates.y > rectangle.getTop() - this.range && coordinates.y < rectangle.getTop() + this.range) {
                rectangle.setY(coordinates.y);
            }
            if (coordinates.y > rectangle.getBottom() - this.range && coordinates.y < rectangle.getBottom() + this.range) {
                rectangle.setY(coordinates.y - rectangle.getHeight());
            }
        }
    }
    
    public static HorizontalAlignmentLine border(final float range, final Supplier<HorizontalCoordinates> coordinates) {
        return new HorizontalAlignmentLine(false, range, coordinates);
    }
    
    public static HorizontalAlignmentLine center(final float range, final Supplier<HorizontalCoordinates> coordinates) {
        return new HorizontalAlignmentLine(true, range, coordinates);
    }
    
    public static class HorizontalCoordinates
    {
        private final float y;
        private final float x1;
        private final float x2;
        
        private HorizontalCoordinates(final float y, final float x1, final float x2) {
            this.y = y;
            this.x1 = x1;
            this.x2 = x2;
        }
        
        public static HorizontalCoordinates of(final float y, final float x1, final float x2) {
            return new HorizontalCoordinates(y, x1, x2);
        }
    }
}
