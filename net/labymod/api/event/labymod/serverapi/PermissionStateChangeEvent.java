// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.serverapi;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.user.permission.ClientPermission;
import net.labymod.api.event.Event;

public class PermissionStateChangeEvent implements Event
{
    private final ClientPermission permission;
    private final State state;
    
    public PermissionStateChangeEvent(@NotNull final ClientPermission permission, @NotNull final State state) {
        this.permission = permission;
        this.state = state;
    }
    
    @NotNull
    public ClientPermission permission() {
        return this.permission;
    }
    
    @NotNull
    public State state() {
        return this.state;
    }
    
    public enum State
    {
        ALLOWED, 
        DISALLOWED;
    }
}
