// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font.text.glyph;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.Disposable;

@Referenceable(named = true)
public interface GlyphProvider extends Disposable
{
    void prepareGlyphs();
    
    @Nullable
    Glyph getGlyph(final int p0);
    
    Glyph randomGlyph(final Glyph p0);
    
    float width(final Glyph p0);
    
    float height(final Glyph p0);
    
    default float shadowOffset() {
        return 1.0f;
    }
}
