// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.api.lss.properties.direct;

import net.labymod.autogen.api.lss.properties.resetters.OverlayWidgetLssPropertyResetter;
import net.labymod.autogen.api.lss.properties.accessors.OverlayWidgetPlayInteractSoundPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.OverlayWidgetStrategyYPropertyValueAccessor;
import net.labymod.autogen.api.lss.properties.accessors.OverlayWidgetStrategyXPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class OverlayWidgetDirectPropertyValueAccessor extends AbstractWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> strategyX;
    protected PropertyValueAccessor<?, ?, ?> strategyY;
    protected PropertyValueAccessor<?, ?, ?> playInteractSound;
    LssPropertyResetter OverlayWidgetResetter;
    
    public OverlayWidgetDirectPropertyValueAccessor() {
        this.strategyX = new OverlayWidgetStrategyXPropertyValueAccessor();
        this.strategyY = new OverlayWidgetStrategyYPropertyValueAccessor();
        this.playInteractSound = new OverlayWidgetPlayInteractSoundPropertyValueAccessor();
        this.OverlayWidgetResetter = new OverlayWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "strategyX": {
                return this.strategyX;
            }
            case "strategyY": {
                return this.strategyY;
            }
            case "playInteractSound": {
                return this.playInteractSound;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "strategyX": {
                return true;
            }
            case "strategyY": {
                return true;
            }
            case "playInteractSound": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.OverlayWidgetResetter;
    }
}
