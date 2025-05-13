// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.widget;

import java.util.List;

public interface WidgetIdentifier
{
    String getIdentifier();
    
    default List<String> getIdentifiers() {
        return List.of(this.getIdentifier());
    }
}
