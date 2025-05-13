// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.gui.background.position;

import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.core.util.camera.spline.position.Location;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.resources.ResourceLocation;

public class ResourceLocationPositionTransition extends AbstractPositionTransition
{
    @NotNull
    private final ResourceLocation location;
    
    public ResourceLocationPositionTransition(@NotNull final Location position, @NotNull final CubicBezier curve, final long duration, @NotNull final ResourceLocation location) {
        super(position, curve, duration);
        this.location = location;
    }
    
    @Override
    public boolean is(final Object value) {
        if (value instanceof final ResourceLocation resourceLocation) {
            return resourceLocation.equals(this.location);
        }
        return false;
    }
}
