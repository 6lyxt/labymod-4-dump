// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.autotext;

import net.labymod.api.client.Minecraft;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.Laby;
import net.labymod.api.client.network.server.ServerAddress;
import net.labymod.api.client.gui.screen.widget.widgets.input.MultiKeybindWidget;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.Config;

public class AutoTextEntry extends Config
{
    @TextFieldWidget.TextFieldSetting(maxLength = 32)
    private final ConfigProperty<String> displayName;
    @TextFieldWidget.TextFieldSetting
    private final ConfigProperty<String> message;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> displayInInteractionMenu;
    @SettingRequires(value = "displayInInteractionMenu", invert = true)
    @MultiKeybindWidget.MultiKeyBindSetting
    private final ConfigProperty<Key[]> requiredKeys;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> sendImmediately;
    private AutoTextServerConfig serverConfig;
    
    public AutoTextEntry() {
        this.displayName = new ConfigProperty<String>("");
        this.message = new ConfigProperty<String>("");
        this.displayInInteractionMenu = new ConfigProperty<Boolean>(false);
        this.requiredKeys = new ConfigProperty<Key[]>(new Key[0]);
        this.sendImmediately = new ConfigProperty<Boolean>(true);
        this.serverConfig = new AutoTextServerConfig();
    }
    
    public AutoTextEntry(final String displayName, final String message, final boolean displayInInteractionMenu, final boolean sendImmediately, final AutoTextServerConfig serverConfig, final Key[] requiredKeys) {
        this.displayName = new ConfigProperty<String>("");
        this.message = new ConfigProperty<String>("");
        this.displayInInteractionMenu = new ConfigProperty<Boolean>(false);
        this.requiredKeys = new ConfigProperty<Key[]>(new Key[0]);
        this.sendImmediately = new ConfigProperty<Boolean>(true);
        this.displayName.set(displayName);
        this.message.set(message);
        this.displayInInteractionMenu.set(displayInInteractionMenu);
        this.sendImmediately.set(sendImmediately);
        this.requiredKeys.set(requiredKeys);
        this.serverConfig = serverConfig;
    }
    
    public ConfigProperty<String> displayName() {
        return this.displayName;
    }
    
    public ConfigProperty<String> message() {
        return this.message;
    }
    
    public ConfigProperty<Boolean> displayInInteractionMenu() {
        return this.displayInInteractionMenu;
    }
    
    public ConfigProperty<Boolean> sendImmediately() {
        return this.sendImmediately;
    }
    
    public ConfigProperty<Key[]> requiredKeys() {
        return this.requiredKeys;
    }
    
    public AutoTextServerConfig serverConfig() {
        return this.serverConfig;
    }
    
    public void setServerConfig(final AutoTextServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }
    
    public ServerAddress serverAddress() {
        return this.serverConfig.enabled().get() ? ServerAddress.parse(this.serverConfig.address().get()) : null;
    }
    
    public boolean isActiveOnCurrentServer() {
        final ServerAddress requiredAddress = this.serverAddress();
        if (requiredAddress == null) {
            return true;
        }
        final ServerData currentAddress = Laby.labyAPI().serverController().getCurrentServerData();
        return currentAddress != null && requiredAddress.equals(currentAddress.address());
    }
    
    public void setServerAddress(final String serverAddress) {
        this.serverConfig = new AutoTextServerConfig(true, serverAddress);
    }
    
    public boolean isConfigured() {
        return (!this.displayName.get().isEmpty() && this.displayInInteractionMenu.get()) || this.requiredKeys.get().length > 0;
    }
    
    public boolean requiresKey(final Key key) {
        final Key[] array;
        final Key[] keys = array = this.requiredKeys.get();
        for (final Key requiredKey : array) {
            if (key == requiredKey) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isEveryKeyPressed() {
        final Key[] keys = this.requiredKeys.get();
        if (keys.length == 0) {
            return false;
        }
        final Minecraft minecraft = Laby.labyAPI().minecraft();
        for (final Key requiredKey : keys) {
            if (!minecraft.isKeyPressed(requiredKey)) {
                return false;
            }
        }
        return true;
    }
    
    public AutoTextEntry copy() {
        return new AutoTextEntry(this.displayName.get(), this.message.get(), this.displayInInteractionMenu.get(), this.sendImmediately.get(), this.serverConfig.copy(), this.requiredKeys.get());
    }
}
