// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.gui.screen;

import java.nio.file.Path;
import java.util.List;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.event.Event;
import net.labymod.api.event.DefaultCancellable;

public class FileDroppedEvent extends DefaultCancellable implements Event
{
    private final MutableMouse mouse;
    private final List<Path> paths;
    
    public FileDroppedEvent(final MutableMouse mouse, final List<Path> paths) {
        this.mouse = mouse;
        this.paths = paths;
    }
    
    public MutableMouse mouse() {
        return this.mouse;
    }
    
    public List<Path> paths() {
        return this.paths;
    }
}
