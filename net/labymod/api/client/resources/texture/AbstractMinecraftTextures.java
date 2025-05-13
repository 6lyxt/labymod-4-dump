// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.resources.texture;

import net.labymod.api.client.gui.screen.theme.config.VanillaThemeConfigAccessor;
import net.labymod.api.Laby;
import net.labymod.api.loader.platform.PlatformEnvironment;
import java.util.Locale;
import net.labymod.api.client.resources.ResourceLocation;

public abstract class AbstractMinecraftTextures implements MinecraftTextures
{
    protected static final String TEXTURES = "textures/";
    protected static final String GUI = "textures/gui/";
    protected static final String GUI_TITLE = "textures/gui/title/";
    protected static final String GUI_TITLE_BACKGROUND = "textures/gui/title/background/";
    private final ResourceLocation BUTTON_TEXTURE;
    private final ResourceLocation ICONS_TEXTURE;
    private final ResourceLocation SERVER_SELECTION_TEXTURE;
    private final ResourceLocation BARS_TEXTURE;
    private final ResourceLocation MINECRAFT_LOGO;
    private final ResourceLocation MINECRAFT_EDITION;
    private final ResourceLocation SPLASH;
    private final ResourceLocation[] PANORAMA;
    private final ResourceLocation PANORAMA_OVERLAY;
    private final ResourceLocation SCREEN_LEGACY_BACKGROUND_TEXTURE;
    private final ResourceLocation SCREEN_LIST_BACKGROUND_TEXTURE;
    private final ResourceLocation SCREEN_MENU_BACKGROUND_TEXTURE;
    private final ResourceLocation INWORLD_MENU_BACKGROUND_TEXTURE;
    private final ResourceLocation INWORLD_MENU_LIST_BACKGROUND_TEXTURE;
    private final ResourceLocation SCREEN_MENU_HEADER_SEPARATOR_TEXTURE;
    private final ResourceLocation SCREEN_MENU_FOOTER_SEPARATOR_TEXTURE;
    private final ResourceLocation INWORLD_MENU_HEADER_SEPARATOR_TEXTURE;
    private final ResourceLocation INWORLD_MENU_FOOTER_SEPARATOR_TEXTURE;
    
    public AbstractMinecraftTextures() {
        this.BUTTON_TEXTURE = resource("textures/gui/", "widgets.png");
        this.ICONS_TEXTURE = resource("textures/gui/", "icons.png");
        this.SERVER_SELECTION_TEXTURE = resource("textures/gui/", "server_selection.png");
        this.BARS_TEXTURE = resource("textures/gui/", "bars.png");
        this.MINECRAFT_LOGO = resource("textures/gui/title/", "minecraft.png");
        this.MINECRAFT_EDITION = resource("textures/gui/title/", "edition.png");
        this.SPLASH = resource("textures/gui/title/", "mojangstudios.png");
        this.PANORAMA = new ResourceLocation[6];
        this.PANORAMA_OVERLAY = resource("textures/gui/title/background/", "panorama_overlay.png");
        this.SCREEN_LEGACY_BACKGROUND_TEXTURE = resource("textures/gui/", "options_background.png");
        this.SCREEN_LIST_BACKGROUND_TEXTURE = resource("textures/gui/", "menu_list_background.png");
        this.SCREEN_MENU_BACKGROUND_TEXTURE = resource("textures/gui/", "menu_background.png");
        this.INWORLD_MENU_BACKGROUND_TEXTURE = resource("textures/gui/", "inworld_menu_background.png");
        this.INWORLD_MENU_LIST_BACKGROUND_TEXTURE = resource("textures/gui/", "inworld_menu_list_background.png");
        this.SCREEN_MENU_HEADER_SEPARATOR_TEXTURE = resource("textures/gui/", "header_separator.png");
        this.SCREEN_MENU_FOOTER_SEPARATOR_TEXTURE = resource("textures/gui/", "footer_separator.png");
        this.INWORLD_MENU_HEADER_SEPARATOR_TEXTURE = resource("textures/gui/", "inworld_header_separator.png");
        this.INWORLD_MENU_FOOTER_SEPARATOR_TEXTURE = resource("textures/gui/", "inworld_footer_separator.png");
        for (int i = 0; i < this.PANORAMA.length; ++i) {
            this.PANORAMA[i] = resource("textures/gui/title/background/", String.format(Locale.ROOT, "panorama_%s.png", i));
        }
    }
    
    @Override
    public ResourceLocation widgetsTexture() {
        return this.BUTTON_TEXTURE;
    }
    
    @Override
    public ResourceLocation screenListBackgroundTexture() {
        return PlatformEnvironment.isFreshUI() ? ((this.isInWorld() && this.isFreshUIEnabled()) ? this.INWORLD_MENU_LIST_BACKGROUND_TEXTURE : this.SCREEN_LIST_BACKGROUND_TEXTURE) : this.SCREEN_LEGACY_BACKGROUND_TEXTURE;
    }
    
    @Override
    public ResourceLocation screenMenuBackgroundTexture() {
        return PlatformEnvironment.isFreshUI() ? ((this.isInWorld() && this.isFreshUIEnabled()) ? this.INWORLD_MENU_BACKGROUND_TEXTURE : this.SCREEN_MENU_BACKGROUND_TEXTURE) : this.SCREEN_LEGACY_BACKGROUND_TEXTURE;
    }
    
    @Override
    public ResourceLocation screenMenuHeaderSeparatorTexture() {
        return this.isInWorld() ? this.INWORLD_MENU_HEADER_SEPARATOR_TEXTURE : this.SCREEN_MENU_HEADER_SEPARATOR_TEXTURE;
    }
    
    @Override
    public ResourceLocation screenMenuFooterSeparatorTexture() {
        return this.isInWorld() ? this.INWORLD_MENU_FOOTER_SEPARATOR_TEXTURE : this.SCREEN_MENU_FOOTER_SEPARATOR_TEXTURE;
    }
    
    @Override
    public ResourceLocation iconsTexture() {
        return this.ICONS_TEXTURE;
    }
    
    @Override
    public ResourceLocation serverSelectionTexture() {
        return this.SERVER_SELECTION_TEXTURE;
    }
    
    @Override
    public ResourceLocation barsTexture() {
        return this.BARS_TEXTURE;
    }
    
    @Override
    public ResourceLocation minecraftLogoTexture() {
        return this.MINECRAFT_LOGO;
    }
    
    @Override
    public ResourceLocation minecraftEditionTexture() {
        return this.MINECRAFT_EDITION;
    }
    
    @Override
    public ResourceLocation splashTexture() {
        return this.SPLASH;
    }
    
    @Override
    public ResourceLocation[] panoramaTextures() {
        return this.PANORAMA;
    }
    
    @Override
    public ResourceLocation panoramaOverlayTexture() {
        return this.PANORAMA_OVERLAY;
    }
    
    public static ResourceLocation resource(final String parent, final String path) {
        return resource(parent + path);
    }
    
    public static ResourceLocation resource(final String path) {
        return Laby.labyAPI().renderPipeline().resources().resourceLocationFactory().createMinecraft(path);
    }
    
    private boolean isInWorld() {
        return Laby.labyAPI().minecraft().isIngame();
    }
    
    private boolean isFreshUIEnabled() {
        final VanillaThemeConfigAccessor config = Laby.labyAPI().themeService().getThemeConfig(VanillaThemeConfigAccessor.class);
        return config != null && config.freshUI().get();
    }
}
