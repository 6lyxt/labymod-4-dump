// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.user;

import net.labymod.api.user.GameUser;

public class UserDiscoverEvent extends AbstractGameUserEvent
{
    private final boolean whitelisted;
    
    public UserDiscoverEvent(final GameUser gameUser, final boolean whitelisted) {
        super(gameUser);
        this.whitelisted = whitelisted;
    }
    
    public boolean isWhitelisted() {
        return this.whitelisted;
    }
}
