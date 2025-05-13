// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.configuration.labymod.main.laby.ingame;

import net.labymod.api.configuration.labymod.main.laby.ingame.zoom.ZoomTransitionConfig;
import net.labymod.api.configuration.labymod.main.laby.ingame.zoom.ZoomCinematicConfig;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.loader.ConfigAccessor;

public interface ZoomConfig extends ConfigAccessor
{
    ConfigProperty<Key> zoomKey();
    
    ConfigProperty<Boolean> zoomHold();
    
    ZoomCinematicConfig zoomCinematic();
    
    ZoomTransitionConfig zoomTransition();
    
    ConfigProperty<Boolean> shouldRenderFirstPersonHand();
    
    ConfigProperty<Boolean> scrollToZoom();
    
    ConfigProperty<Double> zoomDistance();
}
