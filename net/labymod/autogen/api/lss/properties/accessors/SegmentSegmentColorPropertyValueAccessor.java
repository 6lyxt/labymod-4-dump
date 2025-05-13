// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class SegmentSegmentColorPropertyValueAccessor implements PropertyValueAccessor<WheelWidget.Segment, Integer, Integer>
{
    @Override
    public LssProperty getProperty(final WheelWidget.Segment widget) {
        return widget.segmentColor();
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
