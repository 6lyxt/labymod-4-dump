// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.ingame;

import java.util.List;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface ChatInputConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> autoText();
    
    ConfigProperty<Boolean> nameHistory();
    
    ConfigProperty<Boolean> chatSymbols();
    
    ConfigProperty<List<String>> favoriteSymbols();
}
