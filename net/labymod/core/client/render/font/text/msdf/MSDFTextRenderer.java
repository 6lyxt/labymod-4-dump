// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text.msdf;

import net.labymod.api.client.gfx.vertex.VertexFormat;
import net.labymod.core.client.render.font.text.msdf.info.FontInfo;
import net.labymod.api.client.gfx.shader.ShaderConstants;
import net.labymod.api.client.gfx.pipeline.program.RenderPrograms;
import java.util.Objects;
import net.labymod.api.client.gfx.shader.ShaderProgram;
import net.labymod.api.client.gfx.shader.uniform.Uniform1F;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.renderer.immediate.ImmediateRenderer;
import java.util.Iterator;
import net.labymod.api.util.ColorUtil;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.math.vector.FloatMatrix4;
import net.labymod.api.LabyAPI;
import net.labymod.core.client.render.font.text.DefaultTextBuffer;
import net.labymod.api.client.gfx.pipeline.buffer.BufferConsumer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.text.TextBuffer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.font.text.glyph.GlyphConsumer;
import net.labymod.api.client.render.font.VisualCharSequence;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.Style;
import javax.inject.Inject;
import net.labymod.core.client.render.font.text.msdf.listener.ShaderConstantsListener;
import net.labymod.api.Laby;
import net.labymod.core.client.gfx.pipeline.buffer.DefaultBufferBuilder;
import javax.inject.Named;
import net.labymod.api.client.render.font.text.glyph.GlyphProvider;
import net.labymod.api.client.gfx.pipeline.program.RenderProgram;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.core.client.render.font.text.msdf.info.Metrics;
import net.labymod.api.client.gfx.pipeline.buffer.BufferBuilder;
import net.labymod.api.client.render.font.text.StringFormatter;
import net.labymod.core.client.render.font.text.glyph.DefaultTextWidthGlyphConsumer;
import net.labymod.core.client.render.font.text.glyph.DefaultTextGlyphConsumer;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.render.font.text.AbstractTextRenderer;

@Singleton
@Implements(value = TextRenderer.class, key = "msdf_text_renderer")
public class MSDFTextRenderer extends AbstractTextRenderer
{
    private final MSDFGlyphProvider glyphProvider;
    private final MSDFResourceProvider resourceProvider;
    private final DefaultTextGlyphConsumer textConsumer;
    private final DefaultTextWidthGlyphConsumer textWidthConsumer;
    private final float pointSize;
    private final StringFormatter stringFormatter;
    private final BufferBuilder batchBufferBuilder;
    private float lineHeight;
    private Metrics metrics;
    private TextDrawMode cachedTextDrawMode;
    private ResourceLocation cachedFontLocation;
    private RenderProgram cachedRenderProgram;
    private float fontWeight;
    private float edgeStrength;
    
    @Inject
    public MSDFTextRenderer(@Named("msdf_glyph_provider") final GlyphProvider glyphProvider, final MSDFResourceProvider resourceProvider, final StringFormatter stringFormatter) {
        this.batchBufferBuilder = new DefaultBufferBuilder(256);
        this.fontWeight = 0.0f;
        this.edgeStrength = 0.5f;
        this.glyphProvider = (MSDFGlyphProvider)glyphProvider;
        this.resourceProvider = resourceProvider;
        this.stringFormatter = stringFormatter;
        Laby.references().eventBus().registerListener(new ShaderConstantsListener(this));
        this.textConsumer = new DefaultTextGlyphConsumer(glyphProvider);
        this.textWidthConsumer = new DefaultTextWidthGlyphConsumer(glyphProvider);
        this.pointSize = 8.25f;
    }
    
    @Override
    public void prepare(final String namespace, final String font) {
        this.resourceProvider.load(font);
        this.glyphProvider.pointSize(this.pointSize);
        this.glyphProvider.prepareGlyphs();
        this.metrics = this.resourceProvider.getFontInfo().metrics();
        this.lineHeight = this.metrics.lineHeight();
        this.updateEdgeStrength(this.edgeStrength);
        this.recompileShader();
    }
    
    @Override
    public void unload() {
        this.resourceProvider.unload();
    }
    
    @Override
    public float width(final int codepoint, final Style style) {
        this.textWidthConsumer.acceptGlyph(0, style, codepoint);
        float charWidth = this.textWidthConsumer.getAndResetWidth();
        if (style != null && style.hasDecoration(TextDecoration.BOLD)) {
            charWidth *= 1.12f;
        }
        return charWidth;
    }
    
