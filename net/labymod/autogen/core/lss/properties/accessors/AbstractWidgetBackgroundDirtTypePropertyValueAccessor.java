// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.attributes.DirtBackgroundType;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class AbstractWidgetBackgroundDirtTypePropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, DirtBackgroundType, DirtBackgroundType>
{
    @Override
    public LssProperty getProperty(final AbstractWidget widget) {
        return widget.backgroundDirtType();
    }
    
    @Override
    public Class<?> type() {
        return DirtBackgroundType.class;
    }
    
    @Override
    public DirtBackgroundType[] toArray(final Object[] objects) {
        final DirtBackgroundType[] array = new DirtBackgroundType[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (DirtBackgroundType)objects[i];
        }
        return array;
    }
    
    @Override
    public DirtBackgroundType[] toArray(final Collection<DirtBackgroundType> collection) {
        return collection.toArray(new DirtBackgroundType[0]);
    }
}
