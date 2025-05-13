// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session;

import net.labymod.api.labyconnect.LabyConnect;
import java.util.UUID;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectPlayEmoteEvent extends LabyConnectEvent
{
    private final UUID uniqueId;
    private final short emoteId;
    
    public LabyConnectPlayEmoteEvent(final LabyConnect api, final UUID uniqueId, final short emoteId) {
        super(api);
        this.uniqueId = uniqueId;
        this.emoteId = emoteId;
    }
    
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    public short getEmoteId() {
        return this.emoteId;
    }
}
