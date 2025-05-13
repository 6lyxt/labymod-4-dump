// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.vanilla.renderer;

import net.labymod.api.client.render.draw.batch.BatchRectangleRenderer;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public class VanillaCrosshairRenderer extends ThemeRenderer<Widget>
{
    private static final int WIDTH = 1;
    private static final int HEIGHT = 9;
    private final RectangleRenderer rectangleRenderer;
    
    public VanillaCrosshairRenderer() {
        super("Crosshair");
        this.rectangleRenderer = super.labyAPI.renderPipeline().rectangleRenderer();
    }
    
    @Override
    public void renderPost(final Widget widget, final ScreenContext context) {
        final MutableMouse mouse = context.mouse();
        final BatchRectangleRenderer renderer = this.rectangleRenderer.beginBatch(context.stack());
        this.renderRelative(renderer, mouse.getX() - 0.5f, mouse.getY() - 4.5f, 1.0f, 9.0f, Integer.MAX_VALUE);
        this.renderRelative(renderer, mouse.getX() - 4.5f, mouse.getY() - 0.5f, 9.0f, 1.0f, Integer.MAX_VALUE);
        renderer.upload();
    }
    
    private void renderRelative(final BatchRectangleRenderer renderer, final float x, final float y, final float width, final float height, final int color) {
        renderer.pos(x, y, x + width, y + height).color(color).build();
    }
}
