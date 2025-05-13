// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.ButtonWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.ButtonWidgetAlignmentPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ButtonWidgetTextPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ButtonWidgetIconPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ButtonWidgetDisabledColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ButtonWidgetScaleToFitPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class ButtonWidgetDirectPropertyValueAccessor extends HorizontalListWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> scaleToFit;
    protected PropertyValueAccessor<?, ?, ?> disabledColor;
    protected PropertyValueAccessor<?, ?, ?> icon;
    protected PropertyValueAccessor<?, ?, ?> text;
    protected PropertyValueAccessor<?, ?, ?> alignment;
    LssPropertyResetter ButtonWidgetResetter;
    
    public ButtonWidgetDirectPropertyValueAccessor() {
        this.scaleToFit = new ButtonWidgetScaleToFitPropertyValueAccessor();
        this.disabledColor = new ButtonWidgetDisabledColorPropertyValueAccessor();
        this.icon = new ButtonWidgetIconPropertyValueAccessor();
        this.text = new ButtonWidgetTextPropertyValueAccessor();
        this.alignment = new ButtonWidgetAlignmentPropertyValueAccessor();
        this.ButtonWidgetResetter = new ButtonWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "scaleToFit": {
                return this.scaleToFit;
            }
            case "disabledColor": {
                return this.disabledColor;
            }
            case "icon": {
                return this.icon;
            }
            case "text": {
                return this.text;
            }
            case "alignment": {
                return this.alignment;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "scaleToFit": {
                return true;
            }
            case "disabledColor": {
                return true;
            }
            case "icon": {
                return true;
            }
            case "text": {
                return true;
            }
            case "alignment": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.ButtonWidgetResetter;
    }
}
