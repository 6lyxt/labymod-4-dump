// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.FlexibleContentEntryLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.FlexibleContentEntryIgnoreBoundsPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class FlexibleContentEntryDirectPropertyValueAccessor extends WrappedWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> ignoreBounds;
    LssPropertyResetter FlexibleContentEntryResetter;
    
    public FlexibleContentEntryDirectPropertyValueAccessor() {
        this.ignoreBounds = new FlexibleContentEntryIgnoreBoundsPropertyValueAccessor();
        this.FlexibleContentEntryResetter = new FlexibleContentEntryLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "ignoreBounds": {
                return this.ignoreBounds;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "ignoreBounds": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.FlexibleContentEntryResetter;
    }
}
