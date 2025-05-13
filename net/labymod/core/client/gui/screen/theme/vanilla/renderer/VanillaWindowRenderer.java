// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.core.client.gui.screen.widget.widgets.NewWindowWidget;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.Laby;
import net.labymod.api.client.render.draw.builder.VanillaWindowBuilder;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public class VanillaWindowRenderer extends ThemeRenderer<AbstractWidget<?>>
{
    private final VanillaWindowBuilder windowBuilder;
    
    public VanillaWindowRenderer() {
        super("Window");
        this.windowBuilder = Laby.references().vanillaWindowBuilder();
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        final Bounds bounds = widget.bounds();
        final boolean window = widget instanceof NewWindowWidget;
        final boolean topLarge = !window || ((NewWindowWidget)widget).titleBarWidget() != null;
        this.windowBuilder.top(topLarge).bottom(false).rescalable(window).position(bounds.rectangle(BoundsType.OUTER)).render(context.stack(), context.mouse());
    }
}
