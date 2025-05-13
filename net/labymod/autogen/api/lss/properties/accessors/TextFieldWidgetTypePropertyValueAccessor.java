// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class TextFieldWidgetTypePropertyValueAccessor implements PropertyValueAccessor<TextFieldWidget, TextFieldWidget.Type, TextFieldWidget.Type>
{
    @Override
    public LssProperty getProperty(final TextFieldWidget widget) {
        return widget.type();
    }
    
    @Override
    public Class<?> type() {
        return TextFieldWidget.Type.class;
    }
    
    @Override
    public TextFieldWidget.Type[] toArray(final Object[] objects) {
        final TextFieldWidget.Type[] array = new TextFieldWidget.Type[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (TextFieldWidget.Type)objects[i];
        }
        return array;
    }
    
    @Override
    public TextFieldWidget.Type[] toArray(final Collection<TextFieldWidget.Type> collection) {
        return collection.toArray(new TextFieldWidget.Type[0]);
    }
}
