// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.appearance;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface TitleScreenConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> custom();
    
    ConfigProperty<Boolean> socialMediaLinks();
    
    ConfigProperty<Boolean> quickPlay();
}
