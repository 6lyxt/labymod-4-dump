// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.autogen.core.lss.properties.resetters.FlexibleContentWidgetLssPropertyResetter;
import net.labymod.autogen.core.lss.properties.accessors.FlexibleContentWidgetListOrderPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.FlexibleContentWidgetOrientationPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.FlexibleContentWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class FlexibleContentWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries;
    protected PropertyValueAccessor<?, ?, ?> orientation;
    protected PropertyValueAccessor<?, ?, ?> listOrder;
    LssPropertyResetter FlexibleContentWidgetResetter;
    
    public FlexibleContentWidgetDirectPropertyValueAccessor() {
        this.spaceBetweenEntries = new FlexibleContentWidgetSpaceBetweenEntriesPropertyValueAccessor();
        this.orientation = new FlexibleContentWidgetOrientationPropertyValueAccessor();
        this.listOrder = new FlexibleContentWidgetListOrderPropertyValueAccessor();
        this.FlexibleContentWidgetResetter = new FlexibleContentWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "spaceBetweenEntries": {
                return this.spaceBetweenEntries;
            }
            case "orientation": {
                return this.orientation;
            }
            case "listOrder": {
                return this.listOrder;
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
            case "orientation": {
                return true;
            }
            case "listOrder": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.FlexibleContentWidgetResetter;
    }
}
