// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.attributes.WidgetAlignment;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class AbstractWidgetAlignmentYPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, WidgetAlignment, WidgetAlignment>
{
    @Override
    public LssProperty getProperty(final AbstractWidget widget) {
        return widget.alignmentY();
    }
    
    @Override
    public Class<?> type() {
        return WidgetAlignment.class;
    }
    
    @Override
    public WidgetAlignment[] toArray(final Object[] objects) {
        final WidgetAlignment[] array = new WidgetAlignment[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (WidgetAlignment)objects[i];
        }
        return array;
    }
    
    @Override
    public WidgetAlignment[] toArray(final Collection<WidgetAlignment> collection) {
        return collection.toArray(new WidgetAlignment[0]);
    }
}
