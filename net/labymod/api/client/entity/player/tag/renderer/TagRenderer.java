// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.entity.player.tag.renderer;

import net.labymod.api.client.render.matrix.Stack;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import net.labymod.api.client.entity.Entity;

public interface TagRenderer
{
    @MustBeInvokedByOverriders
    void begin(final Entity p0);
    
    void render(final Stack p0, final Entity p1);
    
    boolean isVisible();
    
    float getWidth();
    
    default boolean isDiscrete(final Entity livingEntity) {
        return livingEntity.isCrouching();
    }
    
    float getHeight();
    
    float getScale();
    
    default boolean isOnlyVisibleOnMainTag() {
        return false;
    }
}
