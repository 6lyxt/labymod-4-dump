// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.user.shop.CloakPriority;
import net.labymod.api.user.shop.RenderMode;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface CosmeticsConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> renderCosmetics();
    
    ConfigProperty<RenderMode> renderMode();
    
    ConfigProperty<CloakPriority> cloakPriority();
    
    ConfigProperty<Boolean> hideCosmeticsInDistance();
    
    ConfigProperty<Integer> hideAfterBlocks();
    
    ConfigProperty<Integer> maxCosmeticsPerPlayer();
    
    ConfigProperty<Boolean> onlyFriends();
    
    ConfigProperty<Boolean> walkingPets();
}
