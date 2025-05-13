// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.util.bounds;

import java.util.Locale;

public class DefaultRectangle implements MutableRectangle
{
    protected float left;
    protected float top;
    protected float right;
    protected float bottom;
    
    public DefaultRectangle() {
    }
    
    public DefaultRectangle(final Rectangle rectangle) {
        this.set(rectangle);
    }
    
    protected DefaultRectangle(final float left, final float top, final float right, final float bottom) {
        this.setBounds(left, top, right, bottom);
    }
    
    @Override
    public void setLeft(final float left) {
        this.left = left;
    }
    
    @Override
    public void setTop(final float top) {
        this.top = top;
    }
    
    @Override
    public void setRight(final float right) {
        this.right = right;
    }
    
    @Override
    public void setBottom(final float bottom) {
        this.bottom = bottom;
    }
    
    @Override
    public float getLeft() {
        return this.left;
    }
    
    @Override
    public float getTop() {
        return this.top;
    }
    
    @Override
    public float getRight() {
        return this.right;
    }
    
    @Override
    public float getBottom() {
        return this.bottom;
    }
    
    @Override
    public String toString() {
        return String.format(Locale.ROOT, "%s,%s (%sx%s)", this.left, this.top, this.getWidth(), this.getHeight());
    }
}
