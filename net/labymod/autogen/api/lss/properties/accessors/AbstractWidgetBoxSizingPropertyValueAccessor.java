// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.attributes.BoxSizing;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class AbstractWidgetBoxSizingPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, BoxSizing, BoxSizing>
{
    @Override
    public LssProperty getProperty(final AbstractWidget widget) {
        return widget.boxSizing();
    }
    
    @Override
    public Class<?> type() {
        return BoxSizing.class;
    }
    
    @Override
    public BoxSizing[] toArray(final Object[] objects) {
        final BoxSizing[] array = new BoxSizing[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (BoxSizing)objects[i];
        }
        return array;
    }
    
    @Override
    public BoxSizing[] toArray(final Collection<BoxSizing> collection) {
        return collection.toArray(new BoxSizing[0]);
    }
}
