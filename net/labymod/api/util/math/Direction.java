// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math;

import net.labymod.api.util.math.vector.FloatVector3;

public enum Direction
{
    DOWN(0, 1, new FloatVector3(0.0f, -1.0f, 0.0f)), 
    UP(1, 0, new FloatVector3(0.0f, 1.0f, 0.0f)), 
    NORTH(2, 3, new FloatVector3(0.0f, 0.0f, -1.0f)), 
    SOUTH(3, 2, new FloatVector3(0.0f, 0.0f, 1.0f)), 
    WEST(4, 5, new FloatVector3(-1.0f, 0.0f, 0.0f)), 
    EAST(5, 4, new FloatVector3(1.0f, 0.0f, 0.0f));
    
    public static final Direction[] VALUES;
    private final int index;
    private final int oppositeIndex;
    private final FloatVector3 normal;
    
    private Direction(final int index, final int oppositeIndex, final FloatVector3 normal) {
        this.index = index;
        this.oppositeIndex = oppositeIndex;
        this.normal = normal;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    public int getOppositeIndex() {
        return this.oppositeIndex;
    }
    
    public FloatVector3 getNormal() {
        return this.normal;
    }
    
    public static Direction byIndex(final int index) {
        return byIndex(index, Direction.NORTH);
    }
    
    public static Direction byIndex(final int index, final Direction defaultValue) {
        for (final Direction direction : Direction.VALUES) {
            if (direction.getIndex() == index) {
                return direction;
            }
        }
        return defaultValue;
    }
    
    static {
        VALUES = values();
    }
}
