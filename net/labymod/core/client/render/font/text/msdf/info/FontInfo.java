// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text.msdf.info;

import java.util.List;

public class FontInfo
{
    private Atlas atlas;
    private Metrics metrics;
    private List<GlyphInfo> glyphs;
    
    public Atlas atlas() {
        return this.atlas;
    }
    
    public void atlas(final Atlas atlas) {
        this.atlas = atlas;
    }
    
    public Metrics metrics() {
        return this.metrics;
    }
    
    public void metrics(final Metrics metrics) {
        this.metrics = metrics;
    }
    
    public List<GlyphInfo> glyphs() {
        return this.glyphs;
    }
    
    public void glyphs(final List<GlyphInfo> glyphs) {
        this.glyphs = glyphs;
    }
}
