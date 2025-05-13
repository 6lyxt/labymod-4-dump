// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.overlay;

import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.client.render.RenderEvent;

public class IngameOverlayRenderEvent extends RenderEvent
{
    private final boolean guiHidden;
    
    public IngameOverlayRenderEvent(@NotNull final Stack stack, @NotNull final Phase phase, final boolean guiHidden) {
        super(stack, phase);
        this.guiHidden = guiHidden;
    }
    
    public boolean isGuiHidden() {
        return this.guiHidden;
    }
}
