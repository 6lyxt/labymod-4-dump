// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.screen.widget.widgets.title.background;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.core.main.LabyMod;
import net.labymod.core.client.gui.background.panorama.PanoramaRenderer;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class PanoramaWidget extends AbstractWidget<Widget>
{
    private static final Logging LOGGER;
    private final PanoramaRenderer panoramaRenderer;
    
    public PanoramaWidget() {
        this.panoramaRenderer = LabyMod.references().panoramaRenderer();
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        super.renderWidget(context);
        final Bounds bounds = this.bounds();
        try {
            this.panoramaRenderer.render(context.stack(), bounds.getLeft(), bounds.getTop(), bounds.getRight(), bounds.getBottom(), context.getTickDelta());
        }
        catch (final Exception exception) {
            PanoramaWidget.LOGGER.error("Failed to render panorama", exception);
        }
    }
    
    static {
        LOGGER = Logging.getLogger();
    }
}
