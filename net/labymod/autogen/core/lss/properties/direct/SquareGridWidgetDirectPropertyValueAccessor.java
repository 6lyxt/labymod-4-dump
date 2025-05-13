// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.autogen.core.lss.properties.resetters.SquareGridWidgetLssPropertyResetter;
import net.labymod.autogen.core.lss.properties.accessors.SquareGridWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.SquareGridWidgetPreferredSquareHeightPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class SquareGridWidgetDirectPropertyValueAccessor extends ListWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> preferredSquareHeight;
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries;
    LssPropertyResetter SquareGridWidgetResetter;
    
    public SquareGridWidgetDirectPropertyValueAccessor() {
        this.preferredSquareHeight = new SquareGridWidgetPreferredSquareHeightPropertyValueAccessor();
        this.spaceBetweenEntries = new SquareGridWidgetSpaceBetweenEntriesPropertyValueAccessor();
        this.SquareGridWidgetResetter = new SquareGridWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "preferredSquareHeight": {
                return this.preferredSquareHeight;
            }
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
            case "preferredSquareHeight": {
                return true;
            }
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
        return this.SquareGridWidgetResetter;
    }
}
