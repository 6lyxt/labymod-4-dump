// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.session;

import net.labymod.api.client.session.Session;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.Event;

@LabyEvent(background = true)
public class SessionUpdateEvent implements Event
{
    private final Session previousSession;
    private final Session newSession;
    
    public SessionUpdateEvent(final Session previousSession, final Session newSession) {
        this.previousSession = previousSession;
        this.newSession = newSession;
    }
    
    public Session previousSession() {
        return this.previousSession;
    }
    
    public Session newSession() {
        return this.newSession;
    }
    
    public boolean isAnotherAccount() {
        return this.previousSession == null || (this.newSession != null && !this.previousSession.getUniqueId().equals(this.newSession.getUniqueId()));
    }
}
