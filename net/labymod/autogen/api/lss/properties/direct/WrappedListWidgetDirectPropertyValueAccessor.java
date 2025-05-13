// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.WrappedListWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.WrappedListWidgetMaxLinesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedListWidgetHorizontalSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedListWidgetVerticalSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.WrappedListWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class WrappedListWidgetDirectPropertyValueAccessor extends ListWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries;
    protected PropertyValueAccessor<?, ?, ?> verticalSpaceBetweenEntries;
    protected PropertyValueAccessor<?, ?, ?> horizontalSpaceBetweenEntries;
    protected PropertyValueAccessor<?, ?, ?> maxLines;
    LssPropertyResetter WrappedListWidgetResetter;
    
    public WrappedListWidgetDirectPropertyValueAccessor() {
        this.spaceBetweenEntries = new WrappedListWidgetSpaceBetweenEntriesPropertyValueAccessor();
        this.verticalSpaceBetweenEntries = new WrappedListWidgetVerticalSpaceBetweenEntriesPropertyValueAccessor();
        this.horizontalSpaceBetweenEntries = new WrappedListWidgetHorizontalSpaceBetweenEntriesPropertyValueAccessor();
        this.maxLines = new WrappedListWidgetMaxLinesPropertyValueAccessor();
        this.WrappedListWidgetResetter = new WrappedListWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "spaceBetweenEntries": {
                return this.spaceBetweenEntries;
            }
            case "verticalSpaceBetweenEntries": {
                return this.verticalSpaceBetweenEntries;
            }
            case "horizontalSpaceBetweenEntries": {
                return this.horizontalSpaceBetweenEntries;
            }
            case "maxLines": {
                return this.maxLines;
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
            case "verticalSpaceBetweenEntries": {
                return true;
            }
            case "horizontalSpaceBetweenEntries": {
                return true;
            }
            case "maxLines": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.WrappedListWidgetResetter;
    }
}
