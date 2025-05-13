// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.autogen.core.lss.properties.resetters.StoreItemWidgetLssPropertyResetter;
import net.labymod.autogen.core.lss.properties.accessors.StoreItemWidgetDeletedColorPropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.accessors.StoreItemWidgetInstalledColorPropertyValueAccessor;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;
import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;

public class StoreItemWidgetDirectPropertyValueAccessor extends SimpleWidgetDirectPropertyValueAccessor
{
    protected PropertyValueAccessor<?, ?, ?> installedColor;
    protected PropertyValueAccessor<?, ?, ?> deletedColor;
    LssPropertyResetter StoreItemWidgetResetter;
    
    public StoreItemWidgetDirectPropertyValueAccessor() {
        this.installedColor = new StoreItemWidgetInstalledColorPropertyValueAccessor();
        this.deletedColor = new StoreItemWidgetDeletedColorPropertyValueAccessor();
        this.StoreItemWidgetResetter = new StoreItemWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        switch (key) {
            case "installedColor": {
                return this.installedColor;
            }
            case "deletedColor": {
                return this.deletedColor;
            }
            default: {
                return super.getPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        switch (key) {
            case "installedColor": {
                return true;
            }
            case "deletedColor": {
                return true;
            }
            default: {
                return super.hasPropertyValueAccessor(key);
            }
        }
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.StoreItemWidgetResetter;
    }
}
