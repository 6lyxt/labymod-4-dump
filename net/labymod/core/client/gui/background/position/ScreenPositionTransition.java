// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.background.position;

import java.util.Objects;
import net.labymod.api.client.gui.screen.NamedScreen;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.core.util.camera.spline.position.Location;
import org.jetbrains.annotations.NotNull;

public class ScreenPositionTransition extends AbstractPositionTransition
{
    @NotNull
    private final Object screen;
    
    public ScreenPositionTransition(@NotNull final Location position, @NotNull final CubicBezier curve, final long duration, @NotNull final Object screen) {
        super(position, curve, duration);
        this.screen = screen;
    }
    
    @NotNull
    public Object getScreen() {
        return this.screen;
    }
    
    @Override
    public boolean is(final Object value) {
        if (value instanceof NamedScreen && this.screen instanceof NamedScreen) {
            return (((NamedScreen)this.screen).isOptions() && ((NamedScreen)value).isOptions()) || this.screen.equals(value);
        }
        return this.screen.equals(value);
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        final ScreenPositionTransition that = (ScreenPositionTransition)o;
        return Objects.equals(this.screen, that.screen);
    }
    
    @Override
    public int hashCode() {
        return this.screen.hashCode();
    }
}
