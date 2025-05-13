// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.util.camera.spline.position;

import net.labymod.api.util.math.Quaternion;
import net.labymod.api.util.math.vector.DoubleVector3;

public class Location
{
    private final DoubleVector3 position;
    private double x;
    private double y;
    private double z;
    private double yaw;
    private double pitch;
    private double roll;
    
    public Location() {
        this(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public Location(final double x, final double y, final double z) {
        this(x, y, z, 0.0, 0.0, 0.0);
    }
    
    public Location(final double x, final double y, final double z, final double yaw, final double pitch, final double roll) {
        this.position = new DoubleVector3();
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
        this.position.set((float)x, (float)y, (float)z);
    }
    
    public void setX(final double x) {
        this.x = x;
        this.position.setZ((float)x);
    }
    
    public void setY(final double y) {
        this.y = y;
        this.position.setZ((float)y);
    }
    
    public void setZ(final double z) {
        this.z = z;
        this.position.setZ((float)z);
    }
    
    public void setYaw(final double yaw) {
        this.yaw = yaw;
    }
    
    public void setPitch(final double pitch) {
        this.pitch = pitch;
    }
    
    public void setRoll(final double roll) {
        this.roll = roll;
    }
    
    public void addPosition(final double x, final double y, final double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.position.add(x, y, z);
    }
    
    public void addRotation(final double yaw, final double pitch, final double roll) {
        this.yaw += yaw;
        this.pitch += pitch;
        this.roll += roll;
    }
    
    public void setPosition(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.position.set(x, y, z);
    }
    
    public void setRotation(final double yaw, final double pitch, final double roll) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public DoubleVector3 position() {
        return this.position;
    }
    
    public Quaternion rotation() {
        return Quaternion.fromEuler((float)this.yaw, (float)this.pitch, (float)this.roll);
    }
    
    public double getYaw() {
        return this.yaw;
    }
    
    public double getPitch() {
        return this.pitch;
    }
    
    public double getRoll() {
        return this.roll;
    }
    
    @Override
    public String toString() {
        return this.x + ", " + this.y + ", " + this.z + ", " + this.yaw + ", " + this.pitch + ", " + this.roll;
    }
}
