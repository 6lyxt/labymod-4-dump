// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class FlexibleContentWidgetOrientationPropertyValueAccessor implements PropertyValueAccessor<FlexibleContentWidget, FlexibleContentWidget.FlexibleContentOrientation, FlexibleContentWidget.FlexibleContentOrientation>
{
    @Override
    public LssProperty getProperty(final FlexibleContentWidget widget) {
        return widget.orientation();
    }
    
    @Override
    public Class<?> type() {
        return FlexibleContentWidget.FlexibleContentOrientation.class;
    }
    
    @Override
    public FlexibleContentWidget.FlexibleContentOrientation[] toArray(final Object[] objects) {
        final FlexibleContentWidget.FlexibleContentOrientation[] array = new FlexibleContentWidget.FlexibleContentOrientation[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (FlexibleContentWidget.FlexibleContentOrientation)objects[i];
        }
        return array;
    }
    
    @Override
    public FlexibleContentWidget.FlexibleContentOrientation[] toArray(final Collection<FlexibleContentWidget.FlexibleContentOrientation> collection) {
        return collection.toArray(new FlexibleContentWidget.FlexibleContentOrientation[0]);
    }
}
