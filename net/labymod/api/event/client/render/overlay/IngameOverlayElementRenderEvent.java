// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.render.overlay;

import net.labymod.api.event.Phase;
import net.labymod.api.client.render.matrix.Stack;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.client.render.CancellableRenderEvent;

public class IngameOverlayElementRenderEvent extends CancellableRenderEvent
{
    private final OverlayElementType elementType;
    
    public IngameOverlayElementRenderEvent(@NotNull final OverlayElementType elementType, @NotNull final Stack stack, @NotNull final Phase phase) {
        super(stack, phase);
        this.elementType = elementType;
    }
    
    @NotNull
    public OverlayElementType elementType() {
        return this.elementType;
    }
    
    public enum OverlayElementType
    {
        CROSSHAIR, 
        SCOREBOARD, 
        OFFHAND_TEXTURE, 
        OFFHAND_ITEM, 
        BOSS_BAR, 
        POTION_EFFECTS, 
        ATTACK_INDICATOR, 
        TITLE, 
        ACTION_BAR;
        
        public boolean isOffhand() {
            return this == OverlayElementType.OFFHAND_TEXTURE || this == OverlayElementType.OFFHAND_ITEM;
        }
    }
}
