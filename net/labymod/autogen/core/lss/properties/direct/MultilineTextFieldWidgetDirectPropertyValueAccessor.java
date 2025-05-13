// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.autogen.core.lss.properties.resetters.MultilineTextFieldWidgetLssPropertyResetter;
import net.labymod.autogen.core.lss.properties.accessors.MultilineTextFieldWidgetTextColorPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class MultilineTextFieldWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> textColor;
    LssPropertyResetter MultilineTextFieldWidgetResetter;
    
    public MultilineTextFieldWidgetDirectPropertyValueAccessor() {
        this.textColor = new MultilineTextFieldWidgetTextColorPropertyValueAccessor();
        this.MultilineTextFieldWidgetResetter = new MultilineTextFieldWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "textColor": {
                return this.textColor;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "textColor": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.MultilineTextFieldWidgetResetter;
    }
}
