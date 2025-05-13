// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_21_1.client.font.text.vanilla;

import org.joml.Vector3fc;
import org.joml.Matrix4fc;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.labymod.v1_21_1.client.font.DynamicStringRenderOutput;
import net.labymod.api.util.math.MathHelper;
import org.joml.Matrix4f;
import net.labymod.api.client.render.font.text.FormattedRenderableComponent;
import net.labymod.api.client.render.font.text.RenderableComponentFormatter;
import org.jetbrains.annotations.Nullable;
import net.labymod.core.client.render.font.text.DefaultTextBuffer;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.Laby;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.text.TextBuffer;
import net.labymod.api.client.render.matrix.Stack;
import java.util.Objects;
import net.labymod.api.client.render.font.VisualCharSequence;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.v1_21_1.client.util.MinecraftUtil;
import net.labymod.v1_21_1.client.StringSplitterAccessor;
import net.labymod.api.client.component.format.Style;
import javax.inject.Inject;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.render.font.text.AbstractTextRenderer;

@Singleton
@Implements(value = TextRenderer.class, key = "vanilla_text_renderer")
public class VanillaTextRenderer extends AbstractTextRenderer
{
    private static final RenderEnvironmentContext RENDER_ENVIRONMENT_CONTEXT;
    private boolean bufferSourceAdded;
    
    @Inject
    public VanillaTextRenderer() {
    }
    
    @Override
    public float width(final int codepoint, final Style style) {
        final fgz.f provider = ((StringSplitterAccessor)this.font().b()).getWidthProvider();
        if (style == null || style.isEmpty()) {
            return provider.getWidth(codepoint, xw.a);
        }
        return provider.getWidth(codepoint, (xw)style);
    }
    
    @Override
    public float width(final String text, final Style style) {
        final float superWidth = super.width(text, style);
        if (superWidth != -1.0f) {
            return superWidth;
        }
        final boolean noStyle = style == null || style.isEmpty();
        final fhx font = this.font();
        if (this.disableWidthCaching) {
            return noStyle ? ((float)font.b(text)) : ((float)font.a(MinecraftUtil.fromText(text, style)));
        }
        float width;
        float boldWidth;
        if (noStyle) {
            width = (float)font.b(text);
            boldWidth = (float)font.a(MinecraftUtil.fromText(text, VanillaTextRenderer.BOLD_STYLE));
        }
        else {
            final boolean isBold = style.hasDecoration(TextDecoration.BOLD);
            if (isBold) {
                boldWidth = (float)font.a(MinecraftUtil.fromText(text, style));
                width = (float)font.a(MinecraftUtil.fromText(text, style.unsetDecoration(TextDecoration.BOLD)));
            }
            else {
                width = (float)font.a(MinecraftUtil.fromText(text, style));
                boldWidth = (float)font.a(MinecraftUtil.fromText(text, style.decorate(TextDecoration.BOLD)));
            }
        }
        this.stringWidthPool.addStringWidth(text.hashCode(), text, width, boldWidth);
        return noStyle ? width : (style.hasDecoration(TextDecoration.BOLD) ? boldWidth : width);
    }
    
    @Override
    public float width(final VisualCharSequence text) {
        return (float)this.font().a((aya)text.castTo(aya.class));
    }
    
    @Override
    public float height() {
        Objects.requireNonNull(this.font());
        return 9.0f * this.scale;
    }
    
    @Nullable
    @Override
    public TextBuffer render(final Stack stack) {
        RenderableComponent component = this.component;
        if (component == null) {
            component = RenderableComponent.of(Component.text(this.text, this.style), false);
        }
        stack.push();
        stack.scale(this.scale);
        float x = (this.scale == 0.0f) ? this.scale : (this.x / this.scale);
        final float y = (this.scale == 0.0f) ? this.scale : (this.y / this.scale);
        if (this.centered) {
            x -= component.getWidth() / 2.0f;
        }
        final int color = ColorUtil.combineAlpha(this.color, Laby.labyAPI().renderPipeline().getAlpha());
        float stringWidth;
        if (this.isAlphaAboveThreshold(color)) {
            final boolean hasMultiBufferSource = stack.getMultiBufferSource() != null;
            Object multiBufferSource = stack.getMultiBufferSource();
            if (!hasMultiBufferSource) {
                multiBufferSource = fgo.Q().aO().c();
            }
            stringWidth = this.renderComponent(stack, (gez)multiBufferSource, component, x, y, color);
            if (!hasMultiBufferSource) {
                ((gez.a)multiBufferSource).b();
            }
        }
        else {
            stringWidth = this.getWidth(component) * this.scale;
            if (!this.useFloatingPointPosition) {
                stringWidth = (float)(int)Math.ceil(stringWidth);
            }
        }
        stack.pop();
        this.dispose();
        return new DefaultTextBuffer(stringWidth);
    }
    
