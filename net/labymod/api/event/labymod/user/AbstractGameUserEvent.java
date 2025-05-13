// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.user;

import net.labymod.api.user.GameUser;
import net.labymod.api.event.Event;

public abstract class AbstractGameUserEvent implements Event
{
    private final GameUser gameUser;
    
    public AbstractGameUserEvent(final GameUser gameUser) {
        this.gameUser = gameUser;
    }
    
    public GameUser gameUser() {
        return this.gameUser;
    }
}
