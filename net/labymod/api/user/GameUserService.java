// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.user;

import java.util.Map;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface GameUserService
{
    @NotNull
    GameUser gameUser(final UUID p0);
    
    @NotNull
    GameUser clientGameUser();
    
    @NotNull
    Map<UUID, GameUser> getGameUsers();
}
