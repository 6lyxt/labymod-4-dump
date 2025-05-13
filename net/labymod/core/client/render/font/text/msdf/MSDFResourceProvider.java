// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text.msdf;

import java.util.Locale;
import net.labymod.api.client.resources.texture.Texture;
import java.io.Reader;
import java.io.IOException;
import net.labymod.api.util.GsonUtil;
import java.io.InputStreamReader;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.theme.Theme;
import javax.inject.Inject;
import net.labymod.core.client.render.font.text.msdf.info.FontInfo;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.resources.texture.GameImageTexture;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.reference.annotation.Referenceable;
import javax.inject.Singleton;

@Singleton
@Referenceable
public class MSDFResourceProvider
{
    private static final Logging LOGGER;
    private static final String DEFAULT_PATH_FORMAT = "font/%s.%s";
    private final GameImageTexture.Factory gameImageTextureFactory;
    private final ThemeService themeService;
    private final TextureRepository textureRepository;
    private ResourceLocation fontInfoLocation;
    private ResourceLocation fontTextureLocation;
    private FontInfo fontInfo;
    private boolean loaded;
    
    @Inject
    public MSDFResourceProvider(final GameImageTexture.Factory gameImageTextureFactory, final ThemeService themeService, final TextureRepository textureRepository) {
        this.loaded = false;
        this.gameImageTextureFactory = gameImageTextureFactory;
        this.themeService = themeService;
        this.textureRepository = textureRepository;
    }
    
    public void load(final String fontName) {
        if (this.loaded) {
            return;
        }
        this.loaded = true;
        final Theme theme = this.themeService.currentTheme();
        this.fontInfoLocation = theme.resource(theme.getNamespace(), this.fontPath(fontName, true));
        this.fontTextureLocation = theme.resource(theme.getNamespace(), this.fontPath(fontName, false));
        if (this.readFontInfo()) {
            this.registerTexture();
        }
    }
    
    public void unload() {
        this.loaded = false;
        this.textureRepository.releaseTexture(this.fontTextureLocation);
    }
    
    @Nullable
    public FontInfo getFontInfo() {
        return this.fontInfo;
    }
    
    public ResourceLocation fontTextureLocation() {
        return this.fontTextureLocation;
    }
    
    private boolean readFontInfo() {
        try (final Reader reader = new InputStreamReader(this.fontInfoLocation.openStream())) {
            this.fontInfo = (FontInfo)GsonUtil.DEFAULT_GSON.fromJson(reader, (Class)FontInfo.class);
            if (this.fontInfo == null) {
                MSDFResourceProvider.LOGGER.error("Could not read font info ({})", this.fontInfoLocation);
                final boolean b = false;
                reader.close();
                return b;
            }
            return true;
        }
        catch (final IOException exception) {
            MSDFResourceProvider.LOGGER.error("Could not read font info ({})", this.fontInfoLocation, exception);
            return false;
        }
    }
    
    private void registerTexture() {
        this.textureRepository.register(this.fontTextureLocation, this.gameImageTextureFactory.create(this.fontTextureLocation));
    }
    
    private String fontPath(final String name, final boolean info) {
        return String.format(Locale.ROOT, "font/%s.%s", name, info ? "json" : "png");
    }
    
    static {
        LOGGER = Logging.create(MSDFResourceProvider.class);
    }
}
