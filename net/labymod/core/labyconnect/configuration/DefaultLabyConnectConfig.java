// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.labyconnect.configuration;

import net.labymod.api.configuration.loader.ConfigAccessor;
import net.labymod.api.configuration.loader.ConfigProvider;
import net.labymod.api.property.Property;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.Laby;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.Set;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.configuration.loader.Config;

@ConfigName("labyconnect")
public class DefaultLabyConnectConfig extends Config implements LabyConnectConfigAccessor
{
    @DropdownWidget.DropdownSetting
    private final ConfigProperty<UserStatus> onlineStatus;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showConnectedServer;
    @SettingSection("notifications")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> onlineStatusNotifications;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> serverSwitchNotifications;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> serverSwitchGameModeNotifications;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> incomingRequestNotifications;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> incomingChatMessageNotifications;
    private final ConfigProperty<Set<UUID>> pinnedFriends;
    private final ConfigProperty<Map<UUID, String>> friendNotes;
    
    public DefaultLabyConnectConfig() {
        this.onlineStatus = ConfigProperty.createEnum(UserStatus.ONLINE);
        this.showConnectedServer = new ConfigProperty<Boolean>(true);
        this.onlineStatusNotifications = new ConfigProperty<Boolean>(true);
        this.serverSwitchNotifications = new ConfigProperty<Boolean>(true);
        this.serverSwitchGameModeNotifications = new ConfigProperty<Boolean>(true);
        this.incomingRequestNotifications = new ConfigProperty<Boolean>(true);
        this.incomingChatMessageNotifications = new ConfigProperty<Boolean>(true);
        this.pinnedFriends = new ConfigProperty<Set<UUID>>(new HashSet<UUID>());
        this.friendNotes = new ConfigProperty<Map<UUID, String>>(new HashMap<UUID, String>());
        this.onlineStatus.addChangeListener((type, oldValue, newValue) -> {
            final LabyConnect labyConnect = Laby.labyAPI().labyConnect();
            final LabyConnectSession session = (labyConnect == null) ? null : labyConnect.getSession();
            if (session != null) {
                session.sendSettings();
            }
            return;
        });
        this.showConnectedServer.addChangeListener((type, oldValue, newValue) -> {
            final LabyConnect labyConnect2 = Laby.labyAPI().labyConnect();
            final LabyConnectSession session2 = (labyConnect2 == null) ? null : labyConnect2.getSession();
            if (session2 != null) {
                session2.sendSettings();
            }
        });
    }
    
    @Override
    public ConfigProperty<UserStatus> onlineStatus() {
        return this.onlineStatus;
    }
    
    @Override
    public ConfigProperty<Boolean> showConnectedServer() {
        return this.showConnectedServer;
    }
    
    @Override
    public ConfigProperty<Boolean> onlineStatusNotification() {
        return this.onlineStatusNotifications;
    }
    
    @Override
    public ConfigProperty<Boolean> serverSwitchNotifications() {
        return this.serverSwitchNotifications;
    }
    
    @Override
    public ConfigProperty<Boolean> serverSwitchGameModeNotifications() {
        return this.serverSwitchGameModeNotifications;
    }
    
    @Override
    public ConfigProperty<Boolean> incomingRequestNotifications() {
        return this.incomingRequestNotifications;
    }
    
    @Override
    public ConfigProperty<Boolean> incomingChatMessageNotifications() {
        return this.incomingChatMessageNotifications;
    }
    
    @Override
    public ConfigProperty<Set<UUID>> pinnedFriends() {
        return this.pinnedFriends;
    }
    
    @Override
    public ConfigProperty<Map<UUID, String>> friendNotes() {
        return this.friendNotes;
    }
    
    public static class LabyConnectConfigProvider extends ConfigProvider<LabyConnectConfigAccessor>
    {
        public static final LabyConnectConfigProvider INSTANCE;
        
        private LabyConnectConfigProvider() {
        }
        
        @Override
        protected Class<? extends ConfigAccessor> getType() {
            return DefaultLabyConnectConfig.class;
        }
        
        static {
            INSTANCE = new LabyConnectConfigProvider();
        }
    }
}
