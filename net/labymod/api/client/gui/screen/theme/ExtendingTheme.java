// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.theme;

import net.labymod.api.client.gui.screen.theme.renderer.background.BackgroundRenderer;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.gui.screen.widget.converter.MinecraftWidgetType;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.Laby;
import org.jetbrains.annotations.NotNull;

public abstract class ExtendingTheme extends AbstractTheme
{
    @NotNull
    private final Theme parentTheme;
    
    public ExtendingTheme(final String id, final String parentTheme, final Class<? extends ThemeConfig> configClass) {
        super(id, configClass);
        this.parentTheme = Laby.labyAPI().themeService().getThemeByName(parentTheme);
        if (this.parentTheme == null) {
            throw new IllegalArgumentException("Parent theme not found: " + parentTheme);
        }
    }
    
    public ExtendingTheme(final String id, final String parentTheme) {
        this(id, parentTheme, ThemeConfig.class);
    }
    
    public ExtendingTheme(final String id, final Class<? extends ThemeConfig> configClass) {
        this(id, "vanilla", configClass);
    }
    
    public ExtendingTheme(final String id) {
        this(id, ThemeConfig.class);
    }
    
    @NotNull
    public Theme parentTheme() {
        return this.parentTheme;
    }
    
    @Override
    public void onPreLoad() {
        super.onPreLoad();
        this.parentTheme.onPreLoad();
    }
    
    @Override
    public void onPostLoad() {
        super.onPostLoad();
        this.parentTheme.onPostLoad();
    }
    
    @Override
    public void onUnload() {
        super.onUnload();
        this.parentTheme.onUnload();
    }
    
    @Override
    public ThemeFile createResource(final BasicThemeFile file) {
        final ThemeFile resource = super.createResource(file);
        if (resource.exists() || !this.isResourcesLoaded()) {
            return resource;
        }
        return this.parentTheme.createResource(file);
    }
    
    @Nullable
    @Override
    public <T extends Widget> ThemeRenderer<T> getWidgetRendererByName(final String rendererName) {
        final ThemeRenderer<T> renderer = super.getWidgetRendererByName(rendererName);
        if (renderer != null) {
            return renderer;
        }
        return this.parentTheme.getWidgetRendererByName(rendererName);
    }
    
    @Override
    public ThemeRenderer<?> getWidgetRendererByType(final MinecraftWidgetType type) {
        final ThemeRenderer<?> renderer = super.getWidgetRendererByType(type);
        if (renderer != null) {
            return renderer;
        }
        return this.parentTheme.getWidgetRendererByType(type);
    }
    
    @Nullable
    @Override
    public HoverBackgroundEffect getHoverBackgroundRenderer() {
        final HoverBackgroundEffect renderer = super.getHoverBackgroundRenderer();
        if (renderer != null) {
            return renderer;
        }
        return this.parentTheme.getHoverBackgroundRenderer();
    }
    
    @Nullable
    @Override
    public BackgroundRenderer getBackgroundRenderer() {
        final BackgroundRenderer renderer = super.getBackgroundRenderer();
        if (renderer != null) {
            return renderer;
        }
        return this.parentTheme.getBackgroundRenderer();
    }
}
