// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.autogen.core.lss.properties.resetters.NavigationListWidgetLssPropertyResetter;
import net.labymod.autogen.core.lss.properties.accessors.NavigationListWidgetMaxPaddingPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.NavigationListWidgetPriorityAlignmentPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class NavigationListWidgetDirectPropertyValueAccessor extends HorizontalListWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> priorityAlignment;
    protected PropertyValueAccessor<?, ?, ?> maxPadding;
    LssPropertyResetter NavigationListWidgetResetter;
    
    public NavigationListWidgetDirectPropertyValueAccessor() {
        this.priorityAlignment = new NavigationListWidgetPriorityAlignmentPropertyValueAccessor();
        this.maxPadding = new NavigationListWidgetMaxPaddingPropertyValueAccessor();
        this.NavigationListWidgetResetter = new NavigationListWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "priorityAlignment": {
                return this.priorityAlignment;
            }
            case "maxPadding": {
                return this.maxPadding;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "priorityAlignment": {
                return true;
            }
            case "maxPadding": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.NavigationListWidgetResetter;
    }
}
