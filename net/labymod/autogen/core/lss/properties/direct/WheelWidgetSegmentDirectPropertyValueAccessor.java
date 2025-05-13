// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.autogen.core.lss.properties.resetters.WheelWidgetSegmentLssPropertyResetter;
import net.labymod.autogen.core.lss.properties.accessors.SegmentSegmentColorPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class WheelWidgetSegmentDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> segmentColor;
    LssPropertyResetter SegmentResetter;
    
    public WheelWidgetSegmentDirectPropertyValueAccessor() {
        this.segmentColor = new SegmentSegmentColorPropertyValueAccessor();
        this.SegmentResetter = new WheelWidgetSegmentLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "segmentColor": {
                return this.segmentColor;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "segmentColor": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.SegmentResetter;
    }
}
