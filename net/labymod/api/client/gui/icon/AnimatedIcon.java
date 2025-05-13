// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.icon;

import org.jetbrains.annotations.Nullable;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import java.util.function.Supplier;
import java.util.Objects;
import net.labymod.api.client.resources.AnimatedResourceLocation;

public class AnimatedIcon extends Icon
{
    private final AnimatedResourceLocation location;
    
    protected AnimatedIcon(final AnimatedResourceLocation location) {
        Objects.requireNonNull(location);
        super((Supplier<ResourceLocation>)location::getCurrentResourceLocation, null);
        this.location = location;
    }
    
    public static AnimatedIcon of(final AnimatedResourceLocation location) {
        return new AnimatedIcon(location);
    }
    
    @Override
    public void render(final Stack stack, final float x, final float y, final float width, final float height, final boolean hover, final int color, final Rectangle stencil) {
        this.location.update();
        super.render(stack, x, y, width, height, hover, color, stencil);
    }
    
    @Nullable
    @Override
    public ResourceLocation getResourceLocation() {
        return this.location.getCurrentResourceLocation();
    }
    
    @Override
    public Icon resourceLocation(final ResourceLocation resourceLocation) {
        return super.resourceLocation(resourceLocation);
    }
}
