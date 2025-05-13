// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.ingame.zoom;

import net.labymod.api.client.zoom.ZoomTransitionType;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface ZoomTransitionConfig extends ConfigAccessor
{
    ConfigProperty<Boolean> enabled();
    
    ConfigProperty<ZoomTransitionType> zoomInType();
    
    ConfigProperty<Long> zoomInDuration();
    
    ConfigProperty<ZoomTransitionType> zoomOutType();
    
    ConfigProperty<Long> zoomOutDuration();
}
