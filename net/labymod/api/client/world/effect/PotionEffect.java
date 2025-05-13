// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.world.effect;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.gui.icon.Icon;

public interface PotionEffect
{
    int getDuration();
    
    int getAmplifier();
    
    String getTranslationKey();
    
    @Deprecated
    Icon getIcon();
    
    void renderIcon(final Stack p0, final int p1, final int p2, final int p3, final int p4);
    
    boolean hasMaxDuration();
    
    default boolean isInfiniteDuration() {
        return false;
    }
}
