// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.ScrollbarWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.ScrollbarWidgetMinScrollHeightPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollbarWidgetScrollBackgroundColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollbarWidgetScrollHoverColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollbarWidgetScrollColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollbarWidgetUseLssPositionPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollbarWidgetScrollButtonClickOffsetPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class ScrollbarWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> scrollButtonClickOffset;
    protected PropertyValueAccessor<?, ?, ?> useLssPosition;
    protected PropertyValueAccessor<?, ?, ?> scrollColor;
    protected PropertyValueAccessor<?, ?, ?> scrollHoverColor;
    protected PropertyValueAccessor<?, ?, ?> scrollBackgroundColor;
    protected PropertyValueAccessor<?, ?, ?> minScrollHeight;
    LssPropertyResetter ScrollbarWidgetResetter;
    
    public ScrollbarWidgetDirectPropertyValueAccessor() {
        this.scrollButtonClickOffset = new ScrollbarWidgetScrollButtonClickOffsetPropertyValueAccessor();
        this.useLssPosition = new ScrollbarWidgetUseLssPositionPropertyValueAccessor();
        this.scrollColor = new ScrollbarWidgetScrollColorPropertyValueAccessor();
        this.scrollHoverColor = new ScrollbarWidgetScrollHoverColorPropertyValueAccessor();
        this.scrollBackgroundColor = new ScrollbarWidgetScrollBackgroundColorPropertyValueAccessor();
        this.minScrollHeight = new ScrollbarWidgetMinScrollHeightPropertyValueAccessor();
        this.ScrollbarWidgetResetter = new ScrollbarWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "scrollButtonClickOffset": {
                return this.scrollButtonClickOffset;
            }
            case "useLssPosition": {
                return this.useLssPosition;
            }
            case "scrollColor": {
                return this.scrollColor;
            }
            case "scrollHoverColor": {
                return this.scrollHoverColor;
            }
            case "scrollBackgroundColor": {
                return this.scrollBackgroundColor;
            }
            case "minScrollHeight": {
                return this.minScrollHeight;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "scrollButtonClickOffset": {
                return true;
            }
            case "useLssPosition": {
                return true;
            }
            case "scrollColor": {
                return true;
            }
            case "scrollHoverColor": {
                return true;
            }
            case "scrollBackgroundColor": {
                return true;
            }
            case "minScrollHeight": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.ScrollbarWidgetResetter;
    }
}
