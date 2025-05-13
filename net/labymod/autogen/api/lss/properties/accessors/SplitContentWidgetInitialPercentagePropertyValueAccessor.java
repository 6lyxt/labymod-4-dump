// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.widgets.layout.SplitContentWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class SplitContentWidgetInitialPercentagePropertyValueAccessor implements PropertyValueAccessor<SplitContentWidget, Float, Float>
{
    @Override
    public LssProperty getProperty(final SplitContentWidget widget) {
        return widget.initialPercentage();
    }
    
    @Override
    public Class<?> type() {
        return Float.class;
    }
    
    @Override
    public Float[] toArray(final Object[] objects) {
        final Float[] array = new Float[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (Float)objects[i];
        }
        return array;
    }
    
    @Override
    public Float[] toArray(final Collection<Float> collection) {
        return collection.toArray(new Float[0]);
    }
}
