// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labyconnect.configuration;

import java.util.Map;
import java.util.UUID;
import java.util.Set;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface LabyConnectConfigAccessor extends ConfigAccessor
{
    ConfigProperty<UserStatus> onlineStatus();
    
    ConfigProperty<Boolean> showConnectedServer();
    
    ConfigProperty<Boolean> onlineStatusNotification();
    
    ConfigProperty<Boolean> serverSwitchNotifications();
    
    ConfigProperty<Boolean> serverSwitchGameModeNotifications();
    
    ConfigProperty<Boolean> incomingRequestNotifications();
    
    ConfigProperty<Boolean> incomingChatMessageNotifications();
    
    ConfigProperty<Set<UUID>> pinnedFriends();
    
    ConfigProperty<Map<UUID, String>> friendNotes();
}
