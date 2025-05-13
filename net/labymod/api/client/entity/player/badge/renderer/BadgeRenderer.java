// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.badge.renderer;

import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.matrix.Stack;

public abstract class BadgeRenderer
{
    private final String metadataIdentifier;
    private boolean render;
    
    protected BadgeRenderer() {
        this.render = true;
        this.metadataIdentifier = this.getClass().getName();
    }
    
    public abstract void render(final Stack p0, final float p1, final float p2, final NetworkPlayerInfo p3);
    
    public final boolean shouldRender(final NetworkPlayerInfo playerInfo, final boolean fromCache) {
        if (!this.render) {
            return false;
        }
        if (fromCache) {
            final Object o = playerInfo.metadata().get(this.metadataIdentifier);
            if (o instanceof Boolean) {
                return (boolean)o;
            }
        }
        final boolean visible = this.isVisible(playerInfo);
        playerInfo.metadata().set(this.metadataIdentifier, visible);
        return visible;
    }
    
    protected abstract boolean isVisible(final NetworkPlayerInfo p0);
    
    public int getSize() {
        return 9;
    }
    
    public final boolean beginRender(final Stack stack) {
        return this.render = this.begin(stack);
    }
    
    protected boolean begin(final Stack stack) {
        return true;
    }
    
    public final void endRender(final Stack stack) {
        if (this.render) {
            this.end(stack);
        }
    }
    
    protected void end(final Stack stack) {
    }
}
