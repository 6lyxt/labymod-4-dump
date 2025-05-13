// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.settings;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface SwitchableHandlerRegistry
{
    void registerHandler(final SwitchableHandler p0);
    
    SwitchableHandler getHandler(final Class<? extends SwitchableHandler> p0);
}
