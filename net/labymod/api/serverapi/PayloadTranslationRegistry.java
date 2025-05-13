// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.serverapi;

import java.util.Set;

public interface PayloadTranslationRegistry
{
    void register(final TranslationProtocol p0);
    
    void unregister(final TranslationProtocol p0);
    
    Set<TranslationProtocol> getProtocols();
}
