// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.TilesGridFeedWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.TilesGridFeedWidgetRefreshRadiusPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class TilesGridFeedWidgetDirectPropertyValueAccessor extends TilesGridWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> refreshRadius;
    LssPropertyResetter TilesGridFeedWidgetResetter;
    
    public TilesGridFeedWidgetDirectPropertyValueAccessor() {
        this.refreshRadius = new TilesGridFeedWidgetRefreshRadiusPropertyValueAccessor();
        this.TilesGridFeedWidgetResetter = new TilesGridFeedWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "refreshRadius": {
                return this.refreshRadius;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "refreshRadius": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.TilesGridFeedWidgetResetter;
    }
}
