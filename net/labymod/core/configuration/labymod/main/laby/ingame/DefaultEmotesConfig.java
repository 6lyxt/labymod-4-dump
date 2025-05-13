// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.settings.annotation.SettingDevelopment;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.labymod.main.laby.ingame.EmotesConfig;
import net.labymod.api.configuration.loader.Config;

@SettingRequires("emotes")
public class DefaultEmotesConfig extends Config implements EmotesConfig
{
    @ShowSettingInParent
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> emotes;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 5, y = 4)
    private final ConfigProperty<Boolean> orderEmotesClockwise;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> showCosmeticsInWheel;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> firstPersonHeadAnimation;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> emotePerspective;
    @SwitchWidget.SwitchSetting
    @SettingDevelopment
    private final ConfigProperty<Boolean> emoteDebug;
    
    public DefaultEmotesConfig() {
        this.emotes = new ConfigProperty<Boolean>(true);
        this.orderEmotesClockwise = new ConfigProperty<Boolean>(true);
        this.showCosmeticsInWheel = new ConfigProperty<Boolean>(false);
        this.firstPersonHeadAnimation = new ConfigProperty<Boolean>(true);
        this.emotePerspective = new ConfigProperty<Boolean>(true);
        this.emoteDebug = new ConfigProperty<Boolean>(false);
    }
    
    @Override
    public ConfigProperty<Boolean> emotes() {
        return this.emotes;
    }
    
    @Override
    public ConfigProperty<Boolean> orderEmotesClockwise() {
        return this.orderEmotesClockwise;
    }
    
    @Override
    public ConfigProperty<Boolean> showCosmeticsInWheel() {
        return this.showCosmeticsInWheel;
    }
    
    @Override
    public ConfigProperty<Boolean> firstPersonHeadAnimation() {
        return this.firstPersonHeadAnimation;
    }
    
    @Override
    public ConfigProperty<Boolean> emotePerspective() {
        return this.emotePerspective;
    }
    
    @Override
    public ConfigProperty<Boolean> emoteDebug() {
        return this.emoteDebug;
    }
}
