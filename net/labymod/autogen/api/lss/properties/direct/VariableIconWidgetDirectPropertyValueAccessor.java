// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.VariableIconWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.VariableIconWidgetIconHeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.VariableIconWidgetIconWidthPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class VariableIconWidgetDirectPropertyValueAccessor extends IconWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> iconWidth;
    protected PropertyValueAccessor<?, ?, ?> iconHeight;
    LssPropertyResetter VariableIconWidgetResetter;
    
    public VariableIconWidgetDirectPropertyValueAccessor() {
        this.iconWidth = new VariableIconWidgetIconWidthPropertyValueAccessor();
        this.iconHeight = new VariableIconWidgetIconHeightPropertyValueAccessor();
        this.VariableIconWidgetResetter = new VariableIconWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "iconWidth": {
                return this.iconWidth;
            }
            case "iconHeight": {
                return this.iconHeight;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "iconWidth": {
                return true;
            }
            case "iconHeight": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.VariableIconWidgetResetter;
    }
}
