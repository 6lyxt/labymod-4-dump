// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.attributes.Filter;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class AbstractWidgetFilterPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, Filter[], Filter>
{
    @Override
    public LssProperty getProperty(final AbstractWidget widget) {
        return widget.filter();
    }
    
    @Override
    public Class<?> type() {
        return Filter[].class;
    }
    
    @Override
    public Filter[] toArray(final Object[] objects) {
        final Filter[] array = new Filter[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (Filter)objects[i];
        }
        return array;
    }
    
    @Override
    public Filter[] toArray(final Collection<Filter> collection) {
        return collection.toArray(new Filter[0]);
    }
}
