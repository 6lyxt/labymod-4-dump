// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.LazyGridFeedWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridFeedWidgetRemoveLoadingTextPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridFeedWidgetLoadingColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridFeedWidgetLoadingTextPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridFeedWidgetLoadingTextGapPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.LazyGridFeedWidgetRefreshRadiusPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class LazyGridFeedWidgetDirectPropertyValueAccessor extends LazyGridWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> refreshRadius;
    protected PropertyValueAccessor<?, ?, ?> loadingTextGap;
    protected PropertyValueAccessor<?, ?, ?> loadingText;
    protected PropertyValueAccessor<?, ?, ?> loadingColor;
    protected PropertyValueAccessor<?, ?, ?> removeLoadingText;
    LssPropertyResetter LazyGridFeedWidgetResetter;
    
    public LazyGridFeedWidgetDirectPropertyValueAccessor() {
        this.refreshRadius = new LazyGridFeedWidgetRefreshRadiusPropertyValueAccessor();
        this.loadingTextGap = new LazyGridFeedWidgetLoadingTextGapPropertyValueAccessor();
        this.loadingText = new LazyGridFeedWidgetLoadingTextPropertyValueAccessor();
        this.loadingColor = new LazyGridFeedWidgetLoadingColorPropertyValueAccessor();
        this.removeLoadingText = new LazyGridFeedWidgetRemoveLoadingTextPropertyValueAccessor();
        this.LazyGridFeedWidgetResetter = new LazyGridFeedWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "refreshRadius": {
                return this.refreshRadius;
            }
            case "loadingTextGap": {
                return this.loadingTextGap;
            }
            case "loadingText": {
                return this.loadingText;
            }
            case "loadingColor": {
                return this.loadingColor;
            }
            case "removeLoadingText": {
                return this.removeLoadingText;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "refreshRadius": {
                return true;
            }
            case "loadingTextGap": {
                return true;
            }
            case "loadingText": {
                return true;
            }
            case "loadingColor": {
                return true;
            }
            case "removeLoadingText": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.LazyGridFeedWidgetResetter;
    }
}
