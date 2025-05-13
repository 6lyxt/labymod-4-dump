// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.ComponentWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetTextColorTransitionDurationPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetVisualShiftPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetMaxLinesClipTextPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetClippingTextTooltipPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetUseChatOptionsPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetLeadingSpacesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetMaxLinesPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetCachePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetScaleToFitPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetFontSizePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetLineSpacingPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetShadowPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetAllowColorsPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetIconColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetTextColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetRenderHoverPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetOverflowStrategyPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ComponentWidgetForceVanillaFontPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class ComponentWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> forceVanillaFont;
    protected PropertyValueAccessor<?, ?, ?> overflowStrategy;
    protected PropertyValueAccessor<?, ?, ?> renderHover;
    protected PropertyValueAccessor<?, ?, ?> textColor;
    protected PropertyValueAccessor<?, ?, ?> iconColor;
    protected PropertyValueAccessor<?, ?, ?> allowColors;
    protected PropertyValueAccessor<?, ?, ?> shadow;
    protected PropertyValueAccessor<?, ?, ?> lineSpacing;
    protected PropertyValueAccessor<?, ?, ?> fontSize;
    protected PropertyValueAccessor<?, ?, ?> scaleToFit;
    protected PropertyValueAccessor<?, ?, ?> cache;
    protected PropertyValueAccessor<?, ?, ?> maxLines;
    protected PropertyValueAccessor<?, ?, ?> leadingSpaces;
    protected PropertyValueAccessor<?, ?, ?> useChatOptions;
    protected PropertyValueAccessor<?, ?, ?> clippingTextTooltip;
    protected PropertyValueAccessor<?, ?, ?> maxLinesClipText;
    protected PropertyValueAccessor<?, ?, ?> visualShift;
    protected PropertyValueAccessor<?, ?, ?> textColorTransitionDuration;
    LssPropertyResetter ComponentWidgetResetter;
    
    public ComponentWidgetDirectPropertyValueAccessor() {
        this.forceVanillaFont = new ComponentWidgetForceVanillaFontPropertyValueAccessor();
        this.overflowStrategy = new ComponentWidgetOverflowStrategyPropertyValueAccessor();
        this.renderHover = new ComponentWidgetRenderHoverPropertyValueAccessor();
        this.textColor = new ComponentWidgetTextColorPropertyValueAccessor();
        this.iconColor = new ComponentWidgetIconColorPropertyValueAccessor();
        this.allowColors = new ComponentWidgetAllowColorsPropertyValueAccessor();
        this.shadow = new ComponentWidgetShadowPropertyValueAccessor();
        this.lineSpacing = new ComponentWidgetLineSpacingPropertyValueAccessor();
        this.fontSize = new ComponentWidgetFontSizePropertyValueAccessor();
        this.scaleToFit = new ComponentWidgetScaleToFitPropertyValueAccessor();
        this.cache = new ComponentWidgetCachePropertyValueAccessor();
        this.maxLines = new ComponentWidgetMaxLinesPropertyValueAccessor();
        this.leadingSpaces = new ComponentWidgetLeadingSpacesPropertyValueAccessor();
        this.useChatOptions = new ComponentWidgetUseChatOptionsPropertyValueAccessor();
        this.clippingTextTooltip = new ComponentWidgetClippingTextTooltipPropertyValueAccessor();
        this.maxLinesClipText = new ComponentWidgetMaxLinesClipTextPropertyValueAccessor();
        this.visualShift = new ComponentWidgetVisualShiftPropertyValueAccessor();
        this.textColorTransitionDuration = new ComponentWidgetTextColorTransitionDurationPropertyValueAccessor();
        this.ComponentWidgetResetter = new ComponentWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "forceVanillaFont": {
                return this.forceVanillaFont;
            }
            case "overflowStrategy": {
                return this.overflowStrategy;
            }
            case "renderHover": {
                return this.renderHover;
            }
            case "textColor": {
                return this.textColor;
            }
            case "iconColor": {
                return this.iconColor;
            }
            case "allowColors": {
                return this.allowColors;
            }
            case "shadow": {
                return this.shadow;
            }
            case "lineSpacing": {
                return this.lineSpacing;
            }
            case "fontSize": {
                return this.fontSize;
            }
            case "scaleToFit": {
                return this.scaleToFit;
            }
            case "cache": {
                return this.cache;
            }
            case "maxLines": {
                return this.maxLines;
            }
            case "leadingSpaces": {
                return this.leadingSpaces;
            }
            case "useChatOptions": {
                return this.useChatOptions;
            }
            case "clippingTextTooltip": {
                return this.clippingTextTooltip;
            }
            case "maxLinesClipText": {
                return this.maxLinesClipText;
            }
            case "visualShift": {
                return this.visualShift;
            }
            case "textColorTransitionDuration": {
                return this.textColorTransitionDuration;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "forceVanillaFont": {
                return true;
            }
            case "overflowStrategy": {
                return true;
            }
            case "renderHover": {
                return true;
            }
            case "textColor": {
                return true;
            }
            case "iconColor": {
                return true;
            }
            case "allowColors": {
                return true;
            }
            case "shadow": {
                return true;
            }
            case "lineSpacing": {
                return true;
            }
            case "fontSize": {
                return true;
            }
            case "scaleToFit": {
                return true;
            }
            case "cache": {
                return true;
            }
            case "maxLines": {
                return true;
            }
            case "leadingSpaces": {
                return true;
            }
            case "useChatOptions": {
                return true;
            }
            case "clippingTextTooltip": {
                return true;
            }
            case "maxLinesClipText": {
                return true;
            }
            case "visualShift": {
                return true;
            }
            case "textColorTransitionDuration": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.ComponentWidgetResetter;
    }
}
