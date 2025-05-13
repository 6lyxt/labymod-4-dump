// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_20_4.client.font;

import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.Style;
import java.util.Objects;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.Laby;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.ComponentRendererBuilder;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.font.ComponentRenderMeta;
import net.labymod.api.client.render.font.text.TextBuffer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.v1_20_4.client.util.MinecraftUtil;
import org.joml.Matrix4f;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.font.text.TextRendererProvider;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.font.FontHeightAccessor;

public class DynamicFont extends ews implements FontHeightAccessor
{
    private static final ComponentMapper COMPONENT_MAPPER;
    private static final ComponentRenderer COMPONENT_RENDERER;
    private static final TextRendererProvider TEXT_RENDERER_PROVIDER;
    private static final RenderEnvironmentContext RENDER_ENVIRONMENT_CONTEXT;
    private final ews delegate;
    private final evu customSplitter;
    
    public DynamicFont(final ews delegate) {
        super(delegate.f, delegate.g);
        this.delegate = delegate;
        this.customSplitter = new evu((codepoint, style) -> DynamicFont.TEXT_RENDERER_PROVIDER.getRenderer().width(codepoint, (Style)style));
    }
    
    @NotNull
    public ezv a(@NotNull final ahg location) {
        return this.delegate.a(location);
    }
    
    @NotNull
    public String a(@NotNull final String text) {
        return this.delegate.a(text);
    }
    
    public int b(final String text, final float x, final float y, final int color, final boolean dropShadow, @NotNull final Matrix4f modelViewMatrix, @NotNull final fth bufferSource, @NotNull final ews.a displayMode, final int backgroundColor, final int packedLightCoords, final boolean bidirectionalShaping) {
        if (this.isForceVanillaFont()) {
            return this.delegate.b(text, x, y, color, dropShadow, modelViewMatrix, bufferSource, displayMode, backgroundColor, packedLightCoords, bidirectionalShaping);
        }
        DynamicFont.RENDER_ENVIRONMENT_CONTEXT.setPackedLight(packedLightCoords);
        final Stack stack = this.pushAndGet(modelViewMatrix);
        final TextBuffer buffer = DynamicFont.TEXT_RENDERER_PROVIDER.getRenderer().pos(x, y).color(color).shadow(dropShadow).text(text).drawMode(MinecraftUtil.fromMinecraft(displayMode)).backgroundColor(backgroundColor).render(stack);
        float width = 0.0f;
        if (buffer != null) {
            width = buffer.getWidth();
        }
        stack.pop();
        return MathHelper.ceil(width);
    }
    
    public int b(@NotNull final aua text, final float x, final float y, final int color, final boolean dropShadow, @NotNull final Matrix4f modelViewMatrix, @NotNull final fth bufferSource, @NotNull final ews.a displayMode, final int backgroundColor, final int packedLightCoords) {
        if (this.isForceVanillaFont()) {
            return this.delegate.b(text, x, y, color, dropShadow, modelViewMatrix, bufferSource, displayMode, backgroundColor, packedLightCoords);
        }
        DynamicFont.RENDER_ENVIRONMENT_CONTEXT.setPackedLight(packedLightCoords);
        final StringOutput output = new StringOutput();
        text.accept((aub)output);
        final Stack stack = this.pushAndGet(modelViewMatrix);
        final ComponentRenderMeta meta = DynamicFont.COMPONENT_RENDERER.builder().pos(x, y).text(output.getLabyComponent()).drawMode(MinecraftUtil.fromMinecraft(displayMode)).color(color).shadow(dropShadow).backgroundColor(backgroundColor).render(stack);
        float width = 0.0f;
        if (meta != null) {
            width = meta.getRenderedWidth();
        }
        stack.pop();
        return MathHelper.ceil(width);
    }
    
    public void a(@NotNull final aua text, final float x, final float y, final int color, final int outlineColor, @NotNull final Matrix4f modelViewMatrix, @NotNull final fth bufferSource, final int packedLightCoords) {
        if (this.isForceVanillaFont()) {
            this.delegate.a(text, x, y, color, outlineColor, modelViewMatrix, bufferSource, packedLightCoords);
            return;
        }
        DynamicFont.RENDER_ENVIRONMENT_CONTEXT.setPackedLight(packedLightCoords);
        final StringOutput output = new StringOutput();
        text.accept((aub)output);
        final Stack stack = this.pushAndGet(modelViewMatrix);
        final ComponentRendererBuilder builder = DynamicFont.COMPONENT_RENDERER.builder();
        final Component labyComponent = output.getLabyComponent();
        builder.pos(x, y).text(labyComponent).drawMode(TextDrawMode.NORMAL).color(outlineColor).fontWeight(1000.0f).shadow(false).render(stack);
        builder.pos(x, y).text(labyComponent).drawMode(MinecraftUtil.fromMinecraft(ews.a.c)).color(color).shadow(false).render(stack);
        stack.pop();
    }
    
    public int b(@NotNull final String text) {
        if (this.isForceVanillaFont()) {
            return this.delegate.b(text);
        }
        return MathHelper.ceil(DynamicFont.TEXT_RENDERER_PROVIDER.getRenderer().width(text));
    }
    
