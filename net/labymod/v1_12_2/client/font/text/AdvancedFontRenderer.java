// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.v1_12_2.client.font.text;

import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.render.font.text.TextBuffer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.v1_12_2.client.render.matrix.VersionedStackProvider;
import net.labymod.api.client.gui.screen.theme.ThemeTextRendererProvider;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.Laby;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.render.font.text.TextRendererProvider;

public class AdvancedFontRenderer extends FloatingFontRenderer implements StyledFontRenderer
{
    private static final TextRendererProvider TEXT_RENDERER_PROVIDER;
    private Style style;
    
    public AdvancedFontRenderer(final bid options, final nf location, final cdr textureManager, final boolean useUnicode) {
        super(options, location, textureManager, useUnicode);
        this.style = Style.EMPTY;
    }
    
    @Override
    public float getCharWidthFloat(final char c) {
        if (this.isForceVanillaFont()) {
            int width = super.getOriginalCharWidth(c);
            if (this.style != null && this.style.hasDecoration(TextDecoration.BOLD)) {
                ++width;
            }
            return (float)width;
        }
        return AdvancedFontRenderer.TEXT_RENDERER_PROVIDER.getRenderer().width(c);
    }
    
    @Override
    public float getStringWidthFloat(final String text) {
        if (text == null) {
            return 0.0f;
        }
        if (this.isForceVanillaFont()) {
            final String decorationCodes = AdventureLegacyStyleConverter.getDecorationCodes(this.style);
            this.style(null);
            return (float)super.getOriginalStringWidth(decorationCodes + text);
        }
        return AdvancedFontRenderer.TEXT_RENDERER_PROVIDER.getRenderer().width(text);
    }
    
    public void a(final cep lvt_1_1_) {
        super.a(lvt_1_1_);
        final Theme currentTheme = Laby.references().themeService().currentTheme();
        if (currentTheme == null) {
            return;
        }
        final ThemeTextRendererProvider provider = currentTheme.themeTextRendererProvider();
        if (provider == null) {
            return;
        }
        provider.reload();
    }
    
    public int a(final String text, final float x, final float y, final int color, final boolean shadow) {
        if (text == null) {
            return 0;
        }
        bus.e();
        if (this.isForceVanillaFont()) {
            final String decorationCodes = AdventureLegacyStyleConverter.getDecorationCodes(this.style);
            final int position = super.a(decorationCodes + text, x, y, color, shadow);
            this.style(Style.EMPTY);
            return position;
        }
        final Stack defaultStack = VersionedStackProvider.DEFAULT_STACK;
        final TextBuffer buffer = AdvancedFontRenderer.TEXT_RENDERER_PROVIDER.getRenderer().text(text).pos(x, y).color(color).shadow(shadow).render(defaultStack);
        float width = 0.0f;
        if (buffer != null) {
            width = buffer.getWidth();
        }
        return MathHelper.ceil(width);
    }
    
    @Override
    public void style(final Style style) {
        this.style = style;
    }
    
    private boolean isForceVanillaFont() {
        final RenderAttributesStack renderAttributesStack = Laby.references().renderEnvironmentContext().renderAttributesStack();
        final RenderAttributes attributes = renderAttributesStack.last();
        return attributes.isForceVanillaFont() || AdvancedFontRenderer.TEXT_RENDERER_PROVIDER.isVanillaFontRenderer();
    }
    
    static {
        TEXT_RENDERER_PROVIDER = Laby.references().textRendererProvider();
    }
}
