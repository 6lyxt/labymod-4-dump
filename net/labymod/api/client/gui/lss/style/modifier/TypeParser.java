// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface TypeParser
{
     <T> void register(final Parser<T> p0, final Class<T>... p1);
    
    Object parseValue(final Class<?> p0, final String p1) throws Exception;
    
    public interface Parser<T>
    {
        T parseValue(final Class<T> p0, final String p1) throws Exception;
    }
}
