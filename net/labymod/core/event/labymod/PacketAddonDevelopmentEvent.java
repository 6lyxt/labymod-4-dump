// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.event.labymod;

import org.jetbrains.annotations.NotNull;
import net.labymod.core.labyconnect.protocol.packets.PacketAddonDevelopment;
import net.labymod.api.event.Event;

public class PacketAddonDevelopmentEvent implements Event
{
    private final PacketAddonDevelopment packet;
    
    public PacketAddonDevelopmentEvent(@NotNull final PacketAddonDevelopment packet) {
        this.packet = packet;
    }
    
    @NotNull
    public PacketAddonDevelopment packet() {
        return this.packet;
    }
}
