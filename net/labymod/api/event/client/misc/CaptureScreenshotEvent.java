// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.misc;

import java.io.File;
import net.labymod.api.event.Event;

public class CaptureScreenshotEvent implements Event
{
    private final File destination;
    
    public CaptureScreenshotEvent(final File destination) {
        this.destination = destination;
    }
    
    public File getDestination() {
        return this.destination;
    }
}
