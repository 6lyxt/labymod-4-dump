// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math.position;

public class DefaultPosition implements Position
{
    private double x;
    private double y;
    private double z;
    
    public DefaultPosition() {
        this(0.0, 0.0, 0.0);
    }
    
    public DefaultPosition(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    @Override
    public double getX() {
        return this.x;
    }
    
    @Override
    public void setX(final double x) {
        this.x = x;
    }
    
    @Override
    public double getY() {
        return this.y;
    }
    
    @Override
    public void setY(final double y) {
        this.y = y;
    }
    
    @Override
    public double getZ() {
        return this.z;
    }
    
    @Override
    public void setZ(final double z) {
        this.z = z;
    }
}
