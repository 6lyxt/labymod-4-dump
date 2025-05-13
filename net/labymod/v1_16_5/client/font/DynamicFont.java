// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_16_5.client.font;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.Style;
import java.util.Objects;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.Laby;
import java.util.List;
import net.labymod.api.client.render.font.ComponentRenderMeta;
import net.labymod.api.client.render.font.text.TextBuffer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.render.font.text.TextDrawMode;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.font.text.TextRendererProvider;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.font.FontHeightAccessor;

public class DynamicFont extends dku implements FontHeightAccessor
{
    private static final ComponentMapper COMPONENT_MAPPER;
    private static final ComponentRenderer COMPONENT_RENDERER;
    private static final TextRendererProvider TEXT_RENDERER_PROVIDER;
    private static final RenderEnvironmentContext RENDER_ENVIRONMENT_CONTEXT;
    private final dku delegate;
    private final dkj customSplitter;
    
    public DynamicFont(final dku delegate) {
        super(delegate.d);
        this.delegate = delegate;
        this.customSplitter = new dkj((codepoint, style) -> DynamicFont.TEXT_RENDERER_PROVIDER.getRenderer().width(codepoint, (Style)style));
    }
    
    @NotNull
    public dmw a(@NotNull final vk location) {
        return this.delegate.a(location);
    }
    
    @NotNull
    public String a(@NotNull final String text) {
        return this.delegate.a(text);
    }
    
    public int b(final String text, final float x, final float y, final int color, final boolean dropShadow, @NotNull final b modelViewMatrix, @NotNull final eag bufferSource, final boolean seeThrough, final int backgroundColor, final int packedLightCoords, final boolean bidirectionalShaping) {
        if (this.isForceVanillaFont()) {
            return this.delegate.b(text, x, y, color, dropShadow, modelViewMatrix, bufferSource, seeThrough, backgroundColor, packedLightCoords, bidirectionalShaping);
        }
        DynamicFont.RENDER_ENVIRONMENT_CONTEXT.setPackedLight(packedLightCoords);
        final Stack stack = this.pushAndGet(modelViewMatrix);
        final TextBuffer buffer = DynamicFont.TEXT_RENDERER_PROVIDER.getRenderer().pos(x, y).color(color).shadow(dropShadow).text(text).drawMode(seeThrough ? TextDrawMode.SEE_THROUGH : TextDrawMode.NORMAL).backgroundColor(backgroundColor).render(stack);
        float width = 0.0f;
        if (buffer != null) {
            width = buffer.getWidth();
        }
        stack.pop();
        return MathHelper.ceil(width);
    }
    
    public int b(@NotNull final afa text, final float x, final float y, final int color, final boolean dropShadow, @NotNull final b modelViewMatrix, @NotNull final eag bufferSource, final boolean seeThrough, final int backgroundColor, final int packedLightCoords) {
        if (this.isForceVanillaFont()) {
            return this.delegate.b(text, x, y, color, dropShadow, modelViewMatrix, bufferSource, seeThrough, backgroundColor, packedLightCoords);
        }
        DynamicFont.RENDER_ENVIRONMENT_CONTEXT.setPackedLight(packedLightCoords);
        final StringOutput output = new StringOutput();
        text.accept((afb)output);
        final Stack stack = this.pushAndGet(modelViewMatrix);
        final ComponentRenderMeta meta = DynamicFont.COMPONENT_RENDERER.builder().pos(x, y).text(output.getLabyComponent()).drawMode(seeThrough ? TextDrawMode.SEE_THROUGH : TextDrawMode.NORMAL).color(color).shadow(dropShadow).backgroundColor(backgroundColor).render(stack);
        float width = 0.0f;
        if (meta != null) {
            width = meta.getRenderedWidth();
        }
        stack.pop();
        return MathHelper.ceil(width);
    }
    
    public int b(@NotNull final String text) {
        if (this.isForceVanillaFont()) {
            return this.delegate.b(text);
        }
        return MathHelper.ceil(DynamicFont.TEXT_RENDERER_PROVIDER.getRenderer().width(text));
    }
    
    public int a(@NotNull final nu text) {
        if (this.isForceVanillaFont()) {
            return this.delegate.a(text);
        }
        return MathHelper.ceil(DynamicFont.COMPONENT_RENDERER.width(DynamicFont.COMPONENT_MAPPER.fromMinecraftComponent(text)));
    }
    
    public int a(@NotNull final afa text) {
        if (this.isForceVanillaFont()) {
            return this.delegate.a(text);
        }
        final StringOutput stringOutput = new StringOutput();
        text.accept((afb)stringOutput);
        return this.a((nu)DynamicFont.COMPONENT_MAPPER.toMinecraftComponent(stringOutput.getLabyComponent()));
    }
    
    @NotNull
    public String a(@NotNull final String text, final int maxWidth, final boolean tail) {
        if (this.isForceVanillaFont()) {
            return this.delegate.a(text, maxWidth, tail);
        }
        if (tail) {
            return this.customSplitter.c(text, maxWidth, ob.a);
        }
        return this.customSplitter.b(text, maxWidth, ob.a);
    }
    
    @NotNull
    public String a(@NotNull final String text, final int maxWidth) {
        if (this.isForceVanillaFont()) {
            return this.delegate.a(text, maxWidth);
        }
        return this.customSplitter.b(text, maxWidth, ob.a);
    }
    
    @NotNull
    public nu a(@NotNull final nu text, final int maxWidth) {
        if (this.isForceVanillaFont()) {
            return this.delegate.a(text, maxWidth);
        }
        return this.customSplitter.a(text, maxWidth, ob.a);
    }
    
    public int b(@NotNull final String text, final int maxWidth) {
        if (this.isForceVanillaFont()) {
            return this.delegate.b(text, maxWidth);
        }
        final int lines = this.customSplitter.g(text, maxWidth, ob.a).size();
        return MathHelper.ceil(lines * DynamicFont.TEXT_RENDERER_PROVIDER.getRenderer().height());
    }
    
    @NotNull
    public List<afa> b(@NotNull final nu text, final int maxWidth) {
        if (this.isForceVanillaFont()) {
            return this.delegate.b(text, maxWidth);
        }
        return ly.a().a(this.customSplitter.b(text, maxWidth, ob.a));
    }
    
    public boolean a() {
        return this.delegate.a();
    }
    
    @NotNull
    public dkj b() {
        return this.delegate.b();
    }
    
    private Stack pushAndGet(final b modelViewMatrix) {
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
    
    static class StringOutput implements afb
    {
        private final StringBuilder textBuilder;
        private final TextComponent.Builder builder;
        private ob style;
        
        StringOutput() {
            this.textBuilder = new StringBuilder();
            this.builder = Component.text();
            this.style = ob.a;
        }
        
        public boolean accept(final int position, @NotNull final ob style, final int codepoint) {
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
