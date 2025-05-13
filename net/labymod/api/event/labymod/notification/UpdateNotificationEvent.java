// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.labymod.notification;

import net.labymod.api.notification.Notification;
import net.labymod.api.event.Event;

public class UpdateNotificationEvent implements Event
{
    private final Notification notification;
    
    public UpdateNotificationEvent(final Notification notification) {
        this.notification = notification;
    }
    
    public Notification notification() {
        return this.notification;
    }
}
