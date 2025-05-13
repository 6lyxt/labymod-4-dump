// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.SplitContentWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.SplitContentWidgetMaxPercentagePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.SplitContentWidgetMinPercentagePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.SplitContentWidgetInitialPercentagePropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.SplitContentWidgetSplitGapWidthPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.SplitContentWidgetSplitButtonWidthPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class SplitContentWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> splitButtonWidth;
    protected PropertyValueAccessor<?, ?, ?> splitGapWidth;
    protected PropertyValueAccessor<?, ?, ?> initialPercentage;
    protected PropertyValueAccessor<?, ?, ?> minPercentage;
    protected PropertyValueAccessor<?, ?, ?> maxPercentage;
    LssPropertyResetter SplitContentWidgetResetter;
    
    public SplitContentWidgetDirectPropertyValueAccessor() {
        this.splitButtonWidth = new SplitContentWidgetSplitButtonWidthPropertyValueAccessor();
        this.splitGapWidth = new SplitContentWidgetSplitGapWidthPropertyValueAccessor();
        this.initialPercentage = new SplitContentWidgetInitialPercentagePropertyValueAccessor();
        this.minPercentage = new SplitContentWidgetMinPercentagePropertyValueAccessor();
        this.maxPercentage = new SplitContentWidgetMaxPercentagePropertyValueAccessor();
        this.SplitContentWidgetResetter = new SplitContentWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "splitButtonWidth": {
                return this.splitButtonWidth;
            }
            case "splitGapWidth": {
                return this.splitGapWidth;
            }
            case "initialPercentage": {
                return this.initialPercentage;
            }
            case "minPercentage": {
                return this.minPercentage;
            }
            case "maxPercentage": {
                return this.maxPercentage;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "splitButtonWidth": {
                return true;
            }
            case "splitGapWidth": {
                return true;
            }
            case "initialPercentage": {
                return true;
            }
            case "minPercentage": {
                return true;
            }
            case "maxPercentage": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.SplitContentWidgetResetter;
    }
}
