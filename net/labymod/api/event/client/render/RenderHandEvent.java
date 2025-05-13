// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render;

import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;

public class RenderHandEvent extends CancellableRenderEvent
{
    public RenderHandEvent(@NotNull final Stack stack, @NotNull final Phase phase) {
        super(stack, phase);
    }
}
