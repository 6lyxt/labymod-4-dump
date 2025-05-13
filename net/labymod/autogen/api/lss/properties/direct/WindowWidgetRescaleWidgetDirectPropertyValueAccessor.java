// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.WindowWidgetRescaleWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.RescaleWidgetHoverColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.RescaleWidgetIdleColorPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.RescaleWidgetRenderPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class WindowWidgetRescaleWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> render;
    protected PropertyValueAccessor<?, ?, ?> idleColor;
    protected PropertyValueAccessor<?, ?, ?> hoverColor;
    LssPropertyResetter RescaleWidgetResetter;
    
    public WindowWidgetRescaleWidgetDirectPropertyValueAccessor() {
        this.render = new RescaleWidgetRenderPropertyValueAccessor();
        this.idleColor = new RescaleWidgetIdleColorPropertyValueAccessor();
        this.hoverColor = new RescaleWidgetHoverColorPropertyValueAccessor();
        this.RescaleWidgetResetter = new WindowWidgetRescaleWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "render": {
                return this.render;
            }
            case "idleColor": {
                return this.idleColor;
            }
            case "hoverColor": {
                return this.hoverColor;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "render": {
                return true;
            }
            case "idleColor": {
                return true;
            }
            case "hoverColor": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.RescaleWidgetResetter;
    }
}
