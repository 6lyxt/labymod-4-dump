// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text;

import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.theme.DefaultThemeTextRendererProvider;
import net.labymod.api.client.gui.screen.theme.ThemeTextRendererProvider;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.client.gui.screen.theme.Theme;
import javax.inject.Inject;
import javax.inject.Named;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.event.EventBus;
import net.labymod.api.client.render.font.text.TextRenderer;
import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.render.font.text.TextRendererProvider;

@Singleton
@Implements(TextRendererProvider.class)
public class DefaultTextRendererProvider implements TextRendererProvider
{
    private final RenderAttributesStack renderAttributesStack;
    private final ThemeService themeService;
    private final TextRenderer minecraftTextRenderer;
    private boolean forceMinecraftRenderer;
    private boolean useCustomFont;
    
    @Inject
    public DefaultTextRendererProvider(final EventBus eventBus, final ThemeService themeService, final RenderEnvironmentContext renderEnvironmentContext, @Named("vanilla_text_renderer") final TextRenderer minecraftTextRenderer) {
        this.useCustomFont = true;
        this.themeService = themeService;
        this.minecraftTextRenderer = minecraftTextRenderer;
        this.renderAttributesStack = renderEnvironmentContext.renderAttributesStack();
        eventBus.registerListener(new TextRendererListener(this));
    }
    
    @Override
    public TextRenderer getRenderer() {
        if (!this.themeService.isInitialized() || this.isMinecraftRendererForced()) {
            return this.minecraftTextRenderer;
        }
        final Theme theme = this.themeService.currentTheme();
        final TextRenderer textRenderer = theme.textRenderer();
        if (textRenderer.usable()) {
            return textRenderer;
        }
        return this.minecraftTextRenderer;
    }
    
    @Override
    public boolean useCustomFont() {
        return this.useCustomFont;
    }
    
    @Override
    public void setUseCustomFont(final boolean useCustomFont) {
        if (this.useCustomFont == useCustomFont) {
            return;
        }
        this.useCustomFont = useCustomFont;
        if (useCustomFont) {
            this.forceMinecraftRenderer(false);
        }
        ThreadSafe.executeOnRenderThread(this::reloadActivities);
    }
    
    @Override
    public boolean isMinecraftRendererForced() {
        return this.forceMinecraftRenderer || !this.useCustomFont;
    }
    
    @Override
    public void forceMinecraftRenderer(final boolean force) {
        this.forceMinecraftRenderer = force;
    }
    
    @Override
    public boolean shouldUseMinecraftFont() {
        if (this.isMinecraftRendererForced()) {
            return true;
        }
        final RenderAttributes attributes = this.renderAttributesStack.last();
        return attributes.isForceVanillaFont();
    }
    
    @Override
    public void forceVanillaFont(final boolean condition, final Runnable runnable) {
        if (condition) {
            final boolean currentState = this.isMinecraftRendererForced();
            this.forceMinecraftRenderer(true);
            runnable.run();
            this.forceMinecraftRenderer(currentState);
        }
        else {
            runnable.run();
        }
    }
    
    @Override
    public ThemeTextRendererProvider create(final Theme theme) {
        return new DefaultThemeTextRendererProvider(theme);
    }
    
    private void reloadActivities() {
        final ReferenceStorage references = Laby.references();
        references.activityController().reloadOpenActivities();
        references.componentRenderer().invalidate();
    }
}
