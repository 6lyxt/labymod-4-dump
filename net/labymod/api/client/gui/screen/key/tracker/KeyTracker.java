// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.gui.screen.key.tracker;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import net.labymod.api.client.gui.screen.key.Key;

public abstract class KeyTracker
{
    private final Key key;
    
    protected KeyTracker(@NotNull final Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        this.key = key;
    }
    
    @NotNull
    public Key key() {
        return this.key;
    }
    
    public abstract int getClicksPerSecond();
    
    public abstract void press();
    
    public abstract void update();
}
