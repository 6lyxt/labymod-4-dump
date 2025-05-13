// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme;

import java.io.Reader;
import net.labymod.api.client.resources.ResourceLocation;
import java.io.IOException;
import net.labymod.api.util.GsonUtil;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import net.labymod.core.client.gui.screen.theme.font.ThemeFont;
import net.labymod.api.Laby;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeTextRendererProvider;

public class DefaultThemeTextRendererProvider implements ThemeTextRendererProvider
{
    private final Theme theme;
    private final TextRenderer vanillaTextRenderer;
    private TextRenderer renderer;
    private boolean loaded;
    
    public DefaultThemeTextRendererProvider(final Theme theme) {
        this.theme = theme;
        this.vanillaTextRenderer = Laby.references().textRenderer("vanilla_text_renderer");
    }
    
    @Override
    public TextRenderer textRenderer() {
        if (!this.loaded) {
            return this.vanillaTextRenderer;
        }
        if (this.renderer == null) {
            return this.vanillaTextRenderer;
        }
        return this.renderer;
    }
    
    @Override
    public void load() {
        this.loaded = this.loadFonts(themeFont -> {
            final ThemeFont.Font defaultFont = themeFont.defaultFont();
            if (defaultFont != null) {
                (this.renderer = Laby.references().textRenderer(defaultFont.name() + "_text_renderer")).prepare(this.theme.getNamespace(), defaultFont.font());
            }
        });
    }
    
    @Override
    public void unload() {
        if (!this.loaded) {
            return;
        }
        if (this.renderer == null) {
            return;
        }
        this.renderer.unload();
    }
    
    public boolean loadFonts(final Consumer<ThemeFont> fontConsumer) {
        final ResourceLocation fontData = this.theme.resource(this.theme.getNamespace(), "font/fonts.json");
        ThemeFont themeFont;
        try (final Reader reader = new InputStreamReader(fontData.openStream())) {
            themeFont = (ThemeFont)GsonUtil.DEFAULT_GSON.fromJson(reader, (Class)ThemeFont.class);
        }
        catch (final IOException exception) {
            return false;
        }
        final boolean validThemeFont = themeFont != null;
        if (validThemeFont && fontConsumer != null) {
            fontConsumer.accept(themeFont);
        }
        return validThemeFont;
    }
    
    @Override
    public boolean isLoaded() {
        return this.loaded;
    }
}
