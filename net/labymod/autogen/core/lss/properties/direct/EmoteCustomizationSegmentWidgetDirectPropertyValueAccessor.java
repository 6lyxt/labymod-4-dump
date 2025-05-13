// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.EmoteCustomizationSegmentWidgetLssPropertyResetter;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;

public class EmoteCustomizationSegmentWidgetDirectPropertyValueAccessor extends WheelWidgetSegmentDirectPropertyValueAccessor
{
    LssPropertyResetter EmoteCustomizationSegmentWidgetResetter;
    
    public EmoteCustomizationSegmentWidgetDirectPropertyValueAccessor() {
        this.EmoteCustomizationSegmentWidgetResetter = new EmoteCustomizationSegmentWidgetLssPropertyResetter();
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
        return this.EmoteCustomizationSegmentWidgetResetter;
    }
}
