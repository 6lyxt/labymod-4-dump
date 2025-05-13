// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.ScrollWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetModifyContentWidthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetAutoScrollPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetFixedPositionPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetMoveDirtBackgroundPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetScrollAlwaysVisiblePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetScrollSpeedPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetEnableScrollContentPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ScrollWidgetChildAlignPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class ScrollWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> childAlign;
    protected PropertyValueAccessor<?, ?, ?> enableScrollContent;
    protected PropertyValueAccessor<?, ?, ?> scrollSpeed;
    protected PropertyValueAccessor<?, ?, ?> scrollAlwaysVisible;
    protected PropertyValueAccessor<?, ?, ?> moveDirtBackground;
    protected PropertyValueAccessor<?, ?, ?> fixedPosition;
    protected PropertyValueAccessor<?, ?, ?> autoScroll;
    protected PropertyValueAccessor<?, ?, ?> modifyContentWidth;
    LssPropertyResetter ScrollWidgetResetter;
    
    public ScrollWidgetDirectPropertyValueAccessor() {
        this.childAlign = new ScrollWidgetChildAlignPropertyValueAccessor();
        this.enableScrollContent = new ScrollWidgetEnableScrollContentPropertyValueAccessor();
        this.scrollSpeed = new ScrollWidgetScrollSpeedPropertyValueAccessor();
        this.scrollAlwaysVisible = new ScrollWidgetScrollAlwaysVisiblePropertyValueAccessor();
        this.moveDirtBackground = new ScrollWidgetMoveDirtBackgroundPropertyValueAccessor();
        this.fixedPosition = new ScrollWidgetFixedPositionPropertyValueAccessor();
        this.autoScroll = new ScrollWidgetAutoScrollPropertyValueAccessor();
        this.modifyContentWidth = new ScrollWidgetModifyContentWidthPropertyValueAccessor();
        this.ScrollWidgetResetter = new ScrollWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "childAlign": {
                return this.childAlign;
            }
            case "enableScrollContent": {
                return this.enableScrollContent;
            }
            case "scrollSpeed": {
                return this.scrollSpeed;
            }
            case "scrollAlwaysVisible": {
                return this.scrollAlwaysVisible;
            }
            case "moveDirtBackground": {
                return this.moveDirtBackground;
            }
            case "fixedPosition": {
                return this.fixedPosition;
            }
            case "autoScroll": {
                return this.autoScroll;
            }
            case "modifyContentWidth": {
                return this.modifyContentWidth;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "childAlign": {
                return true;
            }
            case "enableScrollContent": {
                return true;
            }
            case "scrollSpeed": {
                return true;
            }
            case "scrollAlwaysVisible": {
                return true;
            }
            case "moveDirtBackground": {
                return true;
            }
            case "fixedPosition": {
                return true;
            }
            case "autoScroll": {
                return true;
            }
            case "modifyContentWidth": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.ScrollWidgetResetter;
    }
}
