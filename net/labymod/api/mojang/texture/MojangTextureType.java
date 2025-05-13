// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.mojang.texture;

public enum MojangTextureType
{
    SKIN("skins"), 
    CAPE("capes"), 
    ELYTRA("elytra");
    
    public static final MojangTextureType[] VALUES;
    private final String locationPrefix;
    
    private MojangTextureType(final String locationPrefix) {
        this.locationPrefix = locationPrefix;
    }
    
    public String getLocationPrefix() {
        return this.locationPrefix;
    }
    
    static {
        VALUES = values();
    }
}
