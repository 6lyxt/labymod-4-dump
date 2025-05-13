// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.loader.property.ConfigProperty;

public interface MenuBlurConfig
{
    ConfigProperty<Boolean> enabled();
    
    ConfigProperty<Float> strength();
    
    ConfigProperty<Boolean> pauseMenu();
    
    ConfigProperty<Boolean> inventories();
    
    ConfigProperty<Boolean> emoteWheel();
    
    ConfigProperty<Boolean> sprayWheel();
    
    ConfigProperty<Boolean> interactionWheel();
    
    default boolean isMenuBlurEnabled(final ScreenType screenType) {
        if (!this.enabled().get()) {
            return false;
        }
        final ConfigProperty<Boolean> property = switch (screenType.ordinal()) {
            default -> throw new MatchException(null, null);
            case 0 -> this.pauseMenu();
            case 1 -> this.inventories();
            case 2 -> this.emoteWheel();
            case 3 -> this.interactionWheel();
            case 4 -> this.sprayWheel();
        };
        return property.get();
    }
    
    public enum ScreenType
    {
        PAUSE_MENU, 
        INVENTORIES, 
        EMOTE_WHEEL, 
        INTERACTION_WHEEL, 
        SPRAY_WHEEL;
    }
}
