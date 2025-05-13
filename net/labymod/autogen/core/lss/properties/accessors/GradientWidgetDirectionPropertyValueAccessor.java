// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.core.client.gui.screen.widget.widgets.store.GradientWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class GradientWidgetDirectionPropertyValueAccessor implements PropertyValueAccessor<GradientWidget, GradientWidget.Direction, GradientWidget.Direction>
{
    @Override
    public LssProperty getProperty(final GradientWidget widget) {
        return widget.direction();
    }
    
    @Override
    public Class<?> type() {
        return GradientWidget.Direction.class;
    }
    
    @Override
    public GradientWidget.Direction[] toArray(final Object[] objects) {
        final GradientWidget.Direction[] array = new GradientWidget.Direction[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (GradientWidget.Direction)objects[i];
        }
        return array;
    }
    
    @Override
    public GradientWidget.Direction[] toArray(final Collection<GradientWidget.Direction> collection) {
        return collection.toArray(new GradientWidget.Direction[0]);
    }
}
