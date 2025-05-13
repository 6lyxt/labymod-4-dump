// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.badge;

import net.labymod.api.util.CollectionHelper;
import net.labymod.api.client.entity.player.badge.renderer.positions.OverwritePingRenderer;
import net.labymod.api.client.entity.player.badge.renderer.stack.HorizontalStackRenderer;
import net.labymod.api.client.entity.player.badge.renderer.PositionRenderer;
import java.util.Set;

public enum PositionType
{
    OVERWRITE_PING((PositionRenderer)new OverwritePingRenderer()), 
    RIGHT_TO_NAME((PositionRenderer)new HorizontalStackRenderer(false)), 
    LEFT_TO_NAME((PositionRenderer)new HorizontalStackRenderer(false));
    
    public static final Set<PositionType> VALUES;
    private final PositionRenderer renderer;
    
    private PositionType(final PositionRenderer renderer) {
        this.renderer = renderer;
    }
    
    public PositionRenderer renderer() {
        return this.renderer;
    }
    
    public boolean isExpanding() {
        return this.renderer instanceof HorizontalStackRenderer;
    }
    
    static {
        VALUES = CollectionHelper.asUnmodifiableSet(values());
    }
}