    @Override
    public float width(final String text, final Style style) {
        final float superWidth = super.width(text, style);
        if (superWidth != -1.0f) {
            return superWidth;
        }
        final int defaultWidth = this.getWidth(text, style);
        final boolean noStyle = style == null || style.isEmpty();
        if (this.disableWidthCaching) {
            return (float)this.getWidth(text, style);
        }
        float width;
        float boldWidth;
        if (noStyle) {
            width = (float)defaultWidth;
            boldWidth = (float)this.getWidth(text, MSDFTextRenderer.BOLD_STYLE);
        }
        else {
            final boolean isBold = style.hasDecoration(TextDecoration.BOLD);
            if (isBold) {
                boldWidth = (float)defaultWidth;
                width = (float)this.getWidth(text, style.undecorate(TextDecoration.BOLD));
            }
            else {
                width = (float)defaultWidth;
                boldWidth = (float)this.getWidth(text, style.decorate(TextDecoration.BOLD));
            }
        }
        this.stringWidthPool.addStringWidth(text.hashCode(), text, width, boldWidth);
        return (float)defaultWidth;
    }
    
    @Override
    public float width(final VisualCharSequence text) {
        this.stringFormatter.visualFormat(text, this.capitalize, this.textWidthConsumer);
        return (float)MathHelper.ceil(this.textWidthConsumer.getAndResetWidth() * this.scale);
    }
    
    private int getWidth(final String text, final Style style) {
        final VisualCharSequence visualOrder = this.stringFormatter.getVisualOrder(text, style);
        this.stringFormatter.visualFormat(visualOrder, this.capitalize, this.textWidthConsumer);
        return MathHelper.ceil(this.textWidthConsumer.getAndResetWidth() * this.scale);
    }
    
    @Override
    public float height() {
        return (float)MathHelper.ceil(this.lineHeight * this.pointSize * this.scale);
    }
    
    @Override
    public TextRenderer fontWeight(float fontWeight) {
        fontWeight = fontWeight / 1000.0f / 6.0f;
        fontWeight = MathHelper.clamp(fontWeight, 0.0f, 0.16666667f);
        this.updateFontWeight(fontWeight);
        return super.fontWeight(fontWeight);
    }
    
    @Nullable
    @Override
    public TextBuffer render(final Stack stack) {
        this.updateUniforms();
        if (this.text == null && this.component == null) {
            return null;
        }
        final LabyAPI labyAPI = Laby.labyAPI();
        final BufferBuilder bufferBuilder = labyAPI.gfxRenderPipeline().getDefaultBufferBuilder();
        bufferBuilder.begin(this.findBestFontProgram(), () -> "MSDF Font Rendering");
        RenderableComponent component = this.component;
        if (component == null) {
            component = RenderableComponent.of(Component.text(this.text, this.style), false);
        }
        if (this.centered) {
            this.x -= component.getWidth() / 2.0f;
        }
        stack.push();
        stack.scale(this.scale);
        final float x = (this.scale == 0.0f) ? this.x : (this.x / this.scale);
        final float y = (this.scale == 0.0f) ? this.y : (this.y / this.scale);
        final float baselineHeight = this.getBaselineHeight();
        this.textConsumer.addMetadata(1, baselineHeight).addMetadata(0, this.pointSize).fontWeight(this.fontWeight);
        this.renderComponent(stack, bufferBuilder, component, x, y);
        final FloatMatrix4 modelViewMatrix = stack.getProvider().getPosition().copy();
        stack.pop();
        float fontWidth = this.textConsumer.finish(stack, x);
        this.uploadBuffer(bufferBuilder, modelViewMatrix);
        final boolean needsVanilla = this.textConsumer.renderVanillaGlyphs(stack);
        if (!this.useFloatingPointPosition) {
            fontWidth = (float)(int)Math.ceil(fontWidth);
        }
        this.dispose();
        return new DefaultTextBuffer(fontWidth, needsVanilla);
    }
    
    private void renderComponent(final Stack stack, final BufferConsumer buffer, final RenderableComponent component, final float x, final float y) {
        this.textConsumer.position(x + component.getXOffset(), y + component.getYOffset() + component.getInnerY()).color(ColorUtil.combineAlpha(this.color, Laby.labyAPI().renderPipeline().getAlpha())).backgroundColor(this.backgroundColor).shadow(this.shadow).fontWeight(this.fontWeight).stack(stack).bufferConsumer(buffer);
        this.stringFormatter.visualFormat(component.getVisualCharSequence(), this.capitalize, this.textConsumer);
        for (final RenderableComponent child : component.getChildren()) {
            this.renderComponent(stack, buffer, child, x, y);
        }
    }
    
    private void uploadBuffer(final BufferBuilder bufferBuilder, final FloatMatrix4 modelViewMatrix) {
        final GFXRenderPipeline renderPipeline = Laby.labyAPI().gfxRenderPipeline();
        renderPipeline.setProjectionMatrix();
        renderPipeline.setModelViewMatrix(modelViewMatrix);
        ImmediateRenderer.drawWithProgram(bufferBuilder.end());
    }
    
