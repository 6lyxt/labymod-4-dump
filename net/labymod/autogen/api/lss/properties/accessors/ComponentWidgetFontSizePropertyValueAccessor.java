// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.accessors;

import net.labymod.api.client.gui.screen.widget.Widget;
import java.util.Collection;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.render.font.FontSize;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public final class ComponentWidgetFontSizePropertyValueAccessor implements PropertyValueAccessor<ComponentWidget, FontSize, FontSize>
{
    @Override
    public LssProperty getProperty(final ComponentWidget widget) {
        return widget.fontSize();
    }
    
    @Override
    public Class<?> type() {
        return FontSize.class;
    }
    
    @Override
    public FontSize[] toArray(final Object[] objects) {
        final FontSize[] array = new FontSize[objects.length];
        for (int i = 0; i < objects.length; ++i) {
            array[i] = (FontSize)objects[i];
        }
        return array;
    }
    
    @Override
    public FontSize[] toArray(final Collection<FontSize> collection) {
        return collection.toArray(new FontSize[0]);
    }
}
