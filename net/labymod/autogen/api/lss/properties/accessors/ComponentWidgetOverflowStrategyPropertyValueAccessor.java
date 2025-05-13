// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.render.font.TextOverflowStrategy;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class ComponentWidgetOverflowStrategyPropertyValueAccessor implements PropertyValueAccessor<ComponentWidget, TextOverflowStrategy, TextOverflowStrategy>
{
    @Override
    public LssProperty getProperty(final ComponentWidget widget) {
        return widget.overflowStrategy();
    }
    
    @Override
    public Class<?> type() {
        return TextOverflowStrategy.class;
    }
    
    @Override
    public TextOverflowStrategy[] toArray(final Object[] objects) {
        final TextOverflowStrategy[] array = new TextOverflowStrategy[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (TextOverflowStrategy)objects[i];
        }
        return array;
    }
    
    @Override
    public TextOverflowStrategy[] toArray(final Collection<TextOverflowStrategy> collection) {
        return collection.toArray(new TextOverflowStrategy[0]);
    }
}
