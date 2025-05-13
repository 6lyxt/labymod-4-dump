// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby;

import net.labymod.api.configuration.labymod.main.laby.ingame.MenuBlurConfig;
import net.labymod.api.configuration.labymod.main.laby.appearance.TitleScreenConfig;
import net.labymod.api.configuration.labymod.main.laby.appearance.NavigationConfig;
import net.labymod.api.configuration.labymod.model.HighQuality;
import net.labymod.api.configuration.labymod.model.FadeOutAnimationType;
import net.labymod.api.configuration.labymod.main.laby.appearance.DynamicBackgroundConfig;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface AppearanceConfig extends ConfigAccessor
{
    ConfigProperty<String> theme();
    
    DynamicBackgroundConfig dynamicBackground();
    
    ConfigProperty<Boolean> darkLoadingScreen();
    
    ConfigProperty<FadeOutAnimationType> fadeOutAnimation();
    
    ConfigProperty<Boolean> hideMenuBackground();
    
    ConfigProperty<Boolean> cleanWindowTitle();
    
    ConfigProperty<Boolean> fixedTooltips();
    
    ConfigProperty<HighQuality> blurQuality();
    
    NavigationConfig navigation();
    
    TitleScreenConfig titleScreen();
    
    MenuBlurConfig menuBlur();
    
    ConfigProperty<Boolean> replaceSkinCustomization();
}
