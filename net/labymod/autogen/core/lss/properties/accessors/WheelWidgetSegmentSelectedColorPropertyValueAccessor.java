// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class WheelWidgetSegmentSelectedColorPropertyValueAccessor implements PropertyValueAccessor<WheelWidget, Integer, Integer>
{
    @Override
    public LssProperty getProperty(final WheelWidget widget) {
        return widget.segmentSelectedColor();
    }
    
    @Override
    public Class<?> type() {
        return Integer.class;
    }
    
    @Override
    public Integer[] toArray(final Object[] objects) {
        final Integer[] array = new Integer[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (Integer)objects[i];
        }
        return array;
    }
    
    @Override
    public Integer[] toArray(final Collection<Integer> collection) {
        return collection.toArray(new Integer[0]);
    }
}
