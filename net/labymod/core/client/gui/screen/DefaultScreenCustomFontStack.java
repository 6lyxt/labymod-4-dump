// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen;

import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.game.GameScreen;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gui.screen.game.GameScreenRegistry;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import javax.inject.Inject;
import net.labymod.api.client.gfx.pipeline.context.FrameContext;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.models.Implements;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.ScreenCustomFontStack;

@Singleton
@Implements(ScreenCustomFontStack.class)
public class DefaultScreenCustomFontStack implements ScreenCustomFontStack
{
    private final RenderAttributesStack renderAttributesStack;
    private final IngameFancyFontFrameContext ingameFancyFontFrameContext;
    
    @Inject
    public DefaultScreenCustomFontStack(final FrameContextRegistry frameContextRegistry, final RenderEnvironmentContext renderEnvironmentContext) {
        this.renderAttributesStack = renderEnvironmentContext.renderAttributesStack();
        frameContextRegistry.register(this.ingameFancyFontFrameContext = new IngameFancyFontFrameContext());
    }
    
    @Override
    public void push(final Object screen) {
        if (this.isIngameFancyFont()) {
            return;
        }
        if (screen instanceof final LabyScreenAccessor labyScreenAccessor) {
            this.push(labyScreenAccessor.screen());
        }
        else if (screen instanceof final LabyScreen labyScreen) {
            this.push(labyScreen);
        }
        else {
            this.push(GameScreenRegistry.from(screen));
        }
    }
    
    @Override
    public void pop(final Object screen) {
        if (this.isIngameFancyFont()) {
            return;
        }
        if (screen instanceof final LabyScreenAccessor labyScreenAccessor) {
            this.pop(labyScreenAccessor.screen());
        }
        else if (screen instanceof final LabyScreen labyScreen) {
            this.pop(labyScreen);
        }
        else {
            this.pop(GameScreenRegistry.from(screen));
        }
    }
    
    private void push(final LabyScreen screen) {
        final RenderAttributes attributes = this.renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(!screen.allowCustomFont());
        attributes.apply();
    }
    
    private void push(final GameScreen screen) {
        if (screen == null) {
            return;
        }
        final RenderAttributes attributes = this.renderAttributesStack.pushAndGet();
        attributes.setForceVanillaFont(!screen.allowCustomFont());
        attributes.apply();
    }
    
    private void pop(final LabyScreen labyScreen) {
        this.renderAttributesStack.pop();
    }
    
    private void pop(final GameScreen screen) {
        if (screen == null) {
            return;
        }
        this.renderAttributesStack.pop();
    }
    
    private boolean isIngameFancyFont() {
        return this.ingameFancyFontFrameContext.isIngameFancyFont();
    }
    
    private static class IngameFancyFontFrameContext implements FrameContext
    {
        private boolean ingameFancyFont;
        
        @Override
        public void beginFrame() {
            final ThemeService themeService = Laby.references().themeService();
            final FancyThemeConfig config = themeService.getThemeConfig(FancyThemeConfig.class);
            if (config == null) {
                this.ingameFancyFont = true;
                return;
            }
            this.ingameFancyFont = config.isIngameFancyFont();
        }
        
        @Override
        public void endFrame() {
        }
        
        public boolean isIngameFancyFont() {
            return this.ingameFancyFont;
        }
    }
}
