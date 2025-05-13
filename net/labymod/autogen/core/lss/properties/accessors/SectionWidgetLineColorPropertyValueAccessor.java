// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.shop.widgets.SectionedListWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class SectionWidgetLineColorPropertyValueAccessor implements PropertyValueAccessor<SectionedListWidget.SectionWidget, Integer, Integer>
{
    @Override
    public LssProperty getProperty(final SectionedListWidget.SectionWidget widget) {
        return widget.lineColor();
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
