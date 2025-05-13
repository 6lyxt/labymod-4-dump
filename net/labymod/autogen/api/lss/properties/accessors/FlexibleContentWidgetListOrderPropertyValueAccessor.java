// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.util.ListOrder;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class FlexibleContentWidgetListOrderPropertyValueAccessor implements PropertyValueAccessor<FlexibleContentWidget, ListOrder, ListOrder>
{
    @Override
    public LssProperty getProperty(final FlexibleContentWidget widget) {
        return widget.listOrder();
    }
    
    @Override
    public Class<?> type() {
        return ListOrder.class;
    }
    
    @Override
    public ListOrder[] toArray(final Object[] objects) {
        final ListOrder[] array = new ListOrder[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (ListOrder)objects[i];
        }
        return array;
    }
    
    @Override
    public ListOrder[] toArray(final Collection<ListOrder> collection) {
        return collection.toArray(new ListOrder[0]);
    }
}
