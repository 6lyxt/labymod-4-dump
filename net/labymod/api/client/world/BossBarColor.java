// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world;

import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;

public enum BossBarColor
{
    PINK("pink", NamedTextColor.RED), 
    BLUE("blue", NamedTextColor.BLUE), 
    RED("red", NamedTextColor.DARK_RED), 
    GREEN("green", NamedTextColor.GREEN), 
    YELLOW("yellow", NamedTextColor.YELLOW), 
    PURPLE("purple", NamedTextColor.DARK_BLUE), 
    WHITE("white", NamedTextColor.WHITE);
    
    private static final BossBarColor[] VALUES;
    private final String name;
    private final TextColor color;
    
    private BossBarColor(final String name, final TextColor color) {
        this.name = name;
        this.color = color;
    }
    
    public String getName() {
        return this.name;
    }
    
    public TextColor color() {
        return this.color;
    }
    
    public static BossBarColor[] getValues() {
        return BossBarColor.VALUES;
    }
    
    public static BossBarColor getByName(final String name) {
        for (final BossBarColor value : BossBarColor.VALUES) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No boss bar color \"" + name);
    }
    
    public static BossBarColor get(final String name) {
        for (final BossBarColor value : BossBarColor.VALUES) {
            if (value.name().equals(name)) {
                return value;
            }
        }
        throw new IllegalStateException("No enum constant " + BossBarColor.class.getCanonicalName() + "." + name);
    }
    
    public static BossBarColor getOrDefault(final String name, final BossBarColor defaultValue) {
        for (final BossBarColor value : BossBarColor.VALUES) {
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
