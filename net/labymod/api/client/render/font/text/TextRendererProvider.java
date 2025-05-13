// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.font.text;

import net.labymod.api.client.gui.screen.theme.ThemeTextRendererProvider;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface TextRendererProvider
{
    TextRenderer getRenderer();
    
    boolean useCustomFont();
    
    void setUseCustomFont(final boolean p0);
    
    boolean isMinecraftRendererForced();
    
    void forceMinecraftRenderer(final boolean p0);
    
    boolean shouldUseMinecraftFont();
    
    void forceVanillaFont(final boolean p0, final Runnable p1);
    
    default boolean isVanillaFontRenderer() {
        return this.getRenderer().isVanilla();
    }
    
    ThemeTextRendererProvider create(final Theme p0);
}
