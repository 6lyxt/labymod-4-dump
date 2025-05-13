// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world;

public enum BossBarOverlay
{
    PROGRESS("progress"), 
    NOTCHED_6("notched_6"), 
    NOTCHED_10("notched_10"), 
    NOTCHED_12("notched_12"), 
    NOTCHED_20("notched_20");
    
    private static final BossBarOverlay[] VALUES;
    private final String name;
    
    private BossBarOverlay(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public static BossBarOverlay[] getValues() {
        return BossBarOverlay.VALUES;
    }
    
    public static BossBarOverlay getByName(final String name) {
        for (final BossBarOverlay value : BossBarOverlay.VALUES) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No boss bar color \"" + name);
    }
    
    public static BossBarOverlay get(final String name) {
        for (final BossBarOverlay value : BossBarOverlay.VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + BossBarOverlay.class.getCanonicalName() + "." + name);
    }
    
    public static BossBarOverlay getOrDefault(final String name, final BossBarOverlay defaultValue) {
        for (final BossBarOverlay value : BossBarOverlay.VALUES) {
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
