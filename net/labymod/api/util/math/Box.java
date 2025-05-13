// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math;

public final class Box
{
    private final float minX;
    private final float minY;
    private final float minZ;
    private final float maxX;
    private final float maxY;
    private final float maxZ;
    
    public Box(final float minX, final float minY, final float minZ, final float maxX, final float maxY, final float maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }
    
    @Override
    public String toString() {
        return "Box{minX=" + this.minX + ", minY=" + this.minY + ", minZ=" + this.minZ + ", maxX=" + this.maxX + ", maxY=" + this.maxY + ", maxZ=" + this.maxZ + "} ";
    }
    
    public float minX() {
        return this.minX;
    }
    
    public float minY() {
        return this.minY;
    }
    
    public float minZ() {
        return this.minZ;
    }
    
    public float maxX() {
        return this.maxX;
    }
    
    public float maxY() {
        return this.maxY;
    }
    
    public float maxZ() {
        return this.maxZ;
    }
    
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        final Box that = (Box)obj;
        return Float.floatToIntBits(this.minX) == Float.floatToIntBits(that.minX) && Float.floatToIntBits(this.minY) == Float.floatToIntBits(that.minY) && Float.floatToIntBits(this.minZ) == Float.floatToIntBits(that.minZ) && Float.floatToIntBits(this.maxX) == Float.floatToIntBits(that.maxX) && Float.floatToIntBits(this.maxY) == Float.floatToIntBits(that.maxY) && Float.floatToIntBits(this.maxZ) == Float.floatToIntBits(that.maxZ);
    }
    
    @Override
    public int hashCode() {
        int result = (this.minX != 0.0f) ? Float.floatToIntBits(this.minX) : 0;
        result = 31 * result + ((this.minY != 0.0f) ? Float.floatToIntBits(this.minY) : 0);
        result = 31 * result + ((this.minZ != 0.0f) ? Float.floatToIntBits(this.minZ) : 0);
        result = 31 * result + ((this.maxX != 0.0f) ? Float.floatToIntBits(this.maxX) : 0);
        result = 31 * result + ((this.maxY != 0.0f) ? Float.floatToIntBits(this.maxY) : 0);
        result = 31 * result + ((this.maxZ != 0.0f) ? Float.floatToIntBits(this.maxZ) : 0);
        return result;
    }
}
