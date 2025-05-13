// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.world;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Event;

public class RenderWorldEvent implements Event
{
    private final Stack stack;
    private final MinecraftCamera camera;
    private final float partialTicks;
    
    public RenderWorldEvent(@NotNull final Stack stack, @NotNull final MinecraftCamera camera, final float partialTicks) {
        this.stack = stack;
        this.camera = camera;
        this.partialTicks = partialTicks;
    }
    
    @NotNull
    public Stack stack() {
        return this.stack;
    }
    
    @NotNull
    public MinecraftCamera camera() {
        return this.camera;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
    
    @Deprecated
    public float delta() {
        return this.getPartialTicks();
    }
}
