// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.autogen.core.lss.properties.resetters.HudWidgetTilesGridWidgetLssPropertyResetter;
import net.labymod.autogen.core.lss.properties.accessors.HudWidgetTilesGridWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class HudWidgetTilesGridWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries;
    LssPropertyResetter HudWidgetTilesGridWidgetResetter;
    
    public HudWidgetTilesGridWidgetDirectPropertyValueAccessor() {
        this.spaceBetweenEntries = new HudWidgetTilesGridWidgetSpaceBetweenEntriesPropertyValueAccessor();
        this.HudWidgetTilesGridWidgetResetter = new HudWidgetTilesGridWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "spaceBetweenEntries": {
                return this.spaceBetweenEntries;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "spaceBetweenEntries": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.HudWidgetTilesGridWidgetResetter;
    }
}
