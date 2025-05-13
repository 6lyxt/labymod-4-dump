// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.font.text.vanilla;

import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.util.color.format.ColorFormat;
import java.util.Iterator;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.core.client.render.font.text.DefaultTextBuffer;
import net.labymod.api.util.ColorUtil;
import net.labymod.api.Laby;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.render.font.text.TextBuffer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.v1_12_2.client.font.text.StyledFontRenderer;
import net.labymod.api.client.render.font.VisualCharSequence;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.Style;
import javax.inject.Inject;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.core.client.render.font.text.AbstractTextRenderer;

@Singleton
@Implements(value = TextRenderer.class, key = "vanilla_text_renderer")
public class VanillaTextRenderer extends AbstractTextRenderer
{
    private bip fontRenderer;
    
    @Inject
    public VanillaTextRenderer() {
        this.fontRenderer = this.getFontRenderer();
    }
    
    @Override
    public float width(final int codepoint, final Style style) {
        final bip fontRenderer = this.getFontRenderer();
        this.applyStyle(fontRenderer, style);
        return (float)fontRenderer.a((char)codepoint);
    }
    
    @Override
    public float width(final String text, final Style style) {
        final float superWidth = super.width(text, style);
        if (superWidth != -1.0f) {
            return superWidth;
        }
        final bip fontRenderer = this.getFontRenderer();
        final boolean noStyle = style == null || style.isEmpty();
        if (this.disableWidthCaching) {
            if (!noStyle) {
                this.applyStyle(fontRenderer, style);
            }
            return this.getWidth(fontRenderer, text);
        }
        float width;
        float boldWidth;
        if (noStyle) {
            width = this.getWidth(fontRenderer, text);
            this.applyStyle(fontRenderer, VanillaTextRenderer.BOLD_STYLE);
            boldWidth = this.getWidth(fontRenderer, text);
        }
        else {
            final boolean isBold = style.hasDecoration(TextDecoration.BOLD);
            if (isBold) {
                boldWidth = this.getWidth(fontRenderer, text);
                this.applyStyle(fontRenderer, style.decoration(TextDecoration.BOLD, TextDecoration.State.NOT_SET));
                width = this.getWidth(fontRenderer, text);
            }
            else {
                width = this.getWidth(fontRenderer, text);
                this.applyStyle(fontRenderer, style.decoration(TextDecoration.BOLD, TextDecoration.State.TRUE));
                boldWidth = this.getWidth(fontRenderer, text);
            }
        }
        this.stringWidthPool.addStringWidth(text.hashCode(), text, width, boldWidth);
        return noStyle ? width : (style.hasDecoration(TextDecoration.BOLD) ? boldWidth : width);
    }
    
    @Override
    public float width(final VisualCharSequence text) {
        final float[] width = { 0.0f };
        final bip fontRenderer = this.getFontRenderer();
        text.accept((position, style, codePoint) -> {
            this.applyStyle(fontRenderer, style);
            final int n3;
            width[n3] += this.getWidth(fontRenderer, Character.toString(codePoint));
            return true;
        });
        return width[0];
    }
    
    private void applyStyle(final bip fontRenderer, final Style style) {
        if (!(fontRenderer instanceof StyledFontRenderer)) {
            return;
        }
        ((StyledFontRenderer)fontRenderer).style(style);
    }
    
    private float getWidth(final bip fontRenderer, final String text) {
        return fontRenderer.a(text) * this.scale;
    }
    
    @Override
    public float height() {
        return this.getFontRenderer().a * this.scale;
    }
    
    @Nullable
    @Override
    public TextBuffer render(final Stack stack) {
        final bip fontRenderer = this.getFontRenderer();
        this.applyStyle(fontRenderer, this.style);
        stack.push();
        stack.scale(this.scale);
        RenderableComponent component = this.component;
        if (component == null) {
            component = RenderableComponent.of(Component.text(this.text, this.style), false);
        }
        if (this.centered) {
            this.x -= component.getWidth() / 2.0f;
        }
        final float x = (this.scale == 0.0f) ? this.scale : (this.x / this.scale);
        final float y = (this.scale == 0.0f) ? this.scale : (this.y / this.scale);
        final int color = ColorUtil.combineAlpha(this.color, Laby.labyAPI().renderPipeline().getAlpha());
        float stringWidth = 0.0f;
        if (this.isAlphaAboveThreshold(color)) {
            final GFXBridge gfx = Laby.labyAPI().gfxRenderPipeline().gfx();
            gfx.storeBlaze3DStates();
            bus.m();
            stringWidth = this.renderComponent(fontRenderer, component, x, y, color);
            gfx.restoreBlaze3DStates();
        }
        stack.pop();
        if (!this.useFloatingPointPosition) {
            stringWidth = (float)(int)Math.ceil(stringWidth);
        }
        this.dispose();
        this.applyStyle(fontRenderer, Style.EMPTY);
        return new DefaultTextBuffer(stringWidth);
    }
    
    private float renderComponent(final bip fontRenderer, final RenderableComponent component, final float x, final float y, final int color) {
        final Style style = component.style();
        this.applyStyle(fontRenderer, style);
        int styledColor = this.getColor(style, color);
        float width = fontRenderer.a(component.getText(), MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, x + component.getXOffset()), MathHelper.applyFloatingPointPosition(this.useFloatingPointPosition, y + component.getYOffset() + component.getInnerY()), styledColor, this.shadow) * this.scale;
        for (final RenderableComponent child : component.getChildren()) {
            styledColor = this.getColor(child.style(), color);
            final float childWidth = this.renderComponent(fontRenderer, child, x, y, styledColor);
            if (childWidth > width) {
                width = childWidth;
            }
        }
        return width;
    }
    
    private int getColor(@Nullable final Style style, final int color) {
        if (style == null) {
            return color;
        }
        final TextColor styleColor = style.getColor();
        if (styleColor == null) {
            return color;
        }
        int newColor = styleColor.getValue();
        final ColorFormat colorFormat = ColorFormat.ARGB32;
        final float alpha = colorFormat.normalizedAlpha(newColor);
        if (alpha <= 0.0f) {
            newColor |= 0xFF000000;
        }
        return ColorUtil.combineAlpha(newColor, colorFormat.normalizedAlpha(color));
    }
    
    @Override
    public boolean isVanilla() {
        return true;
    }
    
    private bip getFontRenderer() {
        if (this.fontRenderer == null) {
            this.fontRenderer = bib.z().k;
        }
        return this.fontRenderer;
    }
}
