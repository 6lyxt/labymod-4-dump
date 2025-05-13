// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.attributes.ObjectFitType;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class IconWidgetObjectFitPropertyValueAccessor implements PropertyValueAccessor<IconWidget, ObjectFitType, ObjectFitType>
{
    @Override
    public LssProperty getProperty(final IconWidget widget) {
        return widget.objectFit();
    }
    
    @Override
    public Class<?> type() {
        return ObjectFitType.class;
    }
    
    @Override
    public ObjectFitType[] toArray(final Object[] objects) {
        final ObjectFitType[] array = new ObjectFitType[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (ObjectFitType)objects[i];
        }
        return array;
    }
    
    @Override
    public ObjectFitType[] toArray(final Collection<ObjectFitType> collection) {
        return collection.toArray(new ObjectFitType[0]);
    }
}
