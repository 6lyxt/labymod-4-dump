// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.resetters.HeartWidgetLssPropertyResetter;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;

public class HeartWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor
{
    LssPropertyResetter HeartWidgetResetter;
    
    public HeartWidgetDirectPropertyValueAccessor() {
        this.HeartWidgetResetter = new HeartWidgetLssPropertyResetter();
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
        return this.HeartWidgetResetter;
    }
}
