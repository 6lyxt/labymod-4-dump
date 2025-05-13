// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby;

import net.labymod.api.configuration.labymod.main.laby.ingame.MarkerConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.tablist.PingConfig;
import net.labymod.core.configuration.labymod.main.laby.multiplayer.DefaultUserIndicatorConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.UserIndicatorConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.TabListConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ServerListConfig;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultMarkerConfig;
import net.labymod.core.configuration.labymod.main.laby.multiplayer.DefaultTabListConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.core.configuration.labymod.main.laby.multiplayer.DefaultServerListConfig;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.SearchTag;
import net.labymod.core.configuration.labymod.main.laby.multiplayer.DefaultClassicPvPConfig;
import net.labymod.api.configuration.labymod.main.laby.MultiplayerConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultMultiplayerConfig extends Config implements MultiplayerConfig
{
    @SearchTag({ "1.7", "animation", "old", "pvp" })
    @SpriteSlot(x = 2, y = 1)
    private DefaultClassicPvPConfig classicPvP;
    @SpriteSlot(x = 4, y = 1, page = 1)
    private DefaultServerListConfig serverList;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(y = 4)
    private final ConfigProperty<Boolean> confirmDisconnect;
    @SpriteSlot(x = 0, y = 5, page = 1)
    @DropdownWidget.DropdownSetting
    private final ConfigProperty<ServerInfoPosition> showCurrentServerInfo;
    @SpriteSlot(x = 1, y = 5, page = 1)
    private DefaultTabListConfig tabList;
    @IntroducedIn("4.1.0")
    @SpriteSlot(x = 5, y = 2, page = 1)
    private DefaultMarkerConfig marker;
    @SpriteSlot(x = 7, y = 3)
    @SwitchWidget.SwitchSetting
    private ConfigProperty<Boolean> clearTitles;
    
    public DefaultMultiplayerConfig() {
        this.classicPvP = new DefaultClassicPvPConfig();
        this.serverList = new DefaultServerListConfig();
        this.confirmDisconnect = new ConfigProperty<Boolean>(false);
        this.showCurrentServerInfo = ConfigProperty.createEnum(ServerInfoPosition.DISABLED);
        this.tabList = new DefaultTabListConfig();
        this.marker = new DefaultMarkerConfig();
        this.clearTitles = ConfigProperty.create(true);
    }
    
    @Override
    public ClassicPvPConfig classicPvP() {
        return this.classicPvP;
    }
    
    @Override
    public ServerListConfig serverList() {
        return this.serverList;
    }
    
    @Override
    public ConfigProperty<Boolean> confirmDisconnect() {
        return this.confirmDisconnect;
    }
    
    @Override
    public ConfigProperty<ServerInfoPosition> showCurrentServerInfo() {
        return this.showCurrentServerInfo;
    }
    
    @Override
    public TabListConfig tabList() {
        return this.tabList;
    }
    
    @Override
    public UserIndicatorConfig userIndicator() {
        return new DefaultUserIndicatorConfig();
    }
    
    @Override
    public PingConfig ping() {
        return this.tabList.ping();
    }
    
    @Override
    public ConfigProperty<Boolean> serverBanner() {
        return this.tabList.serverBanner();
    }
    
    @Override
    public ConfigProperty<Boolean> customPlayerList() {
        return this.tabList.enabled();
    }
    
    @Override
    public ConfigProperty<Boolean> clearTitles() {
        return this.clearTitles;
    }
    
    @Override
    public MarkerConfig marker() {
        return this.marker;
    }
    
    @Override
    public int getConfigVersion() {
        return 2;
    }
}
