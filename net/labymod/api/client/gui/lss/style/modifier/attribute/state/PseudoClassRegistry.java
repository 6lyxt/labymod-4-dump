// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.lss.style.modifier.attribute.state;

import net.labymod.api.client.gui.lss.style.function.Element;
import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface PseudoClassRegistry
{
    void registerFactory(final String p0, final PseudoClassFactory p1);
    
    PseudoClass parse(final String p0);
    
    PseudoClass parse(final Element p0);
}
