// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class ScrollWidgetChildAlignPropertyValueAccessor implements PropertyValueAccessor<ScrollWidget, VerticalAlignment, VerticalAlignment>
{
    @Override
    public LssProperty getProperty(final ScrollWidget widget) {
        return widget.childAlign();
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
