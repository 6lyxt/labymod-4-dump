// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.SocialPointRendererWidgetLssPropertyResetter;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;

public class SocialPointRendererWidgetDirectPropertyValueAccessor extends AbstractCirclePointWidgetDirectPropertyValueAccessor
{
    LssPropertyResetter SocialPointRendererWidgetResetter;
    
    public SocialPointRendererWidgetDirectPropertyValueAccessor() {
        this.SocialPointRendererWidgetResetter = new SocialPointRendererWidgetLssPropertyResetter();
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
        return this.SocialPointRendererWidgetResetter;
    }
}
