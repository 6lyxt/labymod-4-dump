// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class VerticalListWidgetListAlignmentPropertyValueAccessor implements PropertyValueAccessor<VerticalListWidget, VerticalAlignment, VerticalAlignment>
{
    @Override
    public LssProperty getProperty(final VerticalListWidget widget) {
        return widget.listAlignment();
    }
    
    @Override
    public Class<?> type() {
        return VerticalAlignment.class;
    }
    
    @Override
    public VerticalAlignment[] toArray(final Object[] objects) {
        final VerticalAlignment[] array = new VerticalAlignment[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (VerticalAlignment)objects[i];
        }
        return array;
    }
    
    @Override
    public VerticalAlignment[] toArray(final Collection<VerticalAlignment> collection) {
        return collection.toArray(new VerticalAlignment[0]);
    }
}
