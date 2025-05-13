// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.ingame.zoom;

import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.ingame.zoom.ZoomCinematicConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultZoomCinematicConfig extends Config implements ZoomCinematicConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 3, y = 5)
    private final ConfigProperty<Boolean> enabled;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 4, y = 5)
    private final ConfigProperty<Boolean> disableAfterTransition;
    
    public DefaultZoomCinematicConfig() {
        this.enabled = new ConfigProperty<Boolean>(true);
        this.disableAfterTransition = new ConfigProperty<Boolean>(false);
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<Boolean> disableAfterTransition() {
        return this.disableAfterTransition;
    }
}
