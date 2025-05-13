// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.activity.overlay;

import java.util.function.UnaryOperator;
import java.util.Optional;
import net.labymod.api.addon.LoadedAddon;
import net.labymod.api.client.gui.screen.activity.types.IngameOverlayActivity;
import java.util.List;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface IngameActivityOverlay
{
    List<IngameOverlayActivity> getActivities();
    
    List<IngameOverlayActivity> getActivities(final LoadedAddon p0);
    
    Optional<IngameOverlayActivity> getActivity(final Class<? extends IngameOverlayActivity> p0);
    
    void registerActivity(final IngameOverlayActivity p0);
    
    void unregisterActivity(final IngameOverlayActivity p0);
    
    void unregisterActivities(final LoadedAddon p0);
    
    void replaceActivities(final UnaryOperator<IngameOverlayActivity> p0);
}
