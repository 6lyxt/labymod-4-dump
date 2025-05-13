// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen;

import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.event.Event;

public class ActivityOpenEvent implements Event
{
    private final Activity activity;
    
    public ActivityOpenEvent(final Activity activity) {
        this.activity = activity;
    }
    
    public Activity activity() {
        return this.activity;
    }
}
