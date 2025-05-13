// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.bounds.area;

import java.util.function.Function;
import java.util.Collections;
import java.util.Collection;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import net.labymod.api.util.bounds.MutableRectangle;

public class AreaRectangle implements MutableRectangle
{
    private final Map<RectangleAreaPosition, RectangleArea> areas;
    private float left;
    private float top;
    private float right;
    private float bottom;
    
    public AreaRectangle() {
        this.areas = new HashMap<RectangleAreaPosition, RectangleArea>();
        this.updateAreaBounds();
    }
    
    @NotNull
    public RectangleArea getArea(final RectangleAreaPosition position) {
        return this.areas.get(position);
    }
    
    @Nullable
    public RectangleArea getArea(final float x, final float y) {
        for (final RectangleArea area : this.areas.values()) {
            if (area.isInRectangle(x, y)) {
                return area;
            }
        }
        return null;
    }
    
    public Collection<RectangleArea> getAreas() {
        return Collections.unmodifiableCollection((Collection<? extends RectangleArea>)this.areas.values());
    }
    
    @Override
    public float getLeft() {
        return this.left;
    }
    
    @Override
    public void setLeft(final float left) {
        this.left = left;
        this.updateAreaBounds();
    }
    
    @Override
    public float getTop() {
        return this.top;
    }
    
    @Override
    public void setTop(final float top) {
        this.top = top;
        this.updateAreaBounds();
    }
    
    @Override
    public float getRight() {
        return this.right;
    }
    
    @Override
    public void setRight(final float right) {
        this.right = right;
        this.updateAreaBounds();
    }
    
    @Override
    public float getBottom() {
        return this.bottom;
    }
    
    @Override
    public void setBottom(final float bottom) {
        this.bottom = bottom;
        this.updateAreaBounds();
    }
    
    private void updateAreaBounds() {
        for (int yIndex = 0; yIndex < 3; ++yIndex) {
            for (int xIndex = 0; xIndex < 3; ++xIndex) {
                final RectangleAreaPosition position = RectangleAreaPosition.getPosition(xIndex, yIndex);
                if (position != null) {
                    final float areaWidth = this.getWidth() / 3.0f;
                    final float areaHeight = this.getHeight() / 3.0f;
                    final float areaX = this.getX() + areaWidth * xIndex;
                    final float areaY = this.getY() + areaHeight * yIndex;
                    final RectangleArea area = this.areas.computeIfAbsent(position, RectangleArea::new);
                    area.setBounds(areaX, areaY, areaX + areaWidth, areaY + areaHeight);
                }
            }
        }
    }
}
