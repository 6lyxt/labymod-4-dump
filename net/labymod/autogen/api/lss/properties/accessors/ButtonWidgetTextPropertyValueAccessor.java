// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class ButtonWidgetTextPropertyValueAccessor implements PropertyValueAccessor<ButtonWidget, Component, Component>
{
    @Override
    public LssProperty getProperty(final ButtonWidget widget) {
        return widget.text();
    }
    
    @Override
    public Class<?> type() {
        return Component.class;
    }
    
    @Override
    public Component[] toArray(final Object[] objects) {
        final Component[] array = new Component[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (Component)objects[i];
        }
        return array;
    }
    
    @Override
    public Component[] toArray(final Collection<Component> collection) {
        return collection.toArray(new Component[0]);
    }
}
