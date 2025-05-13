// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.widgets.overlay.OverlayPositionStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.overlay.OverlayWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class OverlayWidgetStrategyYPropertyValueAccessor implements PropertyValueAccessor<OverlayWidget, OverlayPositionStrategy, OverlayPositionStrategy>
{
    @Override
    public LssProperty getProperty(final OverlayWidget widget) {
        return widget.strategyY();
    }
    
    @Override
    public Class<?> type() {
        return OverlayPositionStrategy.class;
    }
    
    @Override
    public OverlayPositionStrategy[] toArray(final Object[] objects) {
        final OverlayPositionStrategy[] array = new OverlayPositionStrategy[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (OverlayPositionStrategy)objects[i];
        }
        return array;
    }
    
    @Override
    public OverlayPositionStrategy[] toArray(final Collection<OverlayPositionStrategy> collection) {
        return collection.toArray(new OverlayPositionStrategy[0]);
    }
}
