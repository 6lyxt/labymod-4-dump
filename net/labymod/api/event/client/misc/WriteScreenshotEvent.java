// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.misc;

import java.io.File;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class WriteScreenshotEvent extends DefaultCancellable implements Event
{
    private final Phase phase;
    private byte[] image;
    private File destination;
    
    public WriteScreenshotEvent(final Phase phase, final byte[] image, final File destination) {
        this.phase = phase;
        this.image = image;
        this.destination = destination;
    }
    
    public Phase getPhase() {
        return this.phase;
    }
    
    public byte[] getImage() {
        return this.image;
    }
    
    public File getDestination() {
        return this.destination;
    }
    
    public void setDestination(final File destination) {
        if (this.phase == Phase.POST) {
            throw new UnsupportedOperationException("Cannot change the destination after the screenshot has been written. Use PRE phase to change the destination.");
        }
        this.destination = destination;
    }
    
    public void setImage(final byte[] image) {
        if (this.phase == Phase.POST) {
            throw new UnsupportedOperationException("Cannot change the image after the screenshot has been written. Use PRE phase to change the image.");
        }
        this.image = image;
    }
}
