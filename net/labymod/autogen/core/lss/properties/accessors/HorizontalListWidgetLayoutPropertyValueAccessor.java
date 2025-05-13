// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class HorizontalListWidgetLayoutPropertyValueAccessor implements PropertyValueAccessor<HorizontalListWidget, HorizontalListWidget.HorizontalListLayout, HorizontalListWidget.HorizontalListLayout>
{
    @Override
    public LssProperty getProperty(final HorizontalListWidget widget) {
        return widget.layout();
    }
    
    @Override
    public Class<?> type() {
        return HorizontalListWidget.HorizontalListLayout.class;
    }
    
    @Override
    public HorizontalListWidget.HorizontalListLayout[] toArray(final Object[] objects) {
        final HorizontalListWidget.HorizontalListLayout[] array = new HorizontalListWidget.HorizontalListLayout[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (HorizontalListWidget.HorizontalListLayout)objects[i];
        }
        return array;
    }
    
    @Override
    public HorizontalListWidget.HorizontalListLayout[] toArray(final Collection<HorizontalListWidget.HorizontalListLayout> collection) {
        return collection.toArray(new HorizontalListWidget.HorizontalListLayout[0]);
    }
}
