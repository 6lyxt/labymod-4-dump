// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect.protocol.model;

import net.labymod.api.user.GameUser;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public interface User
{
    UUID getUniqueId();
    
    String getName();
    
    boolean isSelf();
    
    @NotNull
    UserStatus userStatus();
    
    void updateStatus(@NotNull final UserStatus p0);
    
    GameUser gameUser();
    
    boolean isLabyPlus();
}
