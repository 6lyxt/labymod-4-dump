// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.SwitchWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.SwitchWidgetTextHoverColorPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class SwitchWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> textHoverColor;
    LssPropertyResetter SwitchWidgetResetter;
    
    public SwitchWidgetDirectPropertyValueAccessor() {
        this.textHoverColor = new SwitchWidgetTextHoverColorPropertyValueAccessor();
        this.SwitchWidgetResetter = new SwitchWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "textHoverColor": {
                return this.textHoverColor;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "textHoverColor": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.SwitchWidgetResetter;
    }
}
