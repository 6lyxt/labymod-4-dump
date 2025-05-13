// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.DropdownEntryWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.DropdownEntryWidgetFontSizePropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class DropdownEntryWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> fontSize;
    LssPropertyResetter DropdownEntryWidgetResetter;
    
    public DropdownEntryWidgetDirectPropertyValueAccessor() {
        this.fontSize = new DropdownEntryWidgetFontSizePropertyValueAccessor();
        this.DropdownEntryWidgetResetter = new DropdownEntryWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "fontSize": {
                return this.fontSize;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "fontSize": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.DropdownEntryWidgetResetter;
    }
}
