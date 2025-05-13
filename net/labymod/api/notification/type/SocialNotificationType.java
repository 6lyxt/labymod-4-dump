// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.notification.type;

import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.Laby;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.notification.NotificationType;

public class SocialNotificationType implements NotificationType
{
    private SocialNotificationType() {
    }
    
    @Override
    public boolean shouldPush() {
        return Laby.labyAPI().labyConnect().configProvider().get().onlineStatus().get() != UserStatus.BUSY;
    }
    
    public static NotificationType create() {
        return new SocialNotificationType();
    }
}
