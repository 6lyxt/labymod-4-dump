// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.ScreenRendererWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.ScreenRendererWidgetInteractablePropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class ScreenRendererWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> interactable;
    LssPropertyResetter ScreenRendererWidgetResetter;
    
    public ScreenRendererWidgetDirectPropertyValueAccessor() {
        this.interactable = new ScreenRendererWidgetInteractablePropertyValueAccessor();
        this.ScreenRendererWidgetResetter = new ScreenRendererWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "interactable": {
                return this.interactable;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "interactable": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.ScreenRendererWidgetResetter;
    }
}
