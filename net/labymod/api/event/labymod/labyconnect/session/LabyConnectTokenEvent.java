// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.labyconnect.session;

import net.labymod.api.labyconnect.LabyConnect;
import java.util.UUID;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;

public class LabyConnectTokenEvent extends LabyConnectEvent
{
    private final TokenStorage.Purpose purpose;
    private final UUID uniqueId;
    private final TokenStorage.Token token;
    
    public LabyConnectTokenEvent(final LabyConnect api, final TokenStorage.Purpose purpose, final UUID uniqueId, final TokenStorage.Token token) {
        super(api);
        this.purpose = purpose;
        this.uniqueId = uniqueId;
        this.token = token;
    }
    
    public TokenStorage.Purpose purpose() {
        return this.purpose;
    }
    
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    
    public TokenStorage.Token token() {
        return this.token;
    }
}
