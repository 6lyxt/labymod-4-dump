// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.function;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface FunctionRegistry
{
    void registerFunction(final String p0, final Class<?>... p1);
    
    boolean isFunctionRegistered(final String p0);
    
    Class<?>[] getParameterTypes(final String p0, final int p1);
    
    int[] getAllowedParameterCounts(final String p0);
}
