// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.attributes;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.lss.style.modifier.ProcessedObject;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.theme.renderer.WidgetRenderer;
import net.labymod.api.client.gui.screen.widget.attributes.animation.Interpolatable;

public class Filter implements Interpolatable<Filter>, WidgetRenderer<AbstractWidget<?>>
{
    private FilterType type;
    private ProcessedObject[] values;
    
    public Filter(final FilterType type, final ProcessedObject... values) {
        this.type = type;
        this.values = values;
    }
    
    @Override
    public void renderPre(final AbstractWidget<?> widget, final ScreenContext context) {
        switch (this.type) {
            case BLUR: {
                this.renderBlur(widget, context);
                return;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + String.valueOf(this.type));
            }
        }
    }
    
    @Override
    public void renderPost(final AbstractWidget<?> widget, final ScreenContext context) {
    }
    
    public FilterType getType() {
        return this.type;
    }
    
    public void setType(final FilterType type) {
        this.type = type;
    }
    
    @Override
    public Filter interpolate(final CubicBezier interpolator, final Filter from, final long fromMillis, final long toMillis, final long currentMillis) {
        final ProcessedObject fromProcessedObject = from.values[0];
        final float interpolated = interpolator.interpolate((float)fromProcessedObject.value(), (float)this.values[0].value(), fromMillis, toMillis, currentMillis);
        final ProcessedObject newProcessedObject = new ProcessedObject(fromProcessedObject.postProcessor(), String.valueOf(interpolated), interpolated);
        return new Filter(this.type, new ProcessedObject[] { newProcessedObject });
    }
    
    private void renderBlur(final AbstractWidget<?> widget, final ScreenContext context) {
        final Object value = this.values[0].value();
        if (value == null) {
            return;
        }
        final int radius = MathHelper.ceil((float)value);
        if (radius <= 0) {
            return;
        }
        DefaultFilters.BLUR_RENDERER.setEdgeRadius(widget.getBorderRadius());
        DefaultFilters.BLUR_RENDERER.renderRectangle(context.stack(), widget.bounds().rectangle(BoundsType.BORDER), radius);
    }
}
