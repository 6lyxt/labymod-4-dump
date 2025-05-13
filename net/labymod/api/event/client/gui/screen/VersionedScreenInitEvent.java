// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen;

import net.labymod.api.event.Event;

public class VersionedScreenInitEvent implements Event
{
    private final Object versionedScreen;
    
    public VersionedScreenInitEvent(final Object versionedScreen) {
        this.versionedScreen = versionedScreen;
    }
    
    public Object getVersionedScreen() {
        return this.versionedScreen;
    }
}
