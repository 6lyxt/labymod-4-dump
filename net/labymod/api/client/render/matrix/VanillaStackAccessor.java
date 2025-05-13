// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.matrix;

public interface VanillaStackAccessor
{
    default Stack stack() {
        return this.stack(null);
    }
    
    Stack stack(final Object p0);
}
