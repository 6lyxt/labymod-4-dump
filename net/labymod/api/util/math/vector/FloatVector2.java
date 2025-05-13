// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.math.vector;

public class FloatVector2
{
    private float x;
    private float y;
    
    public FloatVector2() {
        this(0.0f, 0.0f);
    }
    
    public FloatVector2(final FloatVector2 vector) {
        this(vector.getX(), vector.getY());
    }
    
    public FloatVector2(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public float getX() {
        return this.x;
    }
    
    public void setX(final float x) {
        this.x = x;
    }
    
    public float getY() {
        return this.y;
    }
    
    public void setY(final float y) {
        this.y = y;
    }
    
    public void set(final float x, final float y) {
        this.setX(x);
        this.setY(y);
    }
    
    public void set(final FloatVector2 vector) {
        this.set(vector.getX(), vector.getY());
    }
    
    public void multiply(final float x, final float y) {
        this.set(this.getX() * x, this.getY() * y);
    }
    
    public FloatVector2 subReverse(final FloatVector2 vector) {
        this.set(vector.getX() - this.getX(), vector.getY() - this.getY());
        return this;
    }
    
    public FloatVector2 sub(final FloatVector2 vector) {
        return this.sub(vector.getX(), vector.getY());
    }
    
    public FloatVector2 sub(final float x, final float y) {
        return this.add(-x, -y);
    }
    
    public FloatVector2 add(final FloatVector2 vector) {
        return this.add(vector.getX(), vector.getY());
    }
    
    public FloatVector2 add(final float x, final float y) {
        this.x += x;
        this.y += y;
        return this;
    }
    
    public FloatVector2 copy() {
        return new FloatVector2(this.getX(), this.getY());
    }
    
    public float distanceSquared(final FloatVector2 vector) {
        return this.square(this.getX() - vector.getX()) + this.square(this.getY() - vector.getY());
    }
    
    private float square(final float number) {
        return number * number;
    }
    
    @Override
    public String toString() {
        return "FloatVector2[x=" + this.getX() + ", y=" + this.getY();
    }
}
