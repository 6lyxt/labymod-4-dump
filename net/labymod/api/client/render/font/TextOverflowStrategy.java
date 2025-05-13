// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font;

public enum TextOverflowStrategy
{
    WRAP(0.0f), 
    CLIP(3.0f);
    
    private final float lineEndOffset;
    
    private TextOverflowStrategy(final float lineEndOffset) {
        this.lineEndOffset = lineEndOffset;
    }
    
    public float getLineEndOffset() {
        return this.lineEndOffset;
    }
}
