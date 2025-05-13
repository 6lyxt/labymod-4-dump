// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class TextFieldWidgetMarkColorPropertyValueAccessor implements PropertyValueAccessor<TextFieldWidget, Integer, Integer>
{
    @Override
    public LssProperty getProperty(final TextFieldWidget widget) {
        return widget.markColor();
    }
    
    @Override
    public Class<?> type() {
        return Integer.class;
    }
    
    @Override
    public Integer[] toArray(final Object[] objects) {
        final Integer[] array = new Integer[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (Integer)objects[i];
        }
        return array;
    }
    
    @Override
    public Integer[] toArray(final Collection<Integer> collection) {
        return collection.toArray(new Integer[0]);
    }
}
