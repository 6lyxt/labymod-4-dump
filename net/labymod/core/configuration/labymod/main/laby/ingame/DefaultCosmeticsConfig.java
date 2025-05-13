// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.ingame;

import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.user.shop.CloakPriority;
import net.labymod.api.configuration.settings.annotation.SettingExperimental;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.user.shop.RenderMode;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.ShowSettingInParent;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.api.configuration.loader.Config;

@SettingRequires("renderCosmetics")
public class DefaultCosmeticsConfig extends Config implements CosmeticsConfig
{
    @SwitchWidget.SwitchSetting
    @ShowSettingInParent
    @SpriteSlot(x = 2, y = 4)
    private final ConfigProperty<Boolean> renderCosmetics;
    @DropdownWidget.DropdownSetting
    @SettingExperimental
    private final ConfigProperty<RenderMode> renderMode;
    @DropdownWidget.DropdownSetting
    private final ConfigProperty<CloakPriority> cloakPriority;
    @SettingSection("hideInDistance")
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 3, y = 4)
    private final ConfigProperty<Boolean> hideCosmeticsInDistance;
    @SliderWidget.SliderSetting(min = 3.0f, max = 32.0f)
    private final ConfigProperty<Integer> hideAfterBlocks;
    @SliderWidget.SliderSetting(min = 5.0f, max = 256.0f)
    private final ConfigProperty<Integer> maxCosmeticsPerPlayer;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> onlyFriends;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> walkingPets;
    
    public DefaultCosmeticsConfig() {
        this.renderCosmetics = new ConfigProperty<Boolean>(true);
        this.renderMode = ConfigProperty.createEnum(RenderMode.RETAINED);
        this.cloakPriority = ConfigProperty.createEnum(CloakPriority.LABYMOD);
        this.hideCosmeticsInDistance = new ConfigProperty<Boolean>(true);
        this.hideAfterBlocks = new ConfigProperty<Integer>(4);
        this.maxCosmeticsPerPlayer = new ConfigProperty<Integer>(128);
        this.onlyFriends = new ConfigProperty<Boolean>(false);
        this.walkingPets = new ConfigProperty<Boolean>(true);
    }
    
    @Override
    public ConfigProperty<Boolean> renderCosmetics() {
        return this.renderCosmetics;
    }
    
    @Override
    public ConfigProperty<RenderMode> renderMode() {
        return this.renderMode;
    }
    
    @Override
    public ConfigProperty<CloakPriority> cloakPriority() {
        return this.cloakPriority;
    }
    
    @Override
    public ConfigProperty<Boolean> hideCosmeticsInDistance() {
        return this.hideCosmeticsInDistance;
    }
    
    @Override
    public ConfigProperty<Integer> hideAfterBlocks() {
        return this.hideAfterBlocks;
    }
    
    @Override
    public ConfigProperty<Integer> maxCosmeticsPerPlayer() {
        return this.maxCosmeticsPerPlayer;
    }
    
    @Override
    public ConfigProperty<Boolean> onlyFriends() {
        return this.onlyFriends;
    }
    
    @Override
    public ConfigProperty<Boolean> walkingPets() {
        return this.walkingPets;
    }
}
