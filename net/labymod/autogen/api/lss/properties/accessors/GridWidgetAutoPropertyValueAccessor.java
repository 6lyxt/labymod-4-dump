// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.screen.widget.widgets.layout.GridWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class GridWidgetAutoPropertyValueAccessor implements PropertyValueAccessor<GridWidget, Orientation, Orientation>
{
    @Override
    public LssProperty getProperty(final GridWidget widget) {
        return widget.auto();
    }
    
    @Override
    public Class<?> type() {
        return Orientation.class;
    }
    
    @Override
    public Orientation[] toArray(final Object[] objects) {
        final Orientation[] array = new Orientation[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (Orientation)objects[i];
        }
        return array;
    }
    
    @Override
    public Orientation[] toArray(final Collection<Orientation> collection) {
        return collection.toArray(new Orientation[0]);
    }
}