    public int a(@NotNull final vk text) {
        if (this.isForceVanillaFont()) {
            return this.delegate.a(text);
        }
        return MathHelper.ceil(DynamicFont.COMPONENT_RENDERER.width(DynamicFont.COMPONENT_MAPPER.fromMinecraftComponent(text)));
    }
    
    public int a(@NotNull final aua text) {
        if (this.isForceVanillaFont()) {
            return this.delegate.a(text);
        }
        final StringOutput stringOutput = new StringOutput();
        text.accept((aub)stringOutput);
        return this.a((vk)DynamicFont.COMPONENT_MAPPER.toMinecraftComponent(stringOutput.getLabyComponent()));
    }
    
    @NotNull
    public String a(@NotNull final String text, final int maxWidth, final boolean tail) {
        if (this.isForceVanillaFont()) {
            return this.delegate.a(text, maxWidth, tail);
        }
        if (tail) {
            return this.customSplitter.c(text, maxWidth, wc.a);
        }
        return this.customSplitter.b(text, maxWidth, wc.a);
    }
    
    @NotNull
    public String a(@NotNull final String text, final int maxWidth) {
        if (this.isForceVanillaFont()) {
            return this.delegate.a(text, maxWidth);
        }
        return this.customSplitter.b(text, maxWidth, wc.a);
    }
    
    @NotNull
    public vk a(@NotNull final vk text, final int maxWidth) {
        if (this.isForceVanillaFont()) {
            return this.delegate.a(text, maxWidth);
        }
        return this.customSplitter.a(text, maxWidth, wc.a);
    }
    
    public int b(@NotNull final String text, final int maxWidth) {
        if (this.isForceVanillaFont()) {
            return this.delegate.b(text, maxWidth);
        }
        final int lines = this.customSplitter.g(text, maxWidth, wc.a).size();
        return MathHelper.ceil(lines * DynamicFont.TEXT_RENDERER_PROVIDER.getRenderer().height());
    }
    
    public int b(@NotNull final vk text, final int maxWidth) {
        if (this.isForceVanillaFont()) {
            return this.delegate.b(text, maxWidth);
        }
        final int lines = this.customSplitter.b(text, maxWidth, wc.a).size();
        return MathHelper.ceil(lines * DynamicFont.TEXT_RENDERER_PROVIDER.getRenderer().height());
    }
    
    @NotNull
    public List<aua> c(@NotNull final vk text, final int maxWidth) {
        if (this.isForceVanillaFont()) {
            return this.delegate.c(text, maxWidth);
        }
        return si.a().a(this.customSplitter.b(text, maxWidth, wc.a));
    }
    
    public boolean a() {
        return this.delegate.a();
    }
    
    @NotNull
    public evu b() {
        return this.delegate.b();
    }
    
    private Stack pushAndGet(final Matrix4f modelViewMatrix) {
        final Stack stack = Stack.getDefaultEmptyStack();
        stack.push();
        stack.identity();
        stack.multiply(MathHelper.mapper().fromMatrix4f(modelViewMatrix));
        return stack;
    }
    
    private boolean isForceVanillaFont() {
        final RenderAttributesStack renderAttributesStack = Laby.references().renderEnvironmentContext().renderAttributesStack();
        final RenderAttributes attributes = renderAttributesStack.last();
        return attributes.isForceVanillaFont() || DynamicFont.TEXT_RENDERER_PROVIDER.isVanillaFontRenderer();
    }
    
    public float getHeight() {
        if (this.isForceVanillaFont()) {
            Objects.requireNonNull(this.delegate);
            return 9.0f;
        }
        return DynamicFont.TEXT_RENDERER_PROVIDER.getRenderer().height();
    }
    
    static {
        COMPONENT_MAPPER = Laby.references().componentMapper();
        COMPONENT_RENDERER = Laby.references().componentRenderer();
        TEXT_RENDERER_PROVIDER = Laby.references().textRendererProvider();
        RENDER_ENVIRONMENT_CONTEXT = Laby.references().renderEnvironmentContext();
    }
    
    static class StringOutput implements aub
    {
        private final StringBuilder textBuilder;
        private final TextComponent.Builder builder;
        private wc style;
        
        StringOutput() {
            this.textBuilder = new StringBuilder();
            this.builder = Component.text();
            this.style = wc.a;
        }
        
        public boolean accept(final int position, @NotNull final wc style, final int codepoint) {
            if (!style.equals((Object)this.style)) {
                this.builder.append(Component.text(this.textBuilder.toString(), (Style)this.style));
                this.textBuilder.setLength();
                this.style = style;
            }
            this.textBuilder.appendCodePoint(codepoint);
            return true;
        }
        
        public Component getLabyComponent() {
            if (!this.textBuilder.isEmpty()) {
                this.builder.append(Component.text(this.textBuilder.toString(), (Style)this.style));
                this.textBuilder.setLength();
            }
            return this.builder.build();
        }
    }
}
