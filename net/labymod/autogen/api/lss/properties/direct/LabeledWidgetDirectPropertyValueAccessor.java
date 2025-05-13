// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.LabeledWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.LabeledWidgetLabelAlignmentPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class LabeledWidgetDirectPropertyValueAccessor extends HorizontalListWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> labelAlignment;
    LssPropertyResetter LabeledWidgetResetter;
    
    public LabeledWidgetDirectPropertyValueAccessor() {
        this.labelAlignment = new LabeledWidgetLabelAlignmentPropertyValueAccessor();
        this.LabeledWidgetResetter = new LabeledWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "labelAlignment": {
                return this.labelAlignment;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "labelAlignment": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.LabeledWidgetResetter;
    }
}
