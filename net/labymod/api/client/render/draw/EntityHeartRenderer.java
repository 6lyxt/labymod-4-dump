// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.draw;

import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.render.matrix.Stack;

public interface EntityHeartRenderer extends HeartRenderer
{
    void renderHealthBar(final Stack p0, final float p1, final float p2, final int p3);
    
    void updateHealth(final int p0);
    
    void updateHealth(final float p0);
    
    void updateHealth(final LivingEntity p0);
    
    int getWidth(final int p0);
}
