// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.Orientation;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.overlay.selector.SelectorWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class SelectorWidgetOrientationPropertyValueAccessor implements PropertyValueAccessor<SelectorWidget, Orientation, Orientation>
{
    @Override
    public LssProperty getProperty(final SelectorWidget widget) {
        return widget.orientation();
    }
    
    @Override
    public Class<?> type() {
        return Orientation.class;
    }
    
    @Override
    public Orientation[] toArray(final Object[] objects) {
        final Orientation[] array = new Orientation[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (Orientation)objects[i];
        }
        return array;
    }
    
    @Override
    public Orientation[] toArray(final Collection<Orientation> collection) {
        return collection.toArray(new Orientation[0]);
    }
}
