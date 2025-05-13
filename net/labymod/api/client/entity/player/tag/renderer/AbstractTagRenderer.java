// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag.renderer;

import org.jetbrains.annotations.MustBeInvokedByOverriders;
import net.labymod.api.client.entity.Entity;

public abstract class AbstractTagRenderer implements TagRenderer
{
    protected Entity entity;
    
    @MustBeInvokedByOverriders
    @Override
    public void begin(final Entity entity) {
        this.entity = entity;
    }
}
