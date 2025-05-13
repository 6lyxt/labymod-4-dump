// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.bounds.area;

import org.jetbrains.annotations.Contract;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.bounds.DefaultRectangle;

public class RectangleArea extends DefaultRectangle
{
    private final RectangleAreaPosition position;
    
    public RectangleArea(@NotNull final RectangleAreaPosition position) {
        this.position = position;
    }
    
    @Override
    public boolean isXInRectangle(final float x) {
        return x >= this.getLeft() && x <= this.getRight();
    }
    
    @Override
    public boolean isYInRectangle(final float y) {
        return y >= this.getTop() && y <= this.getBottom();
    }
    
    @NotNull
    public RectangleAreaPosition position() {
        return this.position;
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public FloatVector2 absoluteToRelative(final float x, final float y) {
        final FloatVector2 anchorPoint = this.position.anchorPoint(this);
        return new FloatVector2(x - anchorPoint.getX(), y - anchorPoint.getY());
    }
    
    @Contract(value = "_, _ -> new", pure = true)
    @NotNull
    public FloatVector2 relativeToAbsolute(final float x, final float y) {
        final FloatVector2 anchorPoint = this.position.anchorPoint(this);
        return new FloatVector2(x + anchorPoint.getX(), y + anchorPoint.getY());
    }
}
