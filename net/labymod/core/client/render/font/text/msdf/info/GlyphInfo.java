// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text.msdf.info;

import org.jetbrains.annotations.Nullable;
import com.google.gson.annotations.SerializedName;

public class GlyphInfo
{
    private int unicode;
    private float advance;
    @SerializedName("planeBounds")
    @Nullable
    private Bounds plane;
    @SerializedName("atlasBounds")
    @Nullable
    private Bounds atlas;
    
    public int unicode() {
        return this.unicode;
    }
    
    public void unicode(final int unicode) {
        this.unicode = unicode;
    }
    
    public float advance() {
        return this.advance;
    }
    
    public void advance(final float advance) {
        this.advance = advance;
    }
    
    public Bounds plane() {
        return this.plane;
    }
    
    public void plane(final Bounds plane) {
        this.plane = plane;
    }
    
    @Nullable
    public Bounds atlas() {
        return this.atlas;
    }
    
    public void atlas(final Bounds atlas) {
        this.atlas = atlas;
    }
}
