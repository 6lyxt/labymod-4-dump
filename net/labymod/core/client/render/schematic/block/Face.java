// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.schematic.block;

public enum Face
{
    TOP(0, 1, 0), 
    BOTTOM(0, -1, 0), 
    NORTH(0, 0, -1), 
    EAST(1, 0, 0), 
    SOUTH(0, 0, 1), 
    WEST(-1, 0, 0);
    
    public static final Face[] VALUES;
    private final int x;
    private final int y;
    private final int z;
    
    private Face(final int x, final int y, final int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    public float getShading() {
        return this.isXAxis() ? 0.6f : (this.isYAxis() ? 1.0f : 0.8f);
    }
    
    public boolean isXAxis() {
        return this.x != 0;
    }
    
    public boolean isYAxis() {
        return this.y != 0;
    }
    
    public boolean isZAxis() {
        return this.z != 0;
    }
    
    public Face opposite() {
        return switch (this.ordinal()) {
            default -> throw new MatchException(null, null);
            case 0 -> Face.BOTTOM;
            case 1 -> Face.TOP;
            case 2 -> Face.SOUTH;
            case 3 -> Face.WEST;
            case 4 -> Face.NORTH;
            case 5 -> Face.EAST;
        };
    }
    
    static {
        VALUES = values();
    }
}
