// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.CharSequences;
import net.labymod.api.util.StringUtil;
import net.labymod.api.client.component.Component;

public enum GameMode
{
    UNKNOWN(-1), 
    SURVIVAL(0), 
    CREATIVE(1), 
    ADVENTURE(2), 
    SPECTATOR(3);
    
    private static final GameMode[] VALUES;
    private final int id;
    private final String name;
    private final String lowercase;
    private final Component displayName;
    
    private GameMode(final int id) {
        this.id = id;
        final String name = this.name();
        this.lowercase = StringUtil.toLowercase(name);
        this.name = CharSequences.toString(CharSequences.capitalize(this.lowercase));
        this.displayName = Component.translatable("labymod.misc.gameMode." + this.lowercase, new Component[0]);
    }
    
    public static GameMode fromId(final int id) {
        for (final GameMode gameMode : GameMode.VALUES) {
            if (gameMode.getId() == id) {
                return gameMode;
            }
        }
        return GameMode.UNKNOWN;
    }
    
    @NotNull
    public String toLowerCase() {
        return this.lowercase;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Component displayName() {
        return this.displayName;
    }
    
    public int getId() {
        return this.id;
    }
    
    @Deprecated
    @NotNull
    public String lowercase() {
        return this.toLowerCase();
    }
    
    static {
        VALUES = values();
    }
}
