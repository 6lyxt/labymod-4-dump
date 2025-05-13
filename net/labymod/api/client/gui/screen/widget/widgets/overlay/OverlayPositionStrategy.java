// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget.widgets.overlay;

import java.util.Collections;
import org.spongepowered.include.com.google.common.collect.Lists;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.Orientation;
import java.util.List;

public enum OverlayPositionStrategy
{
    CUSTOM(Orientation.NONE, WidgetAlignment.CENTER, (ignored, bounds) -> 0.0f), 
    X_X(Orientation.HORIZONTAL, WidgetAlignment.LEFT, (width, widgetBounds) -> widgetBounds.getX()), 
    MAXY_Y(Orientation.VERTICAL, WidgetAlignment.BOTTOM, (height, widgetBounds) -> widgetBounds.getMaxY()), 
    Y_MAXY(Orientation.VERTICAL, WidgetAlignment.TOP, (height, widgetBounds) -> widgetBounds.getY() - height), 
    MAXX_MAXX(Orientation.HORIZONTAL, WidgetAlignment.RIGHT, (width, widgetBounds) -> widgetBounds.getMaxX() - width);
    
    private static final List<OverlayPositionStrategy> HORIZONTAL;
    private static final List<OverlayPositionStrategy> VERTICAL;
    private final Orientation orientation;
    private final PositionConsumer positionConsumer;
    private final WidgetAlignment alignment;
    
    private OverlayPositionStrategy(final Orientation orientation, final WidgetAlignment alignment, final PositionConsumer positionConsumer) {
        this.orientation = orientation;
        this.alignment = alignment;
        this.positionConsumer = positionConsumer;
    }
    
    public Orientation orientation() {
        return this.orientation;
    }
    
    public PositionConsumer position() {
        return this.positionConsumer;
    }
    
    public WidgetAlignment alignment() {
        return this.alignment;
    }
    
    public static List<OverlayPositionStrategy> horizontalValues() {
        return OverlayPositionStrategy.HORIZONTAL;
    }
    
    public static List<OverlayPositionStrategy> verticalValues() {
        return OverlayPositionStrategy.VERTICAL;
    }
    
    static {
        final List<OverlayPositionStrategy> horizontal = Lists.newArrayList();
        final List<OverlayPositionStrategy> vertical = Lists.newArrayList();
        final OverlayPositionStrategy[] values2;
        final OverlayPositionStrategy[] values = values2 = values();
        for (final OverlayPositionStrategy value : values2) {
            if (value.orientation == Orientation.HORIZONTAL) {
                horizontal.add(value);
            }
            else if (value.orientation == Orientation.VERTICAL) {
                vertical.add(value);
            }
        }
        HORIZONTAL = Collections.unmodifiableList((List<? extends OverlayPositionStrategy>)horizontal);
        VERTICAL = Collections.unmodifiableList((List<? extends OverlayPositionStrategy>)vertical);
    }
    
    public interface PositionConsumer
    {
        float get(final float p0, final Rectangle p1);
    }
}
