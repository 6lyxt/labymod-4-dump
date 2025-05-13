// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.renderer;

import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.render.draw.RectangleRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.function.FloatSupplier;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;

@AutoWidget
public class ProgressableProgressBarWidget extends AbstractWidget<Widget>
{
    private final FloatSupplier progressSupplier;
    private final LssProperty<Integer> progressForegroundColor;
    private final LssProperty<Integer> progressBackgroundColor;
    
    public ProgressableProgressBarWidget(final FloatSupplier progressSupplier) {
        this.progressForegroundColor = new LssProperty<Integer>(-16711936);
        this.progressBackgroundColor = new LssProperty<Integer>(-16777216);
        this.progressSupplier = progressSupplier;
    }
    
    @Override
    public void renderWidget(final ScreenContext context) {
        super.renderWidget(context);
        final RectangleRenderer rectangleRenderer = this.labyAPI.renderPipeline().rectangleRenderer();
        final Bounds bounds = this.bounds();
        float progress = this.progressSupplier.get();
        if (progress < 0.0f) {
            progress = 0.0f;
        }
        else if (progress > 1.0f) {
            progress = 1.0f;
        }
        final float x = bounds.getX();
        final float y = bounds.getY();
        final float width = bounds.getWidth();
        final float height = bounds.getHeight();
        rectangleRenderer.pos(x, y).size(width, height).color(this.progressBackgroundColor.get()).render(context.stack());
        rectangleRenderer.pos(x, y).size(width * progress, height).color(this.progressForegroundColor.get()).render(context.stack());
    }
    
    public LssProperty<Integer> progressForegroundColor() {
        return this.progressForegroundColor;
    }
    
    public LssProperty<Integer> progressBackgroundColor() {
        return this.progressBackgroundColor;
    }
}
