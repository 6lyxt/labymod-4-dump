// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text.glyph;

import org.jetbrains.annotations.Nullable;
import java.util.function.IntFunction;
import net.labymod.api.client.render.font.text.glyph.Glyph;
import net.labymod.core.util.collection.table.CodepointTable;
import net.labymod.api.client.render.font.text.glyph.GlyphProvider;

public abstract class AbstractGlyphProvider implements GlyphProvider
{
    protected CodepointTable<Glyph> glyphs;
    protected boolean disposed;
    
    public AbstractGlyphProvider(final IntFunction<Glyph[]> blockConstructor, final IntFunction<Glyph[][]> blockMapConstructor) {
        this.glyphs = new CodepointTable<Glyph>(blockConstructor, blockMapConstructor);
    }
    
    @Nullable
    @Override
    public Glyph getGlyph(final int codePoint) {
        return this.glyphs.get(codePoint);
    }
    
    @Override
    public boolean isDisposed() {
        return this.disposed;
    }
    
    @Override
    public void dispose() {
        this.glyphs.clear();
    }
}
