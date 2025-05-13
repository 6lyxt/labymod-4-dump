// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.ColorPickerSelectorWidgetLssPropertyResetter;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;

public class ColorPickerSelectorWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    LssPropertyResetter ColorPickerSelectorWidgetResetter;
    
    public ColorPickerSelectorWidgetDirectPropertyValueAccessor() {
        this.ColorPickerSelectorWidgetResetter = new ColorPickerSelectorWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        return super.getPropertyValueAccessor(key);
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        return super.hasPropertyValueAccessor(key);
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.ColorPickerSelectorWidgetResetter;
    }
}
