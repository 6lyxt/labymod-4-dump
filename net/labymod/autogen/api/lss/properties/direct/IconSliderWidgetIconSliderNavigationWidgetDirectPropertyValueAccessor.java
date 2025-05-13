// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.IconSliderWidgetIconSliderNavigationWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.IconSliderNavigationWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class IconSliderWidgetIconSliderNavigationWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries;
    LssPropertyResetter IconSliderNavigationWidgetResetter;
    
    public IconSliderWidgetIconSliderNavigationWidgetDirectPropertyValueAccessor() {
        this.spaceBetweenEntries = new IconSliderNavigationWidgetSpaceBetweenEntriesPropertyValueAccessor();
        this.IconSliderNavigationWidgetResetter = new IconSliderWidgetIconSliderNavigationWidgetLssPropertyResetter();
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
        return this.IconSliderNavigationWidgetResetter;
    }
}
