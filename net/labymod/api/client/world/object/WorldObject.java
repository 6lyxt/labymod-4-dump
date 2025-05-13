// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.object;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.world.object.fog.FogSettings;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.util.math.vector.DoubleVector3;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.util.math.vector.FloatVector3;

public interface WorldObject
{
    @Deprecated(forRemoval = true, since = "4.2.53")
    @NotNull
    default FloatVector3 previousLocation() {
        return this.location();
    }
    
    @Deprecated(forRemoval = true, since = "4.2.53")
    @NotNull
    default FloatVector3 location() {
        return new FloatVector3(this.position());
    }
    
    @NotNull
    default DoubleVector3 previousPosition() {
        return this.position();
    }
    
    @NotNull
    DoubleVector3 position();
    
    @Nullable
    Widget createWidget();
    
    default boolean shouldRender() {
        return true;
    }
    
    default boolean shouldRenderInOverlay() {
        return true;
    }
    
    default boolean shouldRemove() {
        return false;
    }
    
    default boolean isSeeThrough() {
        return true;
    }
    
    @NotNull
    default FogSettings fogSettings() {
        return FogSettings.NO_FOG;
    }
    
    void renderInWorld(@NotNull final MinecraftCamera p0, @NotNull final Stack p1, final double p2, final double p3, final double p4, final float p5, final boolean p6);
    
    default void rotateHorizontally(@NotNull final MinecraftCamera cam, @NotNull final Stack stack) {
        stack.rotate(cam.getYaw(), 0.0f, 1.0f, 0.0f);
    }
    
    default void rotateVertically(@NotNull final MinecraftCamera cam, @NotNull final Stack stack) {
        stack.rotate(-cam.getPitch(), 1.0f, 0.0f, 0.0f);
    }
    
    default void onRemove() {
    }
}
