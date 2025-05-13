// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.ingame.zoom;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.zoom.ZoomTransitionType;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.ingame.zoom.ZoomTransitionConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultZoomTransitionConfig extends Config implements ZoomTransitionConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 3, y = 5)
    private final ConfigProperty<Boolean> enabled;
    @DropdownWidget.DropdownSetting
    @SpriteSlot(y = 6)
    private final ConfigProperty<ZoomTransitionType> zoomInType;
    @SliderWidget.SliderSetting(min = 50.0f, max = 300.0f, steps = 50.0f)
    @SpriteSlot(x = 1, y = 6)
    private final ConfigProperty<Long> zoomInDuration;
    @DropdownWidget.DropdownSetting
    @SpriteSlot(x = 2, y = 6)
    private final ConfigProperty<ZoomTransitionType> zoomOutType;
    @SliderWidget.SliderSetting(min = 50.0f, max = 300.0f, steps = 10.0f)
    @SpriteSlot(x = 3, y = 6)
    private final ConfigProperty<Long> zoomOutDuration;
    
    public DefaultZoomTransitionConfig() {
        this.enabled = new ConfigProperty<Boolean>(true);
        this.zoomInType = ConfigProperty.createEnum(ZoomTransitionType.EASE_OUT);
        this.zoomInDuration = new ConfigProperty<Long>(100L);
        this.zoomOutType = ConfigProperty.createEnum(ZoomTransitionType.EASE_IN);
        this.zoomOutDuration = new ConfigProperty<Long>(100L);
    }
    
    @Override
    public ConfigProperty<Boolean> enabled() {
        return this.enabled;
    }
    
    @Override
    public ConfigProperty<ZoomTransitionType> zoomInType() {
        return this.zoomInType;
    }
    
    @Override
    public ConfigProperty<Long> zoomInDuration() {
        return this.zoomInDuration;
    }
    
    @Override
    public ConfigProperty<ZoomTransitionType> zoomOutType() {
        return this.zoomOutType;
    }
    
    @Override
    public ConfigProperty<Long> zoomOutDuration() {
        return this.zoomOutDuration;
    }
}
