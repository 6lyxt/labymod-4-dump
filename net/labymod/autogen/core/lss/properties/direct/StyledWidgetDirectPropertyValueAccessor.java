// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.StyledWidgetLssPropertyResetter;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.DirectPropertyValueAccessor;

public class StyledWidgetDirectPropertyValueAccessor implements DirectPropertyValueAccessor
{
    LssPropertyResetter StyledWidgetResetter;
    
    public StyledWidgetDirectPropertyValueAccessor() {
        this.StyledWidgetResetter = new StyledWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        return null;
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        return false;
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.StyledWidgetResetter;
    }
}
