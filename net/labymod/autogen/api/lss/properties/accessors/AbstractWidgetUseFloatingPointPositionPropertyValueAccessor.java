// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class AbstractWidgetUseFloatingPointPositionPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, Boolean, Boolean>
{
    @Override
    public LssProperty getProperty(final AbstractWidget widget) {
        return widget.useFloatingPointPosition();
    }
    
    @Override
    public Class<?> type() {
        return Boolean.class;
    }
    
    @Override
    public Boolean[] toArray(final Object[] objects) {
        final Boolean[] array = new Boolean[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (Boolean)objects[i];
        }
        return array;
    }
    
    @Override
    public Boolean[] toArray(final Collection<Boolean> collection) {
        return collection.toArray(new Boolean[0]);
    }
}
