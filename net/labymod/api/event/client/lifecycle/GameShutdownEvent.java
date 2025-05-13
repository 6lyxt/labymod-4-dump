// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.event.client.lifecycle;

import java.io.File;
import org.jetbrains.annotations.Nullable;
import net.labymod.api.event.LabyEvent;
import net.labymod.api.event.Event;

@LabyEvent(background = true)
public class GameShutdownEvent implements Event
{
    @Nullable
    private final Throwable cause;
    @Nullable
    private final File crashReport;
    
    public GameShutdownEvent(@Nullable final Throwable cause, @Nullable final File crashReport) {
        this.cause = cause;
        this.crashReport = crashReport;
    }
    
    public GameShutdownEvent() {
        this(null, null);
    }
    
    public boolean isCrash() {
        return this.cause != null;
    }
    
    @Nullable
    public Throwable getCause() {
        return this.cause;
    }
    
    @Nullable
    public File getCrashReportFile() {
        return this.crashReport;
    }
}
