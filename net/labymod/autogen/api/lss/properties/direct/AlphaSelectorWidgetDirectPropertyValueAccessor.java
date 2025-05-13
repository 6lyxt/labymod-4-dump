// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.AlphaSelectorWidgetLssPropertyResetter;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;

public class AlphaSelectorWidgetDirectPropertyValueAccessor extends SelectorWidgetDirectPropertyValueAccessor
{
    LssPropertyResetter AlphaSelectorWidgetResetter;
    
    public AlphaSelectorWidgetDirectPropertyValueAccessor() {
        this.AlphaSelectorWidgetResetter = new AlphaSelectorWidgetLssPropertyResetter();
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
        return this.AlphaSelectorWidgetResetter;
    }
}
