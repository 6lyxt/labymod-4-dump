// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby.multiplayer;

import net.labymod.api.configuration.labymod.main.laby.multiplayer.classicpvp.OldEquipConfig;
import net.labymod.core.configuration.labymod.main.laby.multiplayer.classicpvp.DefaultOldEquipConfig;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.loader.annotation.PermissionRequired;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.multiplayer.ClassicPvPConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultClassicPvPConfig extends Config implements ClassicPvPConfig
{
    @SwitchWidget.SwitchSetting
    @PermissionRequired("range")
    @VersionCompatibility("1.9<*")
    @SpriteSlot(x = 3, y = 1)
    private final ConfigProperty<Boolean> oldRange;
    @SwitchWidget.SwitchSetting
    @PermissionRequired("slowdown")
    @VersionCompatibility("1.9<*")
    @SpriteSlot(x = 4, y = 1)
    private final ConfigProperty<Boolean> oldSlowdown;
    @SettingSection("inventory")
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @VersionCompatibility("1.9<*")
    @SpriteSlot(x = 5, y = 1)
    private final ConfigProperty<Boolean> oldSurvivalLayout;
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @VersionCompatibility("1.9<*")
    @SpriteSlot(x = 6, y = 1)
    private final ConfigProperty<Boolean> oldCreativeLayout;
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @VersionCompatibility("1.9<*")
    @SpriteSlot(x = 7, y = 1)
    private final ConfigProperty<Boolean> shouldRemoveRecipeBook;
    @SwitchWidget.SwitchSetting
    @VersionCompatibility("1.8.9")
    @SpriteSlot(y = 2)
    private final ConfigProperty<Boolean> potionFix;
    @SettingSection("animations.item")
    @PermissionRequired("animations")
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 1, y = 2)
    private final ConfigProperty<Boolean> oldSword;
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 2, y = 2)
    private final ConfigProperty<Boolean> oldItemPosture;
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 3, y = 2)
    private final ConfigProperty<Boolean> oldBow;
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 4, y = 2)
    private final ConfigProperty<Boolean> oldFood;
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 5, y = 2)
    private final ConfigProperty<Boolean> oldFishingRod;
    @PermissionRequired("animations")
    @SpriteSlot(x = 6, y = 2)
    private DefaultOldEquipConfig oldEquip;
    @SettingSection("animations.player")
    @VersionCompatibility("1.17<*")
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 7, y = 2)
    private final ConfigProperty<Boolean> oldHeadRotation;
    @VersionCompatibility("1.12<*")
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(y = 3)
    private final ConfigProperty<Boolean> oldBackwards;
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 1, y = 3)
    private final ConfigProperty<Boolean> oldSneaking;
    @SwitchWidget.SwitchSetting
    @PermissionRequired(value = "blockbuild", canForceEnable = true)
    @SpriteSlot(x = 2, y = 3)
    private final ConfigProperty<Boolean> oldBlockBuild;
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 3, y = 3)
    private final ConfigProperty<Boolean> oldHeart;
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 4, y = 3)
    private final ConfigProperty<Boolean> oldDamageColor;
    @SwitchWidget.SwitchSetting
    @PermissionRequired("animations")
    @SpriteSlot(x = 5, y = 3)
    private final ConfigProperty<Boolean> oldHitbox;
    
    public DefaultClassicPvPConfig() {
        this.oldRange = new ConfigProperty<Boolean>(false);
        this.oldSlowdown = new ConfigProperty<Boolean>(false);
        this.oldSurvivalLayout = new ConfigProperty<Boolean>(false);
        this.oldCreativeLayout = new ConfigProperty<Boolean>(false);
        this.shouldRemoveRecipeBook = new ConfigProperty<Boolean>(false);
        this.potionFix = new ConfigProperty<Boolean>(false);
        this.oldSword = new ConfigProperty<Boolean>(false);
        this.oldItemPosture = new ConfigProperty<Boolean>(false);
        this.oldBow = new ConfigProperty<Boolean>(false);
        this.oldFood = new ConfigProperty<Boolean>(false);
        this.oldFishingRod = new ConfigProperty<Boolean>(false);
        this.oldEquip = new DefaultOldEquipConfig();
        this.oldHeadRotation = new ConfigProperty<Boolean>(false);
        this.oldBackwards = new ConfigProperty<Boolean>(false);
        this.oldSneaking = new ConfigProperty<Boolean>(false);
        this.oldBlockBuild = new ConfigProperty<Boolean>(false);
        this.oldHeart = new ConfigProperty<Boolean>(false);
        this.oldDamageColor = new ConfigProperty<Boolean>(false);
        this.oldHitbox = new ConfigProperty<Boolean>(false);
    }
    
    @Override
    public ConfigProperty<Boolean> oldRange() {
        return this.oldRange;
    }
    
    @Override
    public ConfigProperty<Boolean> oldSlowdown() {
        return this.oldSlowdown;
    }
    
    @Override
    public ConfigProperty<Boolean> oldSurvivalLayout() {
        return this.oldSurvivalLayout;
    }
    
    @Override
    public ConfigProperty<Boolean> oldCreativeLayout() {
        return this.oldCreativeLayout;
    }
    
    @Override
    public ConfigProperty<Boolean> shouldRemoveRecipeBook() {
        return this.shouldRemoveRecipeBook;
    }
    
    @Override
    public ConfigProperty<Boolean> potionFix() {
        return this.potionFix;
    }
    
    @Override
    public ConfigProperty<Boolean> oldSword() {
        return this.oldSword;
    }
    
    @Override
    public ConfigProperty<Boolean> oldItemPosture() {
        return this.oldItemPosture;
    }
    
    @Override
    public ConfigProperty<Boolean> oldBow() {
        return this.oldBow;
    }
    
    @Override
    public ConfigProperty<Boolean> oldFood() {
        return this.oldFood;
    }
    
    @Override
    public ConfigProperty<Boolean> oldFishingRod() {
        return this.oldFishingRod;
    }
    
    @Override
    public OldEquipConfig oldEquip() {
        return this.oldEquip;
    }
    
    @Override
    public ConfigProperty<Boolean> oldHeadRotation() {
        return this.oldHeadRotation;
    }
    
    @Override
    public ConfigProperty<Boolean> oldBackwards() {
        return this.oldBackwards;
    }
    
    @Override
    public ConfigProperty<Boolean> oldSneaking() {
        return this.oldSneaking;
    }
    
    @Override
    public ConfigProperty<Boolean> oldBlockBuild() {
        return this.oldBlockBuild;
    }
    
    @Override
    public ConfigProperty<Boolean> oldHeart() {
        return this.oldHeart;
    }
    
    @Override
    public ConfigProperty<Boolean> oldDamageColor() {
        return this.oldDamageColor;
    }
    
    @Override
    public ConfigProperty<Boolean> oldHitbox() {
        return this.oldHitbox;
    }
}
