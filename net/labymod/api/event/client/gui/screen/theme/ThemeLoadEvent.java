// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen.theme;

import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.event.Event;

public class ThemeLoadEvent implements Event
{
    private final Phase phase;
    private final Theme theme;
    
    public ThemeLoadEvent(final Phase phase, final Theme theme) {
        this.phase = phase;
        this.theme = theme;
    }
    
    public Phase phase() {
        return this.phase;
    }
    
    public Theme theme() {
        return this.theme;
    }
    
    public enum Phase
    {
        PRE, 
        POST;
    }
}
