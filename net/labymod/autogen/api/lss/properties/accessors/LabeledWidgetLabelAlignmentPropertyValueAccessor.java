// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.screen.widget.widgets.layout.LabeledWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class LabeledWidgetLabelAlignmentPropertyValueAccessor implements PropertyValueAccessor<LabeledWidget, HorizontalAlignment, HorizontalAlignment>
{
    @Override
    public LssProperty getProperty(final LabeledWidget widget) {
        return widget.labelAlignment();
    }
    
    @Override
    public Class<?> type() {
        return HorizontalAlignment.class;
    }
    
    @Override
    public HorizontalAlignment[] toArray(final Object[] objects) {
        final HorizontalAlignment[] array = new HorizontalAlignment[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (HorizontalAlignment)objects[i];
        }
        return array;
    }
    
    @Override
    public HorizontalAlignment[] toArray(final Collection<HorizontalAlignment> collection) {
        return collection.toArray(new HorizontalAlignment[0]);
    }
}
