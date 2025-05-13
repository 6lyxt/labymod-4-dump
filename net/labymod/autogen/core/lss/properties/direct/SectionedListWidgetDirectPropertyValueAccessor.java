// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.autogen.core.lss.properties.resetters.SectionedListWidgetLssPropertyResetter;
import net.labymod.autogen.core.lss.properties.accessors.SectionedListWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class SectionedListWidgetDirectPropertyValueAccessor extends SessionedListWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries;
    LssPropertyResetter SectionedListWidgetResetter;
    
    public SectionedListWidgetDirectPropertyValueAccessor() {
        this.spaceBetweenEntries = new SectionedListWidgetSpaceBetweenEntriesPropertyValueAccessor();
        this.SectionedListWidgetResetter = new SectionedListWidgetLssPropertyResetter();
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
        return this.SectionedListWidgetResetter;
    }
}
