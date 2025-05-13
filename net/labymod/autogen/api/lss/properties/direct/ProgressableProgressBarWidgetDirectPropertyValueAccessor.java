// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.ProgressableProgressBarWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.ProgressableProgressBarWidgetProgressBackgroundColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.ProgressableProgressBarWidgetProgressForegroundColorPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class ProgressableProgressBarWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> progressForegroundColor;
    protected PropertyValueAccessor<?, ?, ?> progressBackgroundColor;
    LssPropertyResetter ProgressableProgressBarWidgetResetter;
    
    public ProgressableProgressBarWidgetDirectPropertyValueAccessor() {
        this.progressForegroundColor = new ProgressableProgressBarWidgetProgressForegroundColorPropertyValueAccessor();
        this.progressBackgroundColor = new ProgressableProgressBarWidgetProgressBackgroundColorPropertyValueAccessor();
        this.ProgressableProgressBarWidgetResetter = new ProgressableProgressBarWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "progressForegroundColor": {
                return this.progressForegroundColor;
            }
            case "progressBackgroundColor": {
                return this.progressBackgroundColor;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "progressForegroundColor": {
                return true;
            }
            case "progressBackgroundColor": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.ProgressableProgressBarWidgetResetter;
    }
}
