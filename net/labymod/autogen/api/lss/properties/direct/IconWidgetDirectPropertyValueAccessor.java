// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.IconWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetBlurryPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetZoomPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetCleanupOnDisposePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetObjectFitPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetClickablePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetColorTransitionDurationPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.IconWidgetIconPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class IconWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> icon;
    protected PropertyValueAccessor<?, ?, ?> color;
    protected PropertyValueAccessor<?, ?, ?> colorTransitionDuration;
    protected PropertyValueAccessor<?, ?, ?> clickable;
    protected PropertyValueAccessor<?, ?, ?> objectFit;
    protected PropertyValueAccessor<?, ?, ?> cleanupOnDispose;
    protected PropertyValueAccessor<?, ?, ?> zoom;
    protected PropertyValueAccessor<?, ?, ?> blurry;
    LssPropertyResetter IconWidgetResetter;
    
    public IconWidgetDirectPropertyValueAccessor() {
        this.icon = new IconWidgetIconPropertyValueAccessor();
        this.color = new IconWidgetColorPropertyValueAccessor();
        this.colorTransitionDuration = new IconWidgetColorTransitionDurationPropertyValueAccessor();
        this.clickable = new IconWidgetClickablePropertyValueAccessor();
        this.objectFit = new IconWidgetObjectFitPropertyValueAccessor();
        this.cleanupOnDispose = new IconWidgetCleanupOnDisposePropertyValueAccessor();
        this.zoom = new IconWidgetZoomPropertyValueAccessor();
        this.blurry = new IconWidgetBlurryPropertyValueAccessor();
        this.IconWidgetResetter = new IconWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "icon": {
                return this.icon;
            }
            case "color": {
                return this.color;
            }
            case "colorTransitionDuration": {
                return this.colorTransitionDuration;
            }
            case "clickable": {
                return this.clickable;
            }
            case "objectFit": {
                return this.objectFit;
            }
            case "cleanupOnDispose": {
                return this.cleanupOnDispose;
            }
            case "zoom": {
                return this.zoom;
            }
            case "blurry": {
                return this.blurry;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "icon": {
                return true;
            }
            case "color": {
                return true;
            }
            case "colorTransitionDuration": {
                return true;
            }
            case "clickable": {
                return true;
            }
            case "objectFit": {
                return true;
            }
            case "cleanupOnDispose": {
                return true;
            }
            case "zoom": {
                return true;
            }
            case "blurry": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.IconWidgetResetter;
    }
}
