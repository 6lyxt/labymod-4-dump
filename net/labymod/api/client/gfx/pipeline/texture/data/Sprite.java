// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline.texture.data;

public class Sprite
{
    private float x;
    private float y;
    private float width;
    private float height;
    
    public Sprite() {
        this(0.0f, 0.0f, 0.0f, 0.0f);
    }
    
    public Sprite(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public static Sprite of(final float x, final float y, final float width, final float height) {
        return new Sprite(x, y, width, height);
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
    
    public float getWidth() {
        return this.width;
    }
    
    public void setWidth(final float width) {
        this.width = width;
    }
    
    public float getHeight() {
        return this.height;
    }
    
    public void setHeight(final float height) {
        this.height = height;
    }
    
    public void setPosition(final float x, final float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setSize(final float width, final float height) {
        this.width = width;
        this.height = height;
    }
    
    public void set(final float x, final float y, final float width, final float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public String toString() {
        return "Sprite{x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final Sprite sprite = (Sprite)o;
        return Float.compare(sprite.x, this.x) == 0 && Float.compare(sprite.y, this.y) == 0 && Float.compare(sprite.width, this.width) == 0 && Float.compare(sprite.height, this.height) == 0;
    }
    
    @Override
    public int hashCode() {
        int result = (this.x != 0.0f) ? Float.floatToIntBits(this.x) : 0;
        result = 31 * result + ((this.y != 0.0f) ? Float.floatToIntBits(this.y) : 0);
        result = 31 * result + ((this.width != 0.0f) ? Float.floatToIntBits(this.width) : 0);
        result = 31 * result + ((this.height != 0.0f) ? Float.floatToIntBits(this.height) : 0);
        return result;
    }
}
