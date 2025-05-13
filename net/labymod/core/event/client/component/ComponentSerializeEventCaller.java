// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.client.component;

import net.labymod.api.Laby;
import net.labymod.api.event.client.component.ComponentSerializeEvent;
import net.labymod.api.client.component.Component;

public final class ComponentSerializeEventCaller
{
    private ComponentSerializeEventCaller() {
    }
    
    public static ComponentSerializeEvent call(final Component component) {
        return Laby.fireEvent(new ComponentSerializeEvent(component));
    }
}
