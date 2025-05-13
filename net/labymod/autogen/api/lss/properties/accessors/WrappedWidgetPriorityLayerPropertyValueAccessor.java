// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.attributes.PriorityLayer;
import net.labymod.api.client.gui.screen.widget.WrappedWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class WrappedWidgetPriorityLayerPropertyValueAccessor implements PropertyValueAccessor<WrappedWidget, PriorityLayer, PriorityLayer>
{
    @Override
    public LssProperty getProperty(final WrappedWidget widget) {
        return widget.priorityLayer();
    }
    
    @Override
    public Class<?> type() {
        return PriorityLayer.class;
    }
    
    @Override
    public PriorityLayer[] toArray(final Object[] objects) {
        final PriorityLayer[] array = new PriorityLayer[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (PriorityLayer)objects[i];
        }
        return array;
    }
    
    @Override
    public PriorityLayer[] toArray(final Collection<PriorityLayer> collection) {
        return collection.toArray(new PriorityLayer[0]);
    }
}
