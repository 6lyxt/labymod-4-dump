// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby;

import net.labymod.api.configuration.labymod.main.laby.other.WindowConfig;
import java.util.List;
import java.util.Set;
import net.labymod.api.configuration.labymod.main.laby.other.AdvancedConfig;
import java.util.Map;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.labymod.main.laby.other.DiscordConfig;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface OtherConfig extends ConfigAccessor
{
    DiscordConfig discord();
    
    ConfigProperty<Boolean> anonymousStatistics();
    
    ConfigProperty<Map<String, Boolean>> outOfMemoryWarnings();
    
    AdvancedConfig advanced();
    
    ConfigProperty<Set<String>> conversionAskedNamespaces();
    
    ConfigProperty<Integer> lastWidth();
    
    ConfigProperty<Integer> lastHeight();
    
    ConfigProperty<Map<String, Boolean>> serverSwitchChoices();
    
    ConfigProperty<List<String>> ignoredAddonRecommendations();
    
    ConfigProperty<String> preferredCurrency();
    
    WindowConfig window();
}
