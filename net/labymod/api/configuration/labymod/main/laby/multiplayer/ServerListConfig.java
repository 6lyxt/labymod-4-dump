// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.multiplayer;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface ServerListConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> draggableEntries();
    
    ConfigProperty<Boolean> liveServerList();
    
    ConfigProperty<Integer> cooldown();
    
    ConfigProperty<Boolean> richServerList();
    
    ConfigProperty<Boolean> niceNames();
    
    ConfigProperty<Boolean> highQualityTextures();
    
    ConfigProperty<Boolean> quickJoinButtons();
    
    ConfigProperty<Boolean> friendsInServerList();
}
