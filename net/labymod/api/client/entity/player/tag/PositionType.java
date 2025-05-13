// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag;

import net.labymod.api.client.entity.player.tag.renderer.positions.LeftToNameRenderer;
import net.labymod.api.client.entity.player.tag.renderer.positions.RightToNameRenderer;
import net.labymod.api.client.entity.player.tag.renderer.positions.BelowNameRenderer;
import net.labymod.api.client.entity.player.tag.renderer.positions.AboveNameRenderer;
import net.labymod.api.client.entity.player.tag.renderer.PositionRenderer;

public enum PositionType
{
    ABOVE_NAME((PositionRenderer)new AboveNameRenderer(), 1), 
    BELOW_NAME((PositionRenderer)new BelowNameRenderer(), 0), 
    RIGHT_TO_NAME((PositionRenderer)new RightToNameRenderer(), 1), 
    LEFT_TO_NAME((PositionRenderer)new LeftToNameRenderer(), 1);
    
    private final PositionRenderer renderer;
    private final int priority;
    
    private PositionType(final PositionRenderer renderer, final int priority) {
        this.renderer = renderer;
        this.priority = priority;
    }
    
    public PositionRenderer renderer() {
        return this.renderer;
    }
    
    public int getPriority() {
        return this.priority;
    }
}
