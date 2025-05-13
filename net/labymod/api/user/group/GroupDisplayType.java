// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.user.group;

public enum GroupDisplayType
{
    NONE, 
    ABOVE_HEAD, 
    BESIDE_NAME;
    
    private static final GroupDisplayType[] VALUES;
    
    public static GroupDisplayType getDisplayType(final String name) {
        for (final GroupDisplayType value : GroupDisplayType.VALUES) {
            if (value.name().equalsIgnoreCase(name)) {
                return value;
            }
        }
        return null;
    }
    
    static {
        VALUES = values();
    }
}
