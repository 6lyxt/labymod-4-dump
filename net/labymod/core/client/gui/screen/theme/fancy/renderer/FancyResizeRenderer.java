// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.render.draw.CircleRenderer;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.screen.widget.widgets.WindowWidget;
import net.labymod.core.client.gui.screen.theme.vanilla.renderer.VanillaResizeRenderer;

@Deprecated
public class FancyResizeRenderer extends VanillaResizeRenderer
{
    @Override
    protected void renderCorner(final WindowWidget.RescaleWidget widget, final Stack stack, final MutableMouse mouse, final float delta) {
        this.circleRenderer.pos(widget.bounds().getLeft(), widget.bounds().getTop()).partial(270.0f, 90.0f).donutRadius(8.0f, 10.0f).color(widget.getCurrentColor()).render(stack);
    }
}
