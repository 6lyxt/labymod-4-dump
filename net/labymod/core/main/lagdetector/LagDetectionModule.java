// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.core.main.lagdetector;

import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.client.gui.screen.key.Key;

public abstract class LagDetectionModule
{
    private final String name;
    
    public LagDetectionModule(final String name) {
        this.name = name;
    }
    
    public abstract void onUpdate();
    
    protected void onKey(final Key key, final KeyEvent.State state) {
    }
    
    public String getName() {
        return this.name;
    }
}
