// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen.theme;

import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class ThemeUnregisterEvent extends DefaultCancellable implements Event
{
    private final Theme theme;
    
    public ThemeUnregisterEvent(final Theme theme) {
        this.theme = theme;
    }
    
    public Theme theme() {
        return this.theme;
    }
}
