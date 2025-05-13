// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text.msdf;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.labymod.api.Laby;
import net.labymod.api.client.render.font.text.glyph.GlyphStyle;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.core.client.render.font.text.msdf.info.Bounds;
import net.labymod.core.client.render.font.text.msdf.info.Atlas;
import net.labymod.core.client.render.font.text.msdf.info.GlyphInfo;
import net.labymod.api.client.render.font.text.glyph.Glyph;

public class MSDFGlyph implements Glyph
{
    private final float advance;
    private final float minU;
    private final float minV;
    private final float maxU;
    private final float maxV;
    private final float width;
    private final float height;
    private final float topPosition;
    private float size;
    private float lineHeight;
    
    public MSDFGlyph(final GlyphInfo glyphInfo, final Atlas atlas) {
        this.advance = glyphInfo.advance();
        final Bounds glyphAtlas = glyphInfo.atlas();
        float left;
        float top;
        float right;
        float bottom;
        if (glyphAtlas == null) {
            left = 0.0f;
            top = 0.0f;
            right = 0.0f;
            bottom = 0.0f;
        }
        else {
            left = glyphAtlas.left();
            top = glyphAtlas.top();
            right = glyphAtlas.right();
            bottom = glyphAtlas.bottom();
        }
        final Bounds glyphPlane = glyphInfo.plane();
        if (glyphAtlas == null) {
            this.width = 0.0f;
            this.height = 0.0f;
            this.topPosition = 0.0f;
        }
        else {
            this.width = glyphPlane.right() - glyphPlane.left();
            this.height = glyphPlane.top() - glyphPlane.bottom();
            this.topPosition = glyphPlane.top();
        }
        final float atlasWidth = atlas.width();
        final float atlasHeight = atlas.height();
        this.minU = 1.0f - top / atlasHeight;
        this.minV = left / atlasWidth;
        this.maxU = 1.0f - bottom / atlasHeight;
        this.maxV = right / atlasWidth;
    }
    
    public MSDFGlyph size(final float size) {
        this.size = size;
        return this;
    }
    
    @Override
    public void render(final BufferConsumer consumer, final Stack stack, float x, float y, final float z, final GlyphStyle glyphStyle) {
        final int packedLight = Laby.references().renderEnvironmentContext().getPackedLightWithCondition();
        final boolean italic = glyphStyle.isItalic();
        final boolean bold = glyphStyle.isBold();
        final int packedColor = glyphStyle.getPackedColor();
        x -= 1.5f;
        final float baseline = y + this.lineHeight;
        y = baseline - this.topPosition * this.size;
        final float width = this.width * this.size;
        final float height = this.height * this.size;
        final float italicOffset = italic ? 1.0f : 0.0f;
        final float boldWeight = bold ? 0.08f : 0.0f;
        final float weight = boldWeight + glyphStyle.getFontWeight();
        consumer.pos(x - italicOffset, y + height, z).uv(this.minV, this.maxU).packedColor(packedColor).light(packedLight).putFloat(weight).endVertex();
        consumer.pos(x + width - italicOffset, y + height, z).uv(this.maxV, this.maxU).packedColor(packedColor).light(packedLight).putFloat(weight).endVertex();
        consumer.pos(x + width + italicOffset, y, z).uv(this.maxV, this.minU).packedColor(packedColor).light(packedLight).putFloat(weight).endVertex();
        consumer.pos(x + italicOffset, y, z).uv(this.minV, this.minU).packedColor(packedColor).light(packedLight).putFloat(weight).endVertex();
    }
    
    @Override
    public float advance() {
        return this.advance;
    }
    
    @Override
    public float width() {
        return this.width;
    }
    
    @Override
    public float height() {
        return this.height;
    }
    
    @Override
    public void applyMetadata(final Int2ObjectMap<Object> metadata) {
        final Object pointSize = metadata.get(0);
        if (pointSize instanceof Number) {
            this.size((float)pointSize);
        }
        final Object lineHeight = metadata.get(1);
        if (lineHeight instanceof Number) {
            this.lineHeight((float)lineHeight);
        }
    }
    
    public void lineHeight(final float lineHeight) {
        this.lineHeight = lineHeight;
    }
}
