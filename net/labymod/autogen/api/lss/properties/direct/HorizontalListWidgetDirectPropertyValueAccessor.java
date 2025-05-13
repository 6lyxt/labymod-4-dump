// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.HorizontalListWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListWidgetLayoutPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListWidgetSpaceRightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.HorizontalListWidgetSpaceLeftPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class HorizontalListWidgetDirectPropertyValueAccessor extends ListWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> spaceLeft;
    protected PropertyValueAccessor<?, ?, ?> spaceRight;
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries;
    protected PropertyValueAccessor<?, ?, ?> layout;
    LssPropertyResetter HorizontalListWidgetResetter;
    
    public HorizontalListWidgetDirectPropertyValueAccessor() {
        this.spaceLeft = new HorizontalListWidgetSpaceLeftPropertyValueAccessor();
        this.spaceRight = new HorizontalListWidgetSpaceRightPropertyValueAccessor();
        this.spaceBetweenEntries = new HorizontalListWidgetSpaceBetweenEntriesPropertyValueAccessor();
        this.layout = new HorizontalListWidgetLayoutPropertyValueAccessor();
        this.HorizontalListWidgetResetter = new HorizontalListWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "spaceLeft": {
                return this.spaceLeft;
            }
            case "spaceRight": {
                return this.spaceRight;
            }
            case "spaceBetweenEntries": {
                return this.spaceBetweenEntries;
            }
            case "layout": {
                return this.layout;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "spaceLeft": {
                return true;
            }
            case "spaceRight": {
                return true;
            }
            case "spaceBetweenEntries": {
                return true;
            }
            case "layout": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.HorizontalListWidgetResetter;
    }
}
