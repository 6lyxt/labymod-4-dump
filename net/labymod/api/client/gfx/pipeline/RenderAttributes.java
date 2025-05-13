// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gfx.pipeline;

import net.labymod.api.Laby;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.render.font.text.TextRendererProvider;
import net.labymod.api.util.Lazy;

public final class RenderAttributes
{
    private static final Lazy<TextRendererProvider> TEXT_RENDERER_PROVIDER;
    private boolean forceVanillaFont;
    private float fontWeight;
    
    public RenderAttributes() {
    }
    
    public RenderAttributes(final RenderAttributes other) {
        this.forceVanillaFont = other.forceVanillaFont;
        this.fontWeight = other.fontWeight;
    }
    
    public boolean isForceVanillaFont() {
        return this.forceVanillaFont;
    }
    
    public void setForceVanillaFont(final boolean forceVanillaFont) {
        this.forceVanillaFont = forceVanillaFont;
    }
    
    public float getFontWeight() {
        return this.fontWeight;
    }
    
    public void setFontWeight(final float fontWeight) {
        this.fontWeight = fontWeight;
    }
    
    public void apply() {
        final TextRendererProvider provider = RenderAttributes.TEXT_RENDERER_PROVIDER.get();
        provider.forceMinecraftRenderer(this.forceVanillaFont);
        final TextRenderer textRenderer = provider.getRenderer();
        textRenderer.fontWeight(this.fontWeight);
    }
    
    static {
        TEXT_RENDERER_PROVIDER = Lazy.of(() -> Laby.references().textRendererProvider());
    }
}
