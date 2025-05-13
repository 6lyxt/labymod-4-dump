// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.TilesGridWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.TilesGridWidgetTileHeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TilesGridWidgetTilesPerLinePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TilesGridWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class TilesGridWidgetDirectPropertyValueAccessor extends ListWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries;
    protected PropertyValueAccessor<?, ?, ?> tilesPerLine;
    protected PropertyValueAccessor<?, ?, ?> tileHeight;
    LssPropertyResetter TilesGridWidgetResetter;
    
    public TilesGridWidgetDirectPropertyValueAccessor() {
        this.spaceBetweenEntries = new TilesGridWidgetSpaceBetweenEntriesPropertyValueAccessor();
        this.tilesPerLine = new TilesGridWidgetTilesPerLinePropertyValueAccessor();
        this.tileHeight = new TilesGridWidgetTileHeightPropertyValueAccessor();
        this.TilesGridWidgetResetter = new TilesGridWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "spaceBetweenEntries": {
                return this.spaceBetweenEntries;
            }
            case "tilesPerLine": {
                return this.tilesPerLine;
            }
            case "tileHeight": {
                return this.tileHeight;
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
            case "tilesPerLine": {
                return true;
            }
            case "tileHeight": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.TilesGridWidgetResetter;
    }
}
