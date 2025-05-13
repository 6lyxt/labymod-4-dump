// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gfx.pipeline.context;

import net.labymod.api.client.gfx.pipeline.RenderAttributes;
import net.labymod.core.client.gui.screen.theme.fancy.FancyThemeConfig;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.gfx.pipeline.context.FrameContext;

public class RenderAttributesFrameContext implements FrameContext
{
    private final RenderAttributesStack renderAttributesStack;
    
    public RenderAttributesFrameContext() {
        this.renderAttributesStack = Laby.references().renderEnvironmentContext().renderAttributesStack();
    }
    
    @Override
    public void beginFrame() {
        final RenderAttributes attributes = this.renderAttributesStack.pushAndGet();
        final FancyThemeConfig config = Laby.labyAPI().themeService().getThemeConfig(FancyThemeConfig.class);
        if (config != null) {
            attributes.setForceVanillaFont(!config.fancyFont().get() || !config.isIngameFancyFont());
        }
        else {
            attributes.setForceVanillaFont(true);
        }
        attributes.apply();
    }
    
    @Override
    public void endFrame() {
        this.renderAttributesStack.pop();
        this.renderAttributesStack.checkStack();
    }
}
