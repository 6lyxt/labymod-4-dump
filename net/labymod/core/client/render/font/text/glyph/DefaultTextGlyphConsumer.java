// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text.glyph;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import net.labymod.api.client.gui.screen.widget.OverlappingTranslator;
import net.labymod.api.util.math.vector.Matrix4;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.render.font.text.glyph.Glyph;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.Style;
import java.util.ArrayList;
import net.labymod.api.Laby;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.render.GraphicsColor;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.render.matrix.Stack;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.labymod.api.client.render.font.text.glyph.GlyphProvider;
import net.labymod.api.client.render.font.text.glyph.GlyphStyle;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.font.text.glyph.GlyphConsumer;

public class DefaultTextGlyphConsumer implements GlyphConsumer
{
    private static final RenderEnvironmentContext RENDER_ENVIRONMENT_CONTEXT;
    private static final GlyphStyle SHARED_GLYPH_STYLE;
    private static final float EFFECT_DEPTH = 0.04f;
    private final GlyphProvider glyphProvider;
    private final Int2ObjectMap<Object> metadata;
    private Stack stack;
    private BufferConsumer consumer;
    private float x;
    private float y;
    private final GraphicsColor color;
    private float alpha;
    private float shadowAlpha;
    private float fontWeight;
    private int backgroundColor;
    private boolean shadow;
    private int defaultColor;
    private final TextRenderer vanillaTextRenderer;
    private final List<VanillaGlyph> vanillaGlyphs;
    private final List<Effect> effects;
    
    public DefaultTextGlyphConsumer(final GlyphProvider glyphProvider) {
        this.glyphProvider = glyphProvider;
        this.color = new GraphicsColor(-1);
        this.defaultColor = this.color.color();
        this.metadata = (Int2ObjectMap<Object>)new Int2ObjectOpenHashMap(2);
        this.vanillaTextRenderer = Laby.references().textRenderer("vanilla_text_renderer");
        this.vanillaGlyphs = new ArrayList<VanillaGlyph>();
        this.effects = new ArrayList<Effect>();
    }
    
