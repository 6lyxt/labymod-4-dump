// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.TextFieldWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetTextShadowPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetFontSizePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetSubmitButtonPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetClearButtonPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetCursorColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetMarkColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetMarkTextColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetTextColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetPlaceHolderColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetTextAlignmentYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetTextAlignmentXPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.TextFieldWidgetTypePropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class TextFieldWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> type;
    protected PropertyValueAccessor<?, ?, ?> textAlignmentX;
    protected PropertyValueAccessor<?, ?, ?> textAlignmentY;
    protected PropertyValueAccessor<?, ?, ?> placeHolderColor;
    protected PropertyValueAccessor<?, ?, ?> textColor;
    protected PropertyValueAccessor<?, ?, ?> markTextColor;
    protected PropertyValueAccessor<?, ?, ?> markColor;
    protected PropertyValueAccessor<?, ?, ?> cursorColor;
    protected PropertyValueAccessor<?, ?, ?> clearButton;
    protected PropertyValueAccessor<?, ?, ?> submitButton;
    protected PropertyValueAccessor<?, ?, ?> fontSize;
    protected PropertyValueAccessor<?, ?, ?> textShadow;
    LssPropertyResetter TextFieldWidgetResetter;
    
    public TextFieldWidgetDirectPropertyValueAccessor() {
        this.type = new TextFieldWidgetTypePropertyValueAccessor();
        this.textAlignmentX = new TextFieldWidgetTextAlignmentXPropertyValueAccessor();
        this.textAlignmentY = new TextFieldWidgetTextAlignmentYPropertyValueAccessor();
        this.placeHolderColor = new TextFieldWidgetPlaceHolderColorPropertyValueAccessor();
        this.textColor = new TextFieldWidgetTextColorPropertyValueAccessor();
        this.markTextColor = new TextFieldWidgetMarkTextColorPropertyValueAccessor();
        this.markColor = new TextFieldWidgetMarkColorPropertyValueAccessor();
        this.cursorColor = new TextFieldWidgetCursorColorPropertyValueAccessor();
        this.clearButton = new TextFieldWidgetClearButtonPropertyValueAccessor();
        this.submitButton = new TextFieldWidgetSubmitButtonPropertyValueAccessor();
        this.fontSize = new TextFieldWidgetFontSizePropertyValueAccessor();
        this.textShadow = new TextFieldWidgetTextShadowPropertyValueAccessor();
        this.TextFieldWidgetResetter = new TextFieldWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "type": {
                return this.type;
            }
            case "textAlignmentX": {
                return this.textAlignmentX;
            }
            case "textAlignmentY": {
                return this.textAlignmentY;
            }
            case "placeHolderColor": {
                return this.placeHolderColor;
            }
            case "textColor": {
                return this.textColor;
            }
            case "markTextColor": {
                return this.markTextColor;
            }
            case "markColor": {
                return this.markColor;
            }
            case "cursorColor": {
                return this.cursorColor;
            }
            case "clearButton": {
                return this.clearButton;
            }
            case "submitButton": {
                return this.submitButton;
            }
            case "fontSize": {
                return this.fontSize;
            }
            case "textShadow": {
                return this.textShadow;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "type": {
                return true;
            }
            case "textAlignmentX": {
                return true;
            }
            case "textAlignmentY": {
                return true;
            }
            case "placeHolderColor": {
                return true;
            }
            case "textColor": {
                return true;
            }
            case "markTextColor": {
                return true;
            }
            case "markColor": {
                return true;
            }
            case "cursorColor": {
                return true;
            }
            case "clearButton": {
                return true;
            }
            case "submitButton": {
                return true;
            }
            case "fontSize": {
                return true;
            }
            case "textShadow": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.TextFieldWidgetResetter;
    }
}
