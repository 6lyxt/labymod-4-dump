// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby;

import net.labymod.api.configuration.labymod.main.laby.ingame.MarkerConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.UserIndicatorConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface MultiplayerConfig extends ConfigAccessor
{
    ClassicPvPConfig classicPvP();
    
    ServerListConfig serverList();
    
    ConfigProperty<Boolean> confirmDisconnect();
    
    ConfigProperty<ServerInfoPosition> showCurrentServerInfo();
    
    TabListConfig tabList();
    
    UserIndicatorConfig userIndicator();
    
    PingConfig ping();
    
    ConfigProperty<Boolean> serverBanner();
    
    ConfigProperty<Boolean> customPlayerList();
    
    ConfigProperty<Boolean> clearTitles();
    
    MarkerConfig marker();
    
    public enum ServerInfoPosition
    {
        DISABLED, 
        ABOVE_BUTTONS, 
        BELOW_BUTTONS;
    }
}
