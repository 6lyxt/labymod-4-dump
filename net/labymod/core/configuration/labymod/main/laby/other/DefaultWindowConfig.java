// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.other;

import net.labymod.api.configuration.labymod.main.laby.other.microsoft.MicrosoftWindowConfig;
import net.labymod.api.property.Property;
import net.labymod.api.util.function.ChangeListener;
import net.labymod.core.configuration.labymod.main.laby.other.window.CleanWindowTitleChangeListener;
import net.labymod.core.configuration.labymod.main.laby.other.microsoft.DefaultMicrosoftWindowConfig;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.configuration.loader.annotation.OSCompatibility;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.other.WindowConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultWindowConfig extends Config implements WindowConfig
{
    @SwitchWidget.SwitchSetting
    @VersionCompatibility("1.13<*")
    @SpriteSlot(x = 4)
    private final ConfigProperty<Boolean> cleanWindowTitle;
    @SwitchWidget.SwitchSetting
    @OSCompatibility({ OperatingSystem.WINDOWS, OperatingSystem.LINUX })
    @SpriteSlot(x = 2, y = 7)
    private final ConfigProperty<Boolean> borderlessWindow;
    @SwitchWidget.SwitchSetting
    @VersionCompatibility("1.13<*")
    private final ConfigProperty<Boolean> restoreWindowResolution;
    @OSCompatibility({ OperatingSystem.WINDOWS })
    private final DefaultMicrosoftWindowConfig microsoftWindow;
    
    public DefaultWindowConfig() {
        this.cleanWindowTitle = ConfigProperty.create(true).addChangeListener(new CleanWindowTitleChangeListener());
        this.borderlessWindow = ConfigProperty.create(false);
        this.restoreWindowResolution = ConfigProperty.create(true);
        this.microsoftWindow = new DefaultMicrosoftWindowConfig();
    }
    
    @Override
    public ConfigProperty<Boolean> cleanWindowTitle() {
        return this.cleanWindowTitle;
    }
    
    @Override
    public ConfigProperty<Boolean> borderlessWindow() {
        return this.borderlessWindow;
    }
    
    @Override
    public ConfigProperty<Boolean> restoreWindowResolution() {
        return this.restoreWindowResolution;
    }
    
    @Override
    public MicrosoftWindowConfig microsoftWindow() {
        return this.microsoftWindow;
    }
}
