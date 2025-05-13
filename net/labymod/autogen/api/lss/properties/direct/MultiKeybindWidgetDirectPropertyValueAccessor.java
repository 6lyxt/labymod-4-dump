// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.MultiKeybindWidgetLssPropertyResetter;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;

public class MultiKeybindWidgetDirectPropertyValueAccessor extends TextFieldWidgetDirectPropertyValueAccessor
{
    LssPropertyResetter MultiKeybindWidgetResetter;
    
    public MultiKeybindWidgetDirectPropertyValueAccessor() {
        this.MultiKeybindWidgetResetter = new MultiKeybindWidgetLssPropertyResetter();
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
        return this.MultiKeybindWidgetResetter;
    }
}
