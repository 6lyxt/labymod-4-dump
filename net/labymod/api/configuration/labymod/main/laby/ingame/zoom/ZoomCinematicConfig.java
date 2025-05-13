// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.ingame.zoom;

import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface ZoomCinematicConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> enabled();
    
    ConfigProperty<Boolean> disableAfterTransition();
}
