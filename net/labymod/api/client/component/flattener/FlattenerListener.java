// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.component.flattener;

import net.labymod.api.client.component.Component;

public interface FlattenerListener
{
    default void push(final Component source) {
    }
    
    void component(final String p0);
    
    default void pop(final Component source) {
    }
}
