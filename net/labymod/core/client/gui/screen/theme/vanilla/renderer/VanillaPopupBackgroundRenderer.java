// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.Laby;
import net.labymod.api.client.render.draw.builder.VanillaWindowBuilder;

public class VanillaPopupBackgroundRenderer extends VanillaBackgroundRenderer
{
    private final VanillaWindowBuilder vanillaWindowBuilder;
    
    public VanillaPopupBackgroundRenderer() {
        super("PopupBackground");
        this.vanillaWindowBuilder = Laby.references().vanillaWindowBuilder();
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        this.vanillaWindowBuilder.top(false).bottom(false).position(widget.bounds().rectangle(BoundsType.OUTER)).render(context.stack(), context.mouse());
    }
}
