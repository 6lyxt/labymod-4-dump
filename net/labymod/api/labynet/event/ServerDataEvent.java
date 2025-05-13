// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.labynet.event;

import org.jetbrains.annotations.NotNull;
import net.labymod.api.event.Event;

public class ServerDataEvent implements Event
{
    private final String type;
    private final String[] arguments;
    
    public ServerDataEvent(@NotNull final String type, @NotNull final String[] arguments) {
        this.type = type;
        this.arguments = arguments;
    }
    
    @NotNull
    public String type() {
        return this.type;
    }
    
    @NotNull
    public String[] arguments() {
        return this.arguments;
    }
}
