// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.LabyConnectRequestsButtonWidgetLssPropertyResetter;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;

public class LabyConnectRequestsButtonWidgetDirectPropertyValueAccessor extends LabyConnectEntryWidgetDirectPropertyValueAccessor
{
    LssPropertyResetter LabyConnectRequestsButtonWidgetResetter;
    
    public LabyConnectRequestsButtonWidgetDirectPropertyValueAccessor() {
        this.LabyConnectRequestsButtonWidgetResetter = new LabyConnectRequestsButtonWidgetLssPropertyResetter();
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
        return this.LabyConnectRequestsButtonWidgetResetter;
    }
}
