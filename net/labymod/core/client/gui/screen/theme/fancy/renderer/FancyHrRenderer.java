// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.theme.fancy.renderer;

import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.HrWidget;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;

public class FancyHrRenderer extends ThemeRenderer<HrWidget>
{
    public FancyHrRenderer() {
        super("Hr");
    }
    
    @Override
    public void renderPost(final HrWidget widget, final ScreenContext context) {
        this.rectangleRenderer.setupSimple(builder -> {
            builder.begin(context.stack());
            builder.render(widget.bounds(), ColorFormat.ARGB32.pack(255, 255, 255, 20));
            builder.uploadToBuffer();
        });
    }
}
