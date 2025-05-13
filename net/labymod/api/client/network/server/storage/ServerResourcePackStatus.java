// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server.storage;

public enum ServerResourcePackStatus
{
    ENABLED, 
    DISABLED, 
    PROMPT;
    
    public static ServerResourcePackStatus[] VALUES;
    
    static {
        ServerResourcePackStatus.VALUES = values();
    }
}
