// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.render.matrix;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface StackProviderFactory
{
    Stack create();
    
    Stack create(final Object p0);
    
    Stack create(final Object p0, final Object p1);
}
