// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class IconWidgetColorTransitionDurationPropertyValueAccessor implements PropertyValueAccessor<IconWidget, Long, Long>
{
    @Override
    public LssProperty getProperty(final IconWidget widget) {
        return widget.colorTransitionDuration();
    }
    
    @Override
    public Class<?> type() {
        return Long.class;
    }
    
    @Override
    public Long[] toArray(final Object[] objects) {
        final Long[] array = new Long[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (Long)objects[i];
        }
        return array;
    }
    
    @Override
    public Long[] toArray(final Collection<Long> collection) {
        return collection.toArray(new Long[0]);
    }
}
