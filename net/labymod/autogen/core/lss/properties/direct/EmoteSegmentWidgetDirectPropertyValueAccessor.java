// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.EmoteSegmentWidgetLssPropertyResetter;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;

public class EmoteSegmentWidgetDirectPropertyValueAccessor extends WheelWidgetSegmentDirectPropertyValueAccessor
{
    LssPropertyResetter EmoteSegmentWidgetResetter;
    
    public EmoteSegmentWidgetDirectPropertyValueAccessor() {
        this.EmoteSegmentWidgetResetter = new EmoteSegmentWidgetLssPropertyResetter();
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
        return this.EmoteSegmentWidgetResetter;
    }
}
