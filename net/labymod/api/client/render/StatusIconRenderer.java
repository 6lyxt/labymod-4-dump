// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Experimental
@Referenceable
public interface StatusIconRenderer
{
    StatusIconRenderer pos(final float p0, final float p1);
    
    StatusIconRenderer statusIcon(final StatusIcon... p0);
    
    StatusIconRenderer amount(final int p0);
    
    void render(final Stack p0);
    
    float getWidth(final int p0, final float p1);
}
