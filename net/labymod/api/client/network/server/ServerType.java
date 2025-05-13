// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.network.server;

public enum ServerType
{
    LAN, 
    REALM, 
    THIRD_PARTY;
    
    private static final ServerType[] VALUES;
    
    public static ServerType[] getValues() {
        return ServerType.VALUES;
    }
    
    public static ServerType get(final String name) {
        for (final ServerType value : ServerType.VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + ServerType.class.getCanonicalName() + "." + name);
    }
    
    public static ServerType getOrDefault(final String name, final ServerType defaultValue) {
        for (final ServerType value : ServerType.VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        return defaultValue;
    }
    
    static {
        VALUES = values();
    }
}
