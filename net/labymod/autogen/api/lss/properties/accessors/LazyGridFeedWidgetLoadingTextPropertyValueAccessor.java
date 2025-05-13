// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.widgets.layout.grid.LazyGridFeedWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class LazyGridFeedWidgetLoadingTextPropertyValueAccessor implements PropertyValueAccessor<LazyGridFeedWidget, String, String>
{
    @Override
    public LssProperty getProperty(final LazyGridFeedWidget widget) {
        return widget.loadingText();
    }
    
    @Override
    public Class<?> type() {
        return String.class;
    }
    
    @Override
    public String[] toArray(final Object[] objects) {
        final String[] array = new String[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (String)objects[i];
        }
        return array;
    }
    
    @Override
    public String[] toArray(final Collection<String> collection) {
        return collection.toArray(new String[0]);
    }
}