    @Nullable
    @Override
    public TextBuffer renderBatch(final Stack stack) {
        return this.render(stack);
    }
    
    @Override
    public void beginBatch(final Stack stack) {
        if (stack.getMultiBufferSource() == null) {
            stack.multiBufferSource(fgo.Q().aO().c());
            this.bufferSourceAdded = true;
        }
    }
    
    @Override
    public void endBatch(final Stack stack) {
        if (this.bufferSourceAdded) {
            this.bufferSourceAdded = false;
            final Object multiBufferSource = stack.getMultiBufferSource();
            if (multiBufferSource instanceof final gez.a bufferSource) {
                bufferSource.b();
            }
            stack.multiBufferSource(null);
        }
    }
    
    @Override
    public boolean isVanilla() {
        return true;
    }
    
    private fhx font() {
        return fgo.Q().h;
    }
    
    private float renderComponent(final Stack stack, final gez bufferSource, final RenderableComponent component, final float x, final float y, final int color) {
        final List<FormattedRenderableComponent> formattedRenderableComponents = RenderableComponentFormatter.format(component);
        float finalWidth = 0.0f;
        final fhx font = this.font();
        for (final FormattedRenderableComponent formattedRenderableComponent : formattedRenderableComponents) {
            final DynamicStringRenderOutput renderOutput = new DynamicStringRenderOutput(font, bufferSource, x, y, color, this.shadow, MathHelper.mapper().toMatrix4f(stack.getProvider().getPosition()), MinecraftUtil.toMinecraft(this.textDrawMode), VanillaTextRenderer.RENDER_ENVIRONMENT_CONTEXT.getPackedLightWithCondition());
            this.renderFormattedComponent(MathHelper.mapper().toMatrix4f(stack.getProvider().getPosition()), renderOutput, formattedRenderableComponent, x, y, color);
            final float renderedWidth = renderOutput.a(this.backgroundColor, x);
            if (renderedWidth > finalWidth) {
                finalWidth = renderedWidth;
            }
        }
        return finalWidth;
    }
    
    public float getWidth(final RenderableComponent component) {
        final List<FormattedRenderableComponent> formattedRenderableComponents = RenderableComponentFormatter.format(component);
        float finalWidth = 0.0f;
        for (final FormattedRenderableComponent formattedRenderableComponent : formattedRenderableComponents) {
            final aya formattedText = this.composite(formattedRenderableComponent);
            final float width = this.font().b().a(formattedText);
            if (width > finalWidth) {
                finalWidth = width;
            }
        }
        return finalWidth;
    }
    
    @NotNull
    private aya composite(final FormattedRenderableComponent component) {
        final List<RenderableComponent> components = component.components();
        final List<aya> sequences = new ArrayList<aya>(components.size());
        for (final RenderableComponent child : components) {
            sequences.add(MinecraftUtil.fromText(child.getText(), child.style()));
        }
        return aya.composite((List)sequences);
    }
    
    private void renderFormattedComponent(final Matrix4f modelViewMatrix, final DynamicStringRenderOutput output, final FormattedRenderableComponent component, final float x, final float y, final int color) {
        for (final RenderableComponent child : component.components()) {
            final aya sequence = MinecraftUtil.fromText(child.getText(), child.style());
            final float textX = MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, x + child.getXOffset());
            final float textY = MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, y + child.getYOffset() + child.getInnerY());
            output.setPosition(textX, textY);
            this.renderText(output, sequence, textX, textY, color, this.shadow, modelViewMatrix);
        }
    }
    
    private void renderText(final DynamicStringRenderOutput output, final aya sequence, final float x, final float y, final int color, final boolean dropShadow, final Matrix4f modelViewMatrix) {
        final Matrix4f copiedModelViewMatrix = new Matrix4f((Matrix4fc)modelViewMatrix);
        if (dropShadow) {
            output.updateModelViewMatrix(modelViewMatrix);
            output.updateColor(color, true);
            sequence.accept((ayb)output);
            copiedModelViewMatrix.translate((Vector3fc)fhx.e);
        }
        output.setPosition(x, y);
        output.updateModelViewMatrix(copiedModelViewMatrix);
        output.updateColor(color, false);
        sequence.accept((ayb)output);
    }
    
    static {
        RENDER_ENVIRONMENT_CONTEXT = Laby.references().renderEnvironmentContext();
    }
}
