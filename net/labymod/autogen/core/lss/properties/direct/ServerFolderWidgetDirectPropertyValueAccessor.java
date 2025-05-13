// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.autogen.core.lss.properties.direct;

import net.labymod.api.client.gui.lss.property.PropertyValueAccessor;
import net.labymod.autogen.core.lss.properties.resetters.ServerFolderWidgetLssPropertyResetter;
import net.labymod.api.client.gui.lss.property.LssPropertyResetter;

public class ServerFolderWidgetDirectPropertyValueAccessor extends ServerEntryWidgetDirectPropertyValueAccessor
{
    LssPropertyResetter ServerFolderWidgetResetter;
    
    public ServerFolderWidgetDirectPropertyValueAccessor() {
        this.ServerFolderWidgetResetter = new ServerFolderWidgetLssPropertyResetter();
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
        return this.ServerFolderWidgetResetter;
    }
}
