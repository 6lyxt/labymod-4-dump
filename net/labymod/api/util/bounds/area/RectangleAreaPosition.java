// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.bounds.area;

import java.util.function.Function;
import java.util.Iterator;
import net.labymod.api.util.math.vector.FloatVector2;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.bounds.Rectangle;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Contract;
import java.util.Arrays;
import java.util.Collection;

public enum RectangleAreaPosition
{
    TOP_LEFT(0, 0, new PositionUnit[] { PositionUnit.TOP, PositionUnit.LEFT }), 
    TOP_CENTER(1, 0, new PositionUnit[] { PositionUnit.TOP, PositionUnit.CENTER }), 
    TOP_RIGHT(2, 0, new PositionUnit[] { PositionUnit.TOP, PositionUnit.RIGHT }), 
    MIDDLE_LEFT(0, 1, new PositionUnit[] { PositionUnit.MIDDLE, PositionUnit.LEFT }), 
    MIDDLE_CENTER(1, 1, new PositionUnit[] { PositionUnit.MIDDLE, PositionUnit.CENTER }), 
    MIDDLE_RIGHT(2, 1, new PositionUnit[] { PositionUnit.MIDDLE, PositionUnit.RIGHT }), 
    BOTTOM_LEFT(0, 2, new PositionUnit[] { PositionUnit.BOTTOM, PositionUnit.LEFT }), 
    BOTTOM_CENTER(1, 2, new PositionUnit[] { PositionUnit.BOTTOM, PositionUnit.CENTER }), 
    BOTTOM_RIGHT(2, 2, new PositionUnit[] { PositionUnit.BOTTOM, PositionUnit.RIGHT });
    
    private final Collection<PositionUnit> positionUnits;
    private final int xIndex;
    private final int yIndex;
    
    private RectangleAreaPosition(final int xIndex, final int yIndex, final PositionUnit[] units) {
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.positionUnits = Arrays.asList(units);
    }
    
    @Contract(pure = true)
    @Nullable
    public static RectangleAreaPosition getPosition(final int xIndex, final int yIndex) {
        return Arrays.stream(values()).filter(value -> value.xIndex == xIndex && value.yIndex == yIndex).findFirst().orElse(null);
    }
    
    @NotNull
    public FloatVector2 anchorPoint(@NotNull final Rectangle rectangle) {
        float x = rectangle.getX();
        float y = rectangle.getY();
        for (final PositionUnit positionUnit : this.positionUnits) {
            x += positionUnit.getXModifier().apply(rectangle.getWidth());
            y += positionUnit.getYModifier().apply(rectangle.getHeight());
        }
        return new FloatVector2(x, y);
    }
    
    public Collection<PositionUnit> getPositionUnits() {
        return this.positionUnits;
    }
    
    public RectangleAreaPosition findBestPosition(final PositionUnit unit) {
        final Collection<PositionUnit> units = this.getPositionUnits();
        if (units.contains(unit)) {
            return this;
        }
        for (final RectangleAreaPosition value : values()) {
            final Collection<PositionUnit> valueUnits = value.getPositionUnits();
            if (valueUnits.contains(unit)) {
                for (final PositionUnit positionUnit : units) {
                    if (valueUnits.contains(positionUnit)) {
                        return value;
                    }
                }
            }
        }
        return this;
    }
    
    public enum PositionUnit
    {
        TOP(PositionUnit::zero, PositionUnit::zero), 
        MIDDLE(PositionUnit::zero, PositionUnit::half), 
        BOTTOM(PositionUnit::zero, PositionUnit::full), 
        LEFT(PositionUnit::zero, PositionUnit::zero), 
        CENTER(PositionUnit::half, PositionUnit::zero), 
        RIGHT(PositionUnit::full, PositionUnit::zero);
        
        private final Function<Float, Float> xModifier;
        private final Function<Float, Float> yModifier;
        
        private PositionUnit(final Function<Float, Float> xModifier, final Function<Float, Float> yModifier) {
            this.xModifier = xModifier;
            this.yModifier = yModifier;
        }
        
        private static float zero(final float value) {
            return 0.0f;
        }
        
        private static float half(final float value) {
            return value / 2.0f;
        }
        
        private static float full(final float value) {
            return value;
        }
        
        public Function<Float, Float> getXModifier() {
            return this.xModifier;
        }
        
        public Function<Float, Float> getYModifier() {
            return this.yModifier;
        }
    }
}
