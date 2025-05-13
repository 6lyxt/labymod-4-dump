// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.NewWindowWidgetLssPropertyResetter;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;

public class NewWindowWidgetDirectPropertyValueAccessor extends InterpolateWidgetDirectPropertyValueAccessor
{
    LssPropertyResetter NewWindowWidgetResetter;
    
    public NewWindowWidgetDirectPropertyValueAccessor() {
        this.NewWindowWidgetResetter = new NewWindowWidgetLssPropertyResetter();
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
        return this.NewWindowWidgetResetter;
    }
}
