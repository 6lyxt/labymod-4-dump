// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text.msdf;

import net.labymod.api.client.render.font.text.glyph.Glyph;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.labymod.api.util.math.MathHelper;
import java.util.Iterator;
import net.labymod.core.client.render.font.text.msdf.info.FontInfo;
import net.labymod.core.client.render.font.text.msdf.info.GlyphInfo;
import javax.inject.Inject;
import it.unimi.dsi.fastutil.floats.Float2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import java.util.Random;
import net.labymod.api.client.render.font.text.glyph.GlyphProvider;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.render.font.text.glyph.AbstractGlyphProvider;

@Singleton
@Implements(value = GlyphProvider.class, key = "msdf_glyph_provider")
public class MSDFGlyphProvider extends AbstractGlyphProvider
{
    private static final Random RANDOM;
    private static final float SHADOW_OFFSET = 0.75f;
    private final MSDFResourceProvider resourceProvider;
    private float pointSize;
    private final Float2ObjectMap<IntList> obfuscatedTable;
    
    @Inject
    public MSDFGlyphProvider(final MSDFResourceProvider resourceProvider) {
        super(x$0 -> new MSDFGlyph[x$0], x$0 -> new MSDFGlyph[x$0][]);
        this.obfuscatedTable = (Float2ObjectMap<IntList>)new Float2ObjectOpenHashMap();
        this.resourceProvider = resourceProvider;
    }
    
    @Override
    public void prepareGlyphs() {
        final FontInfo fontInfo = this.resourceProvider.getFontInfo();
        float maxGlyphWidth = 0.0f;
        for (final GlyphInfo glyph : fontInfo.glyphs()) {
            final char unicode = (char)glyph.unicode();
            final MSDFGlyph value = new MSDFGlyph(glyph, fontInfo.atlas());
            this.glyphs.set(unicode, value);
            final float width = glyph.advance() * this.pointSize;
            if (width > maxGlyphWidth) {
                maxGlyphWidth = width;
            }
        }
        this.generateObfuscatedTable(maxGlyphWidth);
    }
    
    private void generateObfuscatedTable(final float maxGlyphWidth) {
        for (int tableSize = MathHelper.ceil(maxGlyphWidth), width = 0; width < tableSize; ++width) {
            this.glyphs.forEach((glyph, codepoint) -> {
                final float key = glyph.advance() * this.pointSize;
                IntList codepoints = (IntList)this.obfuscatedTable.get(key);
                if (codepoints == null) {
                    codepoints = (IntList)new IntArrayList();
                }
                codepoints.add(codepoint);
                this.obfuscatedTable.put(key, (Object)codepoints);
                return;
            });
        }
    }
    
    @Override
    public Glyph randomGlyph(final Glyph glyph) {
        final IntList codepoints = (IntList)this.obfuscatedTable.get(glyph.advance() * this.pointSize);
        return codepoints.isEmpty() ? glyph : this.getGlyph(codepoints.getInt(MSDFGlyphProvider.RANDOM.nextInt(codepoints.size())));
    }
    
    @Override
    public float width(final Glyph glyph) {
        return glyph.advance() * this.pointSize;
    }
    
    @Override
    public float height(final Glyph glyph) {
        return glyph.height() * this.pointSize;
    }
    
    public void pointSize(final float pointSize) {
        this.pointSize = pointSize;
    }
    
    @Override
    public float shadowOffset() {
        return 0.75f;
    }
    
    static {
        RANDOM = new Random();
    }
}
