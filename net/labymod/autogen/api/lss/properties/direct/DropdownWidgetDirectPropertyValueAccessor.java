// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.DropdownWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.DropdownWidgetWrapperPaddingPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.DropdownWidgetArrowWidthPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class DropdownWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> arrowWidth;
    protected PropertyValueAccessor<?, ?, ?> wrapperPadding;
    LssPropertyResetter DropdownWidgetResetter;
    
    public DropdownWidgetDirectPropertyValueAccessor() {
        this.arrowWidth = new DropdownWidgetArrowWidthPropertyValueAccessor();
        this.wrapperPadding = new DropdownWidgetWrapperPaddingPropertyValueAccessor();
        this.DropdownWidgetResetter = new DropdownWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "arrowWidth": {
                return this.arrowWidth;
            }
            case "wrapperPadding": {
                return this.wrapperPadding;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "arrowWidth": {
                return true;
            }
            case "wrapperPadding": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.DropdownWidgetResetter;
    }
}
