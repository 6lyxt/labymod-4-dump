// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.LazyGridWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridWidgetSpaceBetweenEntriesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridWidgetTileSizePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridWidgetTileWidthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridWidgetTileHeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridWidgetDynamicTilesPerLinePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridWidgetTilesPerLinePropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class LazyGridWidgetDirectPropertyValueAccessor extends SessionedListWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> tilesPerLine;
    protected PropertyValueAccessor<?, ?, ?> dynamicTilesPerLine;
    protected PropertyValueAccessor<?, ?, ?> tileHeight;
    protected PropertyValueAccessor<?, ?, ?> tileWidth;
    protected PropertyValueAccessor<?, ?, ?> tileSize;
    protected PropertyValueAccessor<?, ?, ?> spaceBetweenEntries;
    LssPropertyResetter LazyGridWidgetResetter;
    
    public LazyGridWidgetDirectPropertyValueAccessor() {
        this.tilesPerLine = new LazyGridWidgetTilesPerLinePropertyValueAccessor();
        this.dynamicTilesPerLine = new LazyGridWidgetDynamicTilesPerLinePropertyValueAccessor();
        this.tileHeight = new LazyGridWidgetTileHeightPropertyValueAccessor();
        this.tileWidth = new LazyGridWidgetTileWidthPropertyValueAccessor();
        this.tileSize = new LazyGridWidgetTileSizePropertyValueAccessor();
        this.spaceBetweenEntries = new LazyGridWidgetSpaceBetweenEntriesPropertyValueAccessor();
        this.LazyGridWidgetResetter = new LazyGridWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "tilesPerLine": {
                return this.tilesPerLine;
            }
            case "dynamicTilesPerLine": {
                return this.dynamicTilesPerLine;
            }
            case "tileHeight": {
                return this.tileHeight;
            }
            case "tileWidth": {
                return this.tileWidth;
            }
            case "tileSize": {
                return this.tileSize;
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
            case "tilesPerLine": {
                return true;
            }
            case "dynamicTilesPerLine": {
                return true;
            }
            case "tileHeight": {
                return true;
            }
            case "tileWidth": {
                return true;
            }
            case "tileSize": {
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
        return this.LazyGridWidgetResetter;
    }
}
