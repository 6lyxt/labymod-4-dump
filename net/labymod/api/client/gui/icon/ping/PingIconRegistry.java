// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.icon.ping;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface PingIconRegistry
{
    PingIconRegistry createMultiResourceRenderer(final Stack p0);
    
    PingIconRegistry render(final PingType p0, final int p1, final float p2, final float p3);
    
    void upload();
    
    Icon icon(final PingType p0, final int p1);
    
    default Icon icon(final PingType type) {
        return this.icon(type, 0);
    }
}
