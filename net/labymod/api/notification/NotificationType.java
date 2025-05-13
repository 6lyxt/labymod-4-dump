// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.notification;

public interface NotificationType
{
    default boolean shouldPush() {
        return true;
    }
    
    @Deprecated(forRemoval = true, since = "4.1.18")
    default int getColor() {
        return 0;
    }
}
