// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaBackgroundRenderer;

public class FancyDirtBackgroundRenderer extends VanillaBackgroundRenderer
{
    private final FancyBackgroundRenderer backgroundRenderer;
    
    public FancyDirtBackgroundRenderer(final Theme theme) {
        this.name = "DirtBackground";
        this.backgroundRenderer = new FancyBackgroundRenderer();
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        this.backgroundRenderer.renderPre(widget, context);
    }
}
