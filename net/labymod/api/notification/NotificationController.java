// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.notification;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface NotificationController
{
    void push(final Notification p0);
    
    void pop(final Notification p0);
}