    public DefaultTextGlyphConsumer position(final float x, final float y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    public DefaultTextGlyphConsumer stack(final Stack stack) {
        this.stack = stack;
        return this;
    }
    
    public DefaultTextGlyphConsumer bufferConsumer(final BufferConsumer consumer) {
        this.consumer = consumer;
        return this;
    }
    
    public DefaultTextGlyphConsumer color(final int color) {
        this.color.update(color);
        this.defaultColor = color;
        this.alpha = this.color.alpha();
        this.shadowAlpha = this.color.alpha(true);
        return this;
    }
    
    public DefaultTextGlyphConsumer shadow(final boolean shadow) {
        this.shadow = shadow;
        return this;
    }
    
    public DefaultTextGlyphConsumer backgroundColor(final int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }
    
    public DefaultTextGlyphConsumer addMetadata(final int key, final Object value) {
        this.metadata.put(key, value);
        return this;
    }
    
    public DefaultTextGlyphConsumer fontWeight(final float fontWeight) {
        this.fontWeight = fontWeight;
        DefaultTextGlyphConsumer.SHARED_GLYPH_STYLE.setFontWeight(fontWeight);
        return this;
    }
    
    @Override
    public boolean acceptGlyph(final int position, final Style style, final int codePoint) {
        Glyph glyph = this.glyphProvider.getGlyph(codePoint);
        if (glyph == null || (style != null && style.getFont() != null)) {
            final String character = String.valueOf((char)codePoint);
            this.vanillaGlyphs.add(new VanillaGlyph(character, this.x, this.y, this.color.color(), this.shadow, style));
            final RenderAttributesStack renderAttributesStack = DefaultTextGlyphConsumer.RENDER_ENVIRONMENT_CONTEXT.renderAttributesStack();
            try {
                final RenderAttributes attributes = renderAttributesStack.pushAndGet();
                attributes.setForceVanillaFont(true);
                this.x += this.vanillaTextRenderer.width(character, style);
            }
            finally {
                renderAttributesStack.pop();
            }
            return true;
        }
        if (this.hasDecoration(style, TextDecoration.OBFUSCATED)) {
            glyph = this.glyphProvider.randomGlyph(glyph);
        }
        glyph.applyMetadata(this.metadata);
        if (style != null) {
            final TextColor textColor = style.getColor();
            if (textColor != null) {
                this.color.update(ColorFormat.ARGB32.pack(textColor.getValue(), (int)(this.alpha * 255.0f)));
            }
            else {
                this.color.update(this.defaultColor);
            }
        }
        this.renderGlyph(glyph, this.hasDecoration(style, TextDecoration.ITALIC), this.hasDecoration(style, TextDecoration.BOLD));
        final float shadowOffset = this.shadowOffset();
        final float advance = this.glyphProvider.width(glyph);
        if (this.hasDecoration(style, TextDecoration.STRIKETHROUGH)) {
            this.addEffect(new Effect(this.x + shadowOffset - 1.0f, this.y + shadowOffset + 2.5f, this.x + shadowOffset + advance, this.y + shadowOffset + 2.5f + 1.0f, 0.04f, new GraphicsColor(this.color.color()), this.shadow));
        }
        if (this.hasDecoration(style, TextDecoration.UNDERLINED)) {
            this.addEffect(new Effect(this.x + shadowOffset - 1.0f, this.y + shadowOffset + 7.25f, this.x + shadowOffset + advance, this.y + shadowOffset + 7.25f + 1.0f, 0.04f, new GraphicsColor(this.color.color()), this.shadow));
        }
        this.x += advance;
        return true;
    }
    
    private boolean hasDecoration(final Style style, final TextDecoration decoration) {
        return style != null && style.hasDecoration(decoration);
    }
    
    private void renderGlyph(final Glyph glyph, final boolean italic, final boolean bold) {
        DefaultTextGlyphConsumer.SHARED_GLYPH_STYLE.setItalic(italic);
        DefaultTextGlyphConsumer.SHARED_GLYPH_STYLE.setBold(bold);
        if (this.shadow) {
            final float shadowOffset = this.glyphProvider.shadowOffset();
            float x = this.x;
            float y = this.y;
            x += shadowOffset;
            y += shadowOffset;
            DefaultTextGlyphConsumer.SHARED_GLYPH_STYLE.setPackedColor(this.color.red(true), this.color.green(true), this.color.blue(true), this.shadowAlpha);
            glyph.render(this.consumer, this.stack, x, y, 0.0f, DefaultTextGlyphConsumer.SHARED_GLYPH_STYLE);
        }
        boolean translate = false;
        if (this.shadow) {
            final OverlappingTranslator translator = Laby.labyAPI().gfxRenderPipeline().overlappingTranslator();
            if (translator.isTranslated()) {
                translate = true;
                this.stack.push();
                translator.translate(this, this.stack);
            }
        }
        DefaultTextGlyphConsumer.SHARED_GLYPH_STYLE.setPackedColor(this.color.red(false), this.color.green(false), this.color.blue(false), this.alpha);
        glyph.render(this.consumer, this.stack, this.x, this.y, 0.03f, DefaultTextGlyphConsumer.SHARED_GLYPH_STYLE);
        if (translate) {
            this.stack.pop();
        }
    }
    
    public void addEffect(final Effect effect) {
        this.effects.add(effect);
    }
    
    public float lastX() {
        return this.x + this.shadowOffset();
    }
    
    public float finish(final Stack stack, final float x) {
        if (this.backgroundColor != 0) {
            final GraphicsColor graphicsColor = GraphicsColor.DEFAULT_COLOR.update(this.backgroundColor);
            this.addEffect(new Effect(x - 1.0f, this.y, this.x + 1.0f, 9.0f, 0.04f, graphicsColor));
            this.backgroundColor = 0;
        }
        if (!this.effects.isEmpty()) {
            DefaultTextGlyphConsumer.SHARED_GLYPH_STYLE.setPackedColor(1.0f, 1.0f, 1.0f, 1.0f);
            DefaultTextGlyphConsumer.SHARED_GLYPH_STYLE.setItalic(false);
            DefaultTextGlyphConsumer.SHARED_GLYPH_STYLE.setBold(false);
            for (final Effect effect : this.effects) {
                effect.render(this.consumer, stack, 0.0f, 0.0f, 0.0f, DefaultTextGlyphConsumer.SHARED_GLYPH_STYLE);
            }
            this.effects.clear();
        }
        DefaultTextGlyphConsumer.SHARED_GLYPH_STYLE.setFontWeight(0.0f);
        return this.lastX();
    }
    
    public float shadowOffset() {
        return this.shadow ? this.glyphProvider.shadowOffset() : 0.0f;
    }
    
    public boolean renderVanillaGlyphs(final Stack stack) {
        if (this.vanillaGlyphs.isEmpty()) {
            return false;
        }
        boolean batching = false;
        try {
            for (final VanillaGlyph vanillaGlyph : this.vanillaGlyphs) {
                if (!batching) {
                    this.vanillaTextRenderer.beginBatch(stack);
                    batching = true;
                }
                final RenderAttributesStack renderAttributesStack = DefaultTextGlyphConsumer.RENDER_ENVIRONMENT_CONTEXT.renderAttributesStack();
                try {
                    final RenderAttributes attributes = renderAttributesStack.pushAndGet();
                    attributes.setForceVanillaFont(true);
                    vanillaGlyph.render(stack, this.vanillaTextRenderer);
                }
                finally {
                    renderAttributesStack.pop();
                }
            }
        }
        catch (final ConcurrentModificationException ex) {}
        if (batching) {
            this.vanillaTextRenderer.endBatch(stack);
            this.vanillaGlyphs.clear();
        }
        return batching;
    }
    
    public GlyphProvider glyphProvider() {
        return this.glyphProvider;
    }
    
    static {
        RENDER_ENVIRONMENT_CONTEXT = Laby.references().renderEnvironmentContext();
        SHARED_GLYPH_STYLE = new GlyphStyle();
    }
    
    static class VanillaGlyph
    {
        private final String character;
        private final float x;
        private final float y;
        private final int color;
        private final Style style;
        private final boolean shadow;
        
        public VanillaGlyph(final String character, final float x, final float y, final int color, final boolean shadow, final Style style) {
            this.character = character;
            this.x = x;
            this.y = y;
            this.color = color;
            this.shadow = shadow;
            this.style = style;
        }
        
        public void render(final Stack stack, final TextRenderer textRenderer) {
            textRenderer.text(this.character).pos(this.x, this.y).color(this.color).shadow(this.shadow).style(this.style).renderBatch(stack);
        }
    }
    
    class Effect implements Glyph
    {
        private static final float DEFAULT_SHADOW_OFFSET = 1.0f;
        private final float x;
        private final float y;
        private final float width;
        private final float height;
        private final float depth;
        private final GraphicsColor color;
        private final boolean shadow;
        
        public Effect(final DefaultTextGlyphConsumer this$0, final float x, final float y, final float width, final float height, final float depth, final GraphicsColor color) {
            this(this$0, x, y, width, height, depth, color, false);
        }
        
        public Effect(final DefaultTextGlyphConsumer this$0, final float x, final float y, final float width, final float height, final float depth, final GraphicsColor color, final boolean shadow) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.depth = depth;
            this.color = color;
            this.shadow = shadow;
        }
        
        @Override
        public void render(final BufferConsumer consumer, final Stack stack, final float x, final float y, final float z, final GlyphStyle glyphStyle) {
            final boolean italic = glyphStyle.isItalic();
            if (this.shadow) {
                stack.push();
                stack.translate(1.0f, 1.0f, 0.0f);
                this._render(consumer, stack, italic, this.color.shadowColor());
                stack.pop();
            }
            this._render(consumer, stack, italic, this.color.color());
        }
        
        private void _render(final BufferConsumer consumer, final Stack stack, final boolean italic, final int color) {
            final int light = Laby.references().renderEnvironmentContext().getPackedLightWithCondition();
            final float italicOffset = italic ? 1.0f : 0.0f;
            consumer.pos(this.x - italicOffset, this.height, this.depth).uv(0.0f, 0.0f).color(color).putInt(light).putFloat(0.0f).endVertex();
            consumer.pos(this.width - italicOffset, this.height, this.depth).uv(0.0f, 0.0f).color(color).putInt(light).putFloat(0.0f).endVertex();
            consumer.pos(this.width + italicOffset, this.y, this.depth).uv(0.0f, 0.0f).color(color).putInt(light).putFloat(0.0f).endVertex();
            consumer.pos(this.x + italicOffset, this.y, this.depth).uv(0.0f, 0.0f).color(color).putInt(light).putFloat(0.0f).endVertex();
        }
        
        @Override
        public float advance() {
            return 0.0f;
        }
        
        @Override
        public float width() {
            return 0.0f;
        }
        
        @Override
        public float height() {
            return 0.0f;
        }
        
        @Override
        public void applyMetadata(final Int2ObjectMap<Object> metadata) {
        }
    }
}
