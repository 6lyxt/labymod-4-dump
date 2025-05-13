// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.signobject.object;

public class SignCanvasSize
{
    private final float widthBlocks;
    private final float heightBlocks;
    
    public SignCanvasSize(final float widthBlocks, final float heightBlocks) {
        this.widthBlocks = widthBlocks;
        this.heightBlocks = heightBlocks;
    }
    
    public float getWidthBlocks() {
        return this.widthBlocks;
    }
    
    public float getWidth() {
        return this.widthBlocks * 15.882353f;
    }
    
    public float getHeightBlocks() {
        return this.heightBlocks;
    }
    
    public float getHeight() {
        return this.heightBlocks * 15.882353f;
    }
}
