// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme;

import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import java.util.Locale;
import net.labymod.api.Laby;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public interface Theme
{
    void onPreLoad();
    
    void onPostLoad();
    
    void onUnload();
    
    default ThemeFile createResource(final BasicThemeFile file) {
        return ThemeFile.create(this, file.getNamespace(), file.getPath());
    }
    
    boolean isResourcesLoaded();
    
     <T extends Widget> void registerWidgetRenderer(final ThemeRenderer<T> p0);
    
    void registerEventListener(final ThemeEventListener p0);
    
    void bindType(final MinecraftWidgetType p0, final String p1);
    
    Metadata metadata();
    
    @Deprecated
    default String getName() {
        return this.getId();
    }
    
    String getId();
    
    @ApiStatus.Internal
    int getInternalId();
    
    Class<? extends ThemeConfig> getConfigClass();
    
    @Nullable
    ThemeConfig getOrLoadThemeConfig();
    
    void saveThemeConfig();
    
    String getDisplayName();
    
    default String getNamespace() {
        return Laby.labyAPI().getNamespace(this);
    }
    
    default String getDirectoryPath() {
        return String.format(Locale.ROOT, "themes/%s/", this.getId());
    }
    
    @Nullable
     <T extends Widget> ThemeRenderer<T> getWidgetRendererByName(final String p0);
    
    @Nullable
    ThemeRenderer<?> getWidgetRendererByType(final MinecraftWidgetType p0);
    
    @NotNull
    ThemeFile file(final String p0, final String p1);
    
    default ResourceLocation resource(final String namespace, final String path) {
        return this.file(namespace, path).toResourceLocation();
    }
    
    @NotNull
    TextRenderer textRenderer();
    
    @Nullable
    default ThemeTextRendererProvider themeTextRendererProvider() {
        return null;
    }
    
    @Deprecated
    @Nullable
    BackgroundRenderer getBackgroundRenderer();
    
    @Deprecated
    @Nullable
    HoverBackgroundEffect getHoverBackgroundRenderer();
}
