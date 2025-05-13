// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.render.font.text;

import net.labymod.api.client.gui.screen.theme.ThemeService;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;

public final class TextUtil
{
    private static final RenderAttributesStack RENDER_ATTRIBUTES_STACK;
    
    private TextUtil() {
    }
    
    public static void pushAndApplyAttributes() {
        final RenderAttributes attributes = TextUtil.RENDER_ATTRIBUTES_STACK.pushAndGet();
        attributes.setForceVanillaFont(!isIngameFancyFont());
        attributes.apply();
    }
    
    public static void popRenderAttributes() {
        TextUtil.RENDER_ATTRIBUTES_STACK.pop();
    }
    
    private static boolean isIngameFancyFont() {
        final ThemeService themeService = Laby.references().themeService();
        final FancyThemeConfig config = themeService.getThemeConfig(FancyThemeConfig.class);
        return config != null && config.isIngameFancyFont();
    }
    
    static {
        RENDER_ATTRIBUTES_STACK = Laby.references().renderEnvironmentContext().renderAttributesStack();
    }
}
