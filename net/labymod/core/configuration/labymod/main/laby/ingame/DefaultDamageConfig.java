// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.util.Color;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.labymod.main.laby.ingame.DamageConfig;
import net.labymod.api.configuration.loader.Config;

@SettingRequires("damageColored")
public class DefaultDamageConfig extends Config implements DamageConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> damageColored;
    @ColorPickerWidget.ColorPickerSetting(chroma = true, alpha = true)
    @SpriteSlot(x = 2, y = 1, page = 1)
    private final ConfigProperty<Color> damageColor;
    
    public DefaultDamageConfig() {
        this.damageColored = new ConfigProperty<Boolean>(false);
        this.damageColor = new ConfigProperty<Color>(Color.ofRGB(255, 0, 0, 76));
    }
    
    @Override
    public ConfigProperty<Boolean> damageColored() {
        return this.damageColored;
    }
    
    @Override
    public ConfigProperty<Color> damageColor() {
        return this.damageColor;
    }
}
