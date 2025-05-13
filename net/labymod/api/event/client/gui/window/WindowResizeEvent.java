// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.window;

import net.labymod.api.Laby;
import net.labymod.api.event.Event;

public class WindowResizeEvent implements Event
{
    public WindowResizeEvent() {
        Laby.gfx().initializeCapabilities();
    }
}
