// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.interaction;

import net.labymod.api.client.entity.player.Player;
import java.util.function.Function;

public class InteractionMenuPlaceholder
{
    private final String identifier;
    private final Function<Player, String> function;
    
    private InteractionMenuPlaceholder(final String identifier, final Function<Player, String> function) {
        this.identifier = identifier;
        this.function = function;
    }
    
    public static InteractionMenuPlaceholder of(final String identifier, final Function<Player, String> function) {
        return new InteractionMenuPlaceholder(identifier, function);
    }
    
    public String getIdentifier() {
        return this.identifier;
    }
    
    public String getReplacement(final Player player) {
        return this.function.apply(player);
    }
}
