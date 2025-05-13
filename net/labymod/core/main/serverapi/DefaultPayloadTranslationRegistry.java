// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.serverapi;

import net.labymod.api.Laby;
import java.util.Collections;
import java.util.HashSet;
import net.labymod.api.serverapi.TranslationProtocol;
import java.util.Set;
import net.labymod.api.serverapi.PayloadTranslationRegistry;

public class DefaultPayloadTranslationRegistry implements PayloadTranslationRegistry
{
    private final Set<TranslationProtocol> protocols;
    private final Set<TranslationProtocol> unmodifiableProtocols;
    
    public DefaultPayloadTranslationRegistry() {
        this.protocols = new HashSet<TranslationProtocol>();
        this.unmodifiableProtocols = Collections.unmodifiableSet((Set<? extends TranslationProtocol>)this.protocols);
    }
    
    @Override
    public void register(final TranslationProtocol protocol) {
        this.protocols.add(protocol);
        Laby.references().payloadRegistry().registerPayloadChannel(protocol.identifier());
    }
    
    @Override
    public void unregister(final TranslationProtocol protocol) {
        this.protocols.remove(protocol);
    }
    
    @Override
    public Set<TranslationProtocol> getProtocols() {
        return this.unmodifiableProtocols;
    }
}
