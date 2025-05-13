// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.icon.ping;

public enum PingType
{
    SERVER_PING, 
    LOADING_PING, 
    PLAYER_PING, 
    ERROR;
    
    public static PingType[] VALUES;
    
    static {
        PingType.VALUES = values();
    }
}