    @Nullable
    @Override
    public TextBuffer renderBatch(final Stack stack) {
        this.updateUniforms();
        if (this.text == null) {
            return null;
        }
        if (this.centered) {
            this.x -= this.width(this.text, this.style) / 2.0f;
        }
        stack.push();
        stack.scale(this.scale);
        final float x = (this.scale == 0.0f) ? this.x : (this.x / this.scale);
        final float y = (this.scale == 0.0f) ? this.y : (this.y / this.scale);
        final int color = ColorUtil.combineAlpha(this.color, Laby.labyAPI().renderPipeline().getAlpha());
        boolean needsVanilla;
        float fontWidth;
        if (this.isAlphaAboveThreshold(color)) {
            this.textConsumer.position(MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, x), MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, y)).color(color).shadow(this.shadow).backgroundColor(this.backgroundColor).fontWeight(this.fontWeight).addMetadata(0, this.pointSize).addMetadata(1, this.getBaselineHeight()).stack(stack).bufferConsumer(this.batchBufferBuilder);
            final VisualCharSequence visualOrder = this.stringFormatter.getVisualOrder(this.text, this.style);
            this.stringFormatter.visualFormat(visualOrder, this.capitalize, this.textConsumer);
            needsVanilla = this.textConsumer.renderVanillaGlyphs(stack);
            fontWidth = this.textConsumer.finish(stack, x);
        }
        else {
            needsVanilla = false;
            fontWidth = x + this.width(this.text, this.style);
        }
        if (!this.useFloatingPointPosition) {
            fontWidth = (float)(int)Math.ceil(fontWidth);
        }
        stack.pop();
        this.dispose();
        return new DefaultTextBuffer(fontWidth, needsVanilla);
    }
    
    @Override
    public GlyphProvider glyphProvider() {
        return this.glyphProvider;
    }
    
    @Override
    public void beginBatch(final Stack stack) {
        this.batchBufferBuilder.begin(this.findBestFontProgram(), () -> "MSDF Font Rendering (Batch)");
    }
    
    @Override
    public void endBatch(final Stack stack) {
        this.uploadBuffer(this.batchBufferBuilder, stack.getProvider().getPosition().copy());
        this.updateFontWeight(0.0f);
    }
    
    private void updateUniforms() {
        this.updateEdgeStrength(this.edgeStrength);
    }
    
    public void updateEdgeStrength(final float edgeStrength) {
        this.edgeStrength = edgeStrength;
        final RenderProgram bestFontProgram = this.findBestFontProgram();
        final ShaderProgram shaderProgram = bestFontProgram.shaderProgram();
        final Uniform1F edgeStrengthUniform = shaderProgram.getUniform("EdgeStrength");
        if (edgeStrengthUniform != null) {
            edgeStrengthUniform.set(this.edgeStrength);
        }
    }
    
    private void updateFontWeight(final float fontWeight) {
        this.fontWeight = fontWeight;
    }
    
    private float getBaselineHeight() {
        return this.metrics.baselineHeight() * this.pointSize;
    }
    
    private RenderProgram findBestFontProgram() {
        return this.findBestFontProgram(this.resourceProvider.fontTextureLocation());
    }
    
    private RenderProgram findBestFontProgram(final ResourceLocation location) {
        boolean useCachedRenderProgram = this.textDrawMode == this.cachedTextDrawMode;
        if (!Objects.equals(location, this.cachedFontLocation)) {
            useCachedRenderProgram = false;
        }
        if (useCachedRenderProgram) {
            return this.cachedRenderProgram;
        }
        this.cachedTextDrawMode = this.textDrawMode;
        this.cachedFontLocation = location;
        return this.cachedRenderProgram = RenderPrograms.getFontProgram(this.textDrawMode, location);
    }
    
    public float pointSize() {
        return this.pointSize;
    }
    
    public void fillShaderConstants(final ShaderConstants.Builder builder) {
        builder.addConstant((CharSequence)"TEXTURE_WIDTH", () -> {
            if (this.resourceProvider == null) {
                return "0";
            }
            else {
                final FontInfo fontInfo = this.resourceProvider.getFontInfo();
                if (fontInfo == null) {
                    return "0";
                }
                else {
                    return String.valueOf(fontInfo.atlas().width());
                }
            }
        });
        builder.addConstant((CharSequence)"TEXTURE_HEIGHT", () -> {
            if (this.resourceProvider == null) {
                return "0";
            }
            else {
                final FontInfo fontInfo2 = this.resourceProvider.getFontInfo();
                if (fontInfo2 == null) {
                    return "0";
                }
                else {
                    return String.valueOf(fontInfo2.atlas().height());
                }
            }
        });
    }
    
    private void recompileShader() {
        final VertexFormat format = Laby.references().vertexFormatRegistry().getVertexFormat("labymod:msdf_font");
        if (format == null) {
            return;
        }
        final ShaderProgram shader = format.getShader();
        shader.recompile();
    }
}
