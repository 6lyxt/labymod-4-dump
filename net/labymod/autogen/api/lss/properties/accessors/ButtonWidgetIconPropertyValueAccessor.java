// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class ButtonWidgetIconPropertyValueAccessor implements PropertyValueAccessor<ButtonWidget, Icon, Icon>
{
    @Override
    public LssProperty getProperty(final ButtonWidget widget) {
        return widget.icon();
    }
    
    @Override
    public Class<?> type() {
        return Icon.class;
    }
    
    @Override
    public Icon[] toArray(final Object[] objects) {
        final Icon[] array = new Icon[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (Icon)objects[i];
        }
        return array;
    }
    
    @Override
    public Icon[] toArray(final Collection<Icon> collection) {
        return collection.toArray(new Icon[0]);
    }
}
