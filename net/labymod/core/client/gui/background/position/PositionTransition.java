// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.background.position;

import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import org.jetbrains.annotations.NotNull;
import net.labymod.core.util.camera.spline.position.Location;

public interface PositionTransition
{
    @NotNull
    Location position();
    
    @NotNull
    CubicBezier curve();
    
    long getDuration();
    
    boolean is(final Object p0);
}
