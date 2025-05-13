// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.ChangelogWidgetLssPropertyResetter;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;

public class ChangelogWidgetDirectPropertyValueAccessor extends VerticalListWidgetDirectPropertyValueAccessor
{
    LssPropertyResetter ChangelogWidgetResetter;
    
    public ChangelogWidgetDirectPropertyValueAccessor() {
        this.ChangelogWidgetResetter = new ChangelogWidgetLssPropertyResetter();
    }
    
    @Override
    public PropertyValueAccessor<?, ?, ?> getPropertyValueAccessor(final String key) {
        return super.getPropertyValueAccessor(key);
    }
    
    @Override
    public boolean hasPropertyValueAccessor(final String key) {
        return super.hasPropertyValueAccessor(key);
    }
    
    @Override
    public LssPropertyResetter getPropertyResetter() {
        return this.ChangelogWidgetResetter;
    }
}
