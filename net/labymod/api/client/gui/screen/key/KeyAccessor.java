// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.key;

import java.util.function.Consumer;
import java.util.function.Supplier;

public final class KeyAccessor
{
    private final Supplier<Key> keyGetter;
    private final Supplier<Key> defaultKeyGetter;
    private final Consumer<Key> keyUpdater;
    
    public KeyAccessor(final Supplier<Key> keyGetter, final Supplier<Key> defaultKeyGetter, final Consumer<Key> keyUpdater) {
        this.keyGetter = keyGetter;
        this.defaultKeyGetter = defaultKeyGetter;
        this.keyUpdater = keyUpdater;
    }
    
    public Key get() {
        return this.keyGetter.get();
    }
    
    public Key getDefault() {
        return this.defaultKeyGetter.get();
    }
    
    public void set(final Key key) {
        this.keyUpdater.accept(key);
    }
}
