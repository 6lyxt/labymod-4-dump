// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.autogen.core.lss.properties.resetters.GradientWidgetLssPropertyResetter;
import net.labymod.autogen.core.lss.properties.accessors.GradientWidgetColorEndPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.GradientWidgetColorStartPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.GradientWidgetDirectionPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class GradientWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> direction;
    protected PropertyValueAccessor<?, ?, ?> colorStart;
    protected PropertyValueAccessor<?, ?, ?> colorEnd;
    LssPropertyResetter GradientWidgetResetter;
    
    public GradientWidgetDirectPropertyValueAccessor() {
        this.direction = new GradientWidgetDirectionPropertyValueAccessor();
        this.colorStart = new GradientWidgetColorStartPropertyValueAccessor();
        this.colorEnd = new GradientWidgetColorEndPropertyValueAccessor();
        this.GradientWidgetResetter = new GradientWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "direction": {
                return this.direction;
            }
            case "colorStart": {
                return this.colorStart;
            }
            case "colorEnd": {
                return this.colorEnd;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "direction": {
                return true;
            }
            case "colorStart": {
                return true;
            }
            case "colorEnd": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.GradientWidgetResetter;
    }
}
