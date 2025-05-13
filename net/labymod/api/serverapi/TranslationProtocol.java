// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.serverapi;

import java.util.Iterator;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import java.util.HashSet;
import java.util.Set;
import net.labymod.serverapi.api.Protocol;
import net.labymod.serverapi.api.payload.PayloadChannelIdentifier;

public class TranslationProtocol
{
    private final PayloadChannelIdentifier identifier;
    private final Protocol protocol;
    private final Set<TranslationListener> listeners;
    
    public TranslationProtocol(final PayloadChannelIdentifier identifier, final Protocol protocol) {
        this.identifier = identifier;
        this.protocol = protocol;
        this.listeners = new HashSet<TranslationListener>();
    }
    
    @NotNull
    public Protocol targetProtocol() {
        return this.protocol;
    }
    
    @NotNull
    public PayloadChannelIdentifier identifier() {
        return this.identifier;
    }
    
    public void registerListener(final TranslationListener listener) {
        this.listeners.add(listener);
    }
    
    public boolean forEachListener(final Predicate<TranslationListener> predicate) {
        for (final TranslationListener listener : this.listeners) {
            if (predicate.test(listener)) {
                return true;
            }
        }
        return false;
    }
}
