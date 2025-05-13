// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.configuration.labymod.main.laby;

import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.util.MethodOrder;
import net.labymod.api.Laby;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.DamageConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.ZoomConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.SprayConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.EmotesConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.ChatInputConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultHitboxConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultDamageConfig;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.loader.annotation.SearchTag;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultSprayConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultEmotesConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultCosmeticsConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultChatInputConfig;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultAdvancedChatConfig;
import net.labymod.api.configuration.loader.annotation.PermissionRequired;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultZoomConfig;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.settings.annotation.SettingSection;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.core.configuration.labymod.main.laby.ingame.DefaultMotionBlurConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.configuration.loader.annotation.VersionCompatibility;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.IngameConfig;
import net.labymod.api.configuration.loader.Config;

public class DefaultIngameConfig extends Config implements IngameConfig
{
    @VersionCompatibility("1.8.9")
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> fastWorldLoading;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> fastLanguageReload;
    @IntroducedIn("4.1.0")
    @SettingSection("fieldOfView")
    @SpriteSlot(x = 0, y = 4, page = 1)
    private final DefaultMotionBlurConfig motionBlur;
    @SpriteSlot(x = 7, y = 4)
    private DefaultZoomConfig zoom;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 6, y = 4)
    private final ConfigProperty<Boolean> disableSpeedFOV;
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> noViewBobbing;
    @VersionCompatibility("1.8.9<1.11")
    @SwitchWidget.SwitchSetting
    @PermissionRequired(value = "crosshair_sync", canForceEnable = true)
    @SpriteSlot(x = 7, y = 2, page = 1)
    private final ConfigProperty<Boolean> mouseDelayFix;
    @SettingSection("hud")
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 2, page = 1)
    private final ConfigProperty<Boolean> hudWidgets;
    @SpriteSlot(x = 3, page = 1)
    private DefaultAdvancedChatConfig advancedChat;
    @SpriteSlot(x = 4, y = 4, page = 1)
    private DefaultChatInputConfig chatInput;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 4, page = 1)
    private final ConfigProperty<Boolean> inventoryBanner;
    @SettingSection("player")
    @SpriteSlot(x = 1, y = 4)
    private DefaultCosmeticsConfig cosmetics;
    @SpriteSlot(x = 4, y = 4)
    private DefaultEmotesConfig emotes;
    @IntroducedIn("4.2.0")
    @SpriteSlot(x = 0, y = 6, page = 1)
    private DefaultSprayConfig spray;
    @IntroducedIn("4.2.0")
    @SpriteSlot(x = 1, y = 6, page = 1)
    @SwitchWidget.SwitchSetting
    private final ConfigProperty<Boolean> lootBoxes;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 5, page = 1)
    private final ConfigProperty<Boolean> showCapeParticles;
    @VersionCompatibility("1.11<*")
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 1, y = 3, page = 1)
    private final ConfigProperty<Boolean> hideTotemInOffHand;
    @SettingSection("nametag")
    @SwitchWidget.SwitchSetting(hotkey = true)
    @SearchTag({ "nick hider" })
    @SpriteSlot(x = 6, y = 5, page = 1)
    private final ConfigProperty<Boolean> hideNametag;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 6, y = 3)
    @SettingRequires(value = "hideNametag", invert = true)
    private final ConfigProperty<Boolean> showMyName;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 7, page = 1)
    private final ConfigProperty<Boolean> showCountryFlagBesideName;
    @SwitchWidget.SwitchSetting
    @SpriteSlot(x = 4, page = 1)
    private final ConfigProperty<Boolean> showUserIndicatorBesideName;
    @SwitchWidget.SwitchSetting
    @VersionCompatibility("1.8.9")
    @IntroducedIn("4.1.11")
    private final ConfigProperty<Boolean> translucentSkins;
    @SettingSection("entity")
    @SpriteSlot(y = 1, page = 1)
    private DefaultDamageConfig damage;
    @SpriteSlot(x = 3, y = 1, page = 1)
    private DefaultHitboxConfig hitbox;
    
    public DefaultIngameConfig() {
        this.fastWorldLoading = new ConfigProperty<Boolean>(true);
        this.fastLanguageReload = new ConfigProperty<Boolean>(true);
        this.motionBlur = new DefaultMotionBlurConfig();
        this.zoom = new DefaultZoomConfig();
        this.disableSpeedFOV = new ConfigProperty<Boolean>(false);
        this.noViewBobbing = new ConfigProperty<Boolean>(false);
        this.mouseDelayFix = new ConfigProperty<Boolean>(true);
        this.hudWidgets = new ConfigProperty<Boolean>(true);
        this.advancedChat = new DefaultAdvancedChatConfig();
        this.chatInput = new DefaultChatInputConfig();
        this.inventoryBanner = new ConfigProperty<Boolean>(true);
        this.cosmetics = new DefaultCosmeticsConfig();
        this.emotes = new DefaultEmotesConfig();
        this.spray = new DefaultSprayConfig();
        this.lootBoxes = new ConfigProperty<Boolean>(true);
        this.showCapeParticles = new ConfigProperty<Boolean>(true);
        this.hideTotemInOffHand = new ConfigProperty<Boolean>(false);
        this.hideNametag = new ConfigProperty<Boolean>(false);
        this.showMyName = new ConfigProperty<Boolean>(true);
        this.showCountryFlagBesideName = new ConfigProperty<Boolean>(false);
        this.showUserIndicatorBesideName = new ConfigProperty<Boolean>(true);
        this.translucentSkins = new ConfigProperty<Boolean>(true);
        this.damage = new DefaultDamageConfig();
        this.hitbox = new DefaultHitboxConfig();
    }
    
    @Override
    public ConfigProperty<Boolean> mouseDelayFix() {
        return this.mouseDelayFix;
    }
    
    @Override
    public ConfigProperty<Boolean> hudWidgets() {
        return this.hudWidgets;
    }
    
    @Override
    public AdvancedChatConfig advancedChat() {
        return this.advancedChat;
    }
    
    @Override
    public ChatInputConfig chatInput() {
        return this.chatInput;
    }
    
    @Override
    public ConfigProperty<Boolean> inventoryBanner() {
        return this.inventoryBanner;
    }
    
    @Override
    public CosmeticsConfig cosmetics() {
        return this.cosmetics;
    }
    
    @Override
    public EmotesConfig emotes() {
        return this.emotes;
    }
    
    @Override
    public SprayConfig spray() {
        return this.spray;
    }
    
    @Override
    public ConfigProperty<Boolean> lootBoxes() {
        return this.lootBoxes;
    }
    
    @Override
    public ConfigProperty<Boolean> showCapeParticles() {
        return this.showCapeParticles;
    }
    
    @Override
    public ConfigProperty<Boolean> hideTotemInOffHand() {
        return this.hideTotemInOffHand;
    }
    
    @Override
    public ConfigProperty<Boolean> hideNametag() {
        return this.hideNametag;
    }
    
    @Override
    public ConfigProperty<Boolean> showMyName() {
        return this.showMyName;
    }
    
    @Override
    public ConfigProperty<Boolean> showCountryFlagBesideName() {
        return this.showCountryFlagBesideName;
    }
    
    @Override
    public ConfigProperty<Boolean> showUserIndicatorBesideName() {
        return this.showUserIndicatorBesideName;
    }
    
    @Override
    public ConfigProperty<Boolean> disableSpeedFOV() {
        return this.disableSpeedFOV;
    }
    
    @Override
    public ConfigProperty<Boolean> fastWorldLoading() {
        return this.fastWorldLoading;
    }
    
    @Override
    public ConfigProperty<Boolean> fastLanguageReload() {
        return this.fastLanguageReload;
    }
    
    @Override
    public ConfigProperty<Boolean> noViewBobbing() {
        return this.noViewBobbing;
    }
    
    @Override
    public ZoomConfig zoom() {
        return this.zoom;
    }
    
    @Override
    public DamageConfig damage() {
        return this.damage;
    }
    
    @Override
    public HitboxConfig hitbox() {
        return this.hitbox;
    }
    
    @Override
    public ConfigProperty<Boolean> translucentSkins() {
        return this.translucentSkins;
    }
    
    @Override
    public MotionBlurConfig motionBlur() {
        return this.motionBlur;
    }
    
    @MethodOrder(before = "fastWorldLoading")
    @SpriteSlot(x = 1, y = 1)
    @ButtonWidget.ButtonSetting
    public void performance() {
        final Setting fluxSetting = Laby.labyAPI().coreSettingRegistry().getById("flux");
        if (fluxSetting != null) {
            Laby.labyAPI().showSetting(fluxSetting);
        }
    }
    
    @Override
    public int getConfigVersion() {
        return 2;
    }
}
