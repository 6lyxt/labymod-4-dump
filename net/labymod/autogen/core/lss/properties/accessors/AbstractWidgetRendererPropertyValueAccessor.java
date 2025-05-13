// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.screen.theme.renderer.ThemeRenderer;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class AbstractWidgetRendererPropertyValueAccessor implements PropertyValueAccessor<AbstractWidget, ThemeRenderer, ThemeRenderer>
{
    @Override
    public LssProperty getProperty(final AbstractWidget widget) {
        return widget.renderer();
    }
    
    @Override
    public Class<?> type() {
        return ThemeRenderer.class;
    }
    
    @Override
    public ThemeRenderer[] toArray(final Object[] objects) {
        final ThemeRenderer[] array = new ThemeRenderer[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (ThemeRenderer)objects[i];
        }
        return array;
    }
    
    @Override
    public ThemeRenderer[] toArray(final Collection<ThemeRenderer> collection) {
        return collection.toArray(new ThemeRenderer[0]);
    }
}
