// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.user.shop.item.model;

public class OffsetVector
{
    private double x;
    private double y;
    private double z;
    
    public OffsetVector() {
        this(0.0, 0.0, 0.0);
    }
    
    public OffsetVector(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public OffsetVector(final String x, final String y, final String z) {
        this(parseString(x), parseString(y), parseString(z));
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    private static double parseString(final String value) {
        try {
            return Double.parseDouble(value);
        }
        catch (final NumberFormatException exception) {
            return 0.0;
        }
    }
}
