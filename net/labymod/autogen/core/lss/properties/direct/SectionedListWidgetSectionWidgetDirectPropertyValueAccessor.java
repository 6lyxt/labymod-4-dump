// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.autogen.core.lss.properties.resetters.SectionedListWidgetSectionWidgetLssPropertyResetter;
import net.labymod.autogen.core.lss.properties.accessors.SectionWidgetTextColorPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.SectionWidgetLineColorPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.SectionWidgetLineHeightPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class SectionedListWidgetSectionWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> lineHeight;
    protected PropertyValueAccessor<?, ?, ?> lineColor;
    protected PropertyValueAccessor<?, ?, ?> textColor;
    LssPropertyResetter SectionWidgetResetter;
    
    public SectionedListWidgetSectionWidgetDirectPropertyValueAccessor() {
        this.lineHeight = new SectionWidgetLineHeightPropertyValueAccessor();
        this.lineColor = new SectionWidgetLineColorPropertyValueAccessor();
        this.textColor = new SectionWidgetTextColorPropertyValueAccessor();
        this.SectionWidgetResetter = new SectionedListWidgetSectionWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "lineHeight": {
                return this.lineHeight;
            }
            case "lineColor": {
                return this.lineColor;
            }
            case "textColor": {
                return this.textColor;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "lineHeight": {
                return true;
            }
            case "lineColor": {
                return true;
            }
            case "textColor": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.SectionWidgetResetter;
    }
}
