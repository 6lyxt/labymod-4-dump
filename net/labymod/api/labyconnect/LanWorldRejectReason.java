// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect;

public enum LanWorldRejectReason
{
    REQUEST_RETRACTED(0), 
    REQUEST_REJECTED(1), 
    REQUEST_EXPIRED(2), 
    USER_DISCONNECTED(3), 
    INCOMPATIBLE_MINECRAFT_VERSION(4), 
    UNSUPPORTED_CLIENT_VERSION(5);
    
    private final int id;
    
    private LanWorldRejectReason(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public static LanWorldRejectReason fromId(final int id) {
        for (final LanWorldRejectReason reason : values()) {
            if (reason.id == id) {
                return reason;
            }
        }
        return null;
    }
}
