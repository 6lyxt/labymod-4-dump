// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.resources.pack;

import net.labymod.api.client.resources.pack.IncompatibleResourcePack;
import java.util.Collection;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.Event;

@LabyEvent(background = true)
public class IncompatibleResourcePacksEvent implements Event
{
    private final Collection<IncompatibleResourcePack> incompatibleResourcePacks;
    
    public IncompatibleResourcePacksEvent(final Collection<IncompatibleResourcePack> incompatibleResourcePacks) {
        this.incompatibleResourcePacks = incompatibleResourcePacks;
    }
    
    public Collection<IncompatibleResourcePack> getIncompatibleResourcePacks() {
        return this.incompatibleResourcePacks;
    }
}
