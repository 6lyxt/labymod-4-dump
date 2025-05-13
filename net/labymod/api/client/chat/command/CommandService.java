// 
// Decompiled by Procyon v0.6.0
// 

package net.labymod.api.client.chat.command;

import net.labymod.api.reference.annotation.Referenceable;

@Referenceable
public interface CommandService
{
     <T extends Command> T register(final T p0);
    
    void unregister(final Class<? extends Command> p0);
    
    boolean fireCommand(final String p0, final String[] p1);
    
    boolean fireCommand(final Class<? extends Command> p0, final String p1, final String[] p2);
    
    default void unregister(final Command command) {
        this.unregister(command.getClass());
    }
    
    default boolean fireCommand(final Command command, final String usedPrefix, final String[] arguments) {
        return this.fireCommand(command.getClass(), usedPrefix, arguments);
    }
}
