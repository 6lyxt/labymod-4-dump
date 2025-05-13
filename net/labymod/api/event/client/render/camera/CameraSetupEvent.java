// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.camera;

import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.render.RenderEvent;

public class CameraSetupEvent extends RenderEvent
{
    public CameraSetupEvent(@NotNull final Stack stack, @NotNull final Phase phase) {
        super(stack, phase);
    }
}
