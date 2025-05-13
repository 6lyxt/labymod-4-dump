// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.client.entity.player.tag;

import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.service.DefaultRegistry;

public class NameTagRegistry extends DefaultRegistry<TagRenderer>
{
    private final PositionType positionType;
    
    public NameTagRegistry(final PositionType positionType) {
        this.positionType = positionType;
    }
    
    public PositionType getPositionType() {
        return this.positionType;
    }
}
