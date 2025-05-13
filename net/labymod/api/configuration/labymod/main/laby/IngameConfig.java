// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby;

import net.labymod.api.configuration.labymod.main.laby.ingame.MotionBlurConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.HitboxConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.DamageConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.ZoomConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.SprayConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.EmotesConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.ChatInputConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.AdvancedChatConfig;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface IngameConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> mouseDelayFix();
    
    ConfigProperty<Boolean> hudWidgets();
    
    AdvancedChatConfig advancedChat();
    
    ChatInputConfig chatInput();
    
    ConfigProperty<Boolean> inventoryBanner();
    
    CosmeticsConfig cosmetics();
    
    EmotesConfig emotes();
    
    SprayConfig spray();
    
    ConfigProperty<Boolean> lootBoxes();
    
    ConfigProperty<Boolean> showCapeParticles();
    
    ConfigProperty<Boolean> hideTotemInOffHand();
    
    ConfigProperty<Boolean> hideNametag();
    
    ConfigProperty<Boolean> showMyName();
    
    ConfigProperty<Boolean> showCountryFlagBesideName();
    
    ConfigProperty<Boolean> showUserIndicatorBesideName();
    
    ConfigProperty<Boolean> disableSpeedFOV();
    
    ConfigProperty<Boolean> fastWorldLoading();
    
    ConfigProperty<Boolean> fastLanguageReload();
    
    ConfigProperty<Boolean> noViewBobbing();
    
    ZoomConfig zoom();
    
    DamageConfig damage();
    
    HitboxConfig hitbox();
    
    ConfigProperty<Boolean> translucentSkins();
    
    MotionBlurConfig motionBlur();
}
