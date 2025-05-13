// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.background.position;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.core.util.camera.spline.position.Location;

public abstract class AbstractPositionTransition implements PositionTransition
{
    private final Location position;
    private final CubicBezier curve;
    private final long duration;
    
    protected AbstractPositionTransition(@NotNull final Location position, @NotNull final CubicBezier curve, final long duration) {
        this.position = position;
        this.curve = curve;
        this.duration = duration;
    }
    
    @NotNull
    @Override
    public Location position() {
        return this.position;
    }
    
    @NotNull
    @Override
    public CubicBezier curve() {
        return this.curve;
    }
    
    @Override
    public long getDuration() {
        return this.duration;
    }
}
