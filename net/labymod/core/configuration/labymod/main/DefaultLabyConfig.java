// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main;

import net.labymod.api.configuration.labymod.main.laby.HotkeysConfig;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.api.configuration.labymod.main.laby.NotificationsConfig;
import net.labymod.api.configuration.labymod.main.laby.MultiplayerConfig;
import net.labymod.api.configuration.labymod.main.laby.AppearanceConfig;
import net.labymod.api.configuration.labymod.main.laby.IngameConfig;
import net.labymod.core.configuration.labymod.main.laby.DefaultHotkeysConfig;
import net.labymod.core.configuration.labymod.main.laby.DefaultOtherConfig;
import net.labymod.core.configuration.labymod.main.laby.DefaultNotificationsConfig;
import net.labymod.core.configuration.labymod.main.laby.DefaultMultiplayerConfig;
import net.labymod.core.configuration.labymod.main.laby.DefaultAppearanceConfig;
import net.labymod.core.configuration.labymod.main.laby.DefaultIngameConfig;
import net.labymod.api.configuration.loader.annotation.SpriteTexture;
import net.labymod.api.configuration.loader.annotation.ConfigName;
import net.labymod.api.configuration.labymod.main.LabyConfig;
import net.labymod.api.configuration.loader.Config;

@ConfigName("settings")
@SpriteTexture("settings/main/laby")
public class DefaultLabyConfig extends Config implements LabyConfig
{
    private DefaultIngameConfig ingame;
    private DefaultAppearanceConfig appearance;
    private DefaultMultiplayerConfig multiplayer;
    private DefaultNotificationsConfig notifications;
    private DefaultOtherConfig other;
    private DefaultHotkeysConfig hotkeys;
    
    public DefaultLabyConfig() {
        this.ingame = new DefaultIngameConfig();
        this.appearance = new DefaultAppearanceConfig();
        this.multiplayer = new DefaultMultiplayerConfig();
        this.notifications = new DefaultNotificationsConfig();
        this.other = new DefaultOtherConfig();
        this.hotkeys = new DefaultHotkeysConfig();
    }
    
    @Override
    public IngameConfig ingame() {
        return this.ingame;
    }
    
    @Override
    public AppearanceConfig appearance() {
        return this.appearance;
    }
    
    @Override
    public MultiplayerConfig multiplayer() {
        return this.multiplayer;
    }
    
    @Override
    public NotificationsConfig notifications() {
        return this.notifications;
    }
    
    @Override
    public OtherConfig other() {
        return this.other;
    }
    
    @Override
    public HotkeysConfig hotkeys() {
        return this.hotkeys;
    }
    
    @Override
    public int getConfigVersion() {
        return 3;
    }
}
