// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text.glyph;

import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.render.font.text.glyph.Glyph;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.render.font.text.glyph.GlyphProvider;
import net.labymod.api.client.render.font.text.glyph.GlyphConsumer;

public class DefaultTextWidthGlyphConsumer implements GlyphConsumer
{
    private final GlyphProvider glyphProvider;
    private final TextRenderer vanillaTextRenderer;
    private final RenderAttributesStack renderAttributesStack;
    private float width;
    
    public DefaultTextWidthGlyphConsumer(final GlyphProvider glyphProvider) {
        this.glyphProvider = glyphProvider;
        final ReferenceStorage references = Laby.references();
        this.vanillaTextRenderer = references.textRenderer("vanilla_text_renderer");
        this.renderAttributesStack = references.renderEnvironmentContext().renderAttributesStack();
    }
    
    @Override
    public boolean acceptGlyph(final int position, final Style style, final int codePoint) {
        final Glyph glyph = this.glyphProvider.getGlyph(codePoint);
        if (glyph == null) {
            try {
                final RenderAttributes attributes = this.renderAttributesStack.pushAndGet();
                attributes.setForceVanillaFont(true);
                this.width += this.vanillaTextRenderer.width((char)codePoint, style);
            }
            finally {
                this.renderAttributesStack.pop();
            }
            return true;
        }
        this.width += this.glyphProvider.width(glyph);
        return true;
    }
    
    public float getAndResetWidth() {
        final float width = this.width;
        this.width = 0.0f;
        return width;
    }
    
    public float width() {
        return this.width;
    }
}
