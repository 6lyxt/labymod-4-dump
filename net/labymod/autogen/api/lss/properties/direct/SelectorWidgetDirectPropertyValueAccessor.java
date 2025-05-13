// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.SelectorWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.SelectorWidgetOrientationPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class SelectorWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> orientation;
    LssPropertyResetter SelectorWidgetResetter;
    
    public SelectorWidgetDirectPropertyValueAccessor() {
        this.orientation = new SelectorWidgetOrientationPropertyValueAccessor();
        this.SelectorWidgetResetter = new SelectorWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "orientation": {
                return this.orientation;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "orientation": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.SelectorWidgetResetter;
    }
}
