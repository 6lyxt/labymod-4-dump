// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.ingame;

import java.util.ArrayList;
import net.labymod.api.configuration.loader.annotation.Exclude;
import java.util.List;
import net.labymod.api.configuration.loader.annotation.PermissionRequired;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.ingame.ChatInputConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultChatInputConfig extends Config implements ChatInputConfig
{
    @SwitchWidget.SwitchSetting
    @PermissionRequired("chat_autotext")
    private final ConfigProperty<Boolean> autoText;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> nameHistory;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> chatSymbols;
    @Exclude
    private final ConfigProperty<List<String>> favoriteSymbols;
    
    public DefaultChatInputConfig() {
        this.autoText = new ConfigProperty<Boolean>(true);
        this.nameHistory = new ConfigProperty<Boolean>(true);
        this.chatSymbols = new ConfigProperty<Boolean>(true);
        this.favoriteSymbols = new ConfigProperty<List<String>>(new ArrayList<String>());
    }
    
    @Override
    public ConfigProperty<Boolean> autoText() {
        return this.autoText;
    }
    
    @Override
    public ConfigProperty<Boolean> nameHistory() {
        return this.nameHistory;
    }
    
    @Override
    public ConfigProperty<Boolean> chatSymbols() {
        return this.chatSymbols;
    }
    
    @Override
    public ConfigProperty<List<String>> favoriteSymbols() {
        return this.favoriteSymbols;
    }
}
